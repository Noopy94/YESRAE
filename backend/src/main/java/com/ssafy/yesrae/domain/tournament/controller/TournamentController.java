package com.ssafy.yesrae.domain.tournament.controller;

import com.ssafy.yesrae.common.exception.NoDataException;
import com.ssafy.yesrae.common.model.CommonResponse;
import com.ssafy.yesrae.domain.tournament.dto.request.FindTournamentSongGetReq;
import com.ssafy.yesrae.domain.tournament.dto.request.RegistTournamentResultPostReq;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentSongFindRes;
import com.ssafy.yesrae.domain.tournament.service.TournamentService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    @GetMapping("/auth/song")
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
     *  플레이 한 이상형 월드컵을 각 플레이 시점 마다 구분할 수 있도록 DB에 저장하는 API
     */
    @PostMapping
    public CommonResponse<?> registTournament(
        HttpServletRequest httpServletRequest) {

        // TODO: JWT access-token 에서 유저 PK 꺼낼 수 있는 로직 구현 필요(인가 처리)
        //Long userId = userService.getUserIdByToken(httpServletRequest.getHeader("access-token"));

        Long userId = 1L;
        log.info("TournamentController_registTournament_start: " + userId);

        tournamentService.registTournament(userId);

        log.info("TournamentController_registTournament_end: success");
        return CommonResponse.success(SUCCESS);
    }

    /**
     *  이상형 월드컵 결과를 DB에 저장하는 API
     */
    @PostMapping
    public CommonResponse<?> registTournamentResult(
        RegistTournamentResultPostReq registTournamentResultPostReq) {

        log.info("TournamentController_registTournamentResult_start: "
            + registTournamentResultPostReq.toString());



        log.info("TournamentController_registTournamentResult_end: success");
        return CommonResponse.success(SUCCESS);
    }
}
