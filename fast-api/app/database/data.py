# 노맨틀 계획
"""
1. spotify 30 초 미리 듣기를 다운 받는다 (구현 O, 사용 X -> Spotify 에서 30초 미리 듣기 제공하지 않는 경우 존재) 
2. 해당 데이터를 wav로 바꾸고 음원 분석 수행 후 지운다 (구현 O, 사용 X)
3. 음원 분석 기준 (멜로디와 박자가 유의미할 것 같음) (템포, mel_freq 구하기) (구현 O, 사용 X)
4. 음원 분석 데이터 저장 -> 유사도를 구하기 위한 데이터  (템포 컬럼, mel_freqs 컬럼 저장) (템포 정보는 Spotify API 사용)
5. 매일 일정한 시간이 되면 정답 곡 한개 선택 (인기도 고려)
6. 정답곡을 기준으로 해당 곡과의 전체 곡의 유사도를 계산
7. DB 에 매일 정답곡 기준으로 유사도 갱신
"""

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
변경 사항

DB 에 템포 정보 넣을 필요 없다, Spotify 에서 제공하는 박자 정보 사용
"""



from scipy.spatial import distance
import librosa
import numpy as np
import wget
import os


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
    # 피치 정보
    melody, _ = librosa.core.piptrack(y=y, sr=sr)
    # S : 스펙토그램
    mel_freqs = librosa.feature.melspectrogram(S=melody, sr=sr)
    return mel_freqs

"""
입력한 음원 파일명에 해당하는 파일 삭제
:filename : 파일명
"""
def deleteFile(filename):
    if os.path.isfile(filename):
        os.remove(filename)



"""
TODO : 노맨틀 정답곡 선정 어떻게 할지 고민해보기 (인기도 순위 고려)
CRON 사용 해서 자정에 선정 -> 인기도 상위 50 에서 랜덤하게 추출해서 DB 에 저장
"""



"""
두 곡 유사도 계산 후 반환
템포 차이 계산, 두 곡간의 mel_freqs 간의 코사인 유사성 계산 후 템포와 mel_freq 유사성 결합해 유사도 계산

:tempo1 : 노래 1의 예상 글로벌 템포
:tempo2 : 노래 2의 예상 글로벌 템포
:mel_freq1 : 노래 1의 멜 스펙토그램 
:mel_freq2 : 노래 1의 멜 스펙토그램 
:return : 계산한 두 곡간의 유사도 정보
"""
def calSimilarity(tempo1, tempo2, mel_freq1, mel_freq2):

    # 두 곡간의 템포 차이 계산
    tempo_difference = abs(tempo1 - tempo2)

    """
    TODO :  DB 에 mel_freq 데이터를 문자열로 저장되어 있어서 배열로 변환
    """
    # mel_freq1 = np.array(eval(mel_freq1))
    # mel_freq2 = np.array(eval(mel_freq2))

    # 산술 평균 계산
    mel_freq1_res = np.mean(mel_freq1, axis = -1)
    mel_freq2_res = np.mean(mel_freq2, axis = -1)

     # 두 곡의 mel_freqs 간의 코사인 유사성 계산
    cosine_sim = 1 - distance.cosine(mel_freq1_res.ravel(), mel_freq2_res.ravel())

    print(f"Tempo Difference: {tempo_difference}")
    print(f"Mel Frequency Cosine Similarity: {cosine_sim}")

    # 템포 와 mel_freqs 유사성을 결합하여 유사도 계산
    similarity_score = (20 * cosine_sim + (1 / (1 + tempo_difference))) / 21

    return similarity_score


"""
TODO : DB 에서 acousticness, danceability, instrumentalness, tempo, time_signature, mode(음계) 
가져와서 곡의 특징 계산해서 저장
"""

"""
acousticness, danceability, instrumentalness, tempo, time_signature, mode(음계) 정보를 바탕으로
곡의 특징 수치화
"""
def calculate(acousticness, danceability, instrumentalness, tempo, time_signature, mode):

    rate = [0.05, 0.05, 0.2, 0.4, 0.1, 0.2]
    type = [ acousticness,  danceability, instrumentalness,  tempo, time_signature, mode]

    result = sum(x * y for x, y in zip(rate, type))


    return result


"""
TODO
DB 에 두 곡간 유사도 정보 계산 후  저장
"""

"""
TODO 
곡간의 수치화 정보 비교
"""
def calculate_percentage(character1, character2):

    max_diff = 1.0

    diff = abs(character1 - character2)

    similarity = (max_diff - diff) / max_diff

    return similarity




"""
TEST 용
"""

# 30초 미리 듣기 API 저장
url = ["https://p.scdn.co/mp3-preview/afb3b416f80fe7e3a504fd809af164fd6199f7aa?cid=b76e1a72191a49e1bd4cc3b5aaa2511b"]

# 음원 파일 저장하는 경로
root_path = os.getcwd() +"\\"
print(f"root path : {root_path}")


# 오늘의 정답 노래 이름
today_song_name = "aroha.wav"
# 오늘의 정답 노래 파일
today_song_file = root_path + today_song_name


"""
유사도 TEST 용
"""
# for input_url in range(1):
for input_url in url:

    mp3_name = getMusic(input_url)

    # 음악 파일 로드
    # nomantle_name = "Cruel_Summer.mp3"
    # mp3_name = "aroha2.wav"
    
    mp3_file = root_path + mp3_name

    print("file 1 : {}".format(today_song_name))
    print("file 2 : {}".format(mp3_name))

    
    y1, sr1 = loadmusic(today_song_file)
    y2, sr2 = loadmusic(mp3_file)

    # 음원 파일에서 박자, 멜로디 추출
    mel_freqs_1 = getMelody(y1, sr1)
    mel_freqs_2 = getMelody(y2, sr2)

    print(f"mel_freqs_1 : {mel_freqs_1}")
    print(f"mel_freqs_2 : {mel_freqs_2}")

    similarity_score = calSimilarity(getTempo(y1, sr1), getTempo(y2, sr2), mel_freqs_1, mel_freqs_2)

    print(f"Overall Similarity Score: {similarity_score}")

    deleteFile(mp3_name)


# 멜로디 정보 없을 때 
song_1 = [ 0.172,  0.608, 0,  109.977, 4, 1]
song_2 = [ 0.18,   0.776, 0.0000344,  149.921, 4, 0]

def test_without_melody(): 
    song_1_character= calculate(song_1[0], song_1[1], song_1[2], song_1[3], song_1[4], song_1[5])

    song_2_character= calculate(song_2[0], song_2[1], song_2[2], song_2[3], song_2[4], song_2[5])

    print(f"song_1_character {song_1_character}")

    print(f"song_2_character {song_2_character}")

    similarity = calculate_percentage(song_1_character, song_2_character)

    print(f"similarity {similarity}")

test_without_melody()