from typing import Dict, List
from fastapi import Depends, Body, APIRouter
from apscheduler.schedulers.background import BackgroundScheduler
from fastapi.responses import JSONResponse
from schema.request import SearchSongQuizRequest
from schema.response import SongQuizSchema, SongTitleSchema, SongTotalRankSchema
from service import song_quiz
from service.song_quiz import SongQuizService
from fastapi import HTTPException
import logging

router = APIRouter()

"""
오후 11시 30분이 되면 오늘의 예스래 노래 선택
CRON 사용 해서 오후 11시 30분에 선정 -> 인기도 상위 100 에서 랜덤하게 예스레 노래 선정
선택한 곡 기준으로 유사도 계산해 redis 에 저장
계산한 유사도를 바탕으로 순위 reids 에 저장
"""

"""
def get_song_quiz_service():
    return SongQuizService()
"""
song_quiz_service = SongQuizService()


def song_quiz_update(
):
    logging.info("오늘의 음악 선택, 순위 계산해서 저장")
    song_quiz_service.song_quiz_update()


# Apscheduler 초기화, 백그라운드 스케줄러 실행
scheduler = BackgroundScheduler()

# 스케줄러 실행되는 것 확인 완료
# 시간 설정
scheduler.add_job(song_quiz_update, "cron", hour= 11, minute = 36)

scheduler.start()

"""
노래 추측에 대한 결과 
song name 으로 조회 -> song id 로 -> similiarity 순위 조회
SearchSongQuizRequest : name (추측하는 노래 이름)
List[SongQuizSchema] : 노래 ID, 제목, 유사도, 순위, 앨범 이미지, 정답 여부 
"""
@router.post("/quiz", status_code=200, response_model= List[SongQuizSchema])
async def song_guess(
        search_request : SearchSongQuizRequest,
    ):

    song_name = search_request.get("name")

    logging.info("추측하는 song_name %s", song_name)

    # 동일한 제목의 곡들이 여러개 있을 수 있다
    song_quiz_result : List[SongQuizSchema] = song_quiz_service.get_song_result(song_name)

    if not song_quiz_result:
        raise HTTPException(status_code=404, detail="추측하는 노래 존재하지 X")

    return song_quiz_result


"""
사용자 입력시 해당 글자로 시작하는 곡 5개 반환
request : 곡 제목
response : 해당 곡 제목으로 시작하는 유사한 제목의 곡 5곡
"""
@router.post("/quiz/search", status_code=200, response_model=Dict[str, List[SongTitleSchema]])
async def song_search(
        search_request : SearchSongQuizRequest,
):
    song_name = search_request.get("name")

    logging.info(f"song name : {song_name}")

    song_list_result : List[SongTitleSchema] = song_quiz_service.search_song_title(song_name)

    if song_list_result:
        song_dict = {"song": song_list_result}
    else:
        song_dict = {"song": []}

    return song_dict

    

"""
1000개의 유사도 순위 보기 정보
"""
@router.get("/quiz/result", status_code=200, response_model=Dict[str, List[SongTotalRankSchema]])
async def song_ranks(
):
    
    song_rank_info : List[SongTotalRankSchema] = song_quiz_service.get_ranks()

    if not song_rank_info:
        raise HTTPException(status_code=404, detail="노래 순위 정보가 반환되지 않았습니다")

    rank_dict = {"rank" : song_rank_info}

    return rank_dict