package com.ssafy.yesrae.domain.playlist.dto.request;

import java.util.List;
import lombok.Data;

@Data
public class PlaylistModifyPutReq {

    private Long playlistId;
    private Integer isPublic;
    private String title;
    private String description;
    private List<String> tags;

}
