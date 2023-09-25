package com.ssafy.yesrae.domain.playlist.dto.request;

import com.ssafy.yesrae.domain.playlist.entity.PlayListSong;
import java.util.List;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PlayListRegistPostReq {

    private Long userId;
    private Integer isPublic;
    private String title;
    private String description;
    private List<PlayListSong> musicList;
}
