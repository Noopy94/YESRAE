
"""
음원 박자, 멜로디 정보 분석 후 유사도 계산
"""

from scipy.spatial import distance
import librosa
import numpy as np
import wget
import os
import torchaudio
import torch


"""
# 박자 정보 추출
tempo, beat_frames = librosa.beat.beat_track(y=y, sr=sr)

# 멜로디 정보 추출
melody, _ = librosa.core.piptrack(y=y1, sr=sr1)
mel_freqs1 = librosa.feature.melspectrogram(S=melody, sr=sr1)

melody2, _ = librosa.core.piptrack(y=y2, sr=sr2)
mel_freqs2 = librosa.feature.melspectrogram(S=melody2, sr=sr2)

# 멜로디 및 박자 정보 출력
print("Tempo (BPM):", tempo)
print("Beat Frames:", beat_frames)
print("Melody Frequencies (Hz):", mel_freqs)
tempo : BPM (분당 박자), beat_frames : 각 박자의 프레임 위치
melody_freqs :  각 시간 스텝에 해당하는 주파수,  각 시간 스텝에서 가장 높은 확률로 감지된 주파수
"""



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
    #print("duration ", len(y)/sr)
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


"""
피치 추적, 멜 스펙토그램 정보 추출
:y  : 오디오 시계열 데이터
:sr : 샘플링 주파수, 속도
:return : 멜 스펙토그램 
"""
# 입력 받은 노래의 멜로디 정보 추출
def getMelody(y, sr):

    # 피치 정보
    melody, _ = librosa.core.piptrack(y=y, sr=sr)
    # S : 스펙토그램
    feature = librosa.feature.melspectrogram(S=melody, sr=sr)

    # 평균
    mel_freqs_mean = np.mean(feature, axis=1)
    # 분산
    mel_freqs_var = np.var(feature, axis=1)

    mel_mean_var_concat = np.concatenate((mel_freqs_mean, mel_freqs_var), axis = 0)

    return  mel_mean_var_concat


"""
입력한 음원 파일명에 해당하는 파일 삭제
:filename : 파일명
"""
def deleteFile(filename):
    if os.path.isfile(filename):
        os.remove(filename)


"""
두 곡 유사도 계산 후 반환 (2차원 배열인 경우)
템포 차이 계산, 두 곡간의 mel_freqs 간의 코사인 유사성 계산 후 템포와 mel_freq 유사성 결합해 유사도 계산

:tempo1 : 노래 1의 예상 글로벌 템포
:tempo2 : 노래 2의 예상 글로벌 템포
:mel_freq1 : 노래 1의 멜 스펙토그램 
:mel_freq2 : 노래 2의 멜 스펙토그램 
:return : 계산한 두 곡간의 유사도 정보
"""
def calSimilarity_2D(tempo1, tempo2, mel_freq1, mel_freq2):

    # 두 곡간의 템포 차이 계산
    tempo_difference = abs(tempo1 - tempo2)

    # 산술 평균 계산
    mel_freq1_res = np.mean(mel_freq1, axis = -1)
    mel_freq2_res = np.mean(mel_freq2, axis = -1)

     # 두 곡의 mel_freqs 간의 코사인 유사성 계산
    cosine_sim = 1 - distance.cosine(mel_freq1_res.ravel(), mel_freq2_res.ravel())

    # 템포 와 mel_freqs 유사성을 결합하여 유사도 계산
    similarity_score = (20 * cosine_sim + (1 / (1 + tempo_difference))) / 21

    return similarity_score



"""
두 곡 유사도 계산 후 반환 (1차원 배열)
템포 차이 계산, 두 곡간의 mel_mean_var_concat 간의 코사인 유사성 계산 후 템포와 mel_mean_var_concat 유사성 결합해 유사도 계산

:tempo1 : 노래 1의 예상 글로벌 템포
:tempo2 : 노래 2의 예상 글로벌 템포
:mel_mean_var_concat1 : 노래 1의 평균, 분산 특성값 
:mel_mean_var_concat2 : 노래 2의 평균, 분산 특성값 
:return : 계산한 두 곡간의 유사도 정보
"""
def calSimilarity(tempo1, tempo2, mel_mean_var_concat1, mel_mean_var_concat2):

    # 두 곡간의 템포 차이 계산
    tempo_difference = abs(tempo1 - tempo2)

    # 두 곡의 mel_freqs 간의 코사인 유사성 계산
    cosine_sim = 1 - distance.cosine(mel_mean_var_concat1, mel_mean_var_concat2)

    # 템포 와 mel_freqs 유사성을 결합하여 유사도 계산
    similarity_score = (20 * cosine_sim + (1 / (1 + tempo_difference))) / 21

    return similarity_score
