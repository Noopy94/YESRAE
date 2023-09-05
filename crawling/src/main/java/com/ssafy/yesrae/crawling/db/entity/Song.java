package com.ssafy.yesrae.crawling.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.web.ErrorResponse.Builder;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
public class Song {

    @Id
    private String id;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(nullable = false, length = 40)
    private String albumId;

    @Column(nullable = false, length = 100)
    private String albumName;

    @Column(nullable = false, length = 40)
    private String artistId;

    @Column(nullable = false, length = 100)
    private String artistName;

    @Column(length = 40)
    private String genre;

    @Column(nullable = false)
    private String imgUrl;

    @Column
    private String previewUrl;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate releaseDate;

    @Column(nullable = false, columnDefinition = "FLOAT DEFAULT 0")
    private Float similarity;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer duration;

    @Column(nullable = false)
    private Integer popularity;

}
