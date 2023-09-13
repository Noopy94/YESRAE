from typing import List


from database.orm import Song, SongQuiz, SongQuizRank
from database.repository import SongRepository, SongQuizRepository, SongQuizRankRepository
from datetime import datetime
import random
from config.redis_config import redis_config
from schema.response import SongQuizSchema
from util.song_calculate import SongInfo, CalculateUtil
import redis
from database.connection import get_db




class SongQuizService:
    today_song: Song

    song_repository = SongRepository()

    def __init__(self) -> None:
        redis_config()



    def song_quiz_update(self):
        self.today_song = self.select_today_song()

        self.save_similarity()

        self.save_song_rank()

    """
    인기곡 100개 중에서 랜덤하게 예스레 정답곡 선택
    :return :Song 오늘의 노맨틀 곡
    """
    def select_today_song(
            self,
            # song_repository: SongRepository
    ) -> Song:

        # song_repo = get_song_repository()
        # 인기 높은 100곡
        popular: List[Song] | None = self.song_repository.get_popular_song()

        print("popular song")

        # 날짜, 시간으로 seed 생성
        seed = int(datetime.now().timestamp()) % 100
        random.seed(seed)

        today_song: Song = popular[seed]

        print("today_song", today_song.name)

        # DB 에서 이전날 prior_today_song -> today_song 을 false 로 변환
        # 조금전에 선정한 today_song -> true 로 변환
        prior_today_song : Song = self.song_repository.get_today_song()
        prior_today_song = self.song_repository.update_prior_song(prior_today_song)
        today_song = self.song_repository.update_today_song(today_song)

        return today_song

    """
    정답곡과의 유사도 계산해서 song_quiz 테이블에 저장
    :song :노맨틀 정답 곡
    """
    def save_similarity(
            self,
            song_repository: SongRepository ,
            song_quiz_repository : SongQuizRepository,
            calculate_util: CalculateUtil
    ):
        # 정답곡
        song_id: str = self.today_song.id

        songs: List[Song] = song_repository.get_songs_except_today_song(song_id) 

        today_song_info: SongInfo = SongInfo(**eval(self.today_song))

        for song in songs:
            compare_song_info = SongInfo(**eval(song))

            similarity: float = calculate_util.calculate(today_song_info, compare_song_info)

            song_quiz: SongQuiz = SongQuiz.create(id=song.id, similarity=similarity)

            saved_song_quiz : SongQuiz = song_quiz_repository.save_song_quiz(song_quiz)

        
        # 이전 테이블에 있는 데이터들 지우기 DB 지우기



    """
    순위 저장해서 song_quiz_rank 테이블에 저장
    """
    def save_song_rank(
            self, 
            rd : redis.Redis,
            song_quiz_rank_repository : SongQuizRankRepository 
    ):
        # 다시 redis 에 저장된 모든 정보를 가져와서 순위를 정렬해서 저장해야 한다.
        count = 1000

        rank_datas = rd.zrevrange("song_quiz", count -1, withscores=True, score_cast_func=float)

        song_quiz_ranking  = [{item[0].decode() : idx + 1 for idx, item in enumerate(rank_datas)}]

        for rank_data in song_quiz_ranking:

            song_quiz_rank : SongQuizRank = SongQuizRank.create(id=rank_data.keys, rank=rank_data.values)

            saved_song_quiz_rank : SongQuizRank = song_quiz_rank_repository.save_song_quiz_rank(song_quiz_rank)

        
    """
    검색어에 해당하는 노래 제목, 노래 유사도, 노래 순위 반환
    """
    def search_song_result(
            self,
            song_name : str,
            rd : redis.Redis,
            song_repository: SongRepository ,
            song_quiz_repository : SongQuizRepository,
            song_quiz_rank_repository : SongQuizRankRepository
    ) -> List[SongQuizSchema]:
        
        # 검색한 곡과 이름이 같은 곡들 조회
        search_songs : List[Song] = song_repository.get_song_by_name(song_name)

        search_result = []

        for search_song in search_songs:
            song_quiz = SongQuizSchema()

            # 노래 유사도 조회
            song_similarity : float = song_quiz_repository.get_song_similarity(search_song.id)

            # 노래 순위 반환
            song_rank : int = song_quiz_rank_repository.get_song_rank(search_song.id)

            song_quiz.name = song_name
            song_quiz.similarity = song_similarity
            song_quiz.rank = song_rank
            song_quiz.id = search_song.id

            search_result.append(song_quiz)

        return search_result
    

        
