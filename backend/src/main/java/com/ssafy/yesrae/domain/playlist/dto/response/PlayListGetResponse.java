package com.ssafy.yesrae.domain.playlist.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayListGetResponse {

    private Long id;
    private Long user_id;
    private String title;
    private String description;
    private Long viewCount;
    private Long likeCount;
    private String imgUrl; // S3에 저장된 이미지 url
    private LocalDateTime created_data;

}
