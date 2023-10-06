package com.ssafy.yesrae.domain.template.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * Template Response
 */
@Data
@Builder
public class TemplateFindRes {

    private String title;

    private String content;

    private Integer viewCount;

    //Querydsl 을 위한 생성자

    public TemplateFindRes() {
    }

    public TemplateFindRes(String title, String content, Integer viewCount) {
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
    }
}
