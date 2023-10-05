package com.ssafy.yesrae.domain.tournament.controller;

import com.ssafy.yesrae.common.exception.NoDataException;
import com.ssafy.yesrae.common.model.CommonResponse;
import com.ssafy.yesrae.domain.tournament.dto.request.FindTournamentSongGetReq;
import com.ssafy.yesrae.domain.tournament.dto.request.RegistTournamentResultPostReq;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentPopularSongFindRes;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentRegistPostRes;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentRegistResultPostRes;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentResultFindRes;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentSongFindRes;
import com.ssafy.yesrae.domain.tournament.service.TournamentService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 이상형 월드컵 API를 위한 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/tournament")
public class TournamentController {

    private static final String SUCCESS = "success"; // API 성공 시 return

    private final TournamentService tournamentService;

    @Autowired
    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    /**
     *  이상형 월드컵을 시작하기 위해 이상형 월드컵 게임에 들어올 노래 가져오기
     */
    @GetMapping("/song")
    public CommonResponse<?> findTournamentSong(FindTournamentSongGetReq findTournamentSongGetReq) {

        log.info("TournamentController_findTournamentSong_start: "
            + findTournamentSongGetReq.toString());

        Optional<List<TournamentSongFindRes>> findRes = Optional.ofNullable(
            tournamentService.findTournamentSong(findTournamentSongGetReq));

        log.info("TournamentController_findTournamentSong_end: "
            + findRes.toString());
        return CommonResponse.success(findRes.orElseThrow(NoDataException::new));
    }

    /**
     *  플레이 한 이상형 월드컵을 각 플레이 마다 구분할 수 있도록 DB에 저장
     */
    @PostMapping()
    public CommonResponse<?> registTournament(@PathVariable Long userId) {

        log.info("TournamentController_registTournament_start: " + userId);

        Long tournamentId = tournamentService.registTournament(userId);

        TournamentRegistPostRes tournamentRegistPostRes = TournamentRegistPostRes.builder()
            .id(tournamentId)
            .build();

        log.info("TournamentController_registTournament_end: " + tournamentRegistPostRes.toString());
        return CommonResponse.success(tournamentRegistPostRes);
    }

    /**
     *  이상형 월드컵 결과를 DB에 저장하는 API
     */
    @PostMapping("/result")
    public CommonResponse<?> registTournamentResult(
        @RequestBody RegistTournamentResultPostReq registTournamentResultPostReq) {

        log.info("TournamentController_registTournamentResult_start: "
            + registTournamentResultPostReq.toString());

        List<TournamentRegistResultPostRes> findRes =
            tournamentService.registTournamentResult(registTournamentResultPostReq);

        log.info("TournamentController_registTournamentResult_end: " + findRes.toString());
        return CommonResponse.success(findRes);
    }

    /**
     *  유저의 이상형 월드컵 플레이 결과 로그를 불러오는 API
     */
    @GetMapping("/result/{userId}")
    public CommonResponse<?> findTournamentResult(@PathVariable Long userId) {

        log.info("TournamentController_findTournamentResult_start: "
            + userId);

        List<TournamentResultFindRes> findRes = tournamentService.findTournamentResult(userId);

        log.info("TournamentController_findTournamentResult_end: " + findRes.toString());
        return CommonResponse.success(findRes);
    }

    /**
     *  이상형 월드컵 모든 유저들의 플레이 합한 결과 노래들을 1등 많이 한 순위로 정렬해서 가져오는 API
     */
    @GetMapping("/ranking")
    public CommonResponse<?> findTournamentPopularSong() {

        log.info("TournamentController_findTournamentPopularSong_start");

        List<TournamentPopularSongFindRes> findRes = tournamentService.findTournamentPopularSong();

        log.info("TournamentController_findTournamentPopularSong_end: " + findRes.toString());
        return CommonResponse.success(findRes);
    }
}
