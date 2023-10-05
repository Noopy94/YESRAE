package com.ssafy.yesrae.domain.tournament.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 *  토너먼트에 들어갈 노래 Response
 */
@Data
@Builder
public class TournamentSongFindRes {

    private Long id;

    private String title;

    private String singer;

    private String url;

    //Querydsl을 위한 생성자
    public TournamentSongFindRes() {
    }

    public TournamentSongFindRes(Long id, String title, String singer, String url) {
        this.id = id;
        this.title = title;
        this.singer = singer;
        this.url = url;
    }
}
