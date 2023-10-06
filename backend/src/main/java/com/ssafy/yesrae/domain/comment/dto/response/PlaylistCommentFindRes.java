package com.ssafy.yesrae.domain.comment.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaylistCommentFindRes {

    private Long id;

    private Long playlistId;

    private Long userId;

    private String content;

    private LocalDateTime createdAt;

    private String nickName;

}
