package com.ssafy.yesrae.domain.comment.service;

import com.ssafy.yesrae.domain.comment.dto.request.PlaylistCommentDeletePutReq;
import com.ssafy.yesrae.domain.comment.dto.request.PlaylistCommentRegistPostReq;
import com.ssafy.yesrae.domain.comment.dto.response.PlaylistCommentFindRes;
import com.ssafy.yesrae.domain.comment.entity.PlaylistComment;
import java.util.List;

public interface PlaylistCommentService {

    PlaylistComment registPlaylistComment(
        PlaylistCommentRegistPostReq playlistCommentRegistPostReq);

    List<PlaylistCommentFindRes> findPlaylistComment(Long playlistId);

    boolean deletePlaylistComment(PlaylistCommentDeletePutReq playlistCommentDeletePutReq);

}
