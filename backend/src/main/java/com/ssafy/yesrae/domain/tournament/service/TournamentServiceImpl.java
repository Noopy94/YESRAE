package com.ssafy.yesrae.domain.tournament.service;

import com.ssafy.yesrae.domain.tournament.dto.request.FindTournamentSongGetReq;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentSongFindRes;
import com.ssafy.yesrae.domain.tournament.repository.TournamentSongRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 이상형 월드컵을 위한 API 서비스
 */
@Slf4j
@Transactional
@Service
public class TournamentServiceImpl implements TournamentService{

    private final TournamentSongRepository tournamentSongRepository;

    @Autowired
    public TournamentServiceImpl(TournamentSongRepository tournamentSongRepository) {
        this.tournamentSongRepository = tournamentSongRepository;
    }

    /**
     * 이상형 월드컵을 시작하기 위해 필요한 노래를 가져옴
     * @param findTournamentSongGetReq : 라운드 수
     */
    @Override
    public List<TournamentSongFindRes> findTournamentSong(
        FindTournamentSongGetReq findTournamentSongGetReq) {

        log.info("TournamentService_findTournamentSong_start: "
            + findTournamentSongGetReq.toString());

        List<TournamentSongFindRes> findRes = tournamentSongRepository.findTournamentSong(findTournamentSongGetReq);

        log.info("TemplateService_findAllTemplate_end: success");
        return findRes;
    }
}
