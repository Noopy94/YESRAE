package com.ssafy.yesrae.domain.template.repository;

import com.ssafy.yesrae.domain.template.entity.TemplateArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    TemplateArticle Entity를 위한 Spring Data JPA Repository
 */
@Repository
public interface TemplateArticleRepository extends JpaRepository<TemplateArticle, Long>, QTemplateArticleRepository {

}
