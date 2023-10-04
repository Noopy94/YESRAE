package com.ssafy.yesrae.domain.playlist.dto.request;

import java.util.List;
import lombok.Data;

@Data
public class PlaylistTagUpdatePutReq {

    private Long playlistId;
    private List<String> tagNames;

    public PlaylistTagUpdatePutReq(Long playlistId, List<String> tagNames) {
        this.playlistId = playlistId;
        this.tagNames = tagNames;
    }
}
