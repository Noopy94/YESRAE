package com.ssafy.yesrae.crawling.domain.song.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@SuperBuilder
@Entity
public class Song {
    @Id
    private String id;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHARACTER SET UTF8")
    private String name;

    @Column(nullable = false, length = 40)
    private String albumId;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHARACTER SET UTF8")
    private String albumName;

    @Column(nullable = false, length = 40)
    private String artistId;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHARACTER SET UTF8")
    private String artistName;

    @Column(length = 40)
    private String genre;

    @Column(nullable = false)
    private String imgUrl;

    @Column
    private String previewUrl;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer releaseYear;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer duration;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer popularity;

    // 1에 가까울수록 어쿠스틱(전자악기 덜 썼는지)입니다.
    @Column(nullable = false, columnDefinition = "FLOAT DEFAULT 0")
    private Float acousticness;

    // 1에 가까울수록 댄스에 적합한 곡입니다.
    @Column(nullable = false, columnDefinition = "FLOAT DEFAULT 0" )
    private Float danceability;

    // 1에 가까울수록 빠르고 시끄럽습니다.
    @Column(nullable = false, columnDefinition = "FLOAT DEFAULT 0")
    private Float energy;

    // 1에 가까울수록 보컬이 적습니다.
    @Column(nullable = false, columnDefinition = "FLOAT DEFAULT 0")
    private Float instrumentalness;

    // 음정 정보입니다. 찾지 못한 경우 -1입니다.
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer tune;

    // 0.8이상이면 라이브일 확률이 높습니다.
    @Column(nullable = false, columnDefinition = "FLOAT DEFAULT 0")
    private Float liveness;

    // 노래의 전체적은 데시벨입니다. 일반적으로 -60에서 0사이입니다.
    @Column(nullable = false, columnDefinition = "FLOAT DEFAULT 0")
    private Float loudness;

    // 음계가 메이저의 경우 1 마이너의 경우 0으로 표시됩니다.
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer mode;

    // 말로 된 단어가 많을수록 1에 가까워집니다. 음악과 말이 포함된 노래는 보통 0.33과 0.66 사이에 위치합니다.
    @Column(nullable = false, columnDefinition = "FLOAT DEFAULT 0")
    private Float speechiness;

    // 추정된 노래 전체의 템포(BPM)입니다.
    @Column(nullable = false, columnDefinition = "FLOAT DEFAULT 0")
    private Float tempo;

    // 추정된 박자입니다. 3-7 사이에 위치합니다.
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer timeSignature;

    // 1에 가까울수록 노래가 긍정적으로 들립니다.
    @Column(nullable = false, columnDefinition = "FLOAT DEFAULT 0")
    private Float valence;

    // 노래 꼬맨틀 참여 여부
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT 0")
    private Boolean todaySong;

}
