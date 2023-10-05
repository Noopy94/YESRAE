package com.ssafy.yesrae.domain.playlist.dto.request;

import lombok.Data;

@Data
public class PlaylistSearchGetReq {

    private String keyword;
    private Integer page;
}
