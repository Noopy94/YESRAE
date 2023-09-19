import React, { useState, useEffect } from 'react';
import { useRecoilValue } from 'recoil';
import { userState } from '../../recoil/user/user';
import ButtonComponent from '../common/ButtonComponent';
import { Link, useNavigate } from 'react-router-dom';
import { LightBulbIcon, ClipboardIcon } from '@heroicons/react/24/outline';
import {
  Cog8ToothIcon,
  HeartIcon,
  MusicalNoteIcon,
  UserIcon,
} from '@heroicons/react/24/solid';
import InputComponent from '../common/InputComponent';

export default function HeaderNav() {
  const user = useRecoilValue(userState);
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
    navigate('/board'); // '/board'로 이동합니다.
  };

  return (
    <div className="fixed h-full w-64 bg-black">
      <div className="text-4xl px-8 pt-6 pb-4 font-extrabold">YESRAE</div>
      {user.name ? (
        <>
          <div>
            <img
              src={user.imgUrl}
              alt={user.name}
              className="w-12 h-12 rounded-full"
            />
          </div>
          <div>{user.name}님 안녕하세요.</div>
        </>
      ) : (
        <Link to="/user/login">
          <ButtonComponent type="ismiddle">로그인</ButtonComponent>
        </Link>
      )}
      <InputComponent
        placeholder="YESRAE 검색"
        onChange={onChangeSearch}
        onKeyDown={onHandleKeyDown}
        type="text"
      />
      <ul className="text-white px-8 border-b py-3 mx-4 border-gray-900">
        <li
          className="text-xl pt-1 flex items-center hover:text-yesrae-0"
          onClick={navigateToBoard}
        >
          <ClipboardIcon className="w-8 h-8" />
          <div className="pl-2">게시판</div>
        </li>
        <li className="text-xl pt-3 flex items-center hover:text-red-200">
          <MusicalNoteIcon className="w-8 h-8" />
          <div className="pl-2"> 플레이리스트 </div>
        </li>
        <li className="text-xl pt-3 flex items-center hover:text-red-200">
          <HeartIcon className="w-8 h-8" />
          <div className="pl-2">이상형월드컵</div>
        </li>
        <li className="text-xl pt-3 flex items-center hover:text-red-200">
          <LightBulbIcon className="w-8 h-8" />
          <div className="pl-2">데일리퀴즈</div>
        </li>
        <li className="text-xl pt-3 flex items-center hover:text-red-200">
          <UserIcon className="w-8 h-8" />
          <div className="pl-2">마이페이지</div>
        </li>
        <li className="text-xl pt-3 flex items-center hover:text-red-200">
          <Cog8ToothIcon className="w-8 h-8" />
          <div className="pl-2">AI 목소리</div>
        </li>
      </ul>
    </div>
  );
}
