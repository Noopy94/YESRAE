import { atom } from 'recoil';
import { PlayList } from '../defaultdata/data';

// 플레이 리스트 초기상태
const initialCurrentPlayListState: PlayList = {
  playListId: '1',
  playListUserId: 1,
  playListUserNickName: '유저 닉네임',
  playListTitle: '플레이리스트 제목',
  playListDescription:
    '플레이 리스트 상세 설명 에ㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔㅔ ',
  playListImageUrl: '',
};

export const currentPlayListState = atom({
  key: 'currentPlayListState',
  default: initialCurrentPlayListState,
});
