package com.ssafy.yesrae.domain.tournament.dto.request;

import lombok.Data;

@Data
public class RegistTournamentResultPostReq {

    // 이상형 월드컵 우승한 노래 ID
    private String firstSongId;
    // 준우승한 노래 ID
    private String secondSongId;
    // 4강에서 탈락한 노래 1 ID
    private String semiFinalSongOneId;
    // 4강에서 탈락한 노래 2 ID
    private String semiFinalSongTwoId;
}
