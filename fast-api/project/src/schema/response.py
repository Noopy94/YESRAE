from pydantic import BaseModel


class SongResult(BaseModel):
    is_answer : bool
    similarity : float
    rank : int

    class Config:
        orm_mode = True




