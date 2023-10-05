package com.ssafy.yesrae.domain.tournament.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * DB에 저장된 이상형 월드컵 결과를 각 노래의 이미지와 함께 가져오는 API
 */
@Data
@Builder
public class TournamentRegistResultPostRes {

    private String songTitle;

    private String songSinger;

    private String imgUrl;
}
