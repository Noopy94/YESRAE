package com.ssafy.yesrae.domain.tournament.entity;

import com.ssafy.yesrae.common.model.BaseEntity;
import com.ssafy.yesrae.domain.user.entity.User;
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

/**
 * 유저가 수행한 이상형 월드컵을 저장하는 DB
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@DynamicInsert
@Entity
public class Tournament extends BaseEntity {

    @CreationTimestamp
    private LocalDateTime date;
}
