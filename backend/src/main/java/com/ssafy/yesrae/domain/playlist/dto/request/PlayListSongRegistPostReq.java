package com.ssafy.yesrae.domain.playlist.dto.request;

import lombok.Data;

@Data
public class PlayListSongRegistPostReq {

    Long playListId;
    String songId;

}
