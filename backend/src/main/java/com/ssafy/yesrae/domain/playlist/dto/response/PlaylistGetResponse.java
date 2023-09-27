package com.ssafy.yesrae.domain.playlist.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaylistGetResponse {

    private Long id;
    private Long userId;
    private String title;
    private String description;
    private Long viewCount;
    private Long likeCount;
    private String imgUrl; // S3에 저장된 이미지 url
    private LocalDateTime createdAt;

}
