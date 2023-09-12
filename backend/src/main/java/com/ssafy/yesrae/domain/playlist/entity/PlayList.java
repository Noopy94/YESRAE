package com.ssafy.yesrae.domain.playlist.entity;

import com.ssafy.yesrae.common.model.BaseEntity;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListImgModifyPutReq;
import com.ssafy.yesrae.domain.playlist.dto.request.PlayListModifyPutReq;
import com.ssafy.yesrae.domain.user.entity.User;
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

@Getter // Getter 자동 생성
@ToString   // toString 메소드 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 변수를 파라미터로 받는 생성자
@SuperBuilder   // Builder를 보완한 Annotation. 상속 받은 필드도 build 해줌, but experimental
@DynamicInsert  // INSERT 구문에서 null이 아닌 컬럼들만 실제로 insert
@Where(clause = "deleted_at is null")   // 일괄적으로 적용할 where 조건. 현재 clause는 soft delete를 위함
@Entity
public class PlayList extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 40)
    private String title;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private Integer isPublic; // 0 flase  1 true

    @Column   // 255가 기본이라서
    private String imgUrl; //img url

    @CreationTimestamp
    private LocalDateTime createdData;

    @Column // 기본값 null
    private LocalDateTime deletedAt;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long viewCount;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long likeCount;


    public  void modifyPlayListImg(String imgUrl){
        this.imgUrl = imgUrl;
    }

    public void modifyPlayList(PlayListModifyPutReq modifyInfo){
        this.isPublic = modifyInfo.getIsPublic();
        this.title = modifyInfo.getTitle();
        this.description = modifyInfo.getDescription();
        this.createdData =LocalDateTime.now();
    }

    public void deletePlayList() {
        this.deletedAt = LocalDateTime.now();
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {this.likeCount--;}

    public void incrementViewCount() {
        this.viewCount++;
    }

}
