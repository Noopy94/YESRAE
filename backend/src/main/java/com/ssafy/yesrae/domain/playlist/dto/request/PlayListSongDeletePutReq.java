package com.ssafy.yesrae.domain.playlist.dto.request;

import lombok.Data;

@Data
public class PlayListSongDeletePutReq {

    private Long playListId;
    private String songId;

}
