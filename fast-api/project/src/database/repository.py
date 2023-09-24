
from sqlalchemy import select, Integer, Float
from sqlalchemy.orm import Session
from typing import List, Optional

from config.redis_config import redis_config
from database.orm import Song
from database.connection import SessionFactory
from database.orm import SongQuiz, SongQuizRank
import redis
import datetime


class SongRepository:
    

    """
    노래 ID 에 해당하는 곡 정보 조회
    :song_id  : 음악 ID
    :return    음악 ID에 해당하는 음악 정보 조회
    """

    def get_song_by_id(self, song_id: str) -> Song | None:
        try:
            session = SessionFactory()
            return session.query(Song).filter(Song.id == song_id)
        finally:
            session.close()

    """
    노래 name 에 해당하는 곡 정보 조회
    :song_name  : 음악 제목
    :return 이름 같은 곡 존재 가능
    """
    def get_song_by_name(self, song_name: str) -> List[Song] | None:
        try:
            session = SessionFactory()
            return session.query(Song).filter(Song.name == song_name).all()
        finally:
            session.close()

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
    전체곡 조회
    """
    def get_songs(self) -> List[Song]:
        try:
            session = SessionFactory()
            return session.query(Song).all()
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

    """
    해당 입력으로 시작하는 곡 이름 5곡 조회
    """
    def get_similar_song_name(self, song_name : str)  -> List[Song] | None:
        try:
            session = SessionFactory()
            return session.query(Song).filter(Song.name.startswith(song_name)).limit(5).all()
        finally:
            session.close()
    


    



class SongQuizRepository:
    rd = redis_config()

    """
    노래 ID, 노래 유사도 저장
    redis 에 key : [내일날짜_song_quiz], field : [노래 id] , value : [유사도] 저장
    """

    def save_song_quiz(self, song_quiz: SongQuiz) -> SongQuiz:
        # 내일 날짜 
        new_day = datetime.date.today() + datetime.timedelta(days=1)

        key = str(new_day) + "_song_quiz"
        field = song_quiz.id
        value = song_quiz.similarity

        self.rd.hset(key, field, value)
        return song_quiz
    
    """
    노래 ID 에 해당하는 유사도 조회
    """
    def get_song_similarity(self, song_id : str, key : str) -> Float | None:

        similarity_datas = self.rd.hgetall(key)

        # redis 에 바이트 문자열로 저장되기 때문에 encode
        #song_id_byte = song_id.encode('utf-8')
        #similarity = similarity_datas.get(song_id_byte).decode('utf-8')

        similarity = similarity_datas.get(song_id).decode('utf-8')

        return similarity
    
    """
    유사도 데이터 60시간 후 삭제
    """
    def expire_similarity_data(self):

        new_day = datetime.date.today() + datetime.timedelta(days=1)
        key = str(new_day) + "_song_quiz"
        # 60 시간 후 삭제
        self.rd.expire(key, 216000)
    

    """
    song_quiz 에 있는 모든 정보 조회 (전날에 rank 테이블에 데이터 저장할 때 사용)
    """
    def get_all_song_similarity(self):

        new_day = datetime.date.today() + datetime.timedelta(days=1)

        key = str(new_day) + "_song_quiz"
        # song_quiz 에 저장한 모든 정보 가져오기
        rank_datas = self.rd.hgetall(key)

        return rank_datas



class SongQuizRankRepository:

    rd = redis_config()

    """
    노래 ID, 순위 저장
    redis 에 key : [내일날짜_song_quiz_rank], field : [노래 id] , value : [순위] 저장
    """
    def save_song_quiz_rank(self, song_quiz_rank: SongQuizRank) -> SongQuizRank:
        
        # 내일 날짜
        new_day = datetime.date.today() + datetime.timedelta(days=1)

        key = str(new_day) + "_song_quiz_rank"
        field = song_quiz_rank.id
        value = song_quiz_rank.rank

        self.rd.hset(key, field, value)
        return song_quiz_rank


    """
    노래 ID 에 해당하는 순위 조회
    """
    def get_song_rank(self, song_id , key : str) -> int:

        rank_datas = self.rd.hgetall(key)

        # redis 에 바이트 문자열로 저장되기 때문에 encode
        song_id_byte = song_id.encode('utf-8')

        rank = int(rank_datas.get(song_id_byte))

        # 해당하는 노래가 1000위 안에 안 들어 있을 수도 있다
        # rank : None

        return rank
    

    """
    순위 데이터 60시간 후 삭제
    """
    def expire_rank_data(self):

        new_day = datetime.date.today() + datetime.timedelta(days=1)
        key = str(new_day) + "_song_quiz_rank"
        # 60 시간 후 삭제
        self.rd.expire(key, 216000)

    
    """
    원하는 날짜 song_quiz_rank 에 있는 모든 정보 조회
    """
    def get_all_song_rank(self, day):

        key = str(day) + "_song_quiz"
        # song_quiz_rank 에 저장한 모든 정보 가져오기
        rank_datas = self.rd.hgetall(key)

        return rank_datas