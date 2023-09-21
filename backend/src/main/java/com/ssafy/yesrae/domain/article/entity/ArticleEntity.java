package com.ssafy.yesrae.domain.article.entity;

import com.ssafy.yesrae.common.model.BaseEntity;
import jakarta.persistence.*;
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
@Table(name = "article")
@Where(clause = "deleted_at is null")
@Entity
public class ArticleEntity extends BaseEntity {

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    @Column(nullable = false)
    private Boolean type;

    @Column(nullable = false, columnDefinition = "VARCHAR(40)")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    //잡담(0), 질문(1), 추천(2)
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="category", referencedColumnName = "Id")
    private CategoryEntity categoryEntity;

    @Column(name = "created_date",nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updateDate;

    @Column // 기본값 null
    private LocalDateTime deletedAt;

    //등록시 현재 시간으로 설정
    public void insertArticle() {this.createdDate = LocalDateTime.now();}
    //삭제시 현재 시간으로 설정
    public void deleteArticle() {this.deletedAt = LocalDateTime.now();}

    public void modifyArticle() {this.updateDate = LocalDateTime.now();}

    public void modifyArticle(String title, String content, CategoryEntity categoryEntity){
        this.categoryEntity = categoryEntity;
        this.title = title;
        this.content = content;
    }
}
