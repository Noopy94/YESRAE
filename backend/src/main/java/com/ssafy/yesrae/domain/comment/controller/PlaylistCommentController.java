package com.ssafy.yesrae.domain.comment.controller;

import com.ssafy.yesrae.common.exception.comment.CommentPossessionFailException;
import com.ssafy.yesrae.common.model.CommonResponse;
import com.ssafy.yesrae.domain.comment.dto.request.PlaylistCommentDeletePutReq;
import com.ssafy.yesrae.domain.comment.dto.request.PlaylistCommentRegistPostReq;
import com.ssafy.yesrae.domain.comment.dto.response.PlaylistCommentFindRes;
import com.ssafy.yesrae.domain.comment.service.PlaylistCommentService;
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
@RequestMapping("/playlistComment")
public class PlaylistCommentController {

    private static final String SUCCESS = "success";

    private final PlaylistCommentService playlistCommentService;

    @Autowired
    public PlaylistCommentController(PlaylistCommentService playlistCommentService) {
        this.playlistCommentService = playlistCommentService;
    }

    /**
     * 재생목록 댓글 작성 API
     *
     * @Param playlistCommentRegistPostReq : 댓글 작성할 때 입력한 정보
     */
    @PostMapping
    public CommonResponse<?> registPlaylistComment(
        @RequestBody PlaylistCommentRegistPostReq playlistCommentRegistPostReq) {

        log.info(
            "playlistCommentController_regist_start: " + playlistCommentRegistPostReq.toString());

        playlistCommentService.registPlaylistComment(playlistCommentRegistPostReq);

        log.info("playlistCommentController_regist_end: success ");
        return CommonResponse.success(SUCCESS);
    }

    /**
     * 재생목록 댓글 조회 API
     */
    @GetMapping("/{playlistId}")
    public CommonResponse<?> findPlaylistComment(@PathVariable Long playlistId) {

        log.info("playlistCommentController_find_start: " + playlistId);

        List<PlaylistCommentFindRes> res = playlistCommentService.findPlaylistComment(playlistId);

        log.info("playlistCommentController_find_end: " + res.toString());

        return CommonResponse.success(res);
    }

    /**
     * 재생목록 댓글 삭제 API
     *
     * @Param playlistCommentDeletePutReq : 댓글 Id 와 사용자 Id
     */
    @PutMapping("/delete")
    public CommonResponse<?> deletePlaylistComment(
        @RequestBody PlaylistCommentDeletePutReq playlistCommentDeletePutReq) {

        log.info("PlaylistCommentController_delete_start: "
            + playlistCommentDeletePutReq.toString());

        boolean isDeleted = playlistCommentService.deletePlaylistComment(
            playlistCommentDeletePutReq);

        if (isDeleted) {
            log.info("playlistCommentController_delete_end: success");
            return CommonResponse.success(SUCCESS);
        } else {
            throw new CommentPossessionFailException();
        }
    }
}