package com.ssafy.yesrae.domain.article.dto.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ArticleRegistPostReq {

    //F-Key
    private Long userId;
    // 게시판 타입
    private int Type;
    // 내용
    private String content;
    // 작서일자
    private Timestamp createdDate;
    // 제목
    private String title;
    // 카테고리
    private int category;
}
