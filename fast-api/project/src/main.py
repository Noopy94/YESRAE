from fastapi import FastAPI, Request
from api import song_quiz_api
import logging

app = FastAPI()
app.include_router(song_quiz_api.router)


@app.get("/")
def check_handler():
    return {"hello" : "yesrae"}


"""
@app.middleware("http")
async def log_request(request: Request, call_next):
    # Request 로그 기록
    log_data = {
        "method": request.method,
        "path": request.url.path,
        "query_params": dict(request.query_params),
    }
    logging.info(f"Request: {log_data}")
    
    response = await call_next(request)
    
    # Response 로그 기록
    log_data["status_code"] = response.status_code
    logging.info(f"Response: {log_data}")
    
    return response
"""