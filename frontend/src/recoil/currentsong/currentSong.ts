import { atom } from 'recoil';
import { Song } from '../defaultdata/data';

// 플레이 리스트 초기상태
const initialCurrentSongState = {
  songId: '3cGp1jXxLReLKz7QgVbWZR',
  songTitle: 'Hello',
  songUrl:
    'https://p.scdn.co/mp3-preview/09b71fad1e13164f43c9248ecc8d9ab85abd9ae8?cid=b76e1a72191a49e1bd4cc3b5aaa2511b',
  songArtist: '조이',
  songImgUrl:
    'https://i.scdn.co/image/ab67616d00001e0266ff63bc084fb412aa2dddd3',
};

const initialCurrentSongListState: Song[] = [
  {
    songId: '484dgUyawknLkNyQfXoDkh',
    songTitle: '기억을 걷는 시간 (Feat. 10CM)',
    songUrl:
      'https://p.scdn.co/mp3-preview/d628dcf4d51ed6b8d5ff77b881877f60cfbc8f13?cid=b76e1a72191a49e1bd4cc3b5aaa2511b',
    songArtist: 'Sion',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e02ed58cc90fece9bdd2ed59527',
  },
  {
    songId: '1jCRPdDQvCA5kQXtrPFWPC',
    songTitle: '돌덩이',
    songUrl:
      'https://p.scdn.co/mp3-preview/a8583fb66e438c93e1ed0d4aa57fa1f245d81b6b?cid=b76e1a72191a49e1bd4cc3b5aaa2511b',
    songArtist: '하현우',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e02a99b40ff1a1b414586c0b0a3',
  },
  {
    songId: '6PdMx183cnLH6iqar2XzJv',
    songTitle: 'With You (사내연애 사절! X Young K)',
    songUrl:
      'https://p.scdn.co/mp3-preview/154d06b451936fa2b0b0cd6c1e9757f5f815ad7d?cid=b76e1a72191a49e1bd4cc3b5aaa2511b',
    songArtist: 'Young K',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e02135435ecf6953fd2c9a556d6',
  },
  {
    songId: '7CZRguMolNqIobnXxpV735',
    songTitle: 'Coin',
    songUrl:
      'https://p.scdn.co/mp3-preview/76984dd3cc833134c0284618d7c33a5736681dcb?cid=b76e1a72191a49e1bd4cc3b5aaa2511b',
    songArtist: '아이유',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
  },
  {
    songId: '7CZRguMolNqIobnXxpV735',
    songTitle: 'Coin',
    songUrl:
      'https://p.scdn.co/mp3-preview/76984dd3cc833134c0284618d7c33a5736681dcb?cid=b76e1a72191a49e1bd4cc3b5aaa2511b',
    songArtist: '아이유',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
  },
  {
    songId: '7CZRguMolNqIobnXxpV735',
    songTitle: 'Coin',
    songUrl:
      'https://p.scdn.co/mp3-preview/76984dd3cc833134c0284618d7c33a5736681dcb?cid=b76e1a72191a49e1bd4cc3b5aaa2511b',
    songArtist: '아이유',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
  },
  {
    songId: '7CZRguMolNqIobnXxpV735',
    songTitle: 'Coin',
    songUrl:
      'https://p.scdn.co/mp3-preview/76984dd3cc833134c0284618d7c33a5736681dcb?cid=b76e1a72191a49e1bd4cc3b5aaa2511b',
    songArtist: '아이유',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
  },
  {
    songId: '7CZRguMolNqIobnXxpV735',
    songTitle: 'Coin',
    songUrl:
      'https://p.scdn.co/mp3-preview/76984dd3cc833134c0284618d7c33a5736681dcb?cid=b76e1a72191a49e1bd4cc3b5aaa2511b',
    songArtist: '아이유',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
  },
  {
    songId: '7CZRguMolNqIobnXxpV735',
    songTitle: 'Coin',
    songUrl:
      'https://p.scdn.co/mp3-preview/76984dd3cc833134c0284618d7c33a5736681dcb?cid=b76e1a72191a49e1bd4cc3b5aaa2511b',
    songArtist: '아이유',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
  },
  {
    songId: '7CZRguMolNqIobnXxpV735',
    songTitle: 'Coin',
    songUrl:
      'https://p.scdn.co/mp3-preview/76984dd3cc833134c0284618d7c33a5736681dcb?cid=b76e1a72191a49e1bd4cc3b5aaa2511b',
    songArtist: '아이유',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
  },
  {
    songId: '7CZRguMolNqIobnXxpV735',
    songTitle: 'Coin',
    songUrl:
      'https://p.scdn.co/mp3-preview/76984dd3cc833134c0284618d7c33a5736681dcb?cid=b76e1a72191a49e1bd4cc3b5aaa2511b',
    songArtist: '아이유',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
  },
  {
    songId: '7CZRguMolNqIobnXxpV735',
    songTitle: 'Coin',
    songUrl:
      'https://p.scdn.co/mp3-preview/76984dd3cc833134c0284618d7c33a5736681dcb?cid=b76e1a72191a49e1bd4cc3b5aaa2511b',
    songArtist: '아이유',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
  },
  {
    songId: '7CZRguMolNqIobnXxpV735',
    songTitle: 'Coin',
    songUrl:
      'https://p.scdn.co/mp3-preview/76984dd3cc833134c0284618d7c33a5736681dcb?cid=b76e1a72191a49e1bd4cc3b5aaa2511b',
    songArtist: '아이유',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
  },
  {
    songId: '7CZRguMolNqIobnXxpV735',
    songTitle: 'Coin',
    songUrl:
      'https://p.scdn.co/mp3-preview/76984dd3cc833134c0284618d7c33a5736681dcb?cid=b76e1a72191a49e1bd4cc3b5aaa2511b',
    songArtist: '아이유',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
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
