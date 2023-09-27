package com.ssafy.yesrae.domain.playlist.dto.request;

import lombok.Data;

@Data
public class PlaylistSongRegistPostReq {

    private Long playlistId;
    private String songId;

}
