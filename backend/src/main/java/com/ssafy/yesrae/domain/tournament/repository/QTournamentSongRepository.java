package com.ssafy.yesrae.domain.tournament.repository;

import com.ssafy.yesrae.domain.tournament.dto.request.FindTournamentSongGetReq;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentPopularSongFindRes;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentSongFindRes;
import java.util.List;

/**
 * Querydsl을 위한 이상형 월드컵 Repository
 */
public interface QTournamentSongRepository {

    List<TournamentSongFindRes> findTournamentSong(
        FindTournamentSongGetReq findTournamentSongGetReq);

    List<TournamentPopularSongFindRes> findTournamentPopularSong();
}
