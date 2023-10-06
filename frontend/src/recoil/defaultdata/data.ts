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
    songId: '5BXr7hYZQOeRttkeWYTq5S',
    songTitle: 'ASAP',
    songArtist: 'STAYC',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e02af2fda9fb591d43c355c2ac3',
  },
  {
    songId: '52p5RtEaTXx1u1udrx8Fqr',
    songTitle: '다시 사랑한다 말할까',
    songArtist: '김동률',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e02ca1315dbcedf9331ea92e673',
  },
  {
    songId: '72nntPkEl5JjqEpxq4JCd8',
    songTitle: '매일 매일 기다려 (우리 동네 음악대장)',
    songArtist: '하현우',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e02cbf0bb24716d2ce30a5a8b9f',
  },
];

export const defaultsongs2: Song[] = [
  {
    songId: '210JJAa9nJOgNa0YNrsT5g',
    songTitle: 'Gods',
    songArtist: 'New Jeans',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e028f735a689764b8b75174cfa2',
  },
  {
    songId: '1qosh64U6CR5ki1g1Rf2dZ',
    songTitle: 'Love Lee',
    songArtist: 'AKMU',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e02a75d7535b8657385f8232449',
  },
  {
    songId: '4WUpMUjdoi47LY7gBQBXe3',
    songTitle: '모래 알갱이',
    songUrl: '',
    songArtist: '임영웅',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e02111a6e9b713b646443c5c222',
  },
  {
    songId: '50Q0BUdTTtaMumIELoyrm8',
    songTitle: 'Fast Forward',
    songArtist: '전소미',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e026adf6b9ff59d4b0a568a3896',
  },
  {
    songId: '5grkkSqOpUGHfl2KunfdD9',
    songTitle: 'KIDDING',
    songArtist: '이세계 아이돌',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e028fc56337d0720c411120a529',
  },
];

export const defaultsongs3: Song[] = [
  {
    songId: '2rj6rzz1qLMrZf8yWFwp9r',
    songTitle: '헤어지자 말해요',
    songArtist: '박재정',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e0252e570184e68335314f8cdce',
  },
  {
    songId: '6HgeeiHqVpxxFCB0bjBRT6',
    songTitle: '눈사람',
    songArtist: '정승환',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e02dec4d6dd3add76484226a051',
  },
  {
    songId: '5fOjFkFA0k5MTOo1LmnVTO',
    songTitle: 'Heroes Tonight',
    songUrl: '',
    songArtist: 'Janji, Johnning',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e02fefb9f8c3b9e802da9732453',
  },
  {
    songId: '4ribiWWnI451QMRdOgByIP',
    songTitle: '비와 당신',
    songArtist: '이무진',
    songImgUrl:
      'https://i.scdn.co/image/ab67616d00001e02cd99a47e4804c82e3812e901',
  },
];

export const defaultplayLists1: PlayList[] = [
  {
    playListId: 50001,
    playListUserId: 50001,
    playListTitle: '광란의 댄스파티곡',
    playListUserNickName: '춤신 성호',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e02ef2b13eba01eca97c4c3e473',
  },
  {
    playListId: 50002,
    playListUserId: 50002,
    playListTitle: 'league of legend 캐리곡',
    playListUserNickName: '미드신 리두현',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e02f8fa082806184fcb032d8e0a',
  },
  {
    playListId: 50003,
    playListUserId: 50003,
    playListTitle: '바위는 영어로 Rock(락)!!',
    playListUserNickName: '락황제 김민식',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e0281c835e514e951482d7190fe',
  },
  {
    playListId: 50004,
    playListUserId: 50004,
    playListTitle: '발라드신의 추천픽',
    playListUserNickName: '발라드왕 최주호',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e02b7d6ca50bf766ad72226290c',
  },
  {
    playListId: 50005,
    playListUserId: 50005,
    playListTitle: '슬기로운 의사생활 노래 모음',
    playListUserNickName: '발라드왕 최주호',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e02cd99a47e4804c82e3812e901',
  },
];

export const defaultplayLists2: PlayList[] = [
  {
    playListId: 50006,
    playListUserId: 50006,
    playListTitle: '김동률 모음집',
    playListUserNickName: '동률신',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e02b7cd797580efda45872562df',
  },
  {
    playListId: 50007,
    playListUserId: 50007,
    playListTitle: '걸그룹 추천 모음',
    playListUserNickName: '운동의신 인범',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e02d70036292d54f29e8b68ec01',
  },
  {
    playListId: 50008,
    playListUserId: 50008,
    playListTitle: '동물들이 좋아하는 잔잔한곡',
    playListUserNickName: '동물 애호가',
    playListDescription: '동물들이 좋아하는 잔잔한곡인척 하는 랩모음',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e023cf1c1dbcfa3f1ab7282719b',
  },
  {
    playListId: 50009,
    playListUserId: 50009,
    playListTitle: '흑형들 랩 노래',
    playListUserNickName: '랩신',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e02d5b89c1c1b51c2f8b0573f93',
  },
  {
    playListId: 50010,
    playListUserId: 50010,
    playListTitle: '유행하는 노래1',
    playListUserNickName: 'YESRAE짱',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e022f1a5b69c2bd76474bca8035',
  },
];

export const defaultplayLists3: PlayList[] = [
  {
    playListId: 50011,
    playListUserId: 50011,
    playListTitle: '아이유 노래 모음1',
    playListUserNickName: '그냥신 박민혁',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e02c63be04ae902b1da7a54d247',
  },
  {
    playListId: 50012,
    playListUserId: 50012,
    playListTitle: '아이유 노래 모음2',
    playListUserNickName: '싸피의 여왕',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e02b658276cd9884ef6fae69033',
  },
  {
    playListId: 50013,
    playListUserId: 50013,
    playListTitle: '낭만곡 모음',
    playListUserNickName: '싸피의 여왕',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e0242e57bd83294ffd1bf97565a',
  },
  {
    playListId: 50014,
    playListUserId: 50014,
    playListTitle: '절대자의 추천 플레이 리스트',
    playListUserNickName: '싸피의 여왕',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e022f1a5b69c2bd76474bca8035',
  },
  {
    playListId: 50015,
    playListUserId: 50015,
    playListTitle: '절대자의 추천 플레이 리스트',
    playListUserNickName: '싸피의 여왕',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e022f1a5b69c2bd76474bca8035',
  },
  {
    playListId: 50016,
    playListUserId: 50016,
    playListTitle: '절대자의 추천 플레이 리스트',
    playListUserNickName: '싸피의 여왕',
    playListDescription: '플레이 리스트 설명',
    playListImageUrl:
      'https://i.scdn.co/image/ab67616d00001e022f1a5b69c2bd76474bca8035',
  },
];
