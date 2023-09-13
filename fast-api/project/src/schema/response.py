from pydantic import BaseModel


class SongQuizSchema(BaseModel):
    id : str
    name : str
    similarity : float
    rank : int

    class Config:
        from_attributes = True




