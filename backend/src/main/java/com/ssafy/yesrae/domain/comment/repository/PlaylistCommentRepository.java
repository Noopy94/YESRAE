package com.ssafy.yesrae.domain.comment.repository;

import com.ssafy.yesrae.domain.comment.entity.PlaylistComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistCommentRepository extends JpaRepository<PlaylistComment, Long> {

    List<PlaylistComment> findByPlaylistId(Long playlistId);
}
