package com.ssafy.yesrae.domain.song.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SongFindRes {

    private String id;

    private String name;

    private String albumId;

    private String albumName;

    private String artistId;

    private String artistName;

    private String imgUrl;

    private String previewUrl;

    private Integer releaseYear;

    private Integer duration;

    private Integer popularity;

    private Float acousticness;

    private Float danceability;

    private Float energy;

    private Float instrumentalness;

    private Integer tune;

    private Float liveness;

    private Float loudness;

    private Integer mode;

    private Float speechiness;

    private Float tempo;

    private Integer timeSignature;

    private Float valence;

    private Boolean songlike;

}
