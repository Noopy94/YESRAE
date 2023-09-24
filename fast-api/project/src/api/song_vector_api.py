from fastapi import APIRouter
from service.song_vector import SongVector
import logging

router = APIRouter()

song_vector_service = SongVector()


def save_song_vector(
):
    logging.info("노래 특성값 DB 에 저장")
    song_vector_service.save_song_vector()
    