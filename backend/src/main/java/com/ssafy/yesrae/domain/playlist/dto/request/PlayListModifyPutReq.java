package com.ssafy.yesrae.domain.playlist.dto.request;

import lombok.Data;

@Data
public class PlaylistModifyPutReq {

    private Long playlistId;
    private Integer isPublic;
    private String title;
    private String description;

}
