package com.ssafy.yesrae.api.template.service;

import com.ssafy.yesrae.api.template.request.TemplateRegistPostReq;
import com.ssafy.yesrae.api.template.response.TemplateFindRes;
import com.ssafy.yesrae.db.entity.TemplateArticle;
import java.util.List;

public interface TemplateService {

    TemplateArticle registTemplate(TemplateRegistPostReq registInfo);

    List<TemplateFindRes> findAllTemplate();

    Boolean deleteTemplate(Long articleId);
}
