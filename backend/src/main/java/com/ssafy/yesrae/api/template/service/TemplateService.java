package com.ssafy.yesrae.api.template.service;

import com.ssafy.yesrae.api.template.request.TemplateRegistPostReq;
import com.ssafy.yesrae.db.entity.TemplateArticle;

public interface TemplateService {

    TemplateArticle registTemplate(TemplateRegistPostReq registInfo);
}
