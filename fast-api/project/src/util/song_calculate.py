import numpy as np

"""
spotify API 에서 제공하는 음악에 대한 특징값 이용해서 가중치로 유사도 구하기 
"""

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


def sigmoid(x):
    scale = 100 
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

        return sigmoid(-1 * difference) * 1.82
        
