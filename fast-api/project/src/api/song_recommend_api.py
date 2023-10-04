from fastapi import APIRouter
from schema.request import RecommendSongRequest
from service.song_recommend import SongRecommend
import logging
router = APIRouter()

song_recommend_service = SongRecommend()

"""
노래 ID 바탕으로 추천 노래 반환
"""
@router.post("/recommend", status_code=200)
async def song_recommend(
    songs_recommend_request : RecommendSongRequest
):

    song_name = songs_recommend_request.songs
    
    song_list = []

    songs_to_recommend = [song.id for song in song_name]

    song_list = song_recommend_service.get_recommend_songs(songs_to_recommend)

    recommend_dict = {"songs" : song_list}

    return recommend_dict