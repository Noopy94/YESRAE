package com.ssafy.yesrae.domain.notification.controller;

import com.ssafy.yesrae.common.model.CommonResponse;
import com.ssafy.yesrae.domain.notification.dto.request.NotificationRegistPostReq;
import com.ssafy.yesrae.domain.notification.dto.response.NotificationFindRes;
import com.ssafy.yesrae.domain.notification.service.NotificationService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notification")
public class NotificationController {

    private static final String SUCCESS = "success";

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * 알림 전송 API
     *
     * @param notificationRegistPostReq
     */
    @PostMapping
    public CommonResponse<?> RegistNotification(
        @RequestBody NotificationRegistPostReq notificationRegistPostReq) {

        log.info("NotificationController_regist_start: " + notificationRegistPostReq);

        notificationService.registNotification(notificationRegistPostReq);

        log.info("NotificationController_regist_end: success");
        return CommonResponse.success(SUCCESS);
    }

    /**
     * 특정 유저의 알림 조회 API
     */
    @GetMapping("/user/{userId}")
    public CommonResponse<?> FindNotificationByUserId(@PathVariable Long userId) {

        log.info("NotificationController_findByUserId_start: " + userId);

        List<NotificationFindRes> res = notificationService.findNotificationByUserId(userId);

        log.info("NotificationController_findByUserId_end: " + res.toString());

        return CommonResponse.success(res);

    }

    /**
     * 알림 조회 API
     */
    @GetMapping("/{id}")
    public CommonResponse FindNotification(@PathVariable Long id) {

        log.info("NotificationController_find_start: " + id);

        NotificationFindRes res = notificationService.findNotification(id);

        log.info("NotificationController_find_end: " + res.toString());

        return CommonResponse.success(res);

    }
}