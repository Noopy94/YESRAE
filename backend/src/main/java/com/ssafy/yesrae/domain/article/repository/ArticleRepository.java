package com.ssafy.yesrae.domain.article.repository;

import com.ssafy.yesrae.domain.article.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {
}
