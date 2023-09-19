package com.ssafy.yesrae.domain.article.dto.request;

import lombok.Data;

@Data
public class ArticleModifyPutReq {
    Long Id;
    Long category;
    String content;
    String title;
}
