package com.ssafy.yesrae.domain.tournament.repository;

import com.ssafy.yesrae.domain.tournament.entity.TournamentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentResultRepository extends JpaRepository<TournamentResult, Long>, QTournamentResultRepository {

}
