package com.ssafy.yesrae.domain.article.service;

import com.ssafy.yesrae.domain.article.dto.request.ArticleRegistPostReq;
import com.ssafy.yesrae.domain.article.entity.ArticleEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {
    ArticleEntity registArticle(ArticleRegistPostReq articleRegistPostReq, MultipartFile file);

    Boolean deleteArticle(Long Id);

}
