import HeaderNav from '../../components/HeaderNav/HeaderNav';
import MusicPlayer from '../../components/playercontroller/MusicPlayer';
import {
  defaultplayLists1,
  defaultplayLists2,
  defaultplayLists3,
} from '../../recoil/defaultdata/data';
import { userState } from '../../recoil/user/user';
import { useRecoilState, useRecoilValue } from 'recoil';
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import MyPlayListCarousel from '../../components/common/MyPlayListCarousel';
import {
  currentPageState,
  isListState,
} from '../../recoil/currentpage/currentPage';
import { Link } from 'react-router-dom';

export default function SearchPage() {
  // 노래, 플레이 리스트 데이터 샘플, 나중에 api로 가져올 예정

  const User = useRecoilValue(userState);
  const { keyword } = useParams();
  const [currentPage, setCurrentPage] = useRecoilState(currentPageState);
  const [isList, setIsList] = useRecoilState(isListState);

  useEffect(() => {
    setCurrentPage({ pageName: 'PlayList' });
  }, []);

  return (
    <div>
      <div className="flex">
        <div className="w-2/12">
          <HeaderNav />
        </div>
        <main className="w-10/12 pt-12 pl-20">
          <div className="flex mt-12 mb-3 text-2xl font-bold">
            <img
              src="/src/assets/VectorBold.png"
              className="items-center w-6 h-6 my-1 mr-2"
            />
            상위 결과 플레이 리스트
          </div>
          <div className="flex py-4">
            <MyPlayListCarousel playLists={defaultplayLists2} />
          </div>
          <div className="mt-12 mb-3 text-2xl font-bold">관련 음악 🔥</div>
          <div className="flex py-4">
            <MyPlayListCarousel playLists={defaultplayLists3} />
          </div>

          <div>
            <div className="fixed relative bottom-0 left-0 h-36" />
          </div>
        </main>
      </div>
      <footer>
        <MusicPlayer />
      </footer>
    </div>
  );
}
