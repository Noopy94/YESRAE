package com.ssafy.yesrae.domain.playlist.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayListGetResponse {

    Long id;
    Long user_id;
    String title;
    String description;
    boolean isPublic;
    String img; // S3에 저장된 이미지 url

}
