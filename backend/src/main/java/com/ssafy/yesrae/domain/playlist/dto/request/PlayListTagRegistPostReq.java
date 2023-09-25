package com.ssafy.yesrae.domain.playlist.dto.request;

import lombok.Data;

@Data
public class PlayListTagRegistPostReq {

    private Long playListId;
    private String tagName;

}
