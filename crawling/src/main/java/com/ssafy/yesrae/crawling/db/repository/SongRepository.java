package com.ssafy.yesrae.crawling.db.repository;

import com.ssafy.yesrae.crawling.db.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, String> {

}
