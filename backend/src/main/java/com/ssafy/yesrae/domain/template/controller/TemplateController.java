package com.ssafy.yesrae.domain.template.controller;

import com.ssafy.yesrae.domain.template.dto.request.TemplateFindByConditionGetReq;
import com.ssafy.yesrae.domain.template.dto.request.TemplateRegistPostReq;
import com.ssafy.yesrae.domain.template.dto.response.TemplateFindRes;
import com.ssafy.yesrae.domain.template.service.TemplateService;
import com.ssafy.yesrae.common.exception.Template.TemplateNoResultException;
import com.ssafy.yesrae.common.exception.Template.TemplatePossessionFailException;
import com.ssafy.yesrae.common.model.CommonResponse;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Template Controller
 */
@Slf4j
@RestController
@RequestMapping("/template")
public class TemplateController {

    // log 형식은 협의해서 새로 결정해도 무방
    private static final String SUCCESS = "success"; // API 성공 시 return

    private final TemplateService templateService;

    @Autowired
    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    /**
     * 게시글을 등록하기 위한 API
     *
     * @param templateRegistPostReq : 게시글 등록할 때 입력한 정보
     */
    @PostMapping
    public CommonResponse<?> regist(@RequestBody TemplateRegistPostReq templateRegistPostReq) {
        
        log.info("TemplateController_regist_start: " + templateRegistPostReq.toString());

        templateService.registTemplate(templateRegistPostReq);

        log.info("TemplateController_regist_end: success");
        return CommonResponse.success(SUCCESS);
    }

    /**
     * 게시글 목록 조회 API
     */
    @GetMapping
    public CommonResponse<?> findAll() {

        log.info("TemplateController_findAll_start: ");

        // 검색 결과 없을 경우 어떻게 할지 보완 필요 (현재 null 이 아니라 빈 리스트라서 exception 발동 안 함)
        Optional<List<TemplateFindRes>> findRes = Optional.ofNullable(
            templateService.findAllTemplate());

        log.info("TemplateController_findAll_end: " + findRes);
        return CommonResponse.success(findRes.orElseThrow(TemplateNoResultException::new));
    }

    /**
     *  게시글 검색어로 검색하여 조회 API
     */
    @GetMapping("/search")
    public CommonResponse<?> findByCondition(
        TemplateFindByConditionGetReq templateFindByConditionGetReq) {

        log.info("TemplateController_findByCondition_start: " + templateFindByConditionGetReq.toString());

        // 검색 결과 없을 경우 어떻게 할지 보완 필요 (현재 null 이 아니라 빈 리스트라서 exception 발동 안 함)ㄴ
        Optional<List<TemplateFindRes>> findRes = Optional.ofNullable(
            templateService.findByConditionTemplate(templateFindByConditionGetReq));

        log.info("TemplateController_findByCondition_end: " + findRes);
        return CommonResponse.success(findRes.orElseThrow(TemplateNoResultException::new));
    }

    /**
     * 게시글을 삭제하기 위한 API
     */
    @PutMapping("/delete/{articleId}")
    public CommonResponse<?> delete(@PathVariable Long articleId) {

        log.info("TemplateController_delete_start: " + articleId);

        boolean isDeleted = templateService.deleteTemplate(articleId);

        if (isDeleted) {    // 삭제 성공하면 success
            log.info("TemplateController_delete_end: success");
            return CommonResponse.success(SUCCESS);
        } else {    // 삭제 실패하면 Exception
            throw new TemplatePossessionFailException();
        }
    }
}
