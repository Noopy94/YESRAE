package com.ssafy.yesrae.domain.tournament.service;

import com.ssafy.yesrae.common.exception.NoDataException;
import com.ssafy.yesrae.domain.tournament.dto.request.FindTournamentSongGetReq;
import com.ssafy.yesrae.domain.tournament.dto.request.RegistTournamentResultPostReq;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentPopularSongFindRes;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentRegistResultPostRes;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentResultFindRes;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentSongFindRes;
import com.ssafy.yesrae.domain.tournament.entity.Tournament;
import com.ssafy.yesrae.domain.tournament.entity.TournamentResult;
import com.ssafy.yesrae.domain.tournament.entity.TournamentSong;
import com.ssafy.yesrae.domain.tournament.repository.TournamentRepository;
import com.ssafy.yesrae.domain.tournament.repository.TournamentResultRepository;
import com.ssafy.yesrae.domain.tournament.repository.TournamentSongRepository;
import com.ssafy.yesrae.domain.user.entity.User;
import com.ssafy.yesrae.domain.user.repository.UserRepository;
import java.util.ArrayList;
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
    private final UserRepository userRepository;
    private final TournamentRepository tournamentRepository;
    private final TournamentResultRepository tournamentResultRepository;

    @Autowired
    public TournamentServiceImpl(TournamentSongRepository tournamentSongRepository,
        UserRepository userRepository, TournamentRepository tournamentRepository,
        TournamentResultRepository tournamentResultRepository) {
        this.tournamentSongRepository = tournamentSongRepository;
        this.userRepository = userRepository;
        this.tournamentRepository = tournamentRepository;
        this.tournamentResultRepository = tournamentResultRepository;
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

        log.info("TournamentService_findTournamentSong_end: success");
        return findRes;
    }

    /**
     * 플레이 한 이상형 월드컵을 각 플레이 마다 구분할 수 있도록 DB에 저장
     */
    @Override
    public Long registTournament() {

        log.info("TournamentService_registTournament_start");

        Tournament tournament = Tournament.builder()
            .build();

        tournament = tournamentRepository.save(tournament);

        log.info("TournamentService_registTournament_end: success");
        return tournament.getId();
    }

    /**
     * 이상형 월드컵 결과를 DB에 저장하는 API
     * @param registTournamentResultPostReq : 이상형 월드컵 진행 결과 1등 ~ 4등
     */
    @Override
    public List<TournamentRegistResultPostRes> registTournamentResult(
        RegistTournamentResultPostReq registTournamentResultPostReq) {

        log.info("TournamentService_registTournamentResult_start: "
            + registTournamentResultPostReq.toString());

        List<TournamentSong> tournamentSongs = new ArrayList<>();
        List<TournamentRegistResultPostRes> findRes = new ArrayList<>();

        Tournament tournament = tournamentRepository.findById(registTournamentResultPostReq.getTournamentId())
            .orElseThrow(NoDataException::new);

        tournamentSongs.add(tournamentSongRepository.findById(registTournamentResultPostReq.getFirstSongId())
            .orElseThrow(NoDataException::new));
        tournamentSongs.add(tournamentSongRepository.findById(registTournamentResultPostReq.getSecondSongId())
            .orElseThrow(NoDataException::new));
        tournamentSongs.add(tournamentSongRepository.findById(registTournamentResultPostReq.getSemiFinalSongOneId())
            .orElseThrow(NoDataException::new));
        tournamentSongs.add(tournamentSongRepository.findById(registTournamentResultPostReq.getSemiFinalSongTwoId())
            .orElseThrow(NoDataException::new));

        for(int i = 1; i <= 4; i++) {
            tournamentResultRepository.save(TournamentResult.builder()
                .tournament(tournament)
                .tournamentSong(tournamentSongs.get(i - 1))
                .ranking(i == 4 ? 3 : i)
                .build());

            findRes.add(TournamentRegistResultPostRes.builder()
                .songTitle(tournamentSongs.get(i - 1).getTitle())
                .songSinger(tournamentSongs.get(i - 1).getSinger())
                .imgUrl(tournamentSongs.get(i - 1).getImgUrl())
                .ranking(i == 4 ? 3 : i)
                .build());
        }

        tournamentSongs.get(0).addVote();

        log.info("sTournamentService_registTournamentResult_end: success");
        return findRes;
    }

//    /**
//     * 유저 Id 별 이상형 월드컵 결과를 가져오기 위한 서비스
//     * @param userId : 이상형 월드컵 플레이 유저 Id
//     */
//    @Override
//    public List<TournamentResultFindRes> findTournamentResult(Long userId) {
//        log.info("TournamentService_findTournamentResult_start: "
//            + userId);
//
//        List<TournamentResultFindRes> findRes = tournamentResultRepository.findTournamentResult(userId);
//
//        log.info("TournamentService_findTournamentResult_end: success");
//        return findRes;
//    }

    /**
     * 이상형 월드컵 모든 유저들의 플레이 합한 결과 노래들을 1등 많이 한 순위로 정렬해서 가져오는 API
     */
    @Override
    public List<TournamentPopularSongFindRes> findTournamentPopularSong() {
        log.info("TournamentService_findTournamentPopularSong_start");

        List<TournamentPopularSongFindRes> findRes = tournamentSongRepository.findTournamentPopularSong();

        log.info("TournamentService_findTournamentPopularSong_end: success");
        return findRes;
    }
}
