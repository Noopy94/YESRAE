package com.ssafy.yesrae.domain.notification.service;

import com.ssafy.yesrae.common.exception.notification.NotificationNotFoundException;
import com.ssafy.yesrae.common.exception.user.UserNotFoundException;
import com.ssafy.yesrae.domain.notification.dto.request.NotificationRegistPostReq;
import com.ssafy.yesrae.domain.notification.dto.response.NotificationFindRes;
import com.ssafy.yesrae.domain.notification.entity.Notification;
import com.ssafy.yesrae.domain.notification.repository.NotificationRepository;
import com.ssafy.yesrae.domain.user.entity.User;
import com.ssafy.yesrae.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
        UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    /**
     * 알림 Regist API 에 대한 서비스
     *
     * @param notificationRegistPostReq : 알림 등록할 때 입력한 정보
     */
    @Override
    public Notification registNotification(NotificationRegistPostReq notificationRegistPostReq) {

        log.info("NotificationService_regist_start: " + notificationRegistPostReq.toString());

        // 게시글 작성자의 아이디
        User user = userRepository.findById(notificationRegistPostReq.getRecipientId())
            .orElseThrow(UserNotFoundException::new);

        String title = notificationRegistPostReq.getTitle();
        String content = notificationRegistPostReq.getContent();

        Notification notification = Notification.builder()
            .user(user)
            .title(title)
            .content(content)
            .build();

        notificationRepository.save(notification);

        log.info("NotificationService_regist_end: success");

        return notification;

    }

    /**
     * 특정 유저의 알림 전체 조회 API에 대한 서비스
     */
    @Override
    public List<NotificationFindRes> findNotificationByUserId(Long userId) {

        log.info("NotificationService_findNotificationByUserId_start: "
            + userId);

        List<NotificationFindRes> res = notificationRepository.findAllByUserId(userId)
            .stream().map(m -> NotificationFindRes.builder()
                .id(m.getId()).title(m.getTitle()).content(m.getContent())
                .createdAt(m.getCreatedAt()).isViewed(m.getIsViewed()).build())
            .collect(Collectors.toList());

        log.info("NotificationService_findNotificationByUserId_end: success");

        return res;
    }

    /**
     * 특정 알림 조회 API에 대한 서비스
     */
    @Override
    public NotificationFindRes findNotification(Long notificationId) {

        log.info("NotificationService_findNotification_start: "
            + notificationId);

        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(NotificationNotFoundException::new);

        notification.readNotification();

        NotificationFindRes res = NotificationFindRes.builder().id(notification.getId())
            .title(notification.getTitle()).content(notification.getContent())
            .createdAt(notification.getCreatedAt())
            .isViewed(notification.getIsViewed()).build();

        log.info("NotificationService_findNotification_end: success");

        return res;
    }
}
