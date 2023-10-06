package com.ssafy.yesrae.domain.comment.dto.request;

import lombok.Data;

@Data
public class PlaylistCommentRegistPostReq {

    private Long playlistId;

    private Long userId;

    private String content;

}
