package com.ssafy.yesrae.domain.comment.repository;

import com.ssafy.yesrae.domain.comment.entity.ArticleCommentNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentNotificationReposirory extends JpaRepository<ArticleCommentNotification,Long> {

}
