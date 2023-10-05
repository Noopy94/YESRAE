package com.ssafy.yesrae.domain.tournament.service;

import com.ssafy.yesrae.domain.tournament.dto.request.FindTournamentSongGetReq;
import com.ssafy.yesrae.domain.tournament.dto.request.RegistTournamentResultPostReq;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentPopularSongFindRes;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentRegistResultPostRes;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentResultFindRes;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentSongFindRes;
import java.util.List;

public interface TournamentService {

    List<TournamentSongFindRes> findTournamentSong(FindTournamentSongGetReq findTournamentSongGetReq);

    Long registTournament();

    List<TournamentRegistResultPostRes> registTournamentResult(RegistTournamentResultPostReq registTournamentResultPostReq);

//    List<TournamentResultFindRes> findTournamentResult(Long userId);

    List<TournamentPopularSongFindRes> findTournamentPopularSong();
}
