package com.ssafy.yesrae.domain.tournament.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * 이상형 월드컵 노래 인기 순위 Response
 */
@Data
@Builder
public class TournamentPopularSongFindRes {

    private String title;

    private String singer;

    private Double proportion;

    //Querydsl을 위한 생성자
    public TournamentPopularSongFindRes() {
    }

    public TournamentPopularSongFindRes(String title, String singer,
        Double proportion) {
        this.title = title;
        this.singer = singer;
        this.proportion = proportion;
    }
}
