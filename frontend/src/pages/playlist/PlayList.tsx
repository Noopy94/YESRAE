import HeaderNav from '../../components/HeaderNav/HeaderNav';
import MusicPlayer from '../../components/playercontroller/MusicPlayer';
import { defaultplayLists } from '../../recoil/defaultdata/data';
import { userState } from '../../recoil/user/user';
import { useRecoilState } from 'recoil';
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { currentPlayListState } from '../../recoil/playlist/currentPlayList';
import MyPlayListCarousel from '../../components/common/MyPlayListCarousel';
import {
  currentPageState,
  isListState,
} from '../../recoil/currentpage/currentPage';
import { Link } from 'react-router-dom';

export default function PlayList() {
  // 노래, 플레이 리스트 데이터 샘플, 나중에 api로 가져올 예정

  const [User, setUser] = useRecoilState(userState);
  const { userId } = useParams();
  const [currentPage, setCurrentPage] = useRecoilState(currentPageState);
  const [isList, setIsList] = useRecoilState(isListState);

  useEffect(() => {
    setCurrentPage({ pageName: 'PlayList' });
  }, []);

  window.scrollTo(0, 0);

  // useEffect(() => {
  //   axios
  //     .get(`/api/playList/${User.Id}`)
  //     .then((response) => {
  //       setPlayListData(response.data);
  //     })
  //     .catch((error) => {
  //       console.error('Error fetching playlist data:', error);
  //     });
  // }, [userId]);

  return (
    <div>
      <div className="flex">
        <div className="w-2/12">
          <HeaderNav />
        </div>
        <main className="w-10/12 pt-12 pl-20">
          <header className="flex">
            <Link to="/playlist">
              <div className="pr-12 text-xl font-semibold text-white hover:text-yesrae-0">
                플레이 리스트
              </div>
            </Link>
            <Link to="/follower">
              <div className="pr-12 text-xl font-semibold text-gray-700 hover:font-semibold hover:text-white">
                팔로우
              </div>
            </Link>
            <Link to="/mypage">
              <div className="pr-12 text-xl font-semibold text-gray-700 hover:font-semibold hover:text-white">
                회원 정보 수정
              </div>
            </Link>
            <Link to="/registplaylist">
              <div className="pr-12 text-xl font-semibold text-gray-700 hover:font-semibold hover:text-white">
                플레이 리스트 등록
              </div>
            </Link>
            <Link to="/myplaylist">
              <div className="text-xl font-semibold text-gray-700 hover:font-semibold hover:text-white">
                내 플레이 리스트
              </div>
            </Link>
          </header>
          {User.nickname !== '' ? (
            <div>
              <div className="mt-12 mb-3 text-2xl font-bold">
                나의 플레이 리스트 😎
              </div>
              <div className="flex py-4">
                <MyPlayListCarousel playLists={defaultplayLists} />
              </div>
              <div className="mt-12 mb-3 text-2xl font-bold">
                좋아요한 플레이 리스트 👍
              </div>
              <div className="flex py-4">
                <MyPlayListCarousel playLists={defaultplayLists} />
              </div>
            </div>
          ) : null}
          <div className="flex mt-12 mb-3 text-2xl font-bold">
            <img
              src="/src/assets/VectorBold.png"
              className="items-center w-6 h-6 my-1 mr-2"
            />
            YESRAE 추천 플레이 리스트{' '}
          </div>
          <div className="flex py-4">
            <MyPlayListCarousel playLists={defaultplayLists} />
          </div>
          <div className="mt-12 mb-3 text-2xl font-bold">
            실시간 베스트 플레이 리스트 🔥
          </div>
          <div className="flex py-4">
            <MyPlayListCarousel playLists={defaultplayLists} />
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
