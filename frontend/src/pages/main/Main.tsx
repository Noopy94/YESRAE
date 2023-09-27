import HeaderNav from '../../components/HeaderNav/HeaderNav';
import MusicPlayer from '../../components/playercontroller/MusicPlayer';
import SongCarousel from '../../components/common/SongCarousel';
import PlayListCarousel from '../../components/common/PlayListCarousel';
import { defaultsongs, defaultplayLists } from '../../recoil/defaultdata/data';
import { userState } from '../../recoil/user/user';
import { useRecoilState } from 'recoil';
import React, { useState } from 'react';

export default function Main() {
  // 노래, 플레이 리스트 데이터 샘플, 나중에 api로 가져올 예정
  const [User, setUser] = useRecoilState(userState);

  React.useEffect(() => {
    //api 이용해서 userState 바뀔 때만 추천 알고리즘 변경
    // 로그아웃하고 다시 오면 추천 변경 되게
  }, [userState]);

  function UserRecommend() {
    if (User.nickname) {
      return (
        <div>
          <div className="mt-10 mb-3 text-2xl font-bold">
            {User.nickname}님 맞춤 추천 노래 😍
          </div>
          <div className="flex">
            <SongCarousel songs={defaultsongs} />
          </div>
          <div className="mt-10 mb-3 text-2xl font-bold">
            {User.nickname}님 맞춤 추천 플레이 리스트 🎤
          </div>
          <div className="flex">
            <PlayListCarousel playLists={defaultplayLists} />
          </div>
        </div>
      );
    }
    return null;
  }

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
          <UserRecommend />
          <div className="mt-10 mb-3 text-2xl font-bold">
            YESRAE 추천 플레이 노래 😍
          </div>
          <div className="flex">
            <SongCarousel songs={defaultsongs} />
          </div>
          <div className="mt-10 mb-3 text-2xl font-bold">
            실시간 베스트 플레이 리스트 🔥
          </div>
          <div className="flex">
            <PlayListCarousel playLists={defaultplayLists} />
          </div>
          <div className="mt-10 mb-3 text-2xl font-bold">
            반차 쓰고 싶은 날 💬
          </div>
          <div className="flex">
            <SongCarousel songs={defaultsongs} />
          </div>
          <div className="mt-10 mb-3 text-2xl font-bold">
            가을 감성을 담은 플레이 리스트 🍂
          </div>
          <div className="flex">
            <PlayListCarousel playLists={defaultplayLists} />
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
