import { atom } from 'recoil';
import { PlayList } from '../defaultdata/data';

// 플레이 리스트 초기상태
const initialCurrentPlayListState: PlayList = {
  playListId: 0,
  playListUserId: 0,
  playListUserNickName: '발라드왕 최주호',
  playListTitle: '발라드신의 추천픽',
  playListDescription: '사실 발라드는 없었다...!!!! ',
  playListImageUrl:
    'https://i.scdn.co/image/ab67616d00001e02b7d6ca50bf766ad72226290c',
};

export const currentPlayListState = atom({
  key: 'currentPlayListState',
  default: initialCurrentPlayListState,
});
