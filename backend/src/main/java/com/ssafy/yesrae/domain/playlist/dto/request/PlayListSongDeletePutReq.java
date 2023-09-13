package com.ssafy.yesrae.domain.playlist.dto.request;

import lombok.Data;

@Data
public class PlayListSongDeletePutReq {

    Long playListId;
    String songId;

}
