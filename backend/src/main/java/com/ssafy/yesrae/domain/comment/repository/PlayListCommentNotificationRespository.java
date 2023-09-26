package com.ssafy.yesrae.domain.comment.repository;

import com.ssafy.yesrae.domain.comment.entity.PlayListCommentNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayListCommentNotificationRespository extends
    JpaRepository<PlayListCommentNotification, Long> {

}
