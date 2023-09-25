package com.ssafy.yesrae.domain.playlist.dto.request;

import lombok.Data;

@Data
public class PlayListLikeRegistPostReq {

    private Long userId;
    private Long playListId;

}
