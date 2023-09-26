package com.ssafy.yesrae.domain.notification.repository;

import com.ssafy.yesrae.domain.notification.entity.PlayListCommentNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayListCommentNotificationRespository extends
    JpaRepository<PlayListCommentNotification, Long> {

}
