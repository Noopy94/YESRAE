package com.ssafy.yesrae.domain.playlist.controller;

import com.ssafy.yesrae.common.exception.Template.TemplateNoResultException;
import com.ssafy.yesrae.common.exception.Template.TemplatePossessionFailException;
import com.ssafy.yesrae.common.model.CommonResponse;
import com.ssafy.yesrae.domain.playlist.dto.request.TemplateRegistPostReq;
import com.ssafy.yesrae.domain.playlist.dto.response.TemplateFindRes;
import com.ssafy.yesrae.domain.playlist.service.TemplateService;
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
     * @param registInfo : 게시글 등록할 때 입력한 정보
     */
    @PostMapping
    public CommonResponse<?> regist(@RequestBody TemplateRegistPostReq registInfo) {
        
        log.info("TemplateController_regist_start: " + registInfo.toString());

        templateService.registTemplate(registInfo);

        log.info("TemplateController_regist_end: success");
        return CommonResponse.success(SUCCESS);
    }

    /**
     * 게시글 목록 조회 API
     */
    @GetMapping
    public CommonResponse<?> findAll() {

        log.info("TemplateController_findAll_start: ");

        Optional<List<TemplateFindRes>> findRes = Optional.ofNullable(
            templateService.findAllTemplate());

        log.info("TemplateController_findAll_end: " + findRes);
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
