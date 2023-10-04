package com.ssafy.yesrae.domain.song.service;

import com.ssafy.yesrae.domain.song.dto.request.SongFindGetReq;
import com.ssafy.yesrae.domain.song.dto.request.SonglikeChangePostReq;
import com.ssafy.yesrae.domain.song.dto.response.SongFindRes;
import com.ssafy.yesrae.domain.song.dto.response.SonglikeFindRes;
import com.ssafy.yesrae.domain.song.entity.Songlike;
import java.util.List;

public interface SongService {

    SongFindRes findSong(String songId);

    List<SonglikeFindRes> findSonglikeByUserId(Long userId);

    Integer findSonglikeBySongId(String songId);

    Boolean isSongliked(SongFindGetReq songFindGetReq);

    Boolean isSonglikedOnce(SonglikeChangePostReq songlikeChangePostReq);

    Songlike registSonglike(SonglikeChangePostReq songlikeChangePostReq);

    Boolean changeSonglike(SonglikeChangePostReq songlikeChangePostReq);


}