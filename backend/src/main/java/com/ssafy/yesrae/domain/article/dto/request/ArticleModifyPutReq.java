package com.ssafy.yesrae.domain.article.dto.request;

import lombok.Data;

@Data
public class ArticleModifyPutReq {
    Long userId;
    Long id;
    Long category;
    String content;
    String title;
}
