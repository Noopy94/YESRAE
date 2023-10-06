package com.ssafy.yesrae.domain.song.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SonglikeFindRes {

    private String id;

    private String name;

    private String albumId;

    private String albumName;

    private String artistId;

    private String artistName;

    private String imgUrl;

    private String previewUrl;

    private Integer duration;

    private Integer releaseYear;
}
