import os
from dotenv import load_dotenv
import redis

load_dotenv()


def redis_config():
    try:
        REDIS_HOST = os.getenv("REDIS_HOST")
        REDIS_PORT = os.getenv("REDIS_PORT")
        REDIS_DATABASE = os.getenv("REDIS_DATABASE")
        rd = redis.Redis(host=REDIS_HOST, port=REDIS_PORT, db=REDIS_DATABASE)

        return rd

    except:
        print("redis 연결 실패")

