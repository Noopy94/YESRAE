from pymongo import MongoClient
import os
from dotenv import load_dotenv

MONGO_DB_HOST = os.getenv("MONGO_DB_HOST")
MONGO_DB_PORT = os.getenv("MONGO_DB_PORT")
MONGO_DB_NAME = os.getenv("MONGO_DB_NAME")
MONGO_DB_USER = os.getenv("MONGO_DB_USER")
MONGO_DB_PASSWORD = os.getenv("MONGO_DB_PASSWORD")


MONGO_CONNECTION_STRING = f"mongodb://{MONGO_DB_USER}:{MONGO_DB_PASSWORD}@{MONGO_DB_HOST}:{MONGO_DB_PORT}/{MONGO_DB_NAME}"

# MongoDB 클라이언트를 초기화
mongo_client = MongoClient(MONGO_CONNECTION_STRING)

# MongoDB 데이터베이스를 선택
mongo_db = mongo_client[MONGO_DB_NAME]