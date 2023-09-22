
from pydantic import BaseModel

"""
TODO album_img 추가
노래 제목 추측 결과
"""
class SongQuizSchema(BaseModel):
    id : str
    name : str
    similarity : float
    rank : str
    album_img : str

    class Config:
        from_attributes = True



"""
노래 제목 입력 결과
"""
class SongTitleSchema(BaseModel):
    name : str


"""
전체 정답곡과 유사도 높은 1000곡 결과
"""
class SongTotalRankSchema(BaseModel):
    singer : str
    rank : str
    similarity : float

    class Config:
        from_attributes = True
    