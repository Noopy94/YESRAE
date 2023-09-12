from fastapi import Depends, Body, APIRouter
from apscheduler.schedulers.background import BackgroundScheduler
from service import song_quiz
from service.song_quiz import SongQuizService

router = APIRouter()

"""
TODO
오후 11시 30분이 되면 오늘의 예스래 노래 선택
CRON 사용 해서 오후 11시 30분에 선정 -> 인기도 상위 100 에서 랜덤하게  선정
선택한 곡 기준으로 유사도 계산 후 저장 (redis 사용) , 유사도 순위 저장
"""


def song_quiz_update(
        song_quiz_service: SongQuizService = Depends(),
):
    song_quiz_service.song_quiz_update()


# Apscheduler 초기화, 백그라운드 스케줄러 실행
scheduler = BackgroundScheduler()

# 스케줄러 실행되는 것 확인 완료
scheduler.add_job(song_quiz_update, "cron", hour=23, minute=30)

scheduler.start()

"""
TODO
클라이언트 곡 제목 request
song name 으로 조회 -> song id 로 -> similiarity 순위 조회
입력한 곡의 정답 여부, 유사도 정보, 순위 정보 response
"""


@router.get("/quiz/{song_name}", status_code=200)
def song_search(
        song_name : str,
    ):
    pass


"""
mongo db 를 이용해서 미리 DB 에 모든 유사도 저장
"id" : "노래 id",
"diff" : {"다른 노래 id" : "70", "다른 노래 id" : "60" }
"""
