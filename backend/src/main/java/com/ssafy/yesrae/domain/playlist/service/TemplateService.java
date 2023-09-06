package com.ssafy.yesrae.domain.playlist.service;

import com.ssafy.yesrae.domain.playlist.dto.request.TemplateRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.response.TemplateFindRes;
import com.ssafy.yesrae.domain.playlist.entity.TemplateArticle;
import java.util.List;

public interface TemplateService {

    TemplateArticle registTemplate(TemplateRegistPostReq registInfo);

    List<TemplateFindRes> findAllTemplate();

    Boolean deleteTemplate(Long articleId);
}
