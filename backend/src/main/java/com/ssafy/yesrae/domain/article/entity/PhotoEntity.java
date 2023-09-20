package com.ssafy.yesrae.domain.article.entity;

import com.ssafy.yesrae.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@DynamicInsert
@Table(name = "photo")
@Where(clause = "deleted_at is null")
@Entity
public class PhotoEntity extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="article_id", referencedColumnName = "Id")
    private ArticleEntity articleEntity;

    @Column(nullable = false)
    private String image;
}
