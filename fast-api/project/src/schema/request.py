from pydantic import BaseModel

class SearchSongQuizRequest(BaseModel):
    name : str


    class Config:
        from_attributes = True
    
    def get(self, attribute_name):
        return getattr(self, attribute_name)