export interface Song {
  songId: string;
  songTitle: string;
  songUrl?: string;
  songArtist: string;
  songImgUrl: string;
}

export interface PlayList {
  playListId: string;
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
    songUrl: '',
    songArtist: 'Sion',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e02ed58cc90fece9bdd2ed59527',
  },
  {
    songId: '1jCRPdDQvCA5kQXtrPFWPC',
    songTitle: '돌덩이',
    songUrl: '',
    songArtist: '하현우',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e02a99b40ff1a1b414586c0b0a3',
  },
  {
    songId: '6PdMx183cnLH6iqar2XzJv',
    songTitle: 'With You (사내연애 사절! X Young K)',
    songUrl: '',
    songArtist: 'Young K',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e02135435ecf6953fd2c9a556d6',
  },
  {
    songId: '7CZRguMolNqIobnXxpV735',
    songTitle: 'Coin',
    songArtist: '아이유',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
  },
  {
    songId: '7aGU77FK6dBEFKWVKPeKXe',
    songTitle: 'Marry Me',
    songArtist: '마크툽',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e02ea86ed4afef1984a428d1f17',
  },
  {
    songId: '6',
    songTitle: '노래 6',
    songArtist: '노래 6에 대한 설명',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
  },
  {
    songId: '7',
    songTitle: '노래 7',
    songArtist: '노래 7에 대한 설명',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
  },
  {
    songId: '8',
    songTitle: '노래 8',
    songArtist: '노래 8에 대한 설명',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
  },
];

export const defaultplayLists: PlayList[] = [
  {
    playListId: '1',
    playListUserId: 1,
    playListTitle: '광란의 댄스파티곡',
    playListUserNickName: '춤신 성호',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e02ef2b13eba01eca97c4c3e473',
  },
  {
    playListId: '2',
    playListUserId: 2,
    playListTitle: 'league of legend 캐리곡',
    playListUserNickName: '미드신 리두현',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e02f8fa082806184fcb032d8e0a',
  },
  {
    playListId: '3',
    playListUserId: 3,
    playListTitle: '바위는 영어로 Rock(락)!!',
    playListUserNickName: '락황제 김민식',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e0281c835e514e951482d7190fe',
  },
  {
    playListId: '4',
    playListUserId: 4,
    playListTitle: '발라드신의 추천픽',
    playListUserNickName: '발라드왕 최주호',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e02b7d6ca50bf766ad72226290c',
  },
  {
    playListId: '5',
    playListUserId: 5,
    playListTitle: '발라드신의 추천픽',
    playListUserNickName: '그냥신 박민혁',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e02e787cffec20aa2a396a61647',
  },
  {
    playListId: '6',
    playListUserId: 6,
    playListTitle: '절대자의 추천 플레이 리스트',
    playListUserNickName: '싸피의 여왕',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e022f1a5b69c2bd76474bca8035',
  },
];
