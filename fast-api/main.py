import uvicorn
from fastapi import FastAPI
from starlette.middleware.cors import CORSMiddleware


# FastAPI 핵심 객체
app = FastAPI()


@app.on_event("startup")
def start():
    pass

@app.on_event("shutdown")
def shutdown():
    pass

if __name__ == '__main__':
    uvicorn.run(app, host = '0.0.0.0', port = 8000)