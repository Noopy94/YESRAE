package com.ssafy.yesrae.domain.comment.controller;

import com.ssafy.yesrae.common.exception.comment.CommentPossessionFailException;
import com.ssafy.yesrae.common.model.CommonResponse;
import com.ssafy.yesrae.domain.comment.dto.request.ArticleCommentDeletePutReq;
import com.ssafy.yesrae.domain.comment.dto.request.ArticleCommentRegistPostReq;
import com.ssafy.yesrae.domain.comment.dto.response.ArticleCommentFindRes;
import com.ssafy.yesrae.domain.comment.service.ArticleCommentService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/articleComment")
public class ArticleCommentController {

    private static final String SUCCESS = "success";

    private final ArticleCommentService articleCommentService;

    @Autowired
    public ArticleCommentController(ArticleCommentService articleCommentService) {
        this.articleCommentService = articleCommentService;
    }

    /**
     * 게시글 댓글 작성 API
     *
     * @Param ArticleCommentRegistPostReq : 댓글 작성할 때 입력한 정보
     */
    @PostMapping
    public CommonResponse<?> registArticleComment(
        @RequestBody ArticleCommentRegistPostReq articleCommentRegistPostReq) {

        log.info(
            "ArticleCommentController_regist_start: " + articleCommentRegistPostReq.toString());

        articleCommentService.registArticleComment(articleCommentRegistPostReq);

        log.info("ArticleCommentController_regist_end: success ");
        return CommonResponse.success(SUCCESS);
    }

    /**
     * 게시글 댓글 조회 API
     */
    @GetMapping("/{articleId}")
    public CommonResponse<?> findArticleComment(@PathVariable Long articleId) {

        log.info("ArticleCommentController_find_start: " + articleId);

        List<ArticleCommentFindRes> res = articleCommentService.findArticleComment(articleId);

        log.info("ArticleCommentController_find_end: " + res.toString());

        return CommonResponse.success(res);
    }

    /**
     * 게시글 댓글 삭제 API
     *
     * @Param ArticleCommentDeletePutReq : 댓글 Id 와 사용자 Id
     */
    @PutMapping("/delete")
    public CommonResponse<?> deleteArticleComment(
        @RequestBody ArticleCommentDeletePutReq articleCommentDeletePutReq) {

        log.info("ArticleCommentController_delete_start: "
            + articleCommentDeletePutReq.getArticleCommentId());

        boolean isDeleted = articleCommentService.deleteArticleComment(articleCommentDeletePutReq);

        if (isDeleted) {
            log.info("ArticleCommentController_delete_end: success");
            return CommonResponse.success(SUCCESS);
        } else {
            throw new CommentPossessionFailException();
        }
    }
}
