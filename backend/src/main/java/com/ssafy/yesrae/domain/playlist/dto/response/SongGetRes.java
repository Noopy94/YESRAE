package com.ssafy.yesrae.domain.playlist.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SongGetRes {

    private String songId;

    private String songTitle;

    private String songArtist;

    private String songImgUrl;

    private String songUrl;

    public SongGetRes(String songId, String songTitle, String songArtist, String songImgUrl,
        String songUrl) {
        this.songId = songId;
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.songImgUrl = songImgUrl;
        this.songUrl = songUrl;
    }
}
