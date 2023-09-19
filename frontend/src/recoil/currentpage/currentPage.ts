import { atom } from 'recoil';

// 초기 상태를 정의합니다.
const initialPageState = {
  pageName: 'PlayList', // 현재 페이지의 이름
};

export const currentPageState = atom({
  key: 'currentPageState', // 고유한 키 값을 제공합니다.
  default: initialPageState, // 초기 상태를 설정합니다.
});
