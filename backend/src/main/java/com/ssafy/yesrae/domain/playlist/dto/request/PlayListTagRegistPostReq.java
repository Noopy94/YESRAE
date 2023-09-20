package com.ssafy.yesrae.domain.playlist.dto.request;

import lombok.Data;

@Data
public class PlayListTagRegistPostReq {

    Long playListId;
    String tagName;

}
