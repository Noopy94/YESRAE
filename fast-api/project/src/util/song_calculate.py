import numpy as np

"""
spotify API 에서 제공하는 음악에 대한 특징값 이용해서 가중치로 유사도 구하기 
"""
"""
테스트 데이터
그룹 1) 발라드
그룹 2) 락
그룹 3) 댄스 곡
그룹 4) 힙합
"""


# 1-1) 산들 (복면 가왕) - 응급실
test1_1 = {
    "danceability": 0.395,
    "energy": 0.348,
    "tune": 3,
    "loudness": -6.611,
    "mode": 1,
    "speechiness": 0.0286,
    "acousticness": 0.155,
    "instrumentalness": 0,
    "liveness": 0.134,
    "valence": 0.112,
    "tempo": 138.588,
    "type": "audio_features",
    "id": "74U1eDwX6i7ihyGRuDE105",
    "uri": "spotify:track:74U1eDwX6i7ihyGRuDE105",
    "track_href": "https://api.spotify.com/v1/tracks/74U1eDwX6i7ihyGRuDE105",
    "analysis_url": "https://api.spotify.com/v1/audio-analysis/74U1eDwX6i7ihyGRuDE105",
    "duration_ms": 246782,
    "time_signature": 4
}

# 1-2) 이지 - 응급실
test1_2 = {
    "danceability": 0.579,
    "energy": 0.374,
    "tune": 3,
    "loudness": -6.64,
    "mode": 1,
    "speechiness": 0.0272,
    "acousticness": 0.593,
    "instrumentalness": 0,
    "liveness": 0.114,
    "valence": 0.217,
    "tempo": 139.917,
    "type": "audio_features",
    "id": "5XVEx1pTUR4T7ABtXoGGxx",
    "uri": "spotify:track:5XVEx1pTUR4T7ABtXoGGxx",
    "track_href": "https://api.spotify.com/v1/tracks/5XVEx1pTUR4T7ABtXoGGxx",
    "analysis_url": "https://api.spotify.com/v1/audio-analysis/5XVEx1pTUR4T7ABtXoGGxx",
    "duration_ms": 226548,
    "time_signature": 4
}

# 1-3) 정승환 - 너였다면
test1_3 = {
    "danceability": 0.387,
    "energy": 0.515,
    "tune": 2,
    "loudness": -5.529,
    "mode": 1,
    "speechiness": 0.0338,
    "acousticness": 0.686,
    "instrumentalness": 0,
    "liveness": 0.16,
    "valence": 0.199,
    "tempo": 135.221,
    "type": "audio_features",
    "id": "5i6gHFXg4aLK5xvc2jJJC5",
    "uri": "spotify:track:5i6gHFXg4aLK5xvc2jJJC5",
    "track_href": "https://api.spotify.com/v1/tracks/5i6gHFXg4aLK5xvc2jJJC5",
    "analysis_url": "https://api.spotify.com/v1/audio-analysis/5i6gHFXg4aLK5xvc2jJJC5",
    "duration_ms": 272847,
    "time_signature": 4
}

# 2-1) ellegarden - marryme
test2_1 = {
    "danceability": 0.298,
    "energy": 0.987,
    "tune": 1,
    "loudness": -0.77,
    "mode": 1,
    "speechiness": 0.0623,
    "acousticness": 0.00341,
    "instrumentalness": 0.00000318,
    "liveness": 0.304,
    "valence": 0.304,
    "tempo": 172.084,
    "type": "audio_features",
    "id": "0ZHWFqeBZz9DJB3l8G8UJo",
    "uri": "spotify:track:0ZHWFqeBZz9DJB3l8G8UJo",
    "track_href": "https://api.spotify.com/v1/tracks/0ZHWFqeBZz9DJB3l8G8UJo",
    "analysis_url": "https://api.spotify.com/v1/audio-analysis/0ZHWFqeBZz9DJB3l8G8UJo",
    "duration_ms": 169187,
    "time_signature": 4
}

# 2-2) ellegarden - supernova
test2_2 = {
    "danceability": 0.368,
    "energy": 0.977,
    "tune": 5,
    "loudness": -0.493,
    "mode": 1,
    "speechiness": 0.105,
    "acousticness": 0.0169,
    "instrumentalness": 0,
    "liveness": 0.397,
    "valence": 0.325,
    "tempo": 180.082,
    "type": "audio_features",
    "id": "3Y55AfuHLUFTQEixZJI5VU",
    "uri": "spotify:track:3Y55AfuHLUFTQEixZJI5VU",
    "track_href": "https://api.spotify.com/v1/tracks/3Y55AfuHLUFTQEixZJI5VU",
    "analysis_url": "https://api.spotify.com/v1/audio-analysis/3Y55AfuHLUFTQEixZJI5VU",
    "duration_ms": 225080,
    "time_signature": 4
}
# 2-3) green day - basket case
test2_3 = {
    "danceability": 0.442,
    "energy": 0.943,
    "tune": 3,
    "loudness": -3.205,
    "mode": 1,
    "speechiness": 0.0602,
    "acousticness": 0.00293,
    "instrumentalness": 0.00000871,
    "liveness": 0.091,
    "valence": 0.781,
    "tempo": 85.064,
    "type": "audio_features",
    "id": "6L89mwZXSOwYl76YXfX13s",
    "uri": "spotify:track:6L89mwZXSOwYl76YXfX13s",
    "track_href": "https://api.spotify.com/v1/tracks/6L89mwZXSOwYl76YXfX13s",
    "analysis_url": "https://api.spotify.com/v1/audio-analysis/6L89mwZXSOwYl76YXfX13s",
    "duration_ms": 181533,
    "time_signature": 4
}

# 3-1) 르세라핌 - 안티프레자일
test3_1 = {
    "danceability": 0.879,
    "energy": 0.784,
    "tune": 10,
    "loudness": -3.944,
    "mode": 0,
    "speechiness": 0.081,
    "acousticness": 0.0759,
    "instrumentalness": 0,
    "liveness": 0.125,
    "valence": 0.83,
    "tempo": 105.019,
    "type": "audio_features",
    "id": "0bMoNdAnxNR0OuQbGDovrr",
    "uri": "spotify:track:0bMoNdAnxNR0OuQbGDovrr",
    "track_href": "https://api.spotify.com/v1/tracks/0bMoNdAnxNR0OuQbGDovrr",
    "analysis_url": "https://api.spotify.com/v1/audio-analysis/0bMoNdAnxNR0OuQbGDovrr",
    "duration_ms": 184444,
    "time_signature": 4
}
# 3-2) 르세라핌 - unforgiven
test3_2 = {
    "danceability": 0.795,
    "energy": 0.875,
    "tune": 4,
    "loudness": -4.079,
    "mode": 0,
    "speechiness": 0.0525,
    "acousticness": 0.111,
    "instrumentalness": 0.0000894,
    "liveness": 0.107,
    "valence": 0.375,
    "tempo": 104.008,
    "type": "audio_features",
    "id": "51vRumtqbkNW9wrKfESwfu",
    "uri": "spotify:track:51vRumtqbkNW9wrKfESwfu",
    "track_href": "https://api.spotify.com/v1/tracks/51vRumtqbkNW9wrKfESwfu",
    "analysis_url": "https://api.spotify.com/v1/audio-analysis/51vRumtqbkNW9wrKfESwfu",
    "duration_ms": 182149,
    "time_signature": 4
}

# 3-3) 에스파 - blackmamba
# test3_3 = {
#     "danceability": 0.612,
#     "energy": 0.912,
#     "tune": 2,
#     "loudness": -2.853,
#     "mode": 1,
#     "speechiness": 0.0796,
#     "acousticness": 0.064,
#     "instrumentalness": 0.000799,
#     "liveness": 0.0843,
#     "valence": 0.472,
#     "tempo": 90.006,
#     "type": "audio_features",
#     "id": "7v1X2PGU3uZXu7tzFTTsSh",
#     "uri": "spotify:track:7v1X2PGU3uZXu7tzFTTsSh",
#     "track_href": "https://api.spotify.com/v1/tracks/7v1X2PGU3uZXu7tzFTTsSh",
#     "analysis_url": "https://api.spotify.com/v1/audio-analysis/7v1X2PGU3uZXu7tzFTTsSh",
#     "duration_ms": 174933,
#     "time_signature": 4
# }

# 아브라카다브라
test3_3 = {
     "acousticness": 0.0224,
      "analysis_url": "https://api.spotify.com/v1/audio-analysis/33bXkEzVYbyp1nCTx4KNwc",
      "danceability": 0.803,
      "duration_ms": 182308,
      "energy": 0.973,
      "id": "33bXkEzVYbyp1nCTx4KNwc",
      "instrumentalness": 0.0000867,
      "tune": 5,
      "liveness": 0.0846,
      "loudness": -2.343,
      "mode": 1,
      "speechiness": 0.0953,
      "tempo": 128.022,
      "time_signature": 4,
      "track_href": "https://api.spotify.com/v1/tracks/33bXkEzVYbyp1nCTx4KNwc",
      "type": "audio_features",
      "uri": "spotify:track:33bXkEzVYbyp1nCTx4KNwc",
      "valence": 0.794
}

# 4-1) grey - 하기나해
test4_1 = {
    "danceability": 0.794,
    "energy": 0.62,
    "tune": 0,
    "loudness": -4.557,
    "mode": 1,
    "speechiness": 0.164,
    "acousticness": 0.5,
    "instrumentalness": 0,
    "liveness": 0.131,
    "valence": 0.898,
    "tempo": 98.008,
    "type": "audio_features",
    "id": "1RXMtaUUM2hyixi37Z7vA5",
    "uri": "spotify:track:1RXMtaUUM2hyixi37Z7vA5",
    "track_href": "https://api.spotify.com/v1/tracks/1RXMtaUUM2hyixi37Z7vA5",
    "analysis_url": "https://api.spotify.com/v1/audio-analysis/1RXMtaUUM2hyixi37Z7vA5",
    "duration_ms": 228098,
    "time_signature": 4
}
# 4-2) 로꼬 - 감아
test4_2 = {
    "danceability": 0.72,
    "energy": 0.846,
    "tune": 7,
    "loudness": -4.018,
    "mode": 0,
    "speechiness": 0.0456,
    "acousticness": 0.0461,
    "instrumentalness": 0,
    "liveness": 0.579,
    "valence": 0.511,
    "tempo": 118.038,
    "type": "audio_features",
    "id": "1dteYDjCFppP33ANUGfKFF",
    "uri": "spotify:track:1dteYDjCFppP33ANUGfKFF",
    "track_href": "https://api.spotify.com/v1/tracks/1dteYDjCFppP33ANUGfKFF",
    "analysis_url": "https://api.spotify.com/v1/audio-analysis/1dteYDjCFppP33ANUGfKFF",
    "duration_ms": 230112,
    "time_signature": 4
}
# 4-3) 박재범 - all i wanna do
# test4_3 = {
#     "danceability": 0.403,
#     "energy": 0.724,
#     "tune": 8,
#     "loudness": -6.064,
#     "mode": 1,
#     "speechiness": 0.246,
#     "acousticness": 0.377,
#     "instrumentalness": 0,
#     "liveness": 0.0927,
#     "valence": 0.428,
#     "tempo": 97.133,
#     "type": "audio_features",
#     "id": "2FWquqPNxte8iqZ3ATQG0p",
#     "uri": "spotify:track:2FWquqPNxte8iqZ3ATQG0p",
#     "track_href": "https://api.spotify.com/v1/tracks/2FWquqPNxte8iqZ3ATQG0p",
#     "analysis_url": "https://api.spotify.com/v1/audio-analysis/2FWquqPNxte8iqZ3ATQG0p",
#     "duration_ms": 216786,
#     "time_signature": 4
# }

# 보고 싶다
test4_3 = {

        "acousticness": 0.806,
      "analysis_url": "https://api.spotify.com/v1/audio-analysis/4i39vkBJzBnYvBMua65Rwi",
      "danceability": 0.514,
      "duration_ms": 244128,
      "energy": 0.311,
      "id": "4i39vkBJzBnYvBMua65Rwi",
      "instrumentalness": 0,
      "tune": 5,
      "liveness": 0.0674,
      "loudness": -8.027,
      "mode": 1,
      "speechiness": 0.0286,
      "tempo": 127.948,
      "time_signature": 4,
      "track_href": "https://api.spotify.com/v1/tracks/4i39vkBJzBnYvBMua65Rwi",
      "type": "audio_features",
      "uri": "spotify:track:4i39vkBJzBnYvBMua65Rwi",
      "valence": 0.177
}



"""
가중치 정보
"""
weight = {
    "acousticness": 0.25,
    "danceability": 0.05, 
    "energy": 0.1,
    "instrumentalness": 0.0,
    "tune": 0.1,
    "liveness": 0.05,
    "loudness": 0.15,
    "mode": 0.0,
    "speechiness": 0.1,
    "valence": 0.1,
    "tempo": 0.1,
    "time_signature": 0.0
}


"""
시그모이드 함수
0 ~ 1 반환값 맞추기 위해 사용
:x
"""
# def sigmoid(x):
#     return 1 / (1 + np.exp(-x))

def sigmoid(x):
    scale = 100 
    return scale / (1 + np.exp(-x))

def sigmoid_scaled(x, scale=100):
    return scale / (1 + np.exp(-x))


"""
음악 특성 관련된 클래스
"""

class SongInfo:
    def __init__(self, acousticness=None, danceability=None, energy=None, instrumentalness=None, tune=None, liveness=None
                 , loudness=None, mode=None, speechiness=None, valence=None, tempo=None, time_signature=None, **kwargs):
        """
        acousticness : 0 ~ 1 
        danceability : 0 ~ 1 
        energy : 0 ~ 1 
        instrumentalness :  0 ~ 1 
        tune: -1 ~ 11
        liveness ~= 0 ~ 1 
        loudness = -60 ~ 0
        mode : 0 ~ 1
        speechiness : 0 ~ 1
        tempo : not defined
        time_signature : 3 ~ 7
        valence : 0 ~ 1
        """
        self.acousticness = acousticness
        self.danceability = danceability
        self.energy = energy
        self.instrumentalness = instrumentalness
        self.tune = tune
        self.liveness = liveness
        self.loudness = loudness
        self.mode = mode
        self.speechiness = speechiness
        self.valence = valence
        self.tempo = tempo
        self.time_signature = time_signature

        self.feature_list = list(self.__dict__.keys())
        self._index = 0

    def __iter__(self):
        return self

    def __next__(self):
        if self._index < len(self.feature_list):
            attr_name = self.feature_list[self._index]
            # feature 변수 에 해당하는 값
            attr_value = getattr(self, attr_name)
            self._index += 1
            return attr_name, attr_value
        else:
            raise StopIteration

    def len_feature(self):
        return len(self.feature_list)

    def get_value(self, vals):
        return self.__dict__[vals]



"""
기준 곡과 다른 곡의 유사도 측정
"""
class CalculateUtil:

    def calculate(self, target_song: SongInfo, const_song: SongInfo):
        difference = 0
        # find the rate weights


        for idx, (name, value) in enumerate(const_song):

            if name == "tempo" or name == "tune" or name == "time_signature":
                # difference 가 커지면, 2 곡간의 차이는 크다
                # tempo같은 경우는 같으면 differerence가 1, 다르면 0에 가까워진다
                difference = difference - weight[name] * 1 / (1 + abs((value - target_song.get_value(name))))
            else:

                difference = difference + weight[name] * abs((value - target_song.get_value(name)))

        return sigmoid(-1 * difference) * 1.83
        #return sigmoid_scaled(-1 * difference)


"""
TEST 용
유사도 어떻게 나오는지 확인
"""
calculate_util = CalculateUtil()

"""
for target_idx in range(1, 5):
    for item in range(1, 4):
        target = "test" + str(target_idx) + "_" + str(item)



        for const_idx in range(1, 4):
            # 같은 군집일 때
            if const_idx == target_idx:
                for const_item in range(1, 4):
                    const = "test" + str(const_idx) + "_" + str(const_item)
                    # if target == const:
                    #     pass
                    # else:
                    target_song = SongInfo(**eval(target))
                    const_song = SongInfo(**eval(const))

                    similarity = calculate_util.calculate(target_song, const_song)
                    print("similarity {} & {} = {:.2f}".format(target, const, similarity))
            # 다른 군집일 때
            else:
                for const_item in range(1, 4):
                    const = "test" + str(const_idx) + "_" + str(const_item)
                    if target == const:
                        raise RuntimeError("Different cluster should not be same")
                    else:
                        target_song = SongInfo(**eval(target))
                        const_song = SongInfo(**eval(const))

                        similarity = calculate_util.calculate(target_song, const_song)
                        print("similarity {} & {} = {:.2f}".format(target, const, similarity))

"""