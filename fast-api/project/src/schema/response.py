
from pydantic import BaseModel


class SongQuizSchema(BaseModel):
    id : str
    name : str
    similarity : float
    rank : str

    class Config:
        from_attributes = True




