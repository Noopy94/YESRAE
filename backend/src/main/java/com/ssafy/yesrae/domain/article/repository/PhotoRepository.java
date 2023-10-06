package com.ssafy.yesrae.domain.article.repository;

import com.ssafy.yesrae.domain.article.entity.Article;
import com.ssafy.yesrae.domain.article.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByArticle_Id(Long articleId);

    List<Photo> findByArticle(Article article);
}
