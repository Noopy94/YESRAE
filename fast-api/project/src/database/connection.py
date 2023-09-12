from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
import aioredis


DATABASE_URL = "mysql+pymysql://ssafy:ssafy@localhost:3306/yesrae"

engine = create_engine(DATABASE_URL, echo = True)

SessionFactory = sessionmaker(autocommit = False, autoflush=False, bind=engine)

REDIS_URL = "redis://localhost"
redis = aioredis.from_url(REDIS_URL)

def get_db():

    session = SessionFactory()

    try:
        yield session
    finally:
        session.close()

