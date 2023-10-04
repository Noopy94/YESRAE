package com.ssafy.yesrae.domain.comment.service;

import com.ssafy.yesrae.common.exception.NoDataException;
import com.ssafy.yesrae.common.exception.playlist.PlaylistNotFoundException;
import com.ssafy.yesrae.common.exception.user.UserNotFoundException;
import com.ssafy.yesrae.domain.comment.dto.request.PlaylistCommentDeletePutReq;
import com.ssafy.yesrae.domain.comment.dto.request.PlaylistCommentRegistPostReq;
import com.ssafy.yesrae.domain.comment.dto.response.PlaylistCommentFindRes;
import com.ssafy.yesrae.domain.comment.entity.PlaylistComment;
import com.ssafy.yesrae.domain.comment.repository.PlaylistCommentRepository;
import com.ssafy.yesrae.domain.playlist.entity.Playlist;
import com.ssafy.yesrae.domain.playlist.repository.PlaylistRepository;
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
public class PlaylistCommentServiceImpl implements PlaylistCommentService {

    private final PlaylistCommentRepository playlistCommentRepository;
    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;

    @Autowired
    public PlaylistCommentServiceImpl(PlaylistCommentRepository playlistCommentRepository,
        UserRepository userRepository, PlaylistRepository playlistRepository) {
        this.playlistCommentRepository = playlistCommentRepository;
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
    }

    /**
     * 재생목록 댓글 Regist API 에 대한 서비스
     *
     * @param playlistCommentRegistPostReq : 게시글 댓글 등록할 때 입력한 정보
     */

    @Override
    public PlaylistComment registPlaylistComment(
        PlaylistCommentRegistPostReq playlistCommentRegistPostReq) {

        log.info("playlistCommentService_registplaylistComment_start: "
            + playlistCommentRegistPostReq.toString());

        String content = playlistCommentRegistPostReq.getContent();

        User user = userRepository.findById(playlistCommentRegistPostReq.getUserId())
            .orElseThrow(UserNotFoundException::new);

        Playlist playlist = playlistRepository.findById(
                playlistCommentRegistPostReq.getPlaylistId())
            .orElseThrow(PlaylistNotFoundException::new);

        PlaylistComment playlistComment = PlaylistComment.builder()
            .user(user)
            .playlist(playlist)
            .content(content)
            .build();

        playlistCommentRepository.save(playlistComment);
        log.info("playlistCommentService_registplaylistComment_end : success");
        return playlistComment;

    }

    /**
     * 특정 재생목록의 댓글 전체 조회 API에 대한 서비스
     */
    @Override
    public List<PlaylistCommentFindRes> findPlaylistComment(Long playlistId) {

        log.info("playlistCommentService_findplaylistComment_start: " + playlistId);

        List<PlaylistCommentFindRes> res = playlistCommentRepository.findByPlaylistId(playlistId)
            .stream().map(m -> PlaylistCommentFindRes.builder()
                .id(m.getId())
                .playlistId(m.getPlaylist().getId())
                .userId(m.getUser().getId())
                .content(m.getContent())
                .createdAt(m.getCreatedAt())
                .build()
            ).collect(Collectors.toList());

        log.info("playlistCommentService_findplaylistComment_end: success");

        return res;
    }

    /**
     * 재생목록 댓글 삭제 (Soft Delete) API 에 대한 서비스
     */
    @Override
    public boolean deletePlaylistComment(PlaylistCommentDeletePutReq playlistCommentDeletePutReq) {

        log.info("playlistCommentService_deleteplaylistComment_start: "
            + playlistCommentDeletePutReq.toString());

        PlaylistComment playlistComment = playlistCommentRepository.findById(
                playlistCommentDeletePutReq.getId())
            .orElseThrow(
                NoDataException::new);
        if (playlistComment.getUser().getId().equals(playlistCommentDeletePutReq.getUserId())) {
            playlistComment.deletePlaylistComment();
        } else {
            return false;
        }

        log.info("playlistCommentService_deleteplaylistComment_end: success");
        return true;
    }
}
