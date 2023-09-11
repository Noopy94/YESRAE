from sqlalchemy import select
from sqlalchemy.orm import Session
from typing import List, Optional
from database.orm import Song

"""
노래 ID 에 해당하는 곡 정보 조회
:session  :
:song_id  : 음악 ID
return    음악 ID에 해당하는 음악 정보 조회 
"""
def get_song_by_id(session : Session, song_id : str) -> Song | None: 
    return session.scalars(select(Song).where(Song.id == song_id))


"""
노래 제목에 해당하는 곡 정보 조회
:session :
:song_name  : 음악 제목
"""
def get_song_by_name(session : Session, song_name : str) -> Song | None:
    return session.scalars(select(Song).where(Song.name))