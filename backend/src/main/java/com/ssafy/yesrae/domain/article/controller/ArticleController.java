package com.ssafy.yesrae.domain.article.controller;

import com.ssafy.yesrae.common.exception.Template.FileIOException;
import com.ssafy.yesrae.common.model.CommonResponse;
import com.ssafy.yesrae.domain.article.dto.request.ArticleRegistPostReq;
import com.ssafy.yesrae.domain.article.entity.ArticleEntity;
import com.ssafy.yesrae.domain.article.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    private static final String SUCCESS = "success"; // API 성공 시 return

    private final ArticleService articleService;


    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * Store 등록
     */
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public CommonResponse<?> insertStore(@RequestPart ArticleRegistPostReq registInfo, @RequestPart(value = "file", required = false) MultipartFile file) {

        if (file != null) { // 게시물에 파일 있으면
            log.info("ArticleController_regist_start: " + registInfo.toString() + ", "
                    + file);
        } else {
            log.info("ArticleController_regist_start: " + registInfo.toString());
        }

        ArticleEntity articleEntity = articleService.registArticle(registInfo, file);
        if (articleEntity != null) {  // regist 성공하면 success
            log.info("ArticleController_regist_end: success");
            return CommonResponse.success(SUCCESS);
        } else {    // 실패하면 Exception
            throw new FileIOException();
        }
    }
}
