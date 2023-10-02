package com.ssafy.yesrae.domain.notification.service;

import com.ssafy.yesrae.domain.notification.dto.request.NotificationRegistPostReq;
import com.ssafy.yesrae.domain.notification.dto.response.NotificationFindRes;
import com.ssafy.yesrae.domain.notification.entity.Notification;
import java.util.List;

public interface NotificationService {

    Notification registNotification(NotificationRegistPostReq notificationRegistPostReq);

    List<NotificationFindRes> findNotificationByUserId(Long userId);

    NotificationFindRes findNotification(Long NotificationId);

}
