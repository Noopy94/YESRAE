package com.ssafy.yesrae.domain.playlist.dto.request;

import lombok.Data;

@Data
public class PlayListLikeDeletePutReq {

    private Long userId;
    private Long playListId;

}
