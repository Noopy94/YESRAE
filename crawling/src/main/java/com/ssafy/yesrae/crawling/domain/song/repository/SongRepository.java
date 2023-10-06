package com.ssafy.yesrae.crawling.domain.song.repository;

import com.ssafy.yesrae.crawling.domain.song.entity.Song;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, String> {
    List<Song> findByArtistId(String ArtistId);
}
