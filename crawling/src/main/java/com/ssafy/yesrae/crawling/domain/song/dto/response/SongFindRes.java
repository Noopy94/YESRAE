package com.ssafy.yesrae.crawling.domain.song.dto.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class SongFindRes {

    private String id;
    private String name;
    private String artistId;
    private Integer popularity;

}
