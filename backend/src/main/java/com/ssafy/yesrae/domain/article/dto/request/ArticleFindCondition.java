package com.ssafy.yesrae.domain.article.dto.request;

import lombok.Data;

@Data
public class ArticleFindCondition {
    private Long CategoryId;
    private String Keyword;
}
