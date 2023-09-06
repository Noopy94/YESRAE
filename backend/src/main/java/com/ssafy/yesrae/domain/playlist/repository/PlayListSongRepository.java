package com.ssafy.yesrae.domain.playlist.repository;

import com.ssafy.yesrae.domain.playlist.entity.PlayListLike;
import com.ssafy.yesrae.domain.playlist.entity.PlayListSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    TemplateArticle Entity를 위한 Spring Data JPA Repository
 */
@Repository
public interface PlayListSongRepository extends JpaRepository<PlayListSong, Long> {
}
