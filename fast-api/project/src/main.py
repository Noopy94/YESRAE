from fastapi import FastAPI, Depends
from requests import Session

from database.connection import get_db


from api import song_quiz

app = FastAPI()
app.include_router(song_quiz.router)





@app.get("/")
def check_handler():
    return {"ping" : "pong"}
