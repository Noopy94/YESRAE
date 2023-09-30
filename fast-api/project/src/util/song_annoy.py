
"""
ANN -> 추천 노래 선정
"""

from scipy.spatial import distance
import librosa
import numpy as np
import wget
import os
from annoy import AnnoyIndex
#from song_analyze import deleteFile, getMelody, getTempo, loadmusic, getMusic
from database.repository import SongRepository
import logging


def annoy(target_songs):

    # 벡터의 길이
    f = 12

    # 노래 데이터 가져와서 annoy 실행 -> 가까운 노래 ID 추출

    t = AnnoyIndex(f, 'angular')

    song_repository = SongRepository()

    # 전체 곡 조회
    song_datas =  song_repository.get_songs()

    # annoy idx : 노래 ID
    songs = dict()

    # 추천받을 노래들의 idx 저장
    target_idx = []

    for idx, data in enumerate(song_datas):

        songs[idx] = data.id

        if data.id in target_songs:
            target_idx.append(idx)

        song_feature = [data.acousticness, data.danceability, data.energy, data.instrumentalness, 
                        data.liveness, data.loudness, data.mode, data.speechiness, data.tempo, data.time_signature, data.tune, data.valence]

        t.add_item(idx, song_feature)
    
    
    t.build(12)
    t.save('annoy.ann')

    u = AnnoyIndex(f, 'angular')
    u.load('annoy.ann')

    recommend_songs = set()

    logging.info(f"노래 추천 시작")

    # 추천받을 노래들의 idx
    for idx in target_idx:

        annoy_result = u.get_nns_by_item(idx, 10)

        for result in annoy_result:
            if idx != result:
                logging.info(f"{songs[idx]} 에 연관된 추천 노래 ID {songs[result]}")
                recommend_songs.add(songs[result])
    
    logging.info(f"추천 노래들 개수 : {len(recommend_songs)}")

    
    recommend_songs_list = list(recommend_songs)

    logging.info("노래 추천 완료")

    return recommend_songs_list
