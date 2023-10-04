import HeaderNav from '../../components/HeaderNav/HeaderNav';
import MusicPlayer from '../../components/playercontroller/MusicPlayer';
import { userState } from '../../recoil/user/user';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { currentPageState, isListState } from '../../recoil/currentpage/currentPage';
import ButtonComponent from '../../components/common/ButtonComponent';
import { currentSongListState, currentSongState } from '../../recoil/currentsong/currentSong';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEllipsisH, faHeart } from '@fortawesome/free-solid-svg-icons';
import { useParams } from 'react-router-dom';

export interface ISongDetail {
  id: string,
  name: string,
  albumId: string,
  albumName: string,
  artistId: string,
  artistName: string,
  imgUrl: string,
  previewUrl: string,
  releaseYear: number,
  duration: number,
  popularity: number,
  acousticness: number,
  danceability: number,
  energy: number,
  instrumentalness: number,
  tune: number,
  liveness: number,
  loudness: number,
  mode: number,
  speechiness: number,
  tempo: number,
  timeSignature: number,
  valence: number,
  songlike: boolean,
}

export default function SongDetail() {
  // 노래, 플레이 리스트 데이터 샘플, 나중에 api로 가져올 예정

  const { songId } = useParams();
  const User = useRecoilValue(userState);
  const setCurrentPage = useSetRecoilState(currentPageState);
  const [currentSong, setCurrentSong] = useRecoilState(currentSongState);
  const [isList, setIsList] = useRecoilState(isListState);
  const [songList, setSongList] = useRecoilState(currentSongListState);
  const [songLike, setSongLike] = useState(false);
  const [isTooltipVisible, setTooltipVisible] = useState(false);
  const [currentSongDetail, setCurrentSongDetail] = useState({
    id: '',
    name: '',
    albumId: '',
    albumName: '',
    artistId: '',
    artistName: '',
    imgUrl: '',
    previewUrl: '',
    releaseYear: 0,
    duration: 0,
    popularity: 0,
    acousticness: 0,
    danceability: 0,
    energy: 0,
    instrumentalness: 0,
    tune: 0,
    liveness: 0,
    loudness: 0,
    mode: 0,
    speechiness: 0,
    tempo: 0,
    timeSignature: 0,
    valence: 0,
    songlike: false,
  });

  const onChangePlayList = () => {
    // setCurrentPlayList
  };

  const onChangeSong = (data: ISongDetail) => {
    setCurrentSongDetail(data);
  };

  useEffect(() => {
    setCurrentPage({ pageName: '' });
  }, []);

  const toggleTooltip = () => {
    setTooltipVisible(!isTooltipVisible);
  };

  const onChangeSongLike = () => {
    setSongLike(!songLike);
    axios.post(`http://localhost:8080/song/songlike`, {
      userId: 1,
      songId: songId,
    })
    .catch((error) => {
      console.error('Error songlike :', error);
    });
  };

  useEffect(() => {
    axios.post(`http://localhost:8080/song`, {
      userId: 1,
      songId: songId,
    })
    .then((response) => {
      onChangeSong(response.data.content);
    })
    .catch((error) => {
      console.error('Error fetching song data:', error);
    });
  }, [songId, User.id]);

  return (
    <div>
      <div className='flex'>
        <div className='w-2/12'>
          <HeaderNav />
        </div>
        <main className='w-10/12 px-20 pt-12'>
          <div className='flex pb-10 border-b border-gray-900'>
            <img
              className='w-56 h-56'
              src={currentSongDetail.imgUrl}
            ></img>
            <div className='relative px-6'>
              <div className='text-3xl font-semibold'>
                {currentSongDetail.name}
              </div>
              <div className='pt-3'>
                가수 이름
                {currentSongDetail.artistName}
              </div>
              <div className='w-full h-12 max-w-3xl pt-2 text-gray-400 max-h-24'>
                앨범 이름
                {currentSongDetail.albumName}
              </div>
              <div className='w-full h-12 max-w-3xl pt-2 text-gray-400 max-h-24'>
                출시 연도
                {currentSongDetail.releaseYear}
              </div>
              <div className='w-full h-12 max-w-3xl pt-2 text-gray-400 max-h-24'>
                {currentSongDetail.duration}초
              </div>
              <div className='flex pt-3'>
                <ButtonComponent onClick={onChangePlayList} type='isSmall'>
                  재생하기
                </ButtonComponent>
                <button
                  className='flex items-center justify-center w-10 h-10 mx-4 bg-black border-2 border-gray-700 rounded-full group'
                  onClick={onChangeSongLike}>
                  {songLike === true ? <FontAwesomeIcon
                    icon={faHeart}
                    className='w-5 h-5 text-red-600'
                  /> : <FontAwesomeIcon
                    icon={faHeart}
                    className='group-hover:text-red-600 group-hover:w-5 group-hover:h-5'
                  />}
                </button>
                <button
                  className='flex items-center justify-center w-10 h-10 bg-black border-2 border-gray-700 rounded-full group '
                  onClick={toggleTooltip}
                >
                  <FontAwesomeIcon
                    icon={faEllipsisH}
                    className='w-5 h-5 group-hover:text-white'
                  />
                </button>
                {isTooltipVisible && (
                  <div
                    className='absolute z-20 w-48 py-2 mx-8 bg-gray-900 rounded-lg shadow-lg left-60'>
                    <div className='px-2 py-1 hover:bg-gray-800' onClick={onChangePlayList}>
                      💘 플레이 리스트에 추가
                    </div>
                    <div className='px-2 py-1 hover:bg-gray-800' onClick={onChangeSongLike}>🥰 좋아요
                    </div>
                    <div className='px-2 py-1 hover:bg-gray-800'>👩‍❤️‍👩 공유</div>
                  </div>
                )}
              </div>
            </div>
          </div>
          <div className='fixed relative bottom-0 left-0 h-52'>
            popularity : {currentSongDetail.popularity}<br /><br />
            acosticness : {currentSongDetail.acousticness}<br /><br />
            danceability : {currentSongDetail.danceability}<br /><br />
            energy : {currentSongDetail.energy}<br /><br />
            instrumentalness : {currentSongDetail.instrumentalness}<br /><br />
            livness : {currentSongDetail.liveness}<br /><br />
            loudness : {currentSongDetail.loudness}<br /><br />
            speechiness : {currentSongDetail.speechiness}<br /><br />
            tempo : {currentSongDetail.tempo}<br /><br />
            mode : {currentSongDetail.mode}<br /><br />
            tune : {currentSongDetail.tune}<br /><br />
            timeSignature : {currentSongDetail.timeSignature}<br /><br />
            valence : {currentSongDetail.valence}<br /><br />
          </div>
        </main>
      </div>
      <footer>
        <MusicPlayer />
      </footer>
    </div>
  );
}
