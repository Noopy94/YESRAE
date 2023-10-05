import HeaderNav from '../../components/HeaderNav/HeaderNav';
import MusicPlayer from '../../components/playercontroller/MusicPlayer';
import SongCarousel from '../../components/common/SongCarousel';
import PlayListCarousel from '../../components/common/PlayListCarousel';
import { defaultsongs } from '../../recoil/defaultdata/data';
import { userState } from '../../recoil/user/user';
import { useRecoilState } from 'recoil';
import { useState, useEffect } from 'react';
import {
  isListState,
  currentPageState,
} from '../../recoil/currentpage/currentPage';
import '../../styles.css';
import { PlayList } from '../../recoil/defaultdata/data';
import {
  findBest20LikeCntPlaylistApi,
  findBest20ViewCntPlaylistApi,
} from '../../api/playlistApi';

export default function Main() {
  // 노래, 플레이 리스트 데이터 샘플, 나중에 api로 가져올 예정
  const [User, setUser] = useRecoilState(userState);
  const [isList, setIsList] = useRecoilState(isListState);
  const [currentPage, setCurrentPage] = useRecoilState(currentPageState);
  const [bestLike20songs, setBestLike20Songs] = useState<PlayList[]>([]);
  const [bestView20songs, setBestView20Songs] = useState<PlayList[]>([]);

  useEffect(() => {
    //api 이용해서 userState 바뀔 때만 추천 알고리즘 변경
    // 로그아웃하고 다시 오면 추천 변경 되게
  }, [userState]);

  useEffect(() => {
    // 베스트 플레이 리스트들 2개 가져오기
    async function BestPlaylists() {
      try {
        console.log('베스트 플레이 리스트 가져오기 성공');
        const bestLikeCntPlaylists = await findBest20LikeCntPlaylistApi();
        const bestViewCntPlaylists = await findBest20ViewCntPlaylistApi();
        setBestLike20Songs(bestLikeCntPlaylists);
        setBestView20Songs(bestViewCntPlaylists);
      } catch (error) {
        console.error('베스트 플레이 리스트 가져오기 실패:', error);
      }
    }

    setCurrentPage({ pageName: '' });
    BestPlaylists();
  }, []);

  function UserRecommend() {
    if (User.nickname) {
      return (
        <div>
          <div className="mt-10 mb-3 text-2xl font-bold">
            {User.nickname}님 맞춤 추천 노래 🎤
          </div>
          <div className="flex">
            <SongCarousel songs={defaultsongs} />
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
        <main className="w-10/12 pt-12 pl-20 scrollbar-hide">
          <img
            src="/src/assets/AD.png.png"
            className="flex justify-center h-64 border-2 border-gray-800 w-280 "
          />
          <UserRecommend />
          <div className="mt-10 mb-3 text-2xl font-bold">
            YESRAE 추천 플레이 노래 😍
          </div>
          <div className="flex">
            <SongCarousel songs={defaultsongs} />
          </div>
          <div className="mt-10 mb-3 text-2xl font-bold">
            베스트 좋아요 플레이 리스트 🔥
          </div>
          <div className="flex">
            <PlayListCarousel playLists={bestLike20songs} />
          </div>
          <div className="mt-10 mb-3 text-2xl font-bold">
            베스트 조회수 플레이 리스트 💬
          </div>
          <div className="flex">
            <PlayListCarousel playLists={bestView20songs} />
          </div>
          <div className="mt-10 mb-3 text-2xl font-bold">
            가을 감성을 담은 노래 🍂
          </div>
          <div className="flex">
            <SongCarousel songs={defaultsongs} />
          </div>
          {/* 밑에 부분은 공간 남는거 채우는 용도니 그대로 둘것*/}
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
