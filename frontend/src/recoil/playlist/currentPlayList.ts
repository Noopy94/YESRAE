import { atom } from 'recoil';

// 플레이 리스트 초기상태
const initialCurrentPlayListState = {
  playListId: 0,
  playListUserId: 0,
  playListTitle: '',
  playListDescription: '',
  playListSongs: [],
};

export const currentPlayListState = atom({
  key: 'currentPlayListState',
  default: initialCurrentPlayListState,
});
