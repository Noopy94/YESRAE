package com.ssafy.yesrae.domain.article.repository;

import com.ssafy.yesrae.domain.article.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    List<ArticleEntity> findByTitleContainingAndCategoryEntity_Id(String title, Long id);
}