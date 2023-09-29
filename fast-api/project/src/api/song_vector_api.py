from fastapi import APIRouter
from service.song_vector import SongVector
import logging
from apscheduler.schedulers.background import BackgroundScheduler

router = APIRouter()

song_vector_service = SongVector()

"""
노래 vector 값 추출 후 저장
"""
def save_song_vector(
):
    logging.info("노래 특성값 DB 에 저장")
    song_vector_service.save_song_vector()


scheduler = BackgroundScheduler()

# 스케줄러 실행되는 것 확인 완료
# 시간 설정
scheduler.add_job(save_song_vector, "cron", hour= 7, minute = 42)

scheduler.start()
