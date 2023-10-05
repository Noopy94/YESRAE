import { atom } from 'recoil';
import { Song } from '../defaultdata/data';

// 플레이 리스트 초기상태
const initialCurrentSongState = {
  songId: '484dgUyawknLkNyQfXoDkh',
  songTitle: '기억을 걷는 시간 (Feat. 10CM)',
  songUrl: '/src/assets/testmusic1.mp3',
  songArtist: 'Sion',
  songImgUrl:
    'https://i.scdn.co/image/ab67616d00001e02ed58cc90fece9bdd2ed59527',
};

const initialCurrentSongListState: Song[] = [
  {
    songId: '484dgUyawknLkNyQfXoDkh',
    songTitle: '기억을 걷는 시간 (Feat. 10CM)',
    songUrl: '/src/assets/testmusic1.mp3',
    songArtist: 'Sion',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e02ed58cc90fece9bdd2ed59527',
  },
  {
    songId: '1jCRPdDQvCA5kQXtrPFWPC',
    songTitle: '돌덩이',
    songUrl: '/src/assets/testmusic2.mp3',
    songArtist: '하현우',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e02a99b40ff1a1b414586c0b0a3',
  },
  {
    songId: '3cGp1jXxLReLKz7QgVbWZR',
    songTitle: 'Hello',
    songUrl: '/src/assets/testmusic3.mp3',
    songArtist: '조이',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e0266ff63bc084fb412aa2dddd3',
  },
  {
    songId: '7CZRguMolNqIobnXxpV735',
    songTitle: 'Coin',
    songUrl: '/src/assets/testmusic4.mp3',
    songArtist: '아이유',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
  },
  {
    songId: '6RBziRcDeiho3iTPdtEeg9',
    songTitle: '사건의 지평선',
    songUrl: '/src/assets/testmusic5.mp3',
    songArtist: '윤하',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e022918f236448bf544586e388a',
  },
  {
    songId: '5sdQOyqq2IDhvmx2lHOpwd',
    songTitle: 'Super Shy',
    songUrl: '/src/assets/testmusic6.mp3',
    songArtist: 'NewJeans',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e023d98a0ae7c78a3a9babaf8af',
  },
  {
    songId: '5aHwYjiSGgJAxy10mBMlDT',
    songTitle: 'Dynamite',
    songUrl: '/src/assets/testmusic7.mp3',
    songArtist: '방탄 소년단',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e02184d20129ccf5aafcc776d11',
  },
  {
    songId: '68r87x3VZdAMhv8nBVuynz',
    songTitle: '퀸카',
    songUrl: '/src/assets/testmusic8.mp3',
    songArtist: '아이들',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e0257de3da10da259d0a19a81b4',
  },
];

// 현재 페이지 전역변수, 현재 페이지에 따라서 HeaderNav의 상태가 달라짐
export const currentSongState = atom<Song>({
  key: 'currentSongState',
  default: initialCurrentSongState,
});

export const currentSongListState = atom<Song[]>({
  key: 'currentSongListState',
  default: initialCurrentSongListState,
});
