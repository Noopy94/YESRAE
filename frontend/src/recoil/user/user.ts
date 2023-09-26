import { atom } from 'recoil';
import localStorageEffect from '../localStorageEffect';

// 사용자 상태를 관리하는 Atom 정의
export const userState = atom({
  key: 'userState', // 고유한 키
  default: {
    accessToken: '',
    refreshToken: '',
  },
  effects: [localStorageEffect('user')],
});
