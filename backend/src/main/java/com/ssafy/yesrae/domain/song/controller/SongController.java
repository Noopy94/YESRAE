package com.ssafy.yesrae.domain.song.controller;

import com.ssafy.yesrae.common.exception.song.SonglikePossessionFailException;
import com.ssafy.yesrae.common.model.CommonResponse;
import com.ssafy.yesrae.domain.song.dto.request.SongFindGetReq;
import com.ssafy.yesrae.domain.song.dto.request.SonglikeChangePostReq;
import com.ssafy.yesrae.domain.song.dto.response.SongFindRes;
import com.ssafy.yesrae.domain.song.dto.response.SonglikeFindRes;
import com.ssafy.yesrae.domain.song.service.SongService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/song")
public class SongController {

    private static final String SUCCESS = "success";

    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public CommonResponse<?> findSong(@RequestBody SongFindGetReq songFindGetReq) {

        log.info("SongController_findSong_start: " + songFindGetReq.toString());

        SongFindRes res = songService.findSong(songFindGetReq.getSongId());

        // 이게 맞나?
        SonglikeChangePostReq songlikeChangePostReq = new SonglikeChangePostReq();
        songlikeChangePostReq.setSongId(songFindGetReq.getSongId());
        songlikeChangePostReq.setUserId(songFindGetReq.getUserId());

        Boolean songlike = false;

        if (songService.isSonglikedOnce(songlikeChangePostReq) &&
            songService.isSongliked(songFindGetReq)) {
            songlike = true;
        }
        res.setSonglike(songlike);

        log.info("SongController_findSong_end: " + res);

        return CommonResponse.success(res);
    }


    @GetMapping("/songlikeByUserId/{userId}")
    public CommonResponse<?> findSonglikeByUserId(@PathVariable Long userId) {

        log.info("SongController_findSonglikeByUserId_start: " + userId);

        List<SonglikeFindRes> res = songService.findSonglikeByUserId(userId);

        log.info("SongController_findSong_end: " + res.toString());

        return CommonResponse.success(res);
    }

    @GetMapping("/songlikeBySongId/{songId}")
    public CommonResponse<?> findSonglikeBySongId(@PathVariable String songId) {

        log.info("SongController_findSonglikeBySongId_start: " + songId);

        Integer res = songService.findSonglikeBySongId(songId);

        log.info("SongController_findSonglikeBySongId_end: " + res.toString());

        return CommonResponse.success(res);
    }


    @PostMapping("/songlike")
    public CommonResponse<?> ChangeSonglike(
        @RequestBody SonglikeChangePostReq songlikeChangePostReq) {

        log.info("SongController_ChangeSonglike_start: " + songlikeChangePostReq);

        // 좋아요를 한 번이라도 한 적 있는지 여부 체크
        if (songService.isSonglikedOnce(songlikeChangePostReq)) {

            Boolean isChanged = songService.changeSonglike(songlikeChangePostReq);

            if (!isChanged) {
                throw new SonglikePossessionFailException();
            }
        } else {
            songService.registSonglike(songlikeChangePostReq);
        }

        log.info("SongController_ChangeSonglike_end: success");

        return CommonResponse.success(SUCCESS);
    }
}
