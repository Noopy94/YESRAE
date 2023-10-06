package com.ssafy.yesrae.domain.article.entity;

import com.ssafy.yesrae.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@DynamicInsert
@Table(name = "tag")
@Where(clause = "deleted_at is null")
@Entity
public class Category extends BaseEntity {
    @Column(name = "tag_name",nullable = false, columnDefinition = "VARCHAR(20)")
    private String tagName;

    @Column // 기본값 null
    private LocalDateTime deletedAt;

    public void deleteArticle() {this.deletedAt = LocalDateTime.now();}
}
