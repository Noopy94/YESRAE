# 노맨틀 계획

# 1. spotify 30 초 미리 듣기를 다운 받는다
# 2. 해당 데이터를 wav로 바꾸고 음원 분석 수행 후 지운다
# 3. 음원 분석 기준 (멜로디와 박자가 유의미할 것 같음) (V)
# 4. 음원 분석 데이터 저장 -> 유사도를 구하기 위한 데이터  (템포 컬럼, mel_freqs 컬럼 저장)
# 5. 매일 일정한 시간이 되면 정답 곡 한개 선택
# 6. 정답곡을 기준으로 해당 곡과의 전체 곡의 유사도를 계산
# 7. DB 에 매일 정답곡 기준으로 유사도 갱신 (매일 유사도가 달라지므로 redis 사용이 적합할 것 같음)



# ----------------------------
# 박자 정보 추출
# tempo, beat_frames = librosa.beat.beat_track(y=y, sr=sr)

# 멜로디 정보 추출
# melody, _ = librosa.core.piptrack(y=y1, sr=sr1)
# mel_freqs1 = librosa.feature.melspectrogram(S=melody, sr=sr1)
# melody2, _ = librosa.core.piptrack(y=y2, sr=sr2)
# mel_freqs2 = librosa.feature.melspectrogram(S=melody2, sr=sr2)

# 멜로디 및 박자 정보 출력
# print("Tempo (BPM):", tempo)
# print("Beat Frames:", beat_frames)
# print("Melody Frequencies (Hz):", mel_freqs)
# tempo : BPM (분당 박자), beat_frames : 각 박자의 프레임 위치
# melody_freqs :  각 시간 스텝에 해당하는 주파수,  각 시간 스텝에서 가장 높은 확률로 감지된 주파수
# ------------------------------


from scipy.spatial import distance
import librosa

# 음악 파일 로드
mp3_file1 = "C:\\Users\\SSAFY\\Desktop\\project\\practice\\music\\Supernova.mp3"
mp3_file2 = "C:\\Users\\SSAFY\\Desktop\\project\\practice\\music\\MarryMe.mp3"



# 음원 파일에서 박자, 멜로디 추출

def loadmusic(mp3_file):
    y, sr = librosa.load(mp3_file)
    return y, sr


def getTempo(y, sr):
    tempo, beat_frames = librosa.beat.beat_track(y=y, sr=sr)
    return tempo

def getMelody(y, sr):
    melody, _ = librosa.core.piptrack(y=y, sr=sr)
    mel_freqs = librosa.feature.melspectrogram(S=melody, sr=sr)

    return mel_freqs


y1, sr1 = loadmusic(mp3_file1)
y2, sr2 = loadmusic(mp3_file2)


# 유사도 계산
# 두 곡의 tempo 차이 계산
tempo_difference = abs(getTempo(y1, sr1) - getTempo(y2, sr2))

mel_freqs_1 = getMelody(y1, sr1)
mel_freqs_2 = getMelody(y2, sr2)

# 길이가 다를 수 있다
min_length = min(mel_freqs_1.shape[1], mel_freqs_2.shape[1])
mel_freqs_1 = mel_freqs_1[:, :min_length]
mel_freqs_2 = mel_freqs_2[:, :min_length]

# 두 곡의 mel_freqs 배열을 평균화
# average_mel_freqs = (mel_freqs_1 + mel_freqs_2) / 2


# 두 곡의 mel_freqs 간의 코사인 유사성 계산
# cosine_sim = 1 - distance.cosine(average_mel_freqs.ravel(), mel_freqs_2.ravel())
cosine_sim = 1 - distance.cosine(mel_freqs_1.ravel(), mel_freqs_2.ravel())

# Tempo와 Mel 주파수 유사성을 결합하여 유사도 계산
similarity_score = (cosine_sim + (1 / (1 + tempo_difference))) / 2

print(f"Tempo Difference: {tempo_difference}")
print(f"Mel Frequency Cosine Similarity: {cosine_sim}")
print(f"Overall Similarity Score: {similarity_score}")




