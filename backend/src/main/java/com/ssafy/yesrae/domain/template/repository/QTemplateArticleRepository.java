package com.ssafy.yesrae.domain.template.repository;


import com.ssafy.yesrae.domain.template.dto.request.TemplateFindByConditionGetReq;
import com.ssafy.yesrae.domain.template.dto.response.TemplateFindRes;
import java.util.List;

/**
 * Querydsl을 위한 Repository Template
 */
public interface QTemplateArticleRepository {

    List<TemplateFindRes> findByConditionTemplate(
        TemplateFindByConditionGetReq templateFindByConditionGetReq);
}
