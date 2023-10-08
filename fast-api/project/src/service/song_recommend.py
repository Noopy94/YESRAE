from typing import List
from database.orm import Song
from database.repository import SongRepository, SongVectorRepository
from schema.response import RecommendSongSchema
from util.song_annoy import annoy
import logging

logger = logging.getLogger(__name__)

class SongRecommend:

    def __init__(self):   
        self.song_repository = SongRepository()


    "노래 추천하기"
    def get_recommend_songs(self, song_list):

        # 현재 노래와 ANNOY 통해서 한곡 당 최대 10곡 선정

        recommend_song = annoy(song_list)

        result = []

        for song_id in recommend_song:

            song : Song = self.song_repository.get_song_by_id(song_id)

            song_info = RecommendSongSchema(id=song[0].id, title=song[0].name, singer=song[0].artist_name, album_img=song[0].img_url)

            result.append(song_info)

        return result
