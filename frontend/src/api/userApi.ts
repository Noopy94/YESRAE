import axios from 'axios';

const BASE_URL = 'http://localhost:8080/';

export const login = async (accessToken: string) => {
  const response = await axios.post(BASE_URL + 'user/login', null, {
    headers: {
      Authorization: 'Bearer ' + accessToken,
      'Content-Type': 'application/json',
    },
  });
  console.log(response.data.content);
  return response.data.content;
};
