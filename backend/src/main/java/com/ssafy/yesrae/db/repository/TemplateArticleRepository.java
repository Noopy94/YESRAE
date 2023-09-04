package com.ssafy.yesrae.db.repository;

import com.ssafy.yesrae.db.entity.TemplateArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    TemplateArticle Entity를 위한 Spring Data JPA Repository
 */
@Repository
public interface TemplateArticleRepository extends JpaRepository<TemplateArticle, Long> {

}
