package com.ssafy.yesrae.domain.playlist.dto.response;

import java.util.List;
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
    private List<String> tags;

    public PlaylistGetResponse(Long id, Long userId, String title, String description,
        Long viewCount,
        Long likeCount, String imgUrl, List<String> tags) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.imgUrl = imgUrl;
        this.tags = tags;
    }

    public PlaylistGetResponse(Long id, Long userId, String title, String description,
        Long viewCount,
        Long likeCount, String imgUrl) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.imgUrl = imgUrl;
    }
}
