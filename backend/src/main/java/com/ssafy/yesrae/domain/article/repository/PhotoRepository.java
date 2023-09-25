package com.ssafy.yesrae.domain.article.repository;

import com.ssafy.yesrae.domain.article.entity.Article;
import com.ssafy.yesrae.domain.article.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByArticleEntity_Id(Long articleId);

    List<Photo> findByArticle(Article article);
}
