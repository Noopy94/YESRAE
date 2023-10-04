package com.ssafy.yesrae.domain.comment.service;

import com.ssafy.yesrae.common.exception.NoDataException;
import com.ssafy.yesrae.common.exception.article.ArticleNotFoundException;
import com.ssafy.yesrae.common.exception.comment.CommentNotFoundException;
import com.ssafy.yesrae.common.exception.user.UserNotFoundException;
import com.ssafy.yesrae.domain.article.entity.Article;
import com.ssafy.yesrae.domain.article.repository.ArticleRepository;
import com.ssafy.yesrae.domain.comment.dto.request.ArticleCommentDeletePutReq;
import com.ssafy.yesrae.domain.comment.dto.request.ArticleCommentRegistPostReq;
import com.ssafy.yesrae.domain.comment.dto.response.ArticleCommentFindRes;
import com.ssafy.yesrae.domain.comment.entity.ArticleComment;
import com.ssafy.yesrae.domain.comment.repository.ArticleCommentRepository;
import com.ssafy.yesrae.domain.user.entity.User;
import com.ssafy.yesrae.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleCommentServiceImpl(ArticleCommentRepository articleCommentRepository,
        UserRepository userRepository, ArticleRepository articleRepository) {
        this.articleCommentRepository = articleCommentRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    /**
     * 게시글 댓글 Regist API 에 대한 서비스
     *
     * @param articleCommentRegistPostReq : 게시글 댓글 등록할 때 입력한 정보
     */

    @Override
    public ArticleComment registArticleComment(
        ArticleCommentRegistPostReq articleCommentRegistPostReq) {

        log.info("ArticleCommentService_registArticleComment_start: "
            + articleCommentRegistPostReq.toString());

        String content = articleCommentRegistPostReq.getContent();

        User user = userRepository.findById(articleCommentRegistPostReq.getUserId())
            .orElseThrow(UserNotFoundException::new);

        Article article = articleRepository.findById(articleCommentRegistPostReq.getArticleId())
            .orElseThrow(ArticleNotFoundException::new);

        ArticleComment articleComment = ArticleComment.builder()
            .user(user)
            .article(article)
            .content(content)
            .build();

        articleCommentRepository.save(articleComment);
        log.info("ArticleCommentService_registArticleComment_end : success");
        return articleComment;

    }

    /**
     * 특정 게시글의 댓글 전체 조회 API에 대한 서비스
     */
    @Override
    public List<ArticleCommentFindRes> findArticleCommentByArticleId(Long ArticleId) {
        log.info("ArticleCommentService_findArticleCommentByArticleId_start: " + ArticleId);

        List<ArticleCommentFindRes> res = articleCommentRepository.findByArticleId(ArticleId)
            .stream().map(m -> ArticleCommentFindRes.builder()
                .id(m.getId())
                .articleId(m.getArticle().getId())
                .userId(m.getUser().getId())
                .content(m.getContent())
                .creaetedAt(m.getCreatedAt())
                .build()
            ).collect(Collectors.toList());

        log.info("ArticleCommentService_findArticleCommentByArticleId_end: success ");

        return res;
    }

    /**
     * 특정 댓글 조회 API에 대한 서비스
     */
    @Override
    public ArticleCommentFindRes findArticleComment(Long CommentId) {

        log.info("ArticleCommentService_findArticleComment_start: " + CommentId);

        ArticleComment comment = articleCommentRepository.findById(CommentId)
            .orElseThrow(CommentNotFoundException::new);

        ArticleCommentFindRes res = ArticleCommentFindRes.builder()
            .id(comment.getId())
            .articleId(comment.getArticle().getId())
            .userId(comment.getUser().getId())
            .content(comment.getContent())
            .creaetedAt(comment.getCreatedAt())
            .build();

        log.info("ArticleCommentService_findArticleComment_end: success");

        return res;
    }

    /**
     * 게시글 댓글 삭제 (Soft Delete) API 에 대한 서비스
     */
    @Override
    public boolean deleteArticleComment(ArticleCommentDeletePutReq articleCommentDeletePutReq) {

        log.info("ArticleCommentService_deleteArticleComment_start: "
            + articleCommentDeletePutReq.toString());

        ArticleComment articleComment = articleCommentRepository.findById(
                articleCommentDeletePutReq.getId())
            .orElseThrow(
                NoDataException::new);
        if (articleComment.getUser().getId().equals(articleCommentDeletePutReq.getUserId())) {
            articleComment.deletedArticleComment();
        } else {
            return false;
        }

        log.info("ArticleCommentService_deleteArticleComment_end: success");
        return true;
    }
}
