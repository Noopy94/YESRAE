import React, { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import { useNavigate } from 'react-router-dom';
import { userState } from '../../recoil/user/user';
import HeaderNav from '../../components/HeaderNav/HeaderNav';
import MusicPlayer from '../../components/playercontroller/MusicPlayer';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import {
  registPlaylistApi,
  PlayListRegistPostReq,
} from '../../api/registPlaylistApi';

export default function PlayListRegist() {
  const User = useRecoilValue(userState);
  const navigate = useNavigate();
  const [selectedImage, setSelectedImage] = useState<string | null>(null);
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [playlistTitle, setPlaylistTitle] = useState('');
  const [playlistDescription, setPlaylistDescription] = useState('');
  const [playlistTag, setPlaylistTag] = useState('');
  const [tagList, setTagList] = useState<string[]>([]);

  const handleImageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
      setSelectedFile(file);
      const reader = new FileReader();
      reader.onload = (e) => {
        setSelectedImage(e.target?.result as string);
      };
      reader.readAsDataURL(file);
    }
  };

  const handleAddImageClick = () => {
    const fileInput = document.getElementById(
      'image-input',
    ) as HTMLInputElement;
    fileInput?.click();
  };

  const handleTitleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPlaylistTitle(event.target.value);
  };

  const handleDescriptionChange = (
    event: React.ChangeEvent<HTMLTextAreaElement>,
  ) => {
    setPlaylistDescription(event.target.value);
  };

  const handleTagChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPlaylistTag(event.target.value);
  };

  const handleRemoveTag = (indexToRemove: number) => {
    const newTagList = tagList.filter((_, index) => index !== indexToRemove);
    setTagList(newTagList);
  };

  const AddTag = () => {
    if (playlistTag) {
      const newTagList = [...tagList, playlistTag];
      setTagList(newTagList);
      setPlaylistTag('');
    }
  };

  const onRegistPlayList = async () => {
    console.log('플레이 리스트 등록 시작!');
    // 데이터 모아서 api로 전송! 등록! 완료되면 플레이 리스트로 돌려 보낼것!
    const playListRegistPostReq: PlayListRegistPostReq = {
      userId: User.userId,
      isPublic: 1,
      title: playlistTitle,
      description: playlistDescription,
      tags: tagList,
    };

    try {
      await registPlaylistApi(playListRegistPostReq, selectedFile);
      console.log('플레이 리스트 등록 성공!');

      // 등록 완료 후 페이지 이동
      navigate(`/mypage/${User.userId}`);
    } catch (error) {
      console.error('플레이 리스트 등록 실패:', error);
    }
  };

  useEffect(() => {
    if (User.nickname === '') {
      navigate('/');
    }
  }, []);

  return (
    <div>
      <div className="flex">
        <div className="w-2/12">
          <HeaderNav />
        </div>
        <main className="w-10/12 pt-16 pl-20">
          <div className="flex pb-8 border-b border-gray-800">
            <div className="relative w-64 h-64 overflow-hidden rounded-3xl">
              {selectedImage ? (
                <img
                  src={selectedImage}
                  alt="Preview"
                  className="object-cover w-full h-full cursor-pointer"
                  onClick={handleAddImageClick}
                />
              ) : (
                <div className="flex items-center justify-center w-full h-full text-5xl text-gray-400 bg-gray-900">
                  <button onClick={handleAddImageClick}>+</button>
                </div>
              )}
            </div>
            <div className="flex flex-col gap-3 px-6">
              <div className="text-xl">플레이 리스트 제목</div>
              <input
                className="h-8 px-2 text-white bg-gray-900 rounded-md w-96"
                type="text"
                value={playlistTitle}
                onChange={handleTitleChange}
              />
              <div className="text-xl">플레이 리스트 설명</div>
              <textarea
                className="h-32 px-2 text-white bg-gray-900 rounded-md w-[600px] max-h-32 resize-none"
                value={playlistDescription}
                onChange={handleDescriptionChange}
                rows={4}
                cols={50}
              />
            </div>
          </div>
          <div className="flex items-center gap-1 py-2">
            <div className="text-lg">태그</div>
            <input
              className="h-8 px-2 m-2 text-white bg-gray-900 rounded-md w-[480px]"
              type="text"
              value={playlistTag}
              onChange={handleTagChange}
              onKeyPress={(e) => {
                if (e.key === 'Enter') {
                  AddTag();
                }
              }}
            />
            <button
              className="h-8 px-2 text-lg text-white border-2 border-gray-900 rounded-md hover:font-semibold"
              onClick={AddTag}
            >
              추가
            </button>
          </div>
          <div className="flex flex-wrap">
            {tagList.map((tag, index) => (
              <div
                key={index}
                className="relative flex items-center h-8 px-1 m-2 bg-black border border-white rounded-md"
              >
                <FontAwesomeIcon
                  icon={faXmark}
                  className="justify-center w-3 h-3 p-1 text-white bg-gray-900 rounded-full"
                  onClick={() => handleRemoveTag(index)}
                />
                {tag}
              </div>
            ))}
          </div>
          <div className="flex justify-end px-1 pr-24 pt-36">
            <button
              className="px-2 py-1 text-lg border border-white rounded-md hover:font-semibold"
              onClick={onRegistPlayList}
            >
              {' '}
              등록{' '}
            </button>
          </div>

          {/* 밑에 부분은 공간 남는거 채우는 용도니 그대로 둘것*/}
          <div>
            <div className="fixed relative bottom-0 left-0 h-36" />
          </div>
        </main>
      </div>
      <input
        id="image-input"
        type="file"
        accept="image/*"
        style={{ display: 'none' }}
        onChange={handleImageChange}
      />
      <footer>
        <MusicPlayer />
      </footer>
    </div>
  );
}
