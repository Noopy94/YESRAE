package com.ssafy.yesrae.common.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileDto {

    private String fileName; // S3에 저장되는 실제 파일 이름
    private String originalName; // User 가 지정한 파일 이름
    private String path; // S3 내의 저장 주소
    private String contentType; // 타입
}
