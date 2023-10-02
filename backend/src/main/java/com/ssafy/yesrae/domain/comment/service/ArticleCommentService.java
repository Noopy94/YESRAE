package com.ssafy.yesrae.domain.comment.service;

import com.ssafy.yesrae.domain.comment.dto.request.ArticleCommentDeletePutReq;
import com.ssafy.yesrae.domain.comment.dto.request.ArticleCommentRegistPostReq;
import com.ssafy.yesrae.domain.comment.dto.response.ArticleCommentFindRes;
import com.ssafy.yesrae.domain.comment.entity.ArticleComment;
import java.util.List;

public interface ArticleCommentService {

    ArticleComment registArticleComment(ArticleCommentRegistPostReq articleCommentRegistPostReq);

    List<ArticleCommentFindRes> findArticleCommentByArticleId(Long ArticleId);

    ArticleCommentFindRes findArticleComment(Long CommentId);

    boolean deleteArticleComment(ArticleCommentDeletePutReq articleCommentDeletePutReq);

}
