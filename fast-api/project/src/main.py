from fastapi import FastAPI
from api import song_quiz_api

app = FastAPI()
app.include_router(song_quiz_api.router)


@app.get("/")
def check_handler():
    return {"hello" : "yesrae"}


