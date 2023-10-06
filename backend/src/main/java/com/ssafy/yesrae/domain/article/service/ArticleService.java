package com.ssafy.yesrae.domain.article.service;

import com.ssafy.yesrae.domain.article.dto.request.ArticleDeletePutReq;
import com.ssafy.yesrae.domain.article.dto.request.ArticleFindCondition;
import com.ssafy.yesrae.domain.article.dto.request.ArticleModifyPutReq;
import com.ssafy.yesrae.domain.article.dto.request.ArticleRegistPostReq;
import com.ssafy.yesrae.domain.article.dto.response.ArticleFindRes;
import com.ssafy.yesrae.domain.article.entity.Article;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {
    Article registArticle(ArticleRegistPostReq articleRegistPostReq, List<MultipartFile> files);

    Boolean deleteArticle(ArticleDeletePutReq articleDeletePutReq);

    ArticleFindRes findArticle(Long Id);

    boolean modifyArticle(ArticleModifyPutReq articleModifyPutReq, List<MultipartFile> files);

    List<ArticleFindRes> findAllArticle();

    List<ArticleFindRes> findArticleByTitleAndCategory(ArticleFindCondition articleFindCondition);
}
