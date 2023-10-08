from fastapi import FastAPI, Request
from api import song_quiz_api, song_recommend_api, song_vector_api
import logging
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()
app.include_router(song_quiz_api.router)
app.include_router(song_recommend_api.router)
app.include_router(song_vector_api.router)


"""
CORS 설정
"""
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:5173"],  
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)
