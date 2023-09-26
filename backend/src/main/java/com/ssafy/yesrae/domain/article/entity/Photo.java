package com.ssafy.yesrae.domain.article.entity;

import com.ssafy.yesrae.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@DynamicInsert
@Table(name = "photo")
@Where(clause = "deleted_at is null")
@Entity
public class Photo extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="article_id", referencedColumnName = "Id")
    private Article article;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String fileName; // 실제 업로드 된 파일명

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String originalName; // 원본 파일 이름

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String path; // 파일 업로드 경로

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String contentType; // 확장자명

    @CreationTimestamp
    private LocalDateTime time; // 업로드 시간

    @Column
    private LocalDateTime deletedAt;

    public void deletePhoto() {
        this.deletedAt = LocalDateTime.now();
    }
}
