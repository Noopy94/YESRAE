package com.ssafy.yesrae.domain.tournament.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * 이상형 월드컵 DB에 저장된 튜플 찾기 위한 id response
 */
@Data
@Builder
public class TournamentRegistPostRes {

    private Long id;
}
