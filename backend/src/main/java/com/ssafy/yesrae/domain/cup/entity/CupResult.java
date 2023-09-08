package com.ssafy.yesrae.domain.cup.entity;

import com.ssafy.yesrae.common.model.BaseEntity;
import com.ssafy.yesrae.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
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
public class CupResult extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private CupSong cupSong;

    @CreationTimestamp
    private LocalDateTime deletedAt;

    @Column(nullable = false, columnDefinition = "BOOLEAN")
    private Boolean isWinner;
}
