package com.ssafy.yesrae.api.template.controller;

import com.ssafy.yesrae.api.template.request.TemplateRegistPostReq;
import com.ssafy.yesrae.api.template.service.TemplateService;
import com.ssafy.yesrae.common.exception.Template.TemplateRegistFailException;
import com.ssafy.yesrae.common.model.CommonResponse;
import com.ssafy.yesrae.db.entity.TemplateArticle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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

        TemplateArticle templateArticle = templateService.registTemplate(registInfo);
        if (templateArticle != null) {  // regist 성공하면 success
            log.info("TemplateController_regist_end: success");
            return CommonResponse.success(SUCCESS);
        } else {    // 실패하면 Exception
            throw new TemplateRegistFailException();
        }
    }
}
