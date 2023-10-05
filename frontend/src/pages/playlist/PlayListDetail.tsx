import HeaderNav from '../../components/HeaderNav/HeaderNav';
import MusicPlayer from '../../components/playercontroller/MusicPlayer';
import { useRecoilState } from 'recoil';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { currentPlayListState } from '../../recoil/playlist/currentPlayList';
import {
  currentPageState,
  isListState,
} from '../../recoil/currentpage/currentPage';
import { Link } from 'react-router-dom';
import ButtonComponent from '../../components/common/ButtonComponent';
import {
  currentSongListState,
  currentSongState,
} from '../../recoil/currentsong/currentSong';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHeart, faEllipsisH } from '@fortawesome/free-solid-svg-icons';
import SongListComponent from '../../components/common/SongListComponent';
import PlayListCarousel from '../../components/common/PlayListCarousel';
import { defaultplayLists } from '../../recoil/defaultdata/data';

export default function PlayListDetail() {
  // 노래, 플레이 리스트 데이터 샘플, 나중에 api로 가져올 예정

  const { playListId } = useParams();
  const [currentPage, setCurrentPage] = useRecoilState(currentPageState);
  const [currentSong, setCurrentSong] = useRecoilState(currentSongState);
  const [isList, setIsList] = useRecoilState(isListState);
  const [songList, setSongList] = useRecoilState(currentSongListState);

  const onChangePlayList = () => {
    // setCurrentPlayList
  };

  const onChangeSong = () => {
    // setCurrentSong();
  };

  useEffect(() => {
    setCurrentPage({ pageName: 'PlayList' });
  }, []);

  const [isTooltipVisible, setTooltipVisible] = useState(false);

  const toggleTooltip = () => {
    setTooltipVisible(!isTooltipVisible);
  };

  // useEffect(() => {
  //   axios
  //     .get(`/api/playList/${userId}`)
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
        <main className="w-10/12 px-20 pt-12">
          <div className="flex pb-10 border-b border-gray-900">
            <img
              className="w-56 h-56"
              src=""
              // src={currentPlayList.playListImageUrl}
            ></img>
            <div className="relative px-6">
              <Link to={`/playlistdetail/${playListId}`}>
                <div className="text-3xl font-semibold">
                  {/* {currentPlayList.playListTitle} */}
                  플레이 리스트 제목
                </div>
              </Link>
              <div className="pt-3">
                {/* {currentPlayList.playListUserNickName} */}
                플레이리스트 유저 닉네임
              </div>
              <div className="w-full h-24 max-w-3xl pt-2 text-gray-400 max-h-24">
                {/* {currentPlayList.playListDescription} */}
                플레이리스트 설명
                와아아와아아와아아와아아와아아와아아와아아와아아와아아와아아와아아와아아와아아와아아와아아와아아와아아와아아와아아와아아
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
          <SongListComponent songs={songList} />
          <div className="mt-12 mb-3 text-2xl font-bold">
            연관된 플레이 리스트 😎
          </div>
          <div className="flex py-4">
            <PlayListCarousel playLists={defaultplayLists} />
          </div>
          <div className="mt-12 mb-3 text-2xl font-bold">
            해당 유저의 플레이 리스트 🐏
          </div>
          <div className="flex py-4">
            <PlayListCarousel playLists={defaultplayLists} />
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
