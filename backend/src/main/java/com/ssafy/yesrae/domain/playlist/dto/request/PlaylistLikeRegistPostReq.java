package com.ssafy.yesrae.domain.playlist.dto.request;

import lombok.Data;

@Data
public class PlaylistLikeRegistPostReq {

    private Long userId;
    private Long playlistId;

}
