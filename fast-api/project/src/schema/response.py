
from typing import List, Optional
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
id : 노래 ID
title : 노래 제목
singer : 가수
"""
class SongTitleSchema(BaseModel):
    id : str
    title : str
    singer : str


"""
전체 정답곡과 유사도 높은 1000곡 결과
id : 노래 ID
title : 노래 제목
similarity : 정답곡과의 유사도
singer : 가수
rank : 순위
"""
class SongTotalRankSchema(BaseModel):
    id : str
    title : str
    similarity : float
    singer : str
    rank : Optional[int]

    class Config:
        from_attributes = True


class RecommendSongSchema(BaseModel):
    id : str
    title : str
    singer : str
    album_img : str

    class Config:
        from_attributes = True



class RecommendSongListSchema(BaseModel):
    songs : List[RecommendSongSchema]
    
    class Config:
        from_attributes = True