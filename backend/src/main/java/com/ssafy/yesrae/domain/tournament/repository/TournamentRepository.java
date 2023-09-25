package com.ssafy.yesrae.domain.tournament.repository;

import com.ssafy.yesrae.domain.tournament.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

}
