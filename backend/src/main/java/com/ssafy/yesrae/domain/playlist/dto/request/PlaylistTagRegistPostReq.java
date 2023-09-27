package com.ssafy.yesrae.domain.playlist.dto.request;

import lombok.Data;

@Data
public class PlaylistTagRegistPostReq {

    private Long playlistId;
    private String tagName;

}
