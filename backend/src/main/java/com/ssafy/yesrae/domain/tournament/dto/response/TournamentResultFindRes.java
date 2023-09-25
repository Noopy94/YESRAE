package com.ssafy.yesrae.domain.tournament.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 *  개인 별 이상형 월드컵 결과 Response
 */
@Data
@Builder
public class TournamentResultFindRes {

    private String date;

    private String songTitle;

    private String songSinger;

    private Integer rank;

    //Querydsl을 위한 생성자
    public TournamentResultFindRes() {
    }

    public TournamentResultFindRes(LocalDateTime date, String songTitle, String songSinger, Integer rank) {
        this.date = date.toString();
        this.songTitle = songTitle;
        this.songSinger = songSinger;
        this.rank = rank;
    }
}
