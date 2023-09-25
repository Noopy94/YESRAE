import HeaderNav from '../../components/HeaderNav/HeaderNav';
import MusicPlayer from '../../components/playercontroller/MusicPlayer';
import SongCarousel from '../../components/common/SongCarousel';
import PlayListCarousel from '../../components/common/PlayListCarousel';

export default function Main() {
  // 노래, 플레이 리스트 데이터 샘플, 나중에 api로 가져올 예정
  const songs = [
    {
      id: 1,
      title: '노래 1',
      artistName: '노래 1에 대한 설명',
      imageUrl:
        'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
    },
    {
      id: 2,
      title: '노래 2',
      artistName: '노래 2에 대한 설명',
      imageUrl:
        'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
    },
    {
      id: 3,
      title: '노래 3',
      artistName: '노래 3에 대한 설명',
      imageUrl:
        'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
    },
    {
      id: 4,
      title: '노래 4',
      artistName: '노래 4에 대한 설명',
      imageUrl:
        'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
    },
    {
      id: 5,
      title: '노래 5',
      artistName: '노래 5에 대한 설명',
      imageUrl:
        'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
    },
    {
      id: 6,
      title: '노래 6',
      artistName: '노래 6에 대한 설명',
      imageUrl:
        'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
    },
    {
      id: 7,
      title: '노래 7',
      artistName: '노래 7에 대한 설명',
      imageUrl:
        'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
    },
    {
      id: 8,
      title: '노래 8',
      artistName: '노래 8에 대한 설명',
      imageUrl:
        'https://i.scdn.co/image/ab67616d00001e024ed058b71650a6ca2c04adff',
    },
  ];

  const playlist = [
    {
      id: 1,
      title: '광란의 댄스파티곡',
      userNickName: '춤신 성호',
      imageUrl:
        'https://i.scdn.co/image/ab67616d00001e02ef2b13eba01eca97c4c3e473',
    },
    {
      id: 2,
      title: 'league of legend 캐리곡',
      userNickName: '미드신 리두현',
      imageUrl:
        'https://i.scdn.co/image/ab67616d00001e02f8fa082806184fcb032d8e0a',
    },
    {
      id: 3,
      title: '바위는 영어로 Rock(락)!!',
      userNickName: '락황제 김민식',
      imageUrl:
        'https://i.scdn.co/image/ab67616d00001e0281c835e514e951482d7190fe',
    },
    {
      id: 4,
      title: '발라드신의 추천픽',
      userNickName: '발라드왕 최주호',
      imageUrl:
        'https://i.scdn.co/image/ab67616d00001e02b7d6ca50bf766ad72226290c',
    },
    {
      id: 5,
      title: '진짜 노래란 이런 것이다',
      userNickName: '그냥신 박민혁',
      imageUrl:
        'https://i.scdn.co/image/ab67616d00001e02e787cffec20aa2a396a61647',
    },
    {
      id: 6,
      title: '절대자의 추천 플레이 리스트',
      userNickName: '싸피의 여왕',
      imageUrl:
        'https://i.scdn.co/image/ab67616d00001e022f1a5b69c2bd76474bca8035',
    },
  ];

  return (
    <div>
      <div className="flex">
        <div className="w-2/12">
          <HeaderNav />
        </div>
        <main className="w-10/12 pt-12 pl-20 ">
          <div className="flex justify-center h-64 border-2 border-gray-800 w-280 ">
            대충 노래꼬맨틀 할래요? 광고
          </div>
          <div className="mt-10 mb-3 text-2xl font-bold">
            YESRAE 추천 플레이 노래 😍
          </div>
          <div className="flex">
            <SongCarousel songs={songs} />
          </div>
          <div className="mt-10 mb-3 text-2xl font-bold">
            실시간 베스트 플레이 리스트 🔥
          </div>
          <div className="flex">
            <PlayListCarousel playLists={playlist} />
          </div>
          <div className="mt-10 mb-3 text-2xl font-bold">
            반차 쓰고 싶은 날 💬
          </div>
          <div className="flex">
            <SongCarousel songs={songs} />
          </div>
          <div className="mt-10 mb-3 text-2xl font-bold">
            가을 감성을 담은 플레이 리스트 🍂
          </div>
          <div className="flex">
            <PlayListCarousel playLists={playlist} />
          </div>
          {/* 밑에 부분은 공간 남는거 채우는 용도니 그대로 둘것*/}
          <div>
            <div className="fixed relative bottom-0 left-0 h-36" />
          </div>
        </main>
      </div>
      <footer className="flex">
        <MusicPlayer />
      </footer>
    </div>
  );
}
