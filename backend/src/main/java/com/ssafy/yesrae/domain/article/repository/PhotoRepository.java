package com.ssafy.yesrae.domain.article.repository;

import com.ssafy.yesrae.domain.article.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {
    List<PhotoEntity> findByArticleEntity_Id(Long articleId);

}
