package com.ssafy.yesrae.domain.playlist.dto.request;

import lombok.Data;

@Data
public class PlayListLikeRegistPostReq {

    Long userId;
    String tagName;

}
