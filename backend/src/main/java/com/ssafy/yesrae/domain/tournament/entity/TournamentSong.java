package com.ssafy.yesrae.domain.tournament.entity;

import com.ssafy.yesrae.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

/**
 * 이상형 월드컵에 수록 될 노래 256곡에 대한 Entity
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@DynamicInsert
@Where(clause = "deleted_at is null")
@Entity
public class TournamentSong extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 100)
    private String singer;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer vote;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHARACTER SET UTF8")
    private String imgUrl;

    /**
     * 이상형 월드컵 플레이 시마다 1위로 뽑힌 곡에 대하여 득표 수 올리기 위한 메소드
     */
    public void addVote() {
        this.vote++;
    }
}
