package com.ssafy.yesrae.domain.playlist.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PlaylistImgModifyPutReq {

    private Long playlistId;
    private MultipartFile file;

    public PlaylistImgModifyPutReq(Long playlistId, MultipartFile file) {
        this.playlistId = playlistId;
        this.file = file;
    }
}
