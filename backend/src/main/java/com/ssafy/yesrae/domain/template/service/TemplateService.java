package com.ssafy.yesrae.domain.template.service;

import com.ssafy.yesrae.domain.template.dto.request.TemplateRegistPostReq;
import com.ssafy.yesrae.domain.template.dto.response.TemplateFindRes;
import com.ssafy.yesrae.domain.template.entity.TemplateArticle;
import java.util.List;

public interface TemplateService {

    TemplateArticle registTemplate(TemplateRegistPostReq registInfo);

    List<TemplateFindRes> findAllTemplate();

    Boolean deleteTemplate(Long articleId);
}
