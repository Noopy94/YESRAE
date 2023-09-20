package com.ssafy.yesrae.domain.tournament.service;

import com.ssafy.yesrae.domain.tournament.dto.request.FindTournamentSongGetReq;
import com.ssafy.yesrae.domain.tournament.dto.request.RegistTournamentResultPostReq;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentResultFindRes;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentSongFindRes;
import java.util.List;

public interface TournamentService {

    List<TournamentSongFindRes> findTournamentSong(FindTournamentSongGetReq findTournamentSongGetReq);

    void registTournament(Long userId);

    void registTournamentResult(RegistTournamentResultPostReq registTournamentResultPostReq);

    List<TournamentResultFindRes> findTournamentResult(Long userId);
}
