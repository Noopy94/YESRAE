package com.ssafy.yesrae.domain.playlist.entity;

import com.ssafy.yesrae.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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

/*
    Spring Data JPA Entity를 위한 Template 코드
    Annotations 중에서 @SuperBuilder 사용은 다시 공부하고 재고할 필요가 있음
 */
@Getter // Getter 자동 생성
@ToString   // toString 메소드 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 변수를 파라미터로 받는 생성자
@SuperBuilder   // Builder를 보완한 Annotation. 상속 받은 필드도 build 해줌, but experimental
@DynamicInsert  // INSERT 구문에서 null이 아닌 컬럼들만 실제로 insert
@Where(clause = "deleted_at is null")   // 일괄적으로 적용할 where 조건. 현재 clause는 soft delete를 위함
@Entity
public class PlayList extends BaseEntity {

    /*
        Foreign Key 작성하는 방법
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")   // DB table에 적용될 FK column 이름
     */

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    @Column(nullable = false, columnDefinition = "VARCHAR(40)")
    private String title;

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String description;

    @Column(nullable = false, columnDefinition = "BOOLEAN")
    private boolean isPublic;

    @CreationTimestamp
    private LocalDateTime createdData;

    @Column // 기본값 null
    private LocalDateTime deletedAt;

    public void deletePlayList() {
        this.deletedAt = LocalDateTime.now();
    }
}
