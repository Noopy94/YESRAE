package com.ssafy.yesrae.domain.tournament.repository;

import static com.ssafy.yesrae.domain.tournament.entity.QTournament.tournament;
import static com.ssafy.yesrae.domain.tournament.entity.QTournamentResult.tournamentResult;
import static com.ssafy.yesrae.domain.tournament.entity.QTournamentSong.tournamentSong;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.yesrae.domain.tournament.dto.response.TournamentPopularSongFindRes;
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

        log.info("QTournamentSongRepository_findTournamentResult_start: "
            + userId);

        return queryFactory
            .select(Projections.constructor(TournamentResultFindRes.class,
                tournament.date.as("date"),
                tournamentSong.title.as("songTitle"),
                tournamentSong.singer.as("songSinger"),
                tournamentResult.rank.as("rank")))
            .from(tournamentResult)
            .innerJoin(tournamentResult.tournamentSong, tournamentSong)
            .innerJoin(tournamentResult.tournament, tournament)
            .where(userIdEq(userId))
            .orderBy(tournament.date.desc())
            .fetch();
    }

    /**
     * 이상형 월드컵 모든 유저들의 플레이 합한 결과, 노래들의 인기 순위를 가져오기 위한 Query
     */
    @Override
    public List<TournamentPopularSongFindRes> findTournamentPopularSong() {

        log.info("QTournamentSongRepository_findTournamentPopularSong_start");

        return queryFactory
            .select(Projections.constructor(TournamentPopularSongFindRes.class,
                tournamentSong.title.as("title"),
                tournamentSong.singer.as("singer"),
                tournamentSong.singer.as("songSinger")))
            .from(tournamentResult)
            .innerJoin(tournamentResult.tournamentSong, tournamentSong)
            .innerJoin(tournamentResult.tournament, tournament)
            .where()
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
