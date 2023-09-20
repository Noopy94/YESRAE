package com.ssafy.yesrae.crawling.domain.song.service;

import com.ssafy.yesrae.crawling.domain.song.dto.request.SongRegistPostReq;
import com.ssafy.yesrae.crawling.domain.song.dto.response.SongFindRes;
import com.ssafy.yesrae.crawling.domain.song.entity.Song;
import java.util.List;

public interface SongService {

    Song registSong(SongRegistPostReq songInfo);

    List<SongFindRes> findSongByArtistId(String artistId);

    void deleteSong(String Id);
}
