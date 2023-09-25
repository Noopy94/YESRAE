package com.ssafy.yesrae.domain.tournament.dto.request;

import lombok.Data;

@Data
public class RegistTournamentResultPostReq {

    // 시행한 이상형 월드컵 ID
    private Long tournamentId;
    // 이상형 월드컵 우승한 노래 ID
    private Long firstSongId;
    // 준우승한 노래 ID
    private Long secondSongId;
    // 4강에서 탈락한 노래 1 ID
    private Long semiFinalSongOneId;
    // 4강에서 탈락한 노래 2 ID
    private Long semiFinalSongTwoId;
}
