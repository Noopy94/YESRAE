import React, { useState, useEffect } from 'react';
import { useRecoilValue, useRecoilState } from 'recoil';
import ButtonComponent from '../common/ButtonComponent';
import { Link, useNavigate } from 'react-router-dom';
import Logo from '../../assets/Logo.svg';

import {
  LightBulbIcon,
  ClipboardIcon,
  SparklesIcon,
} from '@heroicons/react/24/outline';
import {
  Cog8ToothIcon,
  HeartIcon,
  MusicalNoteIcon,
  UserIcon,
} from '@heroicons/react/24/solid';
import InputComponent from '../common/InputComponent';
import { userState } from '../../recoil/user/user';
import { currentPageState } from '../../recoil/currentpage/currentPage';

export default function HeaderNav() {
  const user = useRecoilValue(userState); // 유저 로그인 상태 전역 변수로 확인
  const currentPage = useRecoilValue(currentPageState); // currentPage.pageName 상태에 따라서 headerNav 색이 변함
  const [search, setSearch] = useState('');
  const navigate = useNavigate();
  useEffect(() => {
    handleSearch();
  }, [search]);

  const onChangeSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearch(event.target.value);
  };

  const handleSearch = () => {
    //search 값에 따라 연관된 검색어 밑에 보여주기
    console.log('검색어:', search);
  };

  const onHandleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      handleSearch();
    }
  };

  const navigateToBoard = () => {
    navigate('/board');
  };

  const navigateToPlayList = () => {
    navigate('/playlist');
  };

  const navigateToCup = () => {
    navigate('/Cup');
  };

  const navigateToDailyQuiz = () => {
    navigate('/dailyquiz');
  };

  const navigateToMyPage = () => {
    navigate('/mypage');
  };

  const navigateToAiVoice = () => {
    navigate('/aivoice');
  };

  const navigateToServiceInfo = () => {
    navigate('/serviceInfo');
  };

  return (
    <div className="fixed h-full w-64 bg-black">
      <img src={Logo} alt="YESRAE LOGO" className="p-4" />
      <div className="border-t-2 border-gray-900 p-3">
        {user.name ? (
          <div>
            <img
              src={user.imgUrl}
              alt={user.name}
              className="w-12 h-12 rounded-full"
            />
            <div>{user.name}님 안녕하세요.</div>
          </div>
        ) : (
          <div>
            <div className="pb-2 pl-5"> 로그인하고 추천 노래 확인하기 </div>
            <Link to="/user/login">
              <ButtonComponent type="ismiddle">로그인</ButtonComponent>
            </Link>
          </div>
        )}
      </div>
      <div className="border-t-2 border-gray-900">
        <InputComponent
          placeholder="YESRAE 검색"
          onChange={onChangeSearch}
          onKeyDown={onHandleKeyDown}
          type="text"
        />
      </div>
      <ul className="text-white px-8 border-b-2 py-3 border-gray-900">
        {currentPage.pageName === 'Home' ? (
          <li
            className="text-xl pt-1 flex items-center hover:text-yesrae-200 text-yesrae-0 "
            onClick={navigateToBoard}
          >
            <ClipboardIcon className="w-8 h-8" />
            <div className="pl-2">게시판</div>
          </li>
        ) : (
          <li
            className="text-xl pt-1 flex items-center hover:text-white text-gray-400"
            onClick={navigateToBoard}
          >
            <ClipboardIcon className="w-8 h-8" />
            <div className="pl-2">게시판</div>
          </li>
        )}
        {currentPage.pageName === 'PlayList' ? (
          <li
            className="text-xl pt-3 flex items-center hover:text-yesrae-200 text-yesrae-0"
            onClick={navigateToPlayList}
          >
            <MusicalNoteIcon className="w-8 h-8" />
            <div className="pl-2"> 플레이리스트 </div>
          </li>
        ) : (
          <li
            className="text-xl pt-3 flex items-center hover:text-white text-gray-400"
            onClick={navigateToPlayList}
          >
            <MusicalNoteIcon className="w-8 h-8" />
            <div className="pl-2"> 플레이리스트 </div>
          </li>
        )}
        {currentPage.pageName === 'Cup' ? (
          <li
            className="text-xl pt-3 flex items-center hover:text-yesrae-200 text-yesrae-0"
            onClick={navigateToCup}
          >
            <HeartIcon className="w-8 h-8" />
            <div className="pl-2">이상형월드컵</div>
          </li>
        ) : (
          <li
            className="text-xl pt-3 flex items-center hover:text-white text-gray-400"
            onClick={navigateToCup}
          >
            <HeartIcon className="w-8 h-8" />
            <div className="pl-2">이상형월드컵</div>
          </li>
        )}
        {currentPage.pageName === 'DailyQuiz' ? (
          <li
            className="text-xl pt-3 flex items-center hover:text-yesrae-200 text-yesrae-0"
            onClick={navigateToDailyQuiz}
          >
            <LightBulbIcon className="w-8 h-8" />
            <div className="pl-2">데일리퀴즈</div>
          </li>
        ) : (
          <li
            className="text-xl pt-3 flex items-center hover:text-white text-gray-400"
            onClick={navigateToDailyQuiz}
          >
            <LightBulbIcon className="w-8 h-8" />
            <div className="pl-2">데일리퀴즈</div>
          </li>
        )}
        {currentPage.pageName === 'MyPage' ? (
          <li
            className="text-xl pt-3 flex items-center hover:text-yesrae-200 text-yesrae-0"
            onClick={navigateToMyPage}
          >
            <UserIcon className="w-8 h-8" />
            <div className="pl-2">마이페이지</div>
          </li>
        ) : (
          <li
            className="text-xl pt-3 flex items-center hover:text-white text-gray-400"
            onClick={navigateToMyPage}
          >
            <UserIcon className="w-8 h-8" />
            <div className="pl-2">마이페이지</div>
          </li>
        )}
        {currentPage.pageName === 'MyPage' ? (
          <li
            className="text-xl pt-3 flex items-center hover:text-yesrae-200 text-yesrae-0"
            onClick={navigateToAiVoice}
          >
            <Cog8ToothIcon className="w-8 h-8" />
            <div className="pl-2">AI 목소리</div>
          </li>
        ) : (
          <li
            className="text-xl pt-3 flex items-center hover:text-white text-gray-400"
            onClick={navigateToAiVoice}
          >
            <Cog8ToothIcon className="w-8 h-8" />
            <div className="pl-2">AI 목소리</div>
          </li>
        )}
      </ul>
      {currentPage.pageName === 'MyPage' ? (
        <div
          className="text-xl pt-3 px-8 flex items-center hover:text-yesrae-200 text-yesrae-0"
          onClick={navigateToServiceInfo}
        >
          <SparklesIcon className="w-8 h-8" />
          <div className="pl-2">서비스 소개</div>
        </div>
      ) : (
        <div
          className="text-xl pt-3 px-8 flex items-center hover:text-white text-gray-400"
          onClick={navigateToServiceInfo}
        >
          <SparklesIcon className="w-8 h-8" />
          <div className="pl-2">서비스 소개</div>
        </div>
      )}
    </div>
  );
}
