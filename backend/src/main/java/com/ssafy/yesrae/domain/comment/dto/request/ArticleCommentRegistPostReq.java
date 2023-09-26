package com.ssafy.yesrae.domain.comment.dto.request;

import lombok.Data;

@Data
public class ArticleCommentRegistPostReq {

    private Long articleId;

    private Long userId;

    private String content;

}
