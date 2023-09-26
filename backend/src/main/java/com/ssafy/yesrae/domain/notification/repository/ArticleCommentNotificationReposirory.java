package com.ssafy.yesrae.domain.notification.repository;

import com.ssafy.yesrae.domain.notification.entity.ArticleCommentNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentNotificationReposirory extends
    JpaRepository<ArticleCommentNotification, Long> {

}
