import axios from 'axios';

const BASE_URL = 'http://localhost:8080/';

export const songDetail = async (userId: number, songId: string) => {
  const response = await axios.post(BASE_URL + 'song', {
    userId: userId,
    songId: songId,
  });
  if (response.data.success) {
    return response.data.content;
  } else {
    alert(response.data.error.message);
    return null;
  }
};

export const registSongLike = async (userId: number, songId: string, token: string) => {
  const response = await axios.post(BASE_URL + 'song/songlike', {
    userId: userId,
    songId: songId,
  }, {
    headers: {
      Authorization: 'Bearer ' + token,
    },
  });
  if (!response.data.success) {
    alert(response.data.error.message);
  }
};