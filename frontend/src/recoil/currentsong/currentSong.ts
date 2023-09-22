import { atom } from 'recoil';

// 플레이 리스트 초기상태
const initialCurrentSongState = {
  songId: 0,
  songTitle: '',
  songUrl: '',
  songArtist: '',
  songImgUrl: '',
};

// 현재 페이지 전역변수, 현재 페이지에 따라서 HeaderNav의 상태가 달라짐
export const currentSongState = atom({
  key: 'currentPlayListState',
  default: initialCurrentSongState,
});
