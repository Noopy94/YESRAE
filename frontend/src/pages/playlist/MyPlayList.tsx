import HeaderNav from '../../components/HeaderNav/HeaderNav';
import MusicPlayer from '../../components/playercontroller/MusicPlayer';
import { defaultplayLists } from '../../recoil/defaultdata/data';
import { userState } from '../../recoil/user/user';
import { useRecoilState, useRecoilValue } from 'recoil';
import { useEffect, useState } from 'react';
import MyPlayListCarousel from '../../components/common/MyPlayListCarousel';
import { currentPageState } from '../../recoil/currentpage/currentPage';
import { Link, Navigate, useNavigate } from 'react-router-dom';
import { findUserPlayListApi } from '../../api/playlistApi';
import { PlayList } from '../../recoil/defaultdata/data';

export default function MyPlayList() {
  // 노래, 플레이 리스트 데이터 샘플, 나중에 api로 가져올 예정

  const User = useRecoilValue(userState);
  const [currentPage, setCurrentPage] = useRecoilState(currentPageState);
  const [playlist1, setPlayList1] = useState<PlayList[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    // 내 플레이 리스트 가져오기
    async function MyPlaylists() {
      if (User.nickname === '') {
        navigate('/');
      }

      try {
        console.log('내 플레이 리스트 가져오기 성공');
        const myPlaylists = await findUserPlayListApi(User.userId);
        setPlayList1(myPlaylists || []);
      } catch (error) {
        console.error('베스트 플레이 리스트 가져오기 실패:', error);
      }
    }

    setCurrentPage({ pageName: 'MyPage' });
    MyPlaylists();
  }, []);

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
          </header>
          <div>
            <div className="mt-12 mb-3 text-2xl font-bold">
              나의 플레이 리스트 😎
            </div>
            <div className="flex py-4">
              <MyPlayListCarousel playLists={playlist1} />
            </div>
            <div className="mt-12 mb-3 text-2xl font-bold">
              좋아요한 플레이 리스트 👍
            </div>
            <div className="flex py-4">
              <MyPlayListCarousel playLists={defaultplayLists} />
            </div>
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
