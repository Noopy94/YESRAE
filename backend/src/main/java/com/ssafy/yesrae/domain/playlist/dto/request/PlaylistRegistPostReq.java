package com.ssafy.yesrae.domain.playlist.dto.request;

import com.ssafy.yesrae.domain.playlist.entity.PlaylistSong;
import java.util.List;
import lombok.Data;

@Data
public class PlaylistRegistPostReq {

    private Long userId;
    private Integer isPublic;
    private String title;
    private String description;
    private List<String> tags;
}
