# 노맨틀 계획


"""
ANN -> 추천 노래 선정
"""

from scipy.spatial import distance
import librosa
import numpy as np
import wget
import os
from annoy import AnnoyIndex


"""
spotify 30초 미리 듣기 url로 음원 파일 가져오기
:url : 30초 음원 url
:return : 가져온 음원 파일 이름
"""
def getMusic(url):
    mp3_name = wget.download(url)
    return mp3_name

"""
음성 파일에서 오디오 시계열, 샘플링 주파수 정보 추출
:mp3_file : 음성 파일
:return : 오디오 시계열, 샘플링 주파수
"""
def loadmusic(mp3_file):
    # y : 오디오 신호를 나타내는 1차원 numpy 배열
    # sr : 샘플링 주파수(Sampling Rate), 연속적 신호에서 얻어진 초당 샘플링 횟수

    y, sr = librosa.load(mp3_file)
    print("duration ", len(y)/sr)
    return y, sr


"""
입력 받은 음성 정보에서 비트 정보 추출 
:y  : 오디오 시계열 데이터
:sr : 샘플링 주파수
:return : 예상 글로벌 템포 (분당 비트 수)
"""
def getTempo(y, sr):
    tempo, beat_frames = librosa.beat.beat_track(y=y, sr=sr)
    return tempo

"""
피치 추적, 멜 스펙토그램 정보 추출
:y  : 오디오 시계열 데이터
:sr : 샘플링 주파수, 속도
:return : 멜 스펙토그램 
"""
# 입력 받은 노래의 멜로디 정보 추출
def getMelody(y, sr):

    # 간격 조절

    # 피치 정보
    melody, _ = librosa.core.piptrack(y=y, sr=sr)
    # S : 스펙토그램
    # mel_freqs = librosa.feature.melspectrogram(S=melody, sr=sr, n_mels=60)
    mel_freqs = librosa.feature.melspectrogram(S=melody, sr=sr)
    print("mel_freq_shape : ", mel_freqs.shape)
    print("type : ", type(mel_freqs[0][0]))

    mel_freqs_mean = np.mean(mel_freqs, axis=1)
    mel_freqs_var = np.var(mel_freqs, axis=1)
    print("mel_freq_mean_shape :", mel_freqs_mean.shape)
    print("mel_freqs_var_shape :", mel_freqs_var.shape)

    mel_mean_var_concat = np.concatenate((mel_freqs_mean, mel_freqs_var), axis = 0)
    print("mel_mean_var_concat : ",  mel_mean_var_concat.shape)
    return mel_mean_var_concat


"""
입력한 음원 파일명에 해당하는 파일 삭제
:filename : 파일명
"""
def deleteFile(filename):
    if os.path.isfile(filename):
        os.remove(filename)





def calSimilarity(tempo1, tempo2, mel_mean_var_concat1, mel_mean_var_concat2):

    # 두 곡간의 템포 차이 계산
    tempo_difference = abs(tempo1 - tempo2)

    # 두 곡의 mel_freqs 간의 코사인 유사성 계산
    cosine_sim = 1 - distance.cosine(mel_mean_var_concat1, mel_mean_var_concat2)

    print(f"Tempo Difference: {tempo_difference}")
    print(f"Mel Frequency Cosine Similarity: {cosine_sim}")

    # 템포 와 mel_freqs 유사성을 결합하여 유사도 계산
    similarity_score = (20 * cosine_sim + (1 / (1 + tempo_difference))) / 21

    return similarity_score


if __name__ == "__main__":

    try:

        

        # 음원 파일 저장하는 경로
        root_path = os.getcwd() +"\\"
        print(f"root path : {root_path}")

        

        # root_path = root_path + "\\src"
        # os.chdir(root_path)
        # 오늘의 정답 노래 이름
        #today_song_name = "aroha.wav"
        # 오늘의 정답 노래 파일
        # today_song_file = root_path + today_song_name

        today_song_url  = 'https://p.scdn.co/mp3-preview/8cd16a87465e35652134fb7cd4a276812690d750?cid=c551bf26249e4acf9eab170aed614fab'
        #today_song_url = "https://p.scdn.co/mp3-preview/afb3b416f80fe7e3a504fd809af164fd6199f7aa?cid=b76e1a72191a49e1bd4cc3b5aaa2511b"

        today_song_name = getMusic(today_song_url)
        today_song_file = root_path + today_song_name

        y1, sr1 = loadmusic(today_song_file)

        mel_mean_var_concat_1 = getMelody(y1, sr1)

        # feature_list = []
        # feature_list.append(mel_mean_var_concat_1)
        """
        ==================유사도 TEST 용==================
        """
        # 30초 미리 듣기 API 저장
        url = ['https://p.scdn.co/mp3-preview/a76a5bfcf5145901bd4b7ca88e248a2d897950d6?cid=c551bf26249e4acf9eab170aed614fab', 'https://p.scdn.co/mp3-preview/492d170729d672b0e224f399e116d5dafb80755f?cid=c551bf26249e4acf9eab170aed614fab'
            'https://p.scdn.co/mp3-preview/598e05a7fd20278aca5477e1993b252e8fc97daf?cid=c551bf26249e4acf9eab170aed614fab', 'https://p.scdn.co/mp3-preview/8dd93c0af476c8cce9a49b7ad7554c3bb1880555?cid=c551bf26249e4acf9eab170aed614fab',
            'https://p.scdn.co/mp3-preview/29bcbadcb6ee2d7ad5564c2ee5f17520afb1489b?cid=c551bf26249e4acf9eab170aed614fab', 'https://p.scdn.co/mp3-preview/7d0c01de9171bfde69ceeda7625f387f710dcc5b?cid=c551bf26249e4acf9eab170aed614fab']
        
        f = 256
        t = AnnoyIndex(f, 'angular')
        t.add_item(0, mel_mean_var_concat_1)
        for idx, input_url in enumerate(url):

            mp3_name = getMusic(input_url)
            
            mp3_file = root_path + mp3_name
            # today_song_file = root_path + mp3_name

            # print("file 1 : {}".format(today_song_name))
            print("file 2 : {}".format(mp3_name))

            y2, sr2 = loadmusic(mp3_file)

            # 음원 파일에서 박자, 멜로디 추출
            mel_mean_var_concat_2 = getMelody(y2, sr2)

            # feature_list.append(mel_mean_var_concat_2)
            # print("len :", len(feature_list))

            t.add_item(idx+1, mel_mean_var_concat_2)

            # similarity_score = calSimilarity(getTempo(y1, sr1), getTempo(y2, sr2), mel_mean_var_concat_1, mel_mean_var_concat_2)

            # print(f"Overall Similarity Score: {similarity_score}")

            deleteFile(mp3_name)
            deleteFile(today_song_name)
        
        t.build(3)
        t.save('test.ann')
    except Exception as e:
        print("error :", e)
        deleteFile(mp3_name)
        deleteFile(today_song_name)
