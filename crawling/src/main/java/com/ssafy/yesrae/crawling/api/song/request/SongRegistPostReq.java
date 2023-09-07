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
    private float similarity;
    private Integer duration;
    private Integer popularity;
    private float acousticness;
    private float danceability;
    private float energy;
    private float instrumentalness;
    private Integer key;
    private float liveness;
    private float loudness;
    private Integer mode;
    private float speechiness;
    private float tempo;
    private Integer timeSignature;
    private float valence;

}
