package com.ssafy.yesrae.domain.song.dto.request;

import lombok.Data;

@Data
public class SongFindGetReq {

    private Long userId;

    private String songId;

}
