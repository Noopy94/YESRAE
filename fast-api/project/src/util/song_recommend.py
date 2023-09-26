
"""
ANN -> 추천 노래 선정
"""

import random
from scipy.spatial import distance
import librosa
import numpy as np
import wget
import os
from annoy import AnnoyIndex
from song_analyze import calSimilarity, deleteFile, getMelody, getTempo, loadmusic, getMusic


def annoy():
    pass


if __name__ == "__main__":

    try:
        # 음원 파일 저장하는 경로
        root_path = os.getcwd() +"\\"
        print(f"root path : {root_path}")


        today_song_url  = 'https://p.scdn.co/mp3-preview/8cd16a87465e35652134fb7cd4a276812690d750?cid=c551bf26249e4acf9eab170aed614fab'
        #today_song_url = "https://p.scdn.co/mp3-preview/afb3b416f80fe7e3a504fd809af164fd6199f7aa?cid=b76e1a72191a49e1bd4cc3b5aaa2511b"

        today_song_name = getMusic(today_song_url)
        today_song_file = root_path + today_song_name

        y1, sr1 = loadmusic(today_song_file)

        mel_mean_var_concat_1 = getMelody(y1, sr1)


        # 30초 미리 듣기 API 저장
        url = ['https://p.scdn.co/mp3-preview/a76a5bfcf5145901bd4b7ca88e248a2d897950d6?cid=c551bf26249e4acf9eab170aed614fab', 'https://p.scdn.co/mp3-preview/492d170729d672b0e224f399e116d5dafb80755f?cid=c551bf26249e4acf9eab170aed614fab'
            'https://p.scdn.co/mp3-preview/598e05a7fd20278aca5477e1993b252e8fc97daf?cid=c551bf26249e4acf9eab170aed614fab', 'https://p.scdn.co/mp3-preview/8dd93c0af476c8cce9a49b7ad7554c3bb1880555?cid=c551bf26249e4acf9eab170aed614fab',
            'https://p.scdn.co/mp3-preview/29bcbadcb6ee2d7ad5564c2ee5f17520afb1489b?cid=c551bf26249e4acf9eab170aed614fab', 'https://p.scdn.co/mp3-preview/7d0c01de9171bfde69ceeda7625f387f710dcc5b?cid=c551bf26249e4acf9eab170aed614fab']
        
        # ANN
        # 차원
        f = 256
        t = AnnoyIndex(f, 'angular')
        t.add_item(0, mel_mean_var_concat_1)

        for idx, input_url in enumerate(url):

            mp3_name = getMusic(input_url)
            
            mp3_file = root_path + mp3_name

            print("file 2 : {}".format(mp3_name))

            y2, sr2 = loadmusic(mp3_file)

            # 음원 파일에서 박자, 멜로디 추출
            mel_mean_var_concat_2 = getMelody(y2, sr2)

            t.add_item(idx+1, mel_mean_var_concat_2)

            deleteFile(mp3_name)
            deleteFile(today_song_name)
        
        t.build(3)
        t.save('test.ann')
        
        u = AnnoyIndex(f, 'angular')
        u.load('test.ann')

        
        # new_vector = [ random.gauss(0, 1) for i in range(256)]
        # print(u.get_nns_by_item(0, 3))
        # print(u.get_nns_by_vector(new_vector, 3))

    except Exception as e:
        print("error :", e)
        deleteFile(mp3_name)
        deleteFile(today_song_name)