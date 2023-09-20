package com.ssafy.yesrae.domain.template.service;

import com.ssafy.yesrae.domain.template.dto.request.TemplateFindByConditionGetReq;
import com.ssafy.yesrae.domain.template.dto.request.TemplateRegistPostReq;
import com.ssafy.yesrae.domain.template.dto.response.TemplateFindRes;
import com.ssafy.yesrae.domain.template.entity.TemplateArticle;
import java.util.List;

public interface TemplateService {

    TemplateArticle registTemplate(TemplateRegistPostReq templateRegistPostReq);

    List<TemplateFindRes> findAllTemplate();

    List<TemplateFindRes> findByConditionTemplate(TemplateFindByConditionGetReq templateFindByConditionGetReq);

    Boolean deleteTemplate(Long articleId);
}
