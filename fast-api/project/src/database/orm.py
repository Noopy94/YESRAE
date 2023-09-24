from sqlalchemy import Boolean, Column, Integer, String, DateTime, Float, Double, ForeignKey

from sqlalchemy.orm import declarative_base, relationship

Base = declarative_base()


class Song(Base):
    __tablename__ = "song"

    id = Column(String, primary_key=True, nullable=False)
    name = Column(String, nullable=False)
    album_id = Column(String, nullable=False)
    album_name = Column(String, nullable=False)
    artist_id = Column(String, nullable=False)
    artist_name = Column(String, nullable=False)
    genre = Column(String)
    img_url = Column(String, nullable=False)
    preview_url = Column(String, nullable=False)
    release_year = Column(DateTime(), nullable=False)
    duration = Column(Integer, nullable=False, default=0)
    popularity = Column(Integer, nullable=False, default=0)
    acousticness = Column(Float, nullable=False, default=0)
    danceability = Column(Float, nullable=False, default=0)
    energy = Column(Float, nullable=False, default=0)
    instrumentalness = Column(Float, nullable=False, default=0)
    tune = Column(Integer, nullable=False, default=0)
    liveness = Column(Float, nullable=False, default=0)
    loudness = Column(Float, nullable=False, default=0)
    mode = Column(Integer, nullable=False, default=0)
    speechiness = Column(Float, nullable=False, default=0)
    tempo = Column(Float, nullable=False, default=0)
    time_signature = Column(Integer, nullable=False, default=0)
    valence = Column(Float, nullable=False, default=0)
    today_song = Column(Boolean, nullable=False, default=False)

    

    # quiz = relationship('SongQuiz', back_populates="song")
    # rank = relationship('SongQuizRank', back_populates="song", )

    def __repr__(self):
        return f"Song(id={self.id}, name ={self.name}, album_id={self.album_id}, album_name={self.album_name}, artist_name ={self.artist_name})"
    
    def set_today_song(self) -> "Song":
        self.today_song = True
        return self
    
    def unset_today_song(self) -> "Song":
        self.today_song = False
        return self


class SongQuiz(Base):
    __tablename__ = "song_quiz"

    id = Column(String, primary_key=True, nullable=False)
    similarity = Column(Double, nullable=False, default=0)

    # song_id = Column(String, ForeignKey('song.id'), nullable=False)

    # song = relationship('Song', back_populates='quiz')

    @classmethod
    def create(cls, id: str, similarity: float) -> "SongQuiz":
        return cls(
            id=id,
            similarity=similarity,
        )

    def __repr__(self):
        return f"SongQuiz(id={self.id}, similarity ={self.similarity}"


class SongQuizRank(Base):
    __tablename__ = "song_quiz_rank"

    id = Column(String, primary_key=True, nullable=False)
    rank = Column(Integer, nullable=False, default=0)

    # song_id = Column(String, ForeignKey('song.id'), nullable=False)
    # song = relationship('Song', back_populates='rank')

    @classmethod
    def create(cls, id: str, rank: int) -> "SongQuizRank":
        return cls(
            id=id,
            rank=rank,
        )

    def __repr__(self):
        return f"SongQuizRank(id={self.id}, rank ={self.rank}"
