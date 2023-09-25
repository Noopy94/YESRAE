from typing import List
from database.orm import Song, SongQuiz, SongQuizRank
from database.repository import SongRepository, SongQuizRepository, SongQuizRankRepository
import random
from schema.response import SongQuizSchema, SongTitleSchema, SongTotalRankSchema
from util.song_calculate import SongInfo, CalculateUtil
from fastapi.encoders import jsonable_encoder
import datetime
import json

from config.log_config  import logging

logger = logging.getLogger(__name__)


class SongQuizService:

    def __init__(self):
        
        self.today_song: Song
        self.song_repository = SongRepository()
        self.song_quiz_repository = SongQuizRepository()
        self.song_quiz_rank_repository = SongQuizRankRepository()
        self.calculate_util = CalculateUtil()

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

        logging.info("select popular song")

        # 날짜, 시간으로 seed 생성
        seed = int(datetime.datetime.now().timestamp()) % 100
        random.seed(seed)

        today_song: Song = popular[seed]

        logging.info(f"today_song : {today_song.name}")

        # 새로운 오늘의 노래를 선정하면서 이전에 선정되었던 today_song 은 false 로 변경
        prior_today_song : Song = self.song_repository.get_today_song()

        if prior_today_song is None:
            logging.info("이전의 오늘의 노래를 찾지 못했습니다")

        else:
            logging.info(f"prior_song_name : {prior_today_song.name}")
            prior_today_song = self.song_repository.update_prior_song(prior_today_song)

        today_song = self.song_repository.update_today_song(today_song)

        logging.info(f"오늘의 노래 : {today_song.name}")

        return today_song


    """
    노맨틀 정답곡과 DB 에 있는 나머지 곡들과 의 유사도 계산해서 song_quiz 테이블에 저장
    """
    def save_similarity(
            self,
    ):
        # 오늘의 노래 (정답곡)
        song_id: str = self.today_song.id

        songs: List[Song] = self.song_repository.get_songs()

        json_today_song = jsonable_encoder(self.today_song)

        today_song_info: SongInfo = SongInfo(**json_today_song)

        for song in songs:
            json_compare_song = jsonable_encoder(song)
            
            compare_song_info = SongInfo(**json_compare_song)

            # 오늘의 노래와 DB 에 있는 나머지 노래들 간에 유사도 계산
            similarity: float = self.calculate_util.calculate(today_song_info, compare_song_info)

            song_quiz: SongQuiz = SongQuiz.create(id=song.id, similarity=similarity)

            saved_song_quiz : SongQuiz = self.song_quiz_repository.save_song_quiz(song_quiz)
        
        # 60 시간 후 유사도 데이터 삭제
        self.song_quiz_repository.expire_similarity_data()





    """
    노맨틀 정답곡과의 유사도를 기준으로 내림차순으로 1000 곡까지 순위를 song_quiz_rank 테이블에 저장
    """
    def save_song_rank(
            self, 
    ):
        count = 1000

        similarity_datas = self.song_quiz_repository.get_all_song_similarity()

        # song_quiz 의 모든 정보를 가져와서 순위를 정렬해서 1000곡까지 slice
        sorted_rank_data = sorted(similarity_datas.items(), key = lambda x : float(x[1]), reverse= True)[:1000]


        # 1 위 곡
        logging.info(f"1위 곡 : {sorted_rank_data[0]}")
        
        # redis 에 노래 id 와 rank 정보 저장
        for idx, rank_data in enumerate(sorted_rank_data):
            logging.info(f"idx : {idx}, rank_data : {rank_data[0]}")

            result : list = self.song_repository.get_song_by_id(rank_data[0])

            name : str = result[0].name

            singer : str = result[0].artist_name 

            logging.info(f"artist name : {singer}")

            song_quiz_rank : SongQuizRank = SongQuizRank.create(id=rank_data[0], rank=idx + 1, similarity=rank_data[1], name=name, singer = singer)

            saved_song_quiz_rank : SongQuizRank = self.song_quiz_rank_repository.save_song_quiz_rank(song_quiz_rank)
        
        # 60 시간 후 rank 데이터 삭제
        self.song_quiz_rank_repository.expire_rank_data()

    """
    추측하기 했을 때 검색어에 해당하는 노래 제목, 노래 유사도, 노래 순위 반환
    song_name : 추측한 노래 제목
    """
    def get_song_result(
            self,
            song_name : str,
    ) -> List[SongQuizSchema]:
        
        # 검색한 곡과 이름이 같은 곡들 조회
        # 같은 제목의 곡이 여러 개 존재 가능
        search_songs : List[Song] = self.song_repository.get_song_by_name(song_name)

        

        search_result = []
        # 검색한 곡 정보 없을 수 있다
        if search_songs:

            for search_song in search_songs:
                
                today = str(datetime.date.today())
                today_song_quiz =  today + "_song_quiz"
                today_song_rank = today + "_song_quiz_rank"

                logging.info(f"today : {today}")
                logging.info(f"search_song : {search_song.name} , search_song id : {search_song.id}")

                logging.info(f"today_song_quiz {today_song_quiz}")

                # 노래 유사도 조회
                song_similarity : float = self.song_quiz_repository.get_song_similarity(search_song.id, today_song_quiz, True)

                logging.info(f"song_similarity : {song_similarity}")

                # 노래 순위 반환
                song_rank : int = self.song_quiz_rank_repository.get_song_rank(search_song.id, today_song_rank)

                logging.info(f"rank : {song_rank}")

                # 해당 노래가 1000위 안에 안들 수 있다 -> None 반환
                song_quiz = SongQuizSchema(id=search_song.id, title =search_song.name, rank=song_rank, similarity=song_similarity ,album_img= search_song.img_url, answer= False)

                if song_rank == 1:
                    song_quiz.answer = True


                search_result.append(song_quiz)
        else:
            logging.info("검색한 곡이 존재하지 않습니다.")

        return search_result
    

    """
    사용자 입력시 해당 글자로 시작하는 곡 5개 반환
    song_name : 입력한 노래 제목
    """
    def search_song_title(
            self,
            song_name : str,
    )-> List[SongTitleSchema]:

        search_songs : List[Song] = self.song_repository.get_similar_song_name(song_name)

        search_result = []

        if search_songs:
            logging.info("해당 입력으로 시작하는 곡들 존재")

            for song in search_songs:

                song_title = SongTitleSchema(id = song.id, title = song.name, singer = song.artist_name)
                logging.info(f"id : {song.id} , title : {song.name}, singer : {song.artist_name}")

                search_result.append(song_title)
        
        else:
            logging.info("해당 입력으로 시작하는 곡들이 존재하지 않습니다")
        
        return search_result
    

   
    """
    rank 테이블 변경 후
    전체 순위 보기 결과
    """
    def get_ranks(
            self
    )-> List[SongTotalRankSchema]:


        #오늘 순위 보기 위해 오늘의 날짜 설정
        today = datetime.date.today() 

        logging.info(f"오늘 날짜 : {today}")

        # rank 전체 조회
        rank_datas = self.song_quiz_rank_repository.get_all_song_rank(today)

        logging.info(f"rank_datas!! {rank_datas}")

        rank_info = []

        logging.info("rank data item!!", rank_datas.items())
  
        # 해당 ID 로 유사도 조회
        if rank_datas:
            for idx, (id, song) in enumerate(rank_datas.items()):
                today_song_quiz =  str(today) + "_song_quiz"

                json_data = json.loads(song.decode('utf-8'))

                id = id.decode('utf-8')
                song_name = json_data.get("name")
                similarity = json_data.get("similarity")
                rank = json_data.get("rank")
                singer = json_data.get("singer")
                

                # 노래 유사도 조회
                song_rank_info = SongTotalRankSchema(id= id, title= song_name, similarity= similarity, singer= singer, rank=rank)

                rank_info.append(song_rank_info)
        
        sorted_rank_info = sorted(rank_info, key=lambda x: x.rank)
        
        return sorted_rank_info

                


        

