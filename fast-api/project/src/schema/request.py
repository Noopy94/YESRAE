from typing import List
from pydantic import BaseModel

"""
노맨틀 추측하기 요청
name : 추측하는 노래 제목
"""
class SearchSongQuizRequest(BaseModel):
    name : str

    class Config:
        from_attributes = True
    
    def get(self, attribute_name):
        return getattr(self, attribute_name)
    
"""
노래 추천시 받는 노래 ID
id : 노래 ID
"""
class SongIdRequest(BaseModel):
    id: str

    class Config:
        from_attributes = True
    
"""
추천 받고 싶은 노래 ID 리스트
songs : SongIdRequest 목록
"""
class RecommendSongRequest(BaseModel):
    songs : List[SongIdRequest]


    class Config:
        from_attributes = True







    