package com.ssafy.yesrae.domain.user.controller;

import com.ssafy.yesrae.common.model.CommonResponse;
import com.ssafy.yesrae.domain.user.dto.request.UserRegistPostReq;
import com.ssafy.yesrae.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private static final String SUCCESS = "success";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/regist")
    public CommonResponse<?> regist(@RequestBody UserRegistPostReq userRegistPostReq) {

        log.info("UserController_regist_start: " + userRegistPostReq.toString());

        userService.regist(userRegistPostReq);

        log.info("UserController_regist_end: success");

        return CommonResponse.success(SUCCESS);
    }
}
