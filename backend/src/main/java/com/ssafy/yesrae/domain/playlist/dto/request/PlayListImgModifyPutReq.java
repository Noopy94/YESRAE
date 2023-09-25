package com.ssafy.yesrae.domain.playlist.dto.request;

import com.ssafy.yesrae.domain.playlist.entity.PlayListSong;
import java.util.List;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PlayListImgModifyPutReq {

   private Long playListId;
   private MultipartFile img;

}
