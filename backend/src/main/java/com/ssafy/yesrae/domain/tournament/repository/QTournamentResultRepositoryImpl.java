package com.ssafy.yesrae.domain.tournament.repository;

import static com.ssafy.yesrae.domain.tournament.entity.QTournament.tournament;
import static com.ssafy.yesrae.domain.tournament.entity.QTournamentResult.tournamentResult;
import static com.ssafy.yesrae.domain.tournament.entity.QTournamentSong.tournamentSong;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentResultFindRes;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * Querydsl을 위한 이상형 월드컵 결과 Repository
 */
@Slf4j
@Repository
public class QTournamentResultRepositoryImpl implements QTournamentResultRepository{

    private final JPAQueryFactory queryFactory;

    public QTournamentResultRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 유저 Id 별 이상형 월드컵 결과를 가져오기 위한 Query
     *
     * @param userId : 유저 Id
     */
    @Override
    public List<TournamentResultFindRes> findTournamentResult(Long userId){

        log.info("QTournamentResultRepository_findTournamentResult_start: "
            + userId);

        return queryFactory
            .select(Projections.constructor(TournamentResultFindRes.class,
                tournament.date.as("date"),
                tournamentSong.title.as("songTitle"),
                tournamentSong.singer.as("songSinger"),
                tournamentResult.ranking.as("ranking")))
            .from(tournamentResult)
            .innerJoin(tournamentResult.tournamentSong, tournamentSong)
            .innerJoin(tournamentResult.tournament, tournament)
            .where(userIdEq(userId))
            .orderBy(tournament.date.desc())
            .fetch();
    }

    /**
     * user id로 본인이 플레이 한 이상형 월드컵 조회하기 위해 user id 일치하는 데이터 가져오는 조건문
     */
    private BooleanExpression userIdEq(Long userId) {
        return tournamentResult.tournament.user.id.eq(userId);
    }
}
