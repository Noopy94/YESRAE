package com.ssafy.yesrae.domain.comment.service;

import com.ssafy.yesrae.domain.comment.dto.request.PlayListCommentDeletePutReq;
import com.ssafy.yesrae.domain.comment.dto.request.PlayListCommentRegistPostReq;
import com.ssafy.yesrae.domain.comment.dto.response.PlayListCommentFindRes;
import com.ssafy.yesrae.domain.comment.entity.PlayListComment;
import java.util.List;

public interface PlayListCommentService {

    PlayListComment registPlayListComment(
        PlayListCommentRegistPostReq playListCommentRegistPostReq);

    List<PlayListCommentFindRes> findPlayListComment(Long PlayListId);

    boolean deletePlayListComment(PlayListCommentDeletePutReq playListCommentDeletePutReq);

}
