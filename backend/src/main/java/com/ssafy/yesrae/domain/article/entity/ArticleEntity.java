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
@Where(clause = "deleted_at is null")
@Entity
public class ArticleEntity extends BaseEntity {

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    @Column(nullable = false)
    private int type;

    @Column(nullable = false, columnDefinition = "VARCHAR(40)")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    //잡담(0), 질문(1), 추천(2)
    @Column(nullable = false)
    private int category;


}
