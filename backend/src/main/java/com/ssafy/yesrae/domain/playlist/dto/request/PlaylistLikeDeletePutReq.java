package com.ssafy.yesrae.domain.playlist.dto.request;

import lombok.Data;

@Data
public class PlaylistLikeDeletePutReq {

    private Long userId;
    private Long playlistId;

}
