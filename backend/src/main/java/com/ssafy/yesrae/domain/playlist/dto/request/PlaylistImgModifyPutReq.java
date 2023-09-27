package com.ssafy.yesrae.domain.playlist.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PlaylistImgModifyPutReq {

    private Long playlistId;
    private MultipartFile img;

}
