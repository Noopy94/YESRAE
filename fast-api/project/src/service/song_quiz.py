

from typing import List
from database.orm import Song
from database.repository import get_popular_song, get_songs_except_today_song
from datetime import datetime
import random


class SongQuizService:
    
    """
    인기곡 100개 중에서 랜덤하게 예스레 정답곡 선택
    :return :Song 오늘의 노맨틀 곡
    """
    def select_today_song() -> Song:

        # 인기 높은 100곡
        popular : List[Song] | None = get_popular_song()

        # 날짜, 시간으로 seed 생성
        seed = int(datetime.now().timestamp())
        random.seed(seed)

        today_song : Song = popular[seed-1]

        return today_song
    
    """
    정답곡과의 유사도 계산해서 song_quiz 테이블에 저장
    :song :노맨틀 정답 곡
    """
    def save_similarity(song : Song):
        # 정답곡
        song_id : str = song.id

        songs : List[Song] = get_songs_except_today_song(song_id)

        #for song in songs:




        pass


    """
    순위 저장해서 song_quiz_rank 테이블에 저장
    """






    
