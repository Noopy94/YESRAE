package com.ssafy.yesrae.domain.notification.dto.request;

import lombok.Data;

@Data
public class ArticleCommentNotificationRegistPostReq {

    private Long articleId;

    private Long userId;

}
