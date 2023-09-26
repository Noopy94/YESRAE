package com.ssafy.yesrae.domain.article.dto.request;

import lombok.Data;

@Data
public class ArticleDeletePutReq {
    private Long userId;
    private Long articleId;
}
