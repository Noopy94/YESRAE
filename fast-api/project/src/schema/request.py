from pydantic import BaseModel

class SearchSongQuizRequest(BaseModel):
    name : str
