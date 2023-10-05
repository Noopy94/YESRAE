import axios from 'axios';

//const BASE_URL = 'https://i9a304.p.ssafy.io/api';

const BASE_URL = 'http://127.0.0.1:8000';

const getRecommendSong = async (song_id: Array<{ id: string }>) => {
  const recommendRequest = {
    songs: song_id,
  };

  try {
    const response = await axios.post(
      `${BASE_URL}/recommend`,
      recommendRequest,
      {
        headers: {
          'Content-Type': 'application/json',
        },
      },
    );
    const data = response.data;
    console.log('API 응답 데이터', data);
    return data.songs;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
  }
};

export { getRecommendSong };
