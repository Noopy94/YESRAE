package com.ssafy.yesrae.domain.article.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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

    private LocalDateTime createdDate;

    private List<String> files;
}
