from typing import List
from fastapi import Depends, Body, APIRouter
from apscheduler.schedulers.background import BackgroundScheduler
from schema.response import SongQuizSchema
from service import song_quiz
from service.song_quiz import SongQuizService

router = APIRouter()

"""
TODO
오후 11시 30분이 되면 오늘의 예스래 노래 선택
CRON 사용 해서 오후 11시 30분에 선정 -> 인기도 상위 100 에서 랜덤하게  선정
선택한 곡 기준으로 유사도 계산 후 저장 (redis 사용) , 유사도 순위 저장
"""


def get_song_quiz_service():
    return SongQuizService()


def song_quiz_update(
        # song_quiz_service: SongQuizService = Depends(get_song_quiz_service),
):
    print("오늘의 음악 선택, 순위 계산해서 저장")
    # song_quiz_service.song_quiz_update()
    get_song_quiz_service().song_quiz_update()


# Apscheduler 초기화, 백그라운드 스케줄러 실행
scheduler = BackgroundScheduler()

# 스케줄러 실행되는 것 확인 완료
scheduler.add_job(song_quiz_update, "cron", hour=21, minute = 31)

scheduler.start()

"""
TODO
클라이언트 곡 제목 request
song name 으로 조회 -> song id 로 -> similiarity 순위 조회
입력한 곡의 정답 여부, 유사도 정보, 순위 정보 response
"""
@router.get("/quiz/{song_name}", status_code=200, response_model= List[SongQuizSchema])
async def song_search(
        song_name : str,
    ):
    
    song_quiz_result : List[SongQuizSchema] = get_song_quiz_service.search_song_result(str)

    return song_quiz_result
