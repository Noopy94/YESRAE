package com.ssafy.yesrae.domain.playlist.controller;

import com.ssafy.yesrae.common.exception.FileIOException;
import com.ssafy.yesrae.common.exception.NotFoundException;
import com.ssafy.yesrae.common.exception.Template.TemplatePossessionFailException;
import com.ssafy.yesrae.common.model.CommonResponse;
import com.ssafy.yesrae.domain.article.dto.request.ArticleDeletePutReq;
import com.ssafy.yesrae.domain.article.dto.request.ArticleModifyPutReq;
import com.ssafy.yesrae.domain.article.dto.request.ArticleRegistPostReq;
import com.ssafy.yesrae.domain.article.dto.response.ArticleFindRes;
import com.ssafy.yesrae.domain.article.entity.Article;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistImgModifyPutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistModifyPutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.response.PlaylistGetResponse;
import com.ssafy.yesrae.domain.playlist.entity.Playlist;
import com.ssafy.yesrae.domain.playlist.service.PlaylistService;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Template Controller
 */
@Slf4j
@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    // log 형식은 협의해서 새로 결정해도 무방
    private static final String SUCCESS = "success"; // API 성공 시 return

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

//  플레이 리스트 등록을 위한 api
@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
public CommonResponse<?> registPlaylist(@RequestPart PlaylistRegistPostReq playlistRegistPostReq,
    @RequestPart(value = "files", required = false) MultipartFile file) throws IOException {

    if (file != null) { // 게시물에 파일 있으면
        log.info("PlaylistController_regist_start: " + playlistRegistPostReq.toString() + ", "
            + file);
    } else {
        log.info("PlaylistController_regist_start: " + playlistRegistPostReq.toString());
    }

    Playlist playlist = playlistService.registPlaylist(playlistRegistPostReq, file);
    if (playlist != null) {  // regist 성공하면 success
        log.info("PlaylistController_regist_end: success");
        return CommonResponse.success(SUCCESS);
    } else {    // 실패하면 Exception
        throw new FileIOException();
    }
}


    @PutMapping("/delete")
    public CommonResponse<?> deletePlaylist(@PathVariable PlaylistDeletePutReq playlistDeletePutReq) {

        log.info("PlaylistController_delete_start: " + playlistDeletePutReq);

        boolean isdeleted = playlistService.deletePlaylist(playlistDeletePutReq);

        if (isdeleted) {   // 수정 성공하면 success
            log.info("PlaylistController_delete_end: success");
            return CommonResponse.success(SUCCESS);
        } else {    // 수정 실패하면 Exception
            throw new TemplatePossessionFailException();
        }

    }

    @GetMapping("/{Id}")
    public CommonResponse<?> findPlaylist(@PathVariable Long Id) {

        log.info("PlaylisController_find_start: " + Id);

        PlaylistGetResponse playlistGetResponse = playlistService.findPlaylist(Id);

        if (playlistGetResponse != null) { // 조회 성공하면 조회 결과 return
            log.info("PlaylistController_find_end: " + playlistGetResponse.toString());
            return CommonResponse.success(playlistGetResponse);
        } else {    // 조회 실패하면 Exception
            throw new NotFoundException();
        }
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public CommonResponse<?> modifyPlaylist(@RequestPart PlaylistModifyPutReq playlistModifyPutReq,
        @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        if (file != null) {
            log.info("PlaylistController_modify_start: " + playlistModifyPutReq.toString() + ", "
                + file);
        } else {
            log.info("ArticleController_modify_start: " + playlistModifyPutReq.toString());
        }

        PlaylistImgModifyPutReq playlistImgModifyPutReq = new PlaylistImgModifyPutReq(playlistModifyPutReq.getPlaylistId(),file);

        boolean isModified = playlistService.modifyPlaylist(playlistModifyPutReq);
        boolean isModifiedImg = playlistService.modifyPlaylistImg(playlistImgModifyPutReq);


        if (isModified&&isModifiedImg) {   // 수정 성공하면 success
            log.info("PlaylistController_modify_end: success");
            return CommonResponse.success(SUCCESS);
        } else {    // 수정 실패하면 Exception
            throw new TemplatePossessionFailException();
        }
    }






}
