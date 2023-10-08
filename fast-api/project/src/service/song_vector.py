from typing import List
from database.orm import Song
from database.repository import SongRepository, SongVectorRepository
from schema.request import RecommendSongRequest
from util.song_analyze import getMusic, loadmusic, getMelody, deleteFile
import os
import logging

class SongVector:
    def __init__(self):
        
        self.song_repository = SongRepository()
        self.song_vector_repository = SongVectorRepository()

    """
    mongo db 에 추출한 벡터값 저장
    """
    def save_song_vector(
            self
    ):
        
        # url 가져오기
        songs: List[Song] =  self.song_repository.get_songs()

        root_path = os.getcwd() +"\\"

        cnt = 0

        # 가져온 url 로 getMusic, loadmusic, getMelody 벡터값 추출
        for song in songs:

            try: 
                song_name = getMusic(song.preview_url)

                song_file = root_path + song_name

                y, sr = loadmusic(song_file)

                mel_mean_var_concat = getMelody(y, sr)

                song_vector = {
                    "id" : song.id,
                    "vector" : mel_mean_var_concat.tolist()
                }

                # 저장
                self.song_vector_repository.save_vector(song_vector)

                deleteFile(song_name)

            except:
                deleteFile(song_name)