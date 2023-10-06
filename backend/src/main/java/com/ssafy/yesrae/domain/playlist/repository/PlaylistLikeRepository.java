package com.ssafy.yesrae.domain.playlist.repository;

import com.ssafy.yesrae.domain.playlist.entity.Playlist;
import com.ssafy.yesrae.domain.playlist.entity.PlaylistLike;
import com.ssafy.yesrae.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    TemplateArticle Entity를 위한 Spring Data JPA Repository
 */

@Repository
public interface PlaylistLikeRepository extends JpaRepository<PlaylistLike, Long> {

    PlaylistLike findByUserAndPlaylist(User user, Playlist playlist);

    List<PlaylistLike> findAllByUser(User user);

}
