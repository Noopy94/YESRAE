
from typing import Optional
from pydantic import BaseModel

"""
노맨틀 노래 제목 추측 결과
id : 노래 ID
title : 노래 제목
similarity : 유사도
rank : 순위
album_img : 앨범 이미지
answer : 정답 여부
"""
class SongQuizSchema(BaseModel):
    id : str
    title : str
    similarity : float
    rank : Optional[int]
    album_img : str
    answer : bool

    class Config:
        from_attributes = True



"""
노래 제목 입력 결과
"""
class SongTitleSchema(BaseModel):
    id : str
    title : str
    singer : str


"""
전체 정답곡과 유사도 높은 1000곡 결과
"""
class SongTotalRankSchema(BaseModel):
    id : str
    title : str
    similarity : float
    singer : str
    rank : int

    class Config:
        from_attributes = True
    