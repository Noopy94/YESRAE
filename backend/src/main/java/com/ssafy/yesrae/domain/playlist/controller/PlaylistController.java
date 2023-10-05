package com.ssafy.yesrae.domain.playlist.controller;

import com.ssafy.yesrae.common.exception.FileIOException;
import com.ssafy.yesrae.common.exception.NotFoundException;
import com.ssafy.yesrae.common.exception.Template.TemplatePossessionFailException;
import com.ssafy.yesrae.common.model.CommonResponse;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistImgModifyPutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistLikeDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistLikeRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistModifyPutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistSearchGetReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistSongDeletePutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistSongRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistTagRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlaylistTagUpdatePutReq;
import com.ssafy.yesrae.domain.playlist.dto.response.PlaylistGetResponse;
import com.ssafy.yesrae.domain.playlist.dto.response.SongGetRes;
import com.ssafy.yesrae.domain.playlist.entity.Playlist;
import com.ssafy.yesrae.domain.playlist.entity.PlaylistSong;
import com.ssafy.yesrae.domain.playlist.service.PlaylistService;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    //  플레이 리스트 등록, 태그도 같이 등록
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public CommonResponse<?> registPlaylist(
        @RequestPart PlaylistRegistPostReq playlistRegistPostReq,
        @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

        if (file != null) { // 게시물에 파일 있으면
            log.info(
                "PlaylistController_registfile_start: " + playlistRegistPostReq.toString() + ", "
                    + file);
        } else {
            log.info("PlaylistController_regist_start: " + playlistRegistPostReq.toString());
        }

        Playlist playlist = playlistService.registPlaylist(playlistRegistPostReq, file);
        Long Id = playlist.getId();
        for (String s : playlistRegistPostReq.getTags()) {
            playlistService.registPlaylistTag(new PlaylistTagRegistPostReq(Id, s));
        }

        if (playlist != null) {  // regist 성공하면 success
            log.info("PlaylistController_regist_end: success");
            return CommonResponse.success(SUCCESS);
        } else {    // 실패하면 Exception
            throw new FileIOException();
        }
    }

    // playlist에 노래 등록
    @PostMapping("/registsong")
    public CommonResponse<?> RegistSong(
        @RequestBody PlaylistSongRegistPostReq playlistSongRegistPostReq) {

        log.info("PlaylistController_registsong_start: " + playlistSongRegistPostReq.toString());

        PlaylistSong playlistSong = playlistService.registSongInPlaylist(playlistSongRegistPostReq);

        if (playlistSong != null) {
            log.info("PlaylistController_registsong_end: success");
            return CommonResponse.success(SUCCESS);
        } else {
            throw new FileIOException();
        }
    }

    //플레이 리스트 좋아요 등록
    @PostMapping("/registlike")
    public CommonResponse<?> RegistLike(
        @RequestBody PlaylistLikeRegistPostReq playlistLikeRegistPostReq) {

        log.info("PlaylistController_registslike_start: " + playlistLikeRegistPostReq.toString());

        boolean isRegisted = playlistService.registPlaylistLike(playlistLikeRegistPostReq);

        if (isRegisted) {
            log.info("PlaylistController_registlike_end: success");
            return CommonResponse.success(SUCCESS);
        } else {
            throw new FileIOException();
        }
    }

    // 플레이 리스트 제거
    @PutMapping("/delete")
    public CommonResponse<?> deletePlaylist(
        @RequestBody PlaylistDeletePutReq playlistDeletePutReq) {

        log.info("PlaylistController_delete_start: " + playlistDeletePutReq.toString());

        boolean isdeleted = playlistService.deletePlaylist(playlistDeletePutReq);

        if (isdeleted) {   // 수정 성공하면 success
            log.info("PlaylistController_delete_end: success");
            return CommonResponse.success(SUCCESS);
        } else {    // 수정 실패하면 Exception
            throw new TemplatePossessionFailException();
        }
    }

    // 플레이 리스트 노래 제거
    @PutMapping("/deletesong")
    public CommonResponse<?> DeleteSong(
        @RequestBody PlaylistSongDeletePutReq playlistSongDeletePutReq) {

        log.info("PlaylistController_deletesong_start: " + playlistSongDeletePutReq.toString());

        boolean isDeleted = playlistService.deletePlaylistSong(playlistSongDeletePutReq);

        if (isDeleted) {
            log.info("PlaylistController_deletesong_end: success");
            return CommonResponse.success(SUCCESS);
        } else {
            throw new TemplatePossessionFailException();
        }

    }

    // 플레이 리스트 좋아요 삭제
    @PutMapping("/deletelike")
    public CommonResponse<?> DeleteLike(
        @RequestBody PlaylistLikeDeletePutReq playlistLikeDeletePostReq) {

        log.info("PlaylistController_deletelike_start: " + playlistLikeDeletePostReq.toString());
        boolean isDeleted = playlistService.deletePlaylistLike(playlistLikeDeletePostReq);

        if (isDeleted) {
            log.info("PlaylistController_deletelike_end: success");
            return CommonResponse.success(SUCCESS);
        } else {
            throw new TemplatePossessionFailException();
        }

    }

    //플레이 리스트 조회
    @GetMapping("/find/{Id}")
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

    //플레이 리스트 내부 노래 조회
    @GetMapping("/songs/{Id}")
    public CommonResponse<?> findPlaylistSongs(@PathVariable Long Id) {

        log.info("PlaylisController_findSongs_start: " + Id);

        List<SongGetRes> songs = playlistService.getPlaylistSong(Id);

        if (songs != null) { // 조회 성공하면 조회 결과 return
            log.info("PlaylistController_findSongs_end: " + songs.toString());
            return CommonResponse.success(songs);
        } else {    // 조회 실패하면 Exception
            throw new NotFoundException();
        }
    }

    //플레이 리스트 내부 태그 조회
    @GetMapping("/tags/{Id}")
    public CommonResponse<?> findPlaylistTags(@PathVariable Long Id) {

        log.info("PlaylisController_findTags_start: " + Id);

        List<String> tags = playlistService.getPlaylistTag(Id);

        log.info("PlaylistController_findTags_end: " + tags.toString());
        return CommonResponse.success(tags);
    }


    // 플레이 리스트 수정, 태그도 수정
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public CommonResponse<?> modifyPlaylist(@RequestPart PlaylistModifyPutReq playlistModifyPutReq,
        @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        if (file != null) {
            log.info("PlaylistController_modify_start: " + playlistModifyPutReq.toString() + ", "
                + file);
        } else {
            log.info("ArticleController_modify_start: " + playlistModifyPutReq.toString());
        }

        PlaylistImgModifyPutReq playlistImgModifyPutReq = new PlaylistImgModifyPutReq(
            playlistModifyPutReq.getPlaylistId(), file);

        // 기본 내용, 이미지 수정
        boolean isModified = playlistService.modifyPlaylist(playlistModifyPutReq);
        boolean isModifiedImg = playlistService.modifyPlaylistImg(playlistImgModifyPutReq);

        // 태그 수정
        playlistService.updatePlaylistTag(
            new PlaylistTagUpdatePutReq(playlistModifyPutReq.getPlaylistId(),
                playlistModifyPutReq.getTags()));

        if (isModified && isModifiedImg) {   // 수정 성공하면 success
            log.info("PlaylistController_modify_end: success");
            return CommonResponse.success(SUCCESS);
        } else {    // 수정 실패하면 Exception
            throw new TemplatePossessionFailException();
        }
    }

    // 검색시 페이지네이션 플레이 리스트 반환
    @GetMapping("/findtag")
    public CommonResponse<Page<PlaylistGetResponse>> findPlaylistByTag(
        @RequestBody PlaylistSearchGetReq playlistSearchGetReq
    ) {
        log.info("PlaylistController_findPlaylistByTag_start: ");

        // Pageable 객체 생성하여 페이지 크기를 8로 설정
        Pageable pageable = PageRequest.of(playlistSearchGetReq.getPage(), 8);

        // PlaylistService에서 검색 결과를 가져옴
        Page<PlaylistGetResponse> findRes = playlistService.searchByTagPlaylist(
            playlistSearchGetReq.getKeyword(), pageable);

        log.info("PlaylistController_findPlaylistByTag_end: " + findRes);

        return CommonResponse.success(findRes);
    }

    @GetMapping("/findtitle")
    public CommonResponse<Page<PlaylistGetResponse>> findPlaylistByTitle(
        @RequestBody PlaylistSearchGetReq playlistSearchGetReq
    ) {
        log.info("PlaylistController_findPlaylistByTitle_start: ");

        // Pageable 객체 생성하여 페이지 크기를 8로 설정
        Pageable pageable = PageRequest.of(playlistSearchGetReq.getPage(), 8);

        // PlaylistService에서 검색 결과를 가져옴
        Page<PlaylistGetResponse> findRes = playlistService.searchByTitlePlaylist(
            playlistSearchGetReq.getKeyword(), pageable);

        log.info("PlaylistController_findPlaylistByTitle_end: " + findRes);

        return CommonResponse.success(findRes);
    }

    // 좋아요 베스트 플레이 리스트 20개 가져오기
    @GetMapping("/best20likecnt")
    public CommonResponse<List<PlaylistGetResponse>> findBest20likeCntPlayList() {

        log.info("PlaylistController_findBest20likeCntPlayList_start: ");

        List<PlaylistGetResponse> findRes = playlistService.getBest20LikeCntPlaylist();

        log.info("PlaylistController_findBest20likeCntPlayList_end: ");

        return CommonResponse.success(findRes);
    }

    // 조회수 베스트 플레이 리스트 20개 가져오기
    @GetMapping("/best20viewcnt")
    public CommonResponse<List<PlaylistGetResponse>> findBest20viewCntPlayList() {

        log.info("PlaylistController_findBest20viewCntPlayList_start: ");

        List<PlaylistGetResponse> findRes = playlistService.getBest20ViewCntPlaylist();

        log.info("PlaylistController_findBest20viewCntPlayList_end: ");

        return CommonResponse.success(findRes);
    }

    // 유저 플레이 리스트 가져오기
    @GetMapping("/user/{Id}")
    public CommonResponse<List<PlaylistGetResponse>> findUserPlayList(@PathVariable Long Id) {

        log.info("PlaylistController_findUserPlayList_start: " + Id);

        List<PlaylistGetResponse> findRes = playlistService.getUserPlaylist(Id);

        log.info("PlaylistController_findUserPlayList_end: ");

        return CommonResponse.success(findRes);

    }


    // 특정 유저 follwer 플레이 리스트 가져오기
    @GetMapping("/follwerplaylist/{Id}")
    public CommonResponse<List<PlaylistGetResponse>> findFollowerPlayList(@PathVariable Long Id) {

        log.info("PlaylistController_findFollowerPlayList_start: " + Id);

        List<PlaylistGetResponse> findRes = playlistService.getFollowerPlaylist(Id);

        log.info("PlaylistController_findFollowerPlayList_end: ");

        return CommonResponse.success(findRes);

    }


}
