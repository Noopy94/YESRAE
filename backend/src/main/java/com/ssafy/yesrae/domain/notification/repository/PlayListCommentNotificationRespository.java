package com.ssafy.yesrae.domain.notification.repository;

import com.ssafy.yesrae.domain.notification.entity.PlaylistCommentNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistCommentNotificationRespository extends
    JpaRepository<PlaylistCommentNotification, Long> {

}
