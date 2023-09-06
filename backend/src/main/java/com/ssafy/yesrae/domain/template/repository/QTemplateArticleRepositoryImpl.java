package com.ssafy.yesrae.domain.template.repository;

import static com.ssafy.yesrae.domain.template.entity.QTemplateArticle.templateArticle;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.yesrae.domain.template.dto.request.TemplateFindByConditionGetReq;
import com.ssafy.yesrae.domain.template.dto.response.TemplateFindRes;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * Querydsl을 위한 Repository 구현 Template
 */
@Slf4j
@Repository
public class QTemplateArticleRepositoryImpl implements QTemplateArticleRepository{

    private final JPAQueryFactory queryFactory;

    public QTemplateArticleRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 게시글 검색어로 검색 쿼리 템플릿
     *
     * @param templateFindByConditionGetReq : 제목 검색어
     */
    @Override
    public List<TemplateFindRes> findByConditionTemplate(
        TemplateFindByConditionGetReq templateFindByConditionGetReq) {

        log.info("QTemplateArticleRepositoryImpl_findByConditionTemplate_start: "
            + templateFindByConditionGetReq.toString());

        return queryFactory
            .select(Projections.constructor(TemplateFindRes.class,
                templateArticle.title.as("title"),
                templateArticle.content.as("content"),
                templateArticle.viewCount.as("viewCount")))
            .from(templateArticle)
            .where(keywordSearch(templateFindByConditionGetReq.getKeyword()))
            .fetch();
    }

    /**
     * keyword 로 제목 검색
     */
    private BooleanExpression keywordSearch(String keyword) {
        return keyword == null ? null : templateArticle.title.contains(keyword);
    }
}
