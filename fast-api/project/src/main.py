from fastapi import FastAPI, Depends
from requests import Session

from project.src.database.connection import get_db
from project.src.schema.response import SongResult

app = FastAPI()





@app.get("/")
def check_handler():
    return {"ping" : "pong"}


"""
TODO
일정한 시간이 되면 오늘의 예스래 노래 선택
CRON 사용 해서 자정에 선정 -> 인기도 상위 50 에서 랜덤하게 추출해서 DB 에 저장
선택한 곡 기준으로 유사도 계산 후 저장 (redis 사용)
"""



"""
TODO
클라이언트 곡 request
입력한 곡의 정답 여부, 유사도 정보, 순위 정보 response
"""
