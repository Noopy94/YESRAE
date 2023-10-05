import HeaderNav from '../../components/HeaderNav/HeaderNav';
import MusicPlayer from '../../components/playercontroller/MusicPlayer';
import { useRecoilValue, useSetRecoilState } from 'recoil';
import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { currentPageState } from '../../recoil/currentpage/currentPage';
import ButtonComponent from '../../components/common/ButtonComponent';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEllipsisH, faHeart } from '@fortawesome/free-solid-svg-icons';
import PlayListCarousel from '../../components/common/PlayListCarousel';
import { defaultplayLists1, Song } from '../../recoil/defaultdata/data';
import CommentComponent from '../../components/common/CommentComponent.tsx';
import {
  registNotification,
  registPlaylistComment,
} from '../../api/commentApi.ts';
import { userState } from '../../recoil/user/user';
import { findPlaylistApi, findSonglistApi } from '../../api/playlistApi.ts';
import SongListComponent from '../../components/common/SongListComponent.tsx';
import { findnickname } from '../../api/userApi.ts';

interface playList {
  id: number;
  imgUrl: string;
  title: string;
  description: string;
  createdAt: string;
  userId: number;
  nickname: string;
  songs: Song[];
}

export default function PlayListDetail() {
  const user = useRecoilValue(userState);
  const { playListId } = useParams();
  const setCurrentPage = useSetRecoilState(currentPageState);
  const [songsLoading, setSongsLoading] = useState(false);
  const [comment, setComment] = useState('');
  const [isTooltipVisible, setTooltipVisible] = useState(false);
  const [currentPlayListDetail, setCurrentPlayListDetail] = useState<playList>({
    id: 0,
    imgUrl: '',
    title: '',
    description: '',
    createdAt: '',
    userId: 0,
    nickname: '',
    songs: [
      {
        songId: '',
        songTitle: '',
        songUrl: '',
        songArtist: '',
        songImgUrl: '',
      },
    ],
  });

  const onChangePlayList = () => {};

  const onChangePlayListDetail = (data: playList) => {
    setCurrentPlayListDetail(data);
  };

  const onChangeSong = () => {
    // setCurrentSong();
  };

  const onSongsLoading = () => {
    setSongsLoading(true);
  };

  const onChangeComment = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setComment(event.target.value);
  };

  useEffect(() => {
    window.scroll(0, 0);
    setCurrentPage({ pageName: 'PlayList' });
    getPlayList();
  }, []);

  const getPlayList = () => {
    findPlaylistApi(Number(playListId)).then((res) => {
      findnickname(res.userId).then((nickname) => {
        res.nickname = nickname;
      });
      findSonglistApi(Number(playListId)).then((songs) => {
        res.songs = songs;
        onChangePlayListDetail(res);
        onSongsLoading();
      });
    });
  };

  const toggleTooltip = () => {
    setTooltipVisible(!isTooltipVisible);
  };

  const registComment = () => {
    registPlaylistComment(Number(playListId), user.userId, comment).then(
      (res) => {
        if (res) {
          alert('댓글이 등록되었습니다.');
          if (currentPlayListDetail.userId !== user.userId) {
            registNotification(
              'playlistComment',
              currentPlayListDetail.title,
              user.nickname,
              currentPlayListDetail.userId,
            );
          }
          history.go(0);
        }
      },
    );
  };

  return (
    <div>
      <div className="flex">
        <div className="w-2/12">
          <HeaderNav />
        </div>
        <main className="w-10/12 px-20 pt-12">
          <div className="flex pb-10 border-b border-gray-900">
            <img
              className="w-56 h-56"
              src={currentPlayListDetail.imgUrl}
              alt="앨범아트"
            ></img>
            <div className="relative px-6">
              <Link to={`/playlistdetail/${playListId}`}>
                <div className="text-3xl font-semibold">
                  {currentPlayListDetail.title}
                </div>
              </Link>
              <div className="pt-3">{currentPlayListDetail.nickname}</div>
              <div className="w-full h-24 max-w-3xl pt-2 text-gray-400 max-h-24">
                {currentPlayListDetail.description}
              </div>
              <div className="flex pt-3">
                <ButtonComponent onClick={onChangePlayList} type="isSmall">
                  전체재생
                </ButtonComponent>
                <div className="px-4">
                  <ButtonComponent onClick={onChangePlayList} type="istiny">
                    랜덤재생
                  </ButtonComponent>
                </div>
                <div className="flex items-center justify-center w-10 h-10 mr-4 bg-black border-2 border-gray-700 rounded-full group">
                  <FontAwesomeIcon
                    icon={faHeart}
                    className="w-5 h-5 group-hover:text-red-600"
                  />
                </div>
                <div
                  className="flex items-center justify-center w-10 h-10 bg-black border-2 border-gray-700 rounded-full group "
                  onClick={toggleTooltip}
                >
                  <FontAwesomeIcon
                    icon={faEllipsisH}
                    className="w-5 h-5 group-hover:text-white"
                  />
                </div>
                {isTooltipVisible && (
                  <div className="absolute z-20 w-48 py-2 mx-8 bg-gray-900 rounded-lg shadow-lg left-96">
                    <div className="px-2 py-1 hover:bg-gray-800">
                      💘 플레이 리스트에 추가
                    </div>
                    <div className="px-2 py-1 hover:bg-gray-800">🥰 좋아요</div>
                    <div className="px-2 py-1 hover:bg-gray-800">👩‍❤️‍👩 공유</div>
                  </div>
                )}
              </div>
            </div>
          </div>
          {songsLoading ? (
            <SongListComponent songs={currentPlayListDetail.songs} />
          ) : (
            <div />
          )}
          <div className="pb-5 text-2xl">
            댓글목록
            <br />
            <CommentComponent type={'playlist'} typeId={Number(playListId)} />
            <hr />
          </div>
          <textarea
            className="inline w-10/12 h-20 text-black"
            placeholder="댓글을 입력해주세요"
            onChange={onChangeComment}
          ></textarea>
          <div className="inline float-right ">
            <ButtonComponent
              onClick={registComment}
              type={'isSmall'}
              children="등록"
            />
          </div>
          <div className="mt-12 mb-3 text-2xl font-bold">
            연관된 플레이 리스트 😎
          </div>
          <div className="flex py-4">
            <PlayListCarousel playLists={defaultplayLists1} />
          </div>
          <div className="mt-12 mb-3 text-2xl font-bold">
            해당 유저의 플레이 리스트 🐏
          </div>
          <div className="flex py-4">
            <PlayListCarousel playLists={defaultplayLists1} />
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
