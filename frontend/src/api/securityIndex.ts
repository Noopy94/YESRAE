import Axios from 'axios';
import { useRecoilValue, useRecoilState } from 'recoil';
import { userState } from '../recoil/user/user';

//const BASE_URL = 'https://i9a304.p.ssafy.io/api';

const BASE_URL = 'http://localhost:8080/';

// axios 기본 세팅
const axios = Axios.create({
  baseURL: `${BASE_URL}`,
  validateStatus: (status) => status < 500,
});

axios.interceptors.request.use(
  (config) => {
    const user = useRecoilValue(userState);
    const jwtToken = sessionStorage.getItem(user.accessToken);
    // JwtToken 확인
    console.log('Jwt 토근 확인' + jwtToken);
    config.headers['Authorization'] = jwtToken ? `Bearer ${jwtToken}` : '';
    return config;
  },
  (err) => err,
);

export default axios;
