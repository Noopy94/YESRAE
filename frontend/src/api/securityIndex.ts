import Axios from 'axios';

//const BASE_URL = 'https://i9a304.p.ssafy.io/api';

const BASE_URL = 'http://localhost:8080/';

// axios 기본 세팅
const axios = Axios.create({
  baseURL: `${BASE_URL}`,
  validateStatus: (status) => status < 500,
});

axios.interceptors.request.use(
  (config) => {
    const userString = localStorage.getItem('user');
    if (userString) {
      const user = JSON.parse(userString);
      const jwtToken: string = user.accessToken;
      // JwtToken 확인
      console.log('Jwt 토근 확인' + jwtToken);
      config.headers['Authorization'] = jwtToken ? `Bearer ${jwtToken}` : '';
    }
    config.headers['Content-Type'] = 'multipart/form-data';
    return config;
  },
  (err) => err,
);

export default axios;
