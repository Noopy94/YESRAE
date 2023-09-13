from typing import List


from database.orm import Song, SongQuiz, SongQuizRank
from database.repository import SongRepository, SongQuizRepository, SongQuizRankRepository
#from datetime import datetime
import random
from config.redis_config import redis_config
from schema.response import SongQuizSchema
from util.song_calculate import SongInfo, CalculateUtil
import redis
from database.connection import get_db
from fastapi.encoders import jsonable_encoder
import datetime



class SongQuizService:
    today_song: Song

    song_repository = SongRepository()
    song_quiz_repository = SongQuizRepository()
    song_quiz_rank_repository = SongQuizRankRepository()
    calculate_util = CalculateUtil()

    def __init__(self) -> None:
        redis_config()



    def song_quiz_update(self):
        self.today_song = self.select_today_song()

        self.save_similarity()

        self.save_song_rank()

    """
    인기곡 100개 중에서 랜덤하게 예스레 정답곡 선택
    전날의 노맨틀 정답곡은 상태값 today_song column false 로 변경
    노맨틀 정답곡은 today_song column true 로 변경
    :return :Song 오늘의 노맨틀 곡
    """
    def select_today_song(
            self,
    ) -> Song:

        # 인기 높은 100곡 조회
        popular: List[Song] | None = self.song_repository.get_popular_song()

        print("popular song")

        # 날짜, 시간으로 seed 생성
        seed = int(datetime.datetime.now().timestamp()) % 100
        random.seed(seed)

        today_song: Song = popular[seed]

        print("today_song", today_song.name)

        # 새로운 오늘의 노래를 선정하면서 이전에 선정되었던 today_song column 은 false 로 변경
        prior_today_song : Song = self.song_repository.get_today_song()

        if prior_today_song is None:
            print("이전의 오늘의 노래를 찾지 못했습니다.")
        else:
            print("prior_song_name", prior_today_song.name)
            prior_today_song = self.song_repository.update_prior_song(prior_today_song)

        today_song = self.song_repository.update_today_song(today_song)
        print("오늘의 노래", today_song.name)

        return today_song

    """
    노맨틀 정답곡과 DB 에 있는 나머지 곡들과 의 유사도 계산해서 song_quiz 테이블에 저장
    """
    def save_similarity(
            self,
    ):
        # 오늘의 노래 (정답곡)
        song_id: str = self.today_song.id

        # DB 에 저장된 정답곡을 제외한 나머지 노래 정보
        songs: List[Song] = self.song_repository.get_songs_except_today_song(song_id)

        json_today_song = jsonable_encoder(self.today_song)

        today_song_info: SongInfo = SongInfo(**json_today_song)

        for song in songs:
            json_compare_song = jsonable_encoder(song)
            
            compare_song_info = SongInfo(**json_compare_song)

            # 오늘의 노래와 DB 에 있는 나머지 노래들 간에 유사도 계산
            similarity: float = self.calculate_util.calculate(today_song_info, compare_song_info)

            song_quiz: SongQuiz = SongQuiz.create(id=song.id, similarity=similarity)

            saved_song_quiz : SongQuiz = self.song_quiz_repository.save_song_quiz(song_quiz)
            
        # TODO : 이틀 전의 song_quiz, song_quiz_rank 데이터는 삭제




    """
    노맨틀 정답곡과의 유사도를 기준으로 내림차순으로 1000 곡까지 순위를 song_quiz_rank 테이블에 저장
    """
    def save_song_rank(
            self, 
    ):
        count = 1000

        rd = redis_config()

        new_day = datetime.date.today() + datetime.timedelta(days=1)

        key = str(new_day) + "_song_quiz"

        # song_quiz 에 저장한 모든 정보 가져오기
        rank_datas = rd.hgetall(key)

        # song_quiz 의 모든 정보를 가져와서 순위를 정렬해서 1000곡까지 slice
        sorted_rank_data = sorted(rank_datas.items(), key = lambda x : float(x[1]), reverse= True)[:1000]

        print("rank_datas", sorted_rank_data[0])
        
        # redis 에 id 와 rank 정보 저장
        for idx, rank_data in enumerate(sorted_rank_data):
            print("rank_data", idx, rank_data[0])
            song_quiz_rank : SongQuizRank = SongQuizRank.create(id=rank_data[0], rank=idx + 1)

            saved_song_quiz_rank : SongQuizRank = self.song_quiz_rank_repository.save_song_quiz_rank(song_quiz_rank)

        
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
    

        
