from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker


DATABASE_URL = "mysql+pymysql://root:ssafy@localhost:3306/yesrae?charset=utf8mb4"

engine = create_engine(DATABASE_URL, echo = True, encoding = 'utf-8')

SessionFactory = sessionmaker(autocommit = False, autoflush=False, bind=engine)

def get_db():

    session = SessionFactory()

    try:
        yield session
    finally:
        session.close()