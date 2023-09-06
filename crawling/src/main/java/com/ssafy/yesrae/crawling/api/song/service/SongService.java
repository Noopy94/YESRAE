package com.ssafy.yesrae.crawling.api.song.service;

import com.ssafy.yesrae.crawling.api.song.request.SongRegistPostReq;
import com.ssafy.yesrae.crawling.db.entity.Song;

public interface SongService {

    Song registSong(SongRegistPostReq songInfo);

}
