from typing import List

from fastapi import Depends

from database.orm import Song, SongQuiz
from database.repository import SongRepository, SongQuizRepository
from datetime import datetime
import random

from util.song_calculate import SongInfo, CalculateUtil


class SongQuizService:
    today_song: Song

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
            song_repository: SongRepository = Depends()
    ) -> Song:
        # 인기 높은 100곡
        popular: List[Song] | None = song_repository.get_popular_song()

        # 날짜, 시간으로 seed 생성
        seed = int(datetime.now().timestamp())
        random.seed(seed)

        today_song: Song = popular[seed - 1]

        return today_song

    """
    정답곡과의 유사도 계산해서 song_quiz 테이블에 저장
    :song :노맨틀 정답 곡
    """
    def save_similarity(
            self,
            song_repository: SongRepository = Depends(),
            song_quiz_repository : SongQuizRepository = Depends(),
            calculate_util: CalculateUtil = Depends(CalculateUtil)
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


    """
    순위 저장해서 song_quiz_rank 테이블에 저장
    """
    def save_song_rank(self):
        # 다시 redis 에 저장된 모든 정보를 가져와서 순위를 정렬해서 저장해야 한다.

        pass
