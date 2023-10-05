import axios from 'axios';

//const BASE_URL = 'https://i9a304.p.ssafy.io/api';

const BASE_URL = 'http://127.0.0.1:8000';

// 추측할 노래 제목 keydown 입력 시에 입력한 제목과 관련된 노래 제목 얻기
const searchSong = async (song_name: string) => {
  const searchRequest = {
    name: song_name,
  };

  try {
    const response = await axios.post(
      `${BASE_URL}/quiz/search`,
      searchRequest,
      {
        headers: {
          'Content-Type': 'application/json',
        },
      },
    );
    const data = response.data;
    console.log('API 응답 데이터:', data);
    return data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
  }
};

// 추측하기 클릭시 유사도 결과 반환 -> 1000위 안에 없으면 rank : None 반환
const getSongResult = async (song_name: string) => {
  const searchRequest = {
    name: song_name,
  };

  try {
    const response = await axios.post(`${BASE_URL}/quiz`, searchRequest, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    const data = response.data;
    console.log('API 응답 데이터:', data);
    return data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
  }
};

// 1000 위 순위 보기 결과 반환
const getRank = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/quiz/result`);

    return response.data;
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
  }
};

export { getSongResult, searchSong, getRank };
