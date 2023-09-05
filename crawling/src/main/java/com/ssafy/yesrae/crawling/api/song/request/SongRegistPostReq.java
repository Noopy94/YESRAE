package com.ssafy.yesrae.crawling.api.song.request;

import java.time.LocalDate;
import lombok.Data;

@Data
public class SongRegistPostReq {

    private String id;
    private String name;
    private String albumId;
    private String albumName;
    private String artistId;
    private String artistName;
    private String genre;
    private String imgUrl;
    private String previewUrl;
    private LocalDate releaseDate;
    private Float similarity;
    private Integer duration;
    private Integer popularity;

}
