package com.ssafy.yesrae.domain.playlist.repository;

import com.ssafy.yesrae.domain.playlist.entity.Playlist;
import com.ssafy.yesrae.domain.playlist.entity.PlaylistTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    TemplateArticle Entity를 위한 Spring Data JPA Repository
 */
@Repository
public interface PlaylistTagRepository extends JpaRepository<PlaylistTag, Long> {

    // Playlist에 있는 태그들 가져오기
    List<PlaylistTag> findByPlaylist(Playlist playlist);

    List<PlaylistTag> findTagNameByPlaylist(Playlist playlist);

    List<PlaylistTag> findByTagName(String tagName);


}
