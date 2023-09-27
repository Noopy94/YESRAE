import axios from 'axios';

const BASE_URL = 'http://localhost:8080/';

export const login = async (email: string, password: string) => {
  const response = await axios.post(BASE_URL + 'user/login', {
    email: email,
    password: password,
  });
  if (response.data.success) {
    return response.data.content;
  } else {
    alert(response.data.error.message);
    return null;
  }
};

export const oauthLogin = async (accessToken: string) => {
  const response = await axios.post(BASE_URL + 'user/oauth2/login', null, {
    headers: {
      Authorization: 'Bearer ' + accessToken,
      'Content-Type': 'application/json',
    },
  });
  return response.data.content;
};

export const emailCheck = async (email: string) => {
  const response = await axios.get(BASE_URL + 'user/email', {
    params: {
      email: email,
    },
  });

  return response.data.content;
};

export const nicknameCheck = async (nickname: string) => {
  const response = await axios.get(BASE_URL + 'user/nickname', {
    params: {
      nickname: nickname,
    },
  });

  return response.data.content;
};

export const regist = async (
  email: string,
  nickname: string,
  password: string,
  age: number,
) => {
  const response = await axios.post(BASE_URL + 'user/regist', {
    email: email,
    nickname: nickname,
    password: password,
    age: age,
  });
  return response.data;
};
