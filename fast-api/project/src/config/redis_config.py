import os
from dotenv import load_dotenv
import redis
import logging

load_dotenv()


def redis_config():
    try:
        REDIS_HOST = os.getenv("REDIS_HOST")
        REDIS_PORT = os.getenv("REDIS_PORT")
        REDIS_DATABASE = os.getenv("REDIS_DATABASE")
        REDIS_PASSWORD = os.getenv("REDIS_PASSWORD")
        rd = redis.Redis(host=REDIS_HOST, port=REDIS_PORT, db=REDIS_DATABASE, password=REDIS_PASSWORD)

        return rd

    except:
        logging.info("redis 연결 실패")

