from sqlalchemy import  create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

DB_URL = "mysql+pymysql://root:ssafy@localhost:3306/yesrae?charset=utf8mb4"

engine = create_engine(DB_URL, encoding = 'utf-8')

SessionLocal = sessionmaker(autocommit = False, autoflush= False, bind=engine)

Base = declarative_base()