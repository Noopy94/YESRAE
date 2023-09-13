from fastapi import FastAPI
from api import song_quiz

app = FastAPI()
app.include_router(song_quiz.router)





@app.get("/")
def check_handler():
    return {"hello" : "yesrae"}


