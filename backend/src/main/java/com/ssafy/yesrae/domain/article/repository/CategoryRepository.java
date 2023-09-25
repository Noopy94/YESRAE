package com.ssafy.yesrae.domain.article.repository;

import com.ssafy.yesrae.domain.article.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
