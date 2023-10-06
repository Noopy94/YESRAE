package com.ssafy.yesrae.domain.tournament.repository;

import com.ssafy.yesrae.domain.tournament.entity.TournamentSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentSongRepository extends JpaRepository<TournamentSong, Long>, QTournamentSongRepository{

}
