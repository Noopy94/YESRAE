export interface Song {
  songId: string;
  songTitle: string;
  songUrl?: string;
  songArtist: string;
  songImgUrl: string;
}

export interface PlayList {
  playListId: number;
  playListUserId: number;
  playListUserNickName: string;
  playListTitle: string;
  playListDescription: string | null;
  playListImageUrl: string;
}

export const defaultsongs: Song[] = [
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

export const defaultplayLists: PlayList[] = [
  {
    playListId: 25000,
    playListUserId: 25000,
    playListTitle: '광란의 댄스파티곡',
    playListUserNickName: '춤신 성호',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e02ef2b13eba01eca97c4c3e473',
  },
  {
    playListId: 25001,
    playListUserId: 25001,
    playListTitle: 'league of legend 캐리곡',
    playListUserNickName: '미드신 리두현',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e02f8fa082806184fcb032d8e0a',
  },
  {
    playListId: 25003,
    playListUserId: 25003,
    playListTitle: '바위는 영어로 Rock(락)!!',
    playListUserNickName: '락황제 김민식',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e0281c835e514e951482d7190fe',
  },
  {
    playListId: 25004,
    playListUserId: 25004,
    playListTitle: '발라드신의 추천픽',
    playListUserNickName: '발라드왕 최주호',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e02b7d6ca50bf766ad72226290c',
  },
  {
    playListId: 25005,
    playListUserId: 25005,
    playListTitle: '발라드신의 추천픽',
    playListUserNickName: '그냥신 박민혁',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e02e787cffec20aa2a396a61647',
  },
  {
    playListId: 25006,
    playListUserId: 25006,
    playListTitle: '절대자의 추천 플레이 리스트',
    playListUserNickName: '싸피의 여왕',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e022f1a5b69c2bd76474bca8035',
  },
  {
    playListId: 25007,
    playListUserId: 25007,
    playListTitle: '코드를 파.괘.한.다',
    playListUserNickName: '컨설턴트님 짱',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e02135435ecf6953fd2c9a556d6',
  },
  {
    playListId: 25008,
    playListUserId: 25008,
    playListTitle: '신난다 아싸 재미난다',
    playListUserNickName: '싸피짱',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e02ea86ed4afef1984a428d1f17',
  },
];
