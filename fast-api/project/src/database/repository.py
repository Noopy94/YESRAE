from fastapi import Depends
from sqlalchemy import select
from sqlalchemy.orm import Session
from typing import List, Optional
from database.orm import Song
from database.connection import get_db
from project.src.database.orm import SongQuiz, SongQuizRank



class SongRepository:

    def __init__(self, session : Session = Depends(get_db)):
        self.session = session


    """
    노래 ID 에 해당하는 곡 정보 조회
    :song_id  : 음악 ID
    return    음악 ID에 해당하는 음악 정보 조회 
    """
    def get_song_by_id(self, song_id : str) -> Song | None: 
        return self.session.scalars(select(Song).where(Song.id == song_id))


    """
    노래 제목에 해당하는 곡 정보 조회
    :song_name  : 음악 제목
    """
    def get_song_by_name(self, song_name : str) -> Song | None:
        return self.session.scalars(select(Song).where(Song.name == song_name))
    

    """
    노래 30초 멜로디 정보 저장
    :song   :음악 
    """
    def update_melody(self, song : Song) -> Song | None:
        self.session.add(instance=song)
        self.session.commit()
        self.session.refresh(instance=song)
        return song
    
    """
    인기도 높은 100 곡 DB 에서 조회
    """
    def get_popular_song(self) -> List[Song] | None:
        return self.session.scalars(select(Song).order_by(Song.popularity.desc()).limit(100)).all()
    

    """
    유사도 계산을 위해 정답곡을 제외한 전체곡 조회
    """
    def get_songs_except_today_song(self, id : str) -> List[Song]:
        return self.session.scalars(select(Song).filter(Song.id != id)).all()


class SongQuizRepository:
    def __init__(self, session : Session = Depends(get_db)):
        self.session = session

    """
    노래 ID, 노래 유사도 저장
    """
    def save_song_quiz(self, song_quiz : SongQuiz) -> SongQuiz:
        self.session.add(instance=song_quiz)
        self.session.commit()
        self.session.refresh(instance=song_quiz)
        return song_quiz

class SongQuizRank:
    def __init__(self, session : Session = Depends(get_db)):
        self.session = session
    
    """
    노래 ID, 순위 저장
    """
    def save_song_quiz_rank(self, song_quiz_rank : SongQuizRank) -> SongQuizRank:
        self.session.add(instance=song_quiz_rank)
        self.session.commit()
        self.session.refresh(instance=song_quiz_rank)
        return song_quiz_rank
