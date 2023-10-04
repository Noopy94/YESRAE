package com.ssafy.yesrae.domain.playlist.repository;

import com.ssafy.yesrae.domain.playlist.entity.Playlist;
import com.ssafy.yesrae.domain.playlist.entity.PlaylistSong;
import com.ssafy.yesrae.domain.playlist.entity.PlaylistSongId;
import com.ssafy.yesrae.domain.song.entity.Song;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    TemplateArticle Entity를 위한 Spring Data JPA Repository
 */
@Repository
public interface PlaylistSongRepository extends JpaRepository<PlaylistSong, PlaylistSongId> {


    // Playlist에 있는 노래들 가져오기
    List<PlaylistSong> findByPlaylist(Playlist playlist);

    PlaylistSong findBySongAndPlaylist(Song song, Playlist playlist);

}
