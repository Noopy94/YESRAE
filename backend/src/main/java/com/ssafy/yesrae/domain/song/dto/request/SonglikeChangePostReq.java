package com.ssafy.yesrae.domain.song.dto.request;

import lombok.Data;

@Data
public class SonglikeChangePostReq {

    private Long userId;

    private String songId;
    
}
