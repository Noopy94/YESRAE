from fastapi import APIRouter
from schema.request import RecommendSongRequest
from service.song_vector import SongVector
import logging
from apscheduler.schedulers.background import BackgroundScheduler

router = APIRouter()

song_vector_service = SongVector()


def save_song_vector(
):
    logging.info("노래 특성값 DB 에 저장")
    song_vector_service.save_song_vector()


scheduler = BackgroundScheduler()

# 스케줄러 실행되는 것 확인 완료
# 시간 설정
scheduler.add_job(save_song_vector, "cron", hour= 12, minute = 42)

scheduler.start()


# 노래 ID 바탕으로 추천할 노래 선정
async def song_recommend(
    songs_to_recommend : RecommendSongRequest
):
    song_vector_service.recommend_songs(songs_to_recommend)