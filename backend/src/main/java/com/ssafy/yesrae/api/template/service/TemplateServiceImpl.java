package com.ssafy.yesrae.api.template.service;

import com.ssafy.yesrae.api.template.request.TemplateRegistPostReq;
import com.ssafy.yesrae.api.template.response.TemplateFindRes;
import com.ssafy.yesrae.db.entity.TemplateArticle;
import com.ssafy.yesrae.db.repository.TemplateArticleRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Template Service
 */
@Slf4j
@Transactional
@Service
public class TemplateServiceImpl implements TemplateService {

    // Spring Data Jpa 사용을 위한 Repositories
    private final TemplateArticleRepository templateArticleRepository;

    @Autowired
    public TemplateServiceImpl(TemplateArticleRepository templateArticleRepository) {
        this.templateArticleRepository = templateArticleRepository;
    }

    /**
     * 게시글 Regist API 에 대한 서비스
     *
     * @param registInfo : 게시글 등록할 때 입력한 정보
     */
    @Override
    public TemplateArticle registTemplate(TemplateRegistPostReq registInfo) {

        log.info("TemplateService_registTemplate_start: " + registInfo.toString());

        String title = registInfo.getTitle();
        String content = registInfo.getContent();

        TemplateArticle templateArticle = TemplateArticle.builder()
            .title(title)
            .content(content)
            .build();

        templateArticleRepository.save(templateArticle);

        log.info("TemplateService_registTemplate_end: success");
        return templateArticle;
    }

    /**
     *  게시글 전체 조회 API에 대한 서비스
     */
    @Override
    public List<TemplateFindRes> findAllTemplate() {

        log.info("TemplateService_findAllTemplate_start: ");

        List<TemplateFindRes> res = templateArticleRepository.findAll()
                .stream().map(m -> TemplateFindRes.builder()
                .title(m.getTitle())
                .content(m.getContent())
                .viewCount(m.getViewCount())
                .build()
            ).collect(Collectors.toList());

        log.info("TemplateService_findAllTemplate_end: success");
        return res;
    }
}
