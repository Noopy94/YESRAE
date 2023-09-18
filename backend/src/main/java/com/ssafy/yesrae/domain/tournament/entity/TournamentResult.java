package com.ssafy.yesrae.domain.tournament.entity;

import com.ssafy.yesrae.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

/**
 * 유저가 이상형 월드컵을 수행한 결과를 저장하는 DB. 몇 강을 선택했는지 여부와 상관 없이 1등 ~ 4등 노래가 결과로 저장됨
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@DynamicInsert
@Where(clause = "deleted_at is null")
@Entity
public class TournamentResult extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private TournamentSong tournamentSong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @Column(nullable = false, columnDefinition = "INT default 3")
    private Integer rank;
}
