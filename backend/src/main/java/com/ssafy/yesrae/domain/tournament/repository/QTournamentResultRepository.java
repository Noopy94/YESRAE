package com.ssafy.yesrae.domain.tournament.repository;

import com.ssafy.yesrae.domain.tournament.dto.response.TournamentPopularSongFindRes;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentResultFindRes;
import java.util.List;

/**
 * Querydsl을 위한 이상형 월드컵 결과 Repository
 */
public interface QTournamentResultRepository {

    List<TournamentResultFindRes> findTournamentResult(Long userId);

    List<TournamentPopularSongFindRes> findTournamentPopularSong();
}
