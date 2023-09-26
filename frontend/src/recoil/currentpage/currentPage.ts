import { atom } from 'recoil';

// 초기 상태를 정의합니다.
const initialPageState = {
  pageName: '',
};

// 현재 페이지 전역변수, 현재 페이지에 따라서 HeaderNav의 상태가 달라짐
export const currentPageState = atom({
  key: 'currentPageState',
  default: initialPageState,
});
