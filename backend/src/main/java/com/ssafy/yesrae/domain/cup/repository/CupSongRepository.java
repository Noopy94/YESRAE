package com.ssafy.yesrae.domain.cup.repository;

import com.ssafy.yesrae.domain.cup.entity.CupSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CupSongRepository extends JpaRepository<CupSong, Long> {

}
