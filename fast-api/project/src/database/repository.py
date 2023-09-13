
from sqlalchemy import select
from sqlalchemy.orm import Session
from typing import List, Optional

from config.redis_config import redis_config
from database.orm import Song
from database.connection import SessionFactory
from database.orm import SongQuiz, SongQuizRank
import redis


class SongRepository:


    # def __init__(self):
    #     self.session = get_db()

    """
    노래 ID 에 해당하는 곡 정보 조회
    :song_id  : 음악 ID
    return    음악 ID에 해당하는 음악 정보 조회 
    """

    def get_song_by_id(self, song_id: str) -> Song | None:
        try:
            session = SessionFactory()
            return session.query(Song).filter(Song.id == song_id)
        finally:
            session.close()

    """
    노래 제목에 해당하는 곡 정보 조회
    :song_name  : 음악 제목
    이름 같은 곡 존재 가능
    """

    def get_song_by_name(self, song_name: str) -> List[Song] | None:
        try:
            session = SessionFactory()
            return session.query(Song).where(Song.name == song_name).all()
        finally:
            session.close()

    """
    노래 30초 멜로디 정보 저장
    :song   :음악 
    """
    """
    def update_melody(self, song: Song) -> Song | None:
        try:
            session = SessionFactory()
            session.add(instance=song)
            session.commit()
            session.refresh(instance=song)
            return song
        finally:
            session.close()
    """

    """
    인기도 높은 100 곡 DB 에서 조회
    """

    def get_popular_song(self) -> List[Song] | None:
        try : 
            session = SessionFactory()
            return session.query(Song).order_by(Song.popularity.desc()).limit(100).all()
        finally:
            session.close()

    """
    유사도 계산을 위해 정답곡을 제외한 전체곡 조회
    """

    def get_songs_except_today_song(self, id: str) -> List[Song]:
        try:
            session = SessionFactory()
            return session.query(Song).filter(Song.id != id).all()
        finally:
            session.close()


    """
    정답곡으로 설정되어 있는 곡 조회
    """
    def get_today_song(self) -> Song | None:
        try : 
            session = SessionFactory()
            return session.query(Song).filter(Song.today_song == True).first()
        finally:
            session.close()

    """
    정답곡으로 설정
    """
    def update_today_song(self, song: Song) -> Song:
        try:
            session = SessionFactory()
            song.set_today_song()
            session.add(instance=song)
            session.commit()
            session.refresh(instance=song)
            return song
        finally:
            session.close()
    
    """
    정답곡 설정 해제 -> 이전 곡이 되어서
    """
    def update_prior_song(self, song : Song) -> Song:
        try:
            session = SessionFactory()
            song.unset_today_song()
            session.add(instance=song)
            session.commit()
            session.refresh(instance=song)
            return song
        finally:
            session.close()

    



class SongQuizRepository:
    """
    def __init__(self, rd: redis.Redis):
        self.rd = rd
    """

    """
    노래 ID, 노래 유사도 저장
    """

    def save_song_quiz(self, song_quiz: SongQuiz) -> SongQuiz:
        redis_config()
        key = song_quiz.id
        self.rd.set(key, song_quiz.similarity)
        return song_quiz
    
    """
    노래 ID 에 해당하는 유사도 조회
    """
    def get_song_similarity(self, song_id) -> Song | None:
        return self.rd.get(song_id)


class SongQuizRankRepository:
    def __init__(self, rd: redis.Redis):
        self.rd = rd

    """
    노래 ID, 순위 저장
    """

    def save_song_quiz_rank(self, song_quiz_rank: SongQuizRank) -> SongQuizRank:
        key = song_quiz_rank.id
        self.rd.set(key, song_quiz_rank.rank)
        return song_quiz_rank
    """
    노래 ID 에 해당하는 순위 조회
    """
    def get_song_rank(self, song_id) -> Song | None:
        return self.rd.get(song_id)
