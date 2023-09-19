package com.ssafy.yesrae.domain.article.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleFindRes {
    //user nickname
    private String nickname;
    //게시판 타입
    private String tagName;
    //내용
    private String content;

    private String title;

    private boolean type;

    private String createdDate;
}
