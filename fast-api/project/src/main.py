from fastapi import FastAPI, Request
from api import song_quiz_api, song_vector_api
import logging
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()
app.include_router(song_quiz_api.router)
app.include_router(song_vector_api.router)


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

