package com.ssafy.yesrae.domain.comment.service;

import com.ssafy.yesrae.common.exception.NoDataException;
import com.ssafy.yesrae.common.exception.playList.PlayListNotFoundException;
import com.ssafy.yesrae.common.exception.user.UserNotFoundException;
import com.ssafy.yesrae.domain.comment.dto.request.PlayListCommentDeletePutReq;
import com.ssafy.yesrae.domain.comment.dto.request.PlayListCommentRegistPostReq;
import com.ssafy.yesrae.domain.comment.dto.response.PlayListCommentFindRes;
import com.ssafy.yesrae.domain.comment.entity.PlayListComment;
import com.ssafy.yesrae.domain.comment.repository.PlayListCommentRepository;
import com.ssafy.yesrae.domain.playlist.entity.PlayList;
import com.ssafy.yesrae.domain.playlist.repository.PlayListRepository;
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
public class PlayListCommentServiceImpl implements PlayListCommentService {

    private final PlayListCommentRepository playListCommentRepository;
    private final UserRepository userRepository;
    private final PlayListRepository playListRepository;

    @Autowired
    public PlayListCommentServiceImpl(PlayListCommentRepository playListCommentRepository,
        UserRepository userRepository, PlayListRepository playListRepository) {
        this.playListCommentRepository = playListCommentRepository;
        this.userRepository = userRepository;
        this.playListRepository = playListRepository;
    }

    /**
     * 재생목록 댓글 Regist API 에 대한 서비스
     *
     * @param playListCommentRegistPostReq : 게시글 댓글 등록할 때 입력한 정보
     */

    @Override
    public PlayListComment registPlayListComment(
        PlayListCommentRegistPostReq playListCommentRegistPostReq) {

        log.info("PlayListCommentService_registPlayListComment_start: "
            + playListCommentRegistPostReq.toString());

        String content = playListCommentRegistPostReq.getContent();

        User user = userRepository.findById(playListCommentRegistPostReq.getUserId())
            .orElseThrow(UserNotFoundException::new);

        PlayList playList = playListRepository.findById(
                playListCommentRegistPostReq.getPlayListId())
            .orElseThrow(PlayListNotFoundException::new);

        PlayListComment playListComment = PlayListComment.builder()
            .user(user)
            .playList(playList)
            .content(content)
            .build();

        playListCommentRepository.save(playListComment);
        log.info("PlayListCommentService_registPlayListComment_end : success");
        return playListComment;

    }

    /**
     * 특정 재생목록의 댓글 전체 조회 API에 대한 서비스
     */
    @Override
    public List<PlayListCommentFindRes> findPlayListComment(Long PlayListId) {

        log.info("PlayListCommentService_findPlayListComment_start: ");

        List<PlayListCommentFindRes> res = playListCommentRepository.findByPlayListId(PlayListId)
            .stream().map(m -> PlayListCommentFindRes.builder()
                .commentId(m.getId())
                .playListId(m.getPlayList().getId())
                .userId(m.getUser().getId())
                .content(m.getContent())
                .creaetedAt(m.getCreatedAt())
                .build()
            ).collect(Collectors.toList());

        log.info("PlayListCommentService_findPlayListComment_end: success ");

        return res;
    }

    /**
     * 재생목록 댓글 삭제 (Soft Delete) API 에 대한 서비스
     */
    @Override
    public boolean deletePlayListComment(PlayListCommentDeletePutReq playListCommentDeletePutReq) {

        log.info("PlayListCommentService_deletePlayListComment_start: ");

        PlayListComment playListComment = playListCommentRepository.findById(
                playListCommentDeletePutReq.getPlayListCommentId())
            .orElseThrow(
                NoDataException::new);
        if (playListComment.getUser().getId().equals(playListCommentDeletePutReq.getUserId())) {
            playListComment.deletedPlayListComment();
        } else {
            return false;
        }

        log.info("PlayListCommentService_deletePlayListComment_end: true ");
        return true;
    }
}
