package com.ssafy.yesrae.domain.article.repository;

import com.ssafy.yesrae.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByTitleContainingAndCategoryEntity_Id(String title, Long id);
}