package com.ssafy.yesrae.domain.song.repository;

import com.ssafy.yesrae.domain.song.entity.Songlike;
import com.ssafy.yesrae.domain.song.entity.SonglikeId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SonglikeRepository extends JpaRepository<Songlike, SonglikeId> {

    List<Songlike> findByUserId(Long userId);

    List<Songlike> findBySongId(String songId);

}
