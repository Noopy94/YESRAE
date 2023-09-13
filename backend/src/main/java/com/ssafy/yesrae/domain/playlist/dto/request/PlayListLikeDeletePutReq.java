package com.ssafy.yesrae.domain.playlist.dto.request;

import lombok.Data;

@Data
public class PlayListLikeDeletePutReq {

    Long userId;
    Long playListId;

}
