from typing import List
from database.orm import Song
from database.repository import SongRepository, SongVectorRepository
from schema.request import RecommendSongRequest
from util.song_analyze import getMusic, loadmusic, getMelody, deleteFile
import os

class SongVector:
    song_repository = SongRepository()
    song_vector_repository = SongVectorRepository()

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

                print("type : ", type(mel_mean_var_concat))

                song_vector = {
                    "id" : song.id,
                    "vector" : mel_mean_var_concat.tolist()
                }

                # 저장
                self.song_vector_repository.save_vector(song_vector)

                deleteFile(song_name)

            except:
                deleteFile(song_name)


    "노래 추천하기"
    def get_recommend_songs(songs : RecommendSongRequest):
        # 전체 노래 가져와서 (특성값 list 저장)
        # RecommendSongRequest 에는 최대 5곡 
        # 현재 노래와 ANNOY 통해서 한곡 당 최대 10곡 선정
        pass



        


