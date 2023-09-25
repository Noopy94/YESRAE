package com.ssafy.yesrae.domain.playlist.dto.request;

import lombok.Data;

@Data
public class PlayListSongRegistPostReq {

    private Long playListId;
    private String songId;

}
