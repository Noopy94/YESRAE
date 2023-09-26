package com.ssafy.yesrae.domain.comment.controller;

import com.ssafy.yesrae.common.exception.comment.CommentPossessionFailException;
import com.ssafy.yesrae.common.model.CommonResponse;
import com.ssafy.yesrae.domain.comment.dto.request.PlayListCommentDeletePutReq;
import com.ssafy.yesrae.domain.comment.dto.request.PlayListCommentRegistPostReq;
import com.ssafy.yesrae.domain.comment.dto.response.PlayListCommentFindRes;
import com.ssafy.yesrae.domain.comment.service.PlayListCommentService;
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
@RequestMapping("/playListComment")
public class PlayListCommentController {

    private static final String SUCCESS = "success";

    private final PlayListCommentService playListCommentService;

    @Autowired
    public PlayListCommentController(PlayListCommentService playListCommentService) {
        this.playListCommentService = playListCommentService;
    }

    /**
     * 재생목록 댓글 작성 API
     *
     * @Param PlayListCommentRegistPostReq : 댓글 작성할 때 입력한 정보
     */
    @PostMapping
    public CommonResponse<?> registPlayListComment(
        @RequestBody PlayListCommentRegistPostReq playListCommentRegistPostReq) {

        log.info(
            "PLayListCommentController_regist_start: " + playListCommentRegistPostReq.toString());

        playListCommentService.registPlayListComment(playListCommentRegistPostReq);

        log.info("PlayListCommentController_regist_end: success ");
        return CommonResponse.success(SUCCESS);
    }

    /**
     * 재생목록 댓글 조회 API
     */
    @GetMapping("/{playListId}")
    public CommonResponse<?> findPlayListComment(@PathVariable Long playListId) {

        log.info("PlayListCommentController_find_start: " + playListId);

        List<PlayListCommentFindRes> res = playListCommentService.findPlayListComment(playListId);

        log.info("PlayListCommentController_find_end: " + res.toString());

        return CommonResponse.success(res);
    }

    /**
     * 재생목록 댓글 삭제 API
     *
     * @Param PlayListCommentDeletePutReq : 댓글 Id 와 사용자 Id
     */
    @PutMapping("/delete")
    public CommonResponse<?> deletePlayListComment(
        @RequestBody PlayListCommentDeletePutReq playListCommentDeletePutReq) {

        log.info("PlayListCommentController_delete_start: "
            + playListCommentDeletePutReq.getPlayListCommentId());

        boolean isDeleted = playListCommentService.deletePlayListComment(
            playListCommentDeletePutReq);

        if (isDeleted) {
            log.info("PlayListCommentController_delete_end: success");
            return CommonResponse.success(SUCCESS);
        } else {
            throw new CommentPossessionFailException();
        }
    }
}