from sqlalchemy import Boolean, Column, Integer, String, DateTime, Float

from sqlalchemy.orm import declarative_base

Base = declarative_base()

class Song(Base):

    __tablename__ = "song"

    id = Column(String, primary_key= True, nullable=False)
    name = Column(String, nullable=False)
    album = Column(String, nullable=False)
    album_name = Column(String, nullable=False)
    artist = Column(String, nullable=False)
    artist_name = Column(String, nullable=False)
    genre = Column(String)
    img_url = Column(String, nullable=False)
    preview_url = Column(String, nullable=False)
    release_date = Column(DateTime(), nullable=False)
    duration = Column(Integer, nullable=False, default=0) 
    popularity = Column(Integer,nullable= False, default=0)   
    acousticness = Column(Float, nullable=False, default=0)
    danceability = Column(Float, nullable=False, default=0)
    energy = Column(Float, nullable=False, default=0)
    instrumentalness = Column(Float, nullable=False, default=0)
    key = Column(Integer,  nullable=False, default=0)
    liveness = Column(Float, nullable=False, default=0)
    loudness = Column(Float, nullable=False, default=0)
    mode = Column(Integer,  nullable=False, default=0)
    speechiness = Column(Float, nullable=False, default=0)
    tempo = Column(Float, nullable=False, default=0)
    time_signature = Column(Integer, nullable=False, default=0)
    valence = Column(Float, nullable=False, default=0)
    today_song = Column(bool, nullable=False , default=False)
    
    def __repr__(self):
        return f"Song(id={self.id}, name ={self.name}, album={self.album}, album_name={self.album_name}, artist = {self.artist}, artist_name ={self.artist_name} "




