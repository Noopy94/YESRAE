import React, { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import ButtonComponent from '../common/ButtonComponent';
import { Link, useNavigate } from 'react-router-dom';
import Logo from '../../assets/Logo.svg';

import { ClipboardIcon, LightBulbIcon, SparklesIcon } from '@heroicons/react/24/outline';
import { Cog8ToothIcon, HeartIcon, MusicalNoteIcon, UserIcon } from '@heroicons/react/24/solid';
import InputComponent from '../common/InputComponent';
import { userState } from '../../recoil/user/user';
import { currentPageState } from '../../recoil/currentpage/currentPage';

export default function HeaderNav() {
  const user = useRecoilValue(userState);
  const currentPage = useRecoilValue(currentPageState);
  const [search, setSearch] = useState('');
  const navigate = useNavigate();
  useEffect(() => {
    handleSearch();
  }, [search]);

  const handleLogout = () => {
    localStorage.removeItem('user');
    window.location.href = '/';
  };

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
    navigate('/cup');
  };

  const navigateToDailyQuiz = () => {
    navigate('/quiz');
  };

  const navigateToMyPage = () => {
    navigate('/mypage/' + user.id);
  };

  const navigateToAiVoice = () => {
    navigate('/aivoice');
  };

  const navigateToServiceInfo = () => {
    navigate('/serviceinfo');
  };

  return (
    <div className='fixed z-30 w-64 h-full bg-black border-r border-gray-900'>
      <Link to='/'>
        <img src={Logo} alt='YESRAE LOGO' className='py-4 px-7' />
      </Link>
      <div className='p-3 border-t-2 border-gray-900'>
        {user.nickname ? (
          <div className='flex items-center justify-evenly gap-x-2'>
            <img
              src={
                user.imageUrl ? user.imageUrl : '/src/assets/defaultUserImg.png'
              }
              alt={user.nickname}
              className='rounded-full w-14 h-14'
            />
            <div className='flex flex-col gap-y-2'>
              <div>{user.nickname}님</div>
              <ButtonComponent type='isSmall' onClick={handleLogout}>
                로그아웃
              </ButtonComponent>
            </div>
          </div>
        ) : (
          <div>
            <div className='pb-2 pl-5'> 로그인하고 추천 노래 확인하기</div>
            <Link to='/user/login'>
              <ButtonComponent type='isMiddle'>로그인</ButtonComponent>
            </Link>
          </div>
        )}
      </div>
      <div className='border-t-2 border-gray-900'>
        <InputComponent
          placeholder='YESRAE 검색'
          onChange={onChangeSearch}
          onKeyDown={onHandleKeyDown}
          type='text'
        />
      </div>
      <ul className='px-8 py-3 text-white border-b-2 border-gray-900'>
        {currentPage.pageName === 'Home' ? (
          <li
            className='flex items-center pt-1 text-xl hover:text-yesrae-200 text-yesrae-0 '
            onClick={navigateToBoard}
          >
            <ClipboardIcon className='w-8 h-8' />
            <div className='pl-2'>게시판</div>
          </li>
        ) : (
          <li
            className='flex items-center pt-1 text-xl text-gray-400 hover:text-white'
            onClick={navigateToBoard}
          >
            <ClipboardIcon className='w-8 h-8' />
            <div className='pl-2'>게시판</div>
          </li>
        )}
        {currentPage.pageName === 'PlayList' ? (
          <li
            className='flex items-center pt-3 text-xl hover:text-yesrae-200 text-yesrae-0'
            onClick={navigateToPlayList}
          >
            <MusicalNoteIcon className='w-8 h-8' />
            <div className='pl-2'> 플레이리스트</div>
          </li>
        ) : (
          <li
            className='flex items-center pt-3 text-xl text-gray-400 hover:text-white'
            onClick={navigateToPlayList}
          >
            <MusicalNoteIcon className='w-8 h-8' />
            <div className='pl-2'> 플레이리스트</div>
          </li>
        )}
        {currentPage.pageName === 'Cup' ? (
          <li
            className='flex items-center pt-3 text-xl hover:text-yesrae-200 text-yesrae-0'
            onClick={navigateToCup}
          >
            <HeartIcon className='w-8 h-8' />
            <div className='pl-2'>이상형월드컵</div>
          </li>
        ) : (
          <li
            className='flex items-center pt-3 text-xl text-gray-400 hover:text-white'
            onClick={navigateToCup}
          >
            <HeartIcon className='w-8 h-8' />
            <div className='pl-2'>이상형월드컵</div>
          </li>
        )}
        {currentPage.pageName === 'DailyQuiz' ? (
          <li
            className='flex items-center pt-3 text-xl hover:text-yesrae-200 text-yesrae-0'
            onClick={navigateToDailyQuiz}
          >
            <LightBulbIcon className='w-8 h-8' />
            <div className='pl-2'>데일리퀴즈</div>
          </li>
        ) : (
          <li
            className='flex items-center pt-3 text-xl text-gray-400 hover:text-white'
            onClick={navigateToDailyQuiz}
          >
            <LightBulbIcon className='w-8 h-8' />
            <div className='pl-2'>데일리퀴즈</div>
          </li>
        )}
        {currentPage.pageName === 'MyPage' ? (
          <li
            className='flex items-center pt-3 text-xl hover:text-yesrae-200 text-yesrae-0'
            onClick={navigateToMyPage}
          >
            <UserIcon className='w-8 h-8' />
            <div className='pl-2'>마이페이지</div>
          </li>
        ) : (
          <li
            className='flex items-center pt-3 text-xl text-gray-400 hover:text-white'
            onClick={navigateToMyPage}
          >
            <UserIcon className='w-8 h-8' />
            <div className='pl-2'>마이페이지</div>
          </li>
        )}
        {currentPage.pageName === 'AI' ? (
          <li
            className='flex items-center pt-3 text-xl hover:text-yesrae-200 text-yesrae-0'
            onClick={navigateToAiVoice}
          >
            <Cog8ToothIcon className='w-8 h-8' />
            <div className='pl-2'>AI 목소리</div>
          </li>
        ) : (
          <li
            className='flex items-center pt-3 text-xl text-gray-400 hover:text-white'
            onClick={navigateToAiVoice}
          >
            <Cog8ToothIcon className='w-8 h-8' />
            <div className='pl-2'>AI 목소리</div>
          </li>
        )}
      </ul>
      {currentPage.pageName === 'AI' ? (
        <div
          className='flex items-center px-8 pt-3 text-xl hover:text-yesrae-200 text-yesrae-0'
          onClick={navigateToServiceInfo}
        >
          <SparklesIcon className='w-8 h-8' />
          <div className='pl-2'>서비스 소개</div>
        </div>
      ) : (
        <div
          className='flex items-center px-8 pt-3 text-xl text-gray-400 hover:text-white'
          onClick={navigateToServiceInfo}
        >
          <SparklesIcon className='w-8 h-8' />
          <div className='pl-2'>서비스 소개</div>
        </div>
      )}
    </div>
  );
}
