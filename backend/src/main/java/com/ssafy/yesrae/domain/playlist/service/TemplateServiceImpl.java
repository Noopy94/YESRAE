package com.ssafy.yesrae.domain.playlist.service;

import com.ssafy.yesrae.common.exception.NoDataException;
import com.ssafy.yesrae.domain.playlist.dto.request.TemplateRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.response.TemplateFindRes;
import com.ssafy.yesrae.domain.playlist.entity.TemplateArticle;
import com.ssafy.yesrae.domain.playlist.repository.TemplateArticleRepository;
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

    /**
     * 게시글 삭제 (Soft Delete) API 에 대한 서비스
     */
    @Override
    public Boolean deleteTemplate(Long articleId) {

        log.info("TemplateService_deleteTemplate_start: ");

        TemplateArticle templateArticle = templateArticleRepository.findById(articleId)
            .orElseThrow(NoDataException::new);

        // 실제 서비스에서는 article을 작성한 유저와 삭제 요청을 한 유저를 비교해서 둘이 같을 경우에만 삭제가 되도록.
        // 둘을 비교해서 두 유저 정보가 다를 경우 false 를 리턴하면 됨
        templateArticle.deleteTemplate();
        log.info("TemplateService_deleteTemplate_end: true");
        return true;
    }
}
