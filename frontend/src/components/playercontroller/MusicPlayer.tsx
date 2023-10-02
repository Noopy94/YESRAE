import React, { useState, useRef, useEffect } from 'react';
import { Howl, Howler } from 'howler';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faPlay,
  faPause,
  faForwardStep,
  faBackwardStep,
  faVolumeHigh,
  faVolumeXmark,
  faList,
  faHeart,
  faShuffle,
  faRepeat,
} from '@fortawesome/free-solid-svg-icons';
import { useRecoilState } from 'recoil';
import {
  currentSongListState,
  currentSongState,
} from '../../recoil/currentsong/currentSong';
import MiniSongListComponent from '../common/MiniSongListComponent';
import { isListState } from '../../recoil/currentpage/currentPage';

const MusicPlayer: React.FC = () => {
  const [sound, setSound] = useState<Howl | null>(null);
  const [isPlaying, setIsPlaying] = useState<boolean>(false);
  const [isList, setIsList] = useRecoilState(isListState);
  const [currentTime, setCurrentTime] = useState<number>(0);
  const [volume, setVolume] = useState<number>(0.5);
  const [isMuted, setIsMuted] = useState<boolean>(false); // 볼륨 상태 추가
  const soundRef = useRef<Howl | null>(null);
  const [song, setSong] = useRecoilState(currentSongState);
  const [songList, setSongList] = useRecoilState(currentSongListState);

  const musicUrl = '/src/assets/test2.mp3';

  const playMusic = () => {
    if (!soundRef.current) {
      const newSound = new Howl({
        src: [musicUrl],
        volume: isMuted ? 0 : volume, // 볼륨 상태에 따라 조절
        onend: () => {},
        onplay: () => {
          setCurrentTime(0);
        },
      });
      soundRef.current = newSound;
      setSound(newSound);
    }

    if (isPlaying) {
      soundRef.current.pause();
    } else {
      Howler.ctx.resume().then(() => {
        soundRef.current?.play();
      });
    }

    setIsPlaying(!isPlaying);
  };

  const updateCurrentTime = () => {
    if (soundRef.current) {
      setCurrentTime(soundRef.current.seek());
    }
  };

  const changeVolume = (newVolume: number) => {
    if (soundRef.current) {
      soundRef.current.volume(newVolume);
      setVolume(newVolume);
    }
  };

  const toggleMute = () => {
    if (soundRef.current) {
      if (isMuted) {
        soundRef.current.volume(volume); // 볼륨 복원
      } else {
        soundRef.current.volume(0); // 볼륨 끄기
      }
      setIsMuted(!isMuted);
    }
  };

  const showList = () => {
    setIsList(!isList);
  };

  useEffect(() => {
    const intervalId = setInterval(updateCurrentTime, 30);
    return () => clearInterval(intervalId);
  }, []);

  return (
    <div>
      <div
        className={`fixed bottom-0 left-0 right-0 z-30 flex h-full pb-16 transition-transform duration-300 transform ${
          isList ? 'translate-y-0' : 'translate-y-full'
        } bg-black`}
      >
        <div className="flex items-center justify-center w-full">
          <img src={song.songImgUrl} className="w-[480px] h-[480px]" />
        </div>
        <div className="py-6 border-l border-gray-900 w-96">
          <div className="pb-4 pl-4 font-extrabold"> 이어지는 노래</div>
          <MiniSongListComponent songs={songList} />
        </div>
      </div>
      <div className="fixed z-50 w-full bottom-16">
        <div className="h-1 bg-gray-900 ">
          <div
            className="h-1 bg-yesrae-0"
            style={{
              width: `${(currentTime / 30) * 100}%`,
            }}
          ></div>
        </div>
      </div>
      <div className="fixed bottom-0 left-0 z-40 flex items-center justify-between w-full p-2 bg-gray-900 shadow-md">
        <div className="flex">
          <img src={song.songImgUrl} className="w-12 h-12"></img>
          <div className="px-4">
            <div className="w-24 overflow-hidden text-sm max-w-24 whitespace-nowrap text-overflow-ellipsis">
              {song.songTitle}
            </div>
            <div className="w-24 overflow-hidden text-sm max-w-24 whitespace-nowrap text-overflow-ellipsis">
              {song.songArtist}
            </div>
          </div>
          <button>
            <FontAwesomeIcon
              icon={faHeart}
              className="w-5 h-5 px-2 hover:text-yesrae-0"
            />
          </button>
        </div>
        <div className="justify-center ml-20">
          <button>
            <FontAwesomeIcon
              icon={faShuffle}
              className="w-5 h-5 px-2 hover:text-yesrae-0"
            />
          </button>
          <button>
            <FontAwesomeIcon
              icon={faBackwardStep}
              className="w-5 h-5 px-2 hover:text-yesrae-0"
            />
          </button>
          <button onClick={playMusic}>
            {isPlaying ? (
              <FontAwesomeIcon
                icon={faPause}
                className="justify-center w-6 h-6 px-2 text-yesrae-0 hover:text-yesrae-100"
              />
            ) : (
              <FontAwesomeIcon
                icon={faPlay}
                className="justify-center w-6 h-6 px-2 text-yesrae-0 hover:text-yesrae-100"
              />
            )}
          </button>
          <button>
            <FontAwesomeIcon
              icon={faForwardStep}
              className="w-5 h-5 px-2 hover:text-yesrae-0"
            />
          </button>
          <button>
            <FontAwesomeIcon
              icon={faRepeat}
              className="w-5 h-5 px-2 hover:text-yesrae-0"
            />
          </button>
        </div>
        <div className="flex items-center my-auto">
          <button
            onClick={toggleMute}
            className="w-6 h-6 mx-2 hover:text-yesrae-0"
          >
            {isMuted ? (
              <FontAwesomeIcon icon={faVolumeXmark} />
            ) : (
              <FontAwesomeIcon icon={faVolumeHigh} />
            )}
          </button>
          <input
            type="range"
            min={0}
            max={1}
            step={0.01}
            value={isMuted ? 0 : volume}
            onChange={(e) =>
              isMuted ? toggleMute() : changeVolume(parseFloat(e.target.value))
            }
            className="w-20 h-1 mx-2"
          />
          <div className="px-4 text-sm text-center text-gray-400 w-36">
            {`00:${currentTime.toFixed(0).padStart(2, '0')} / 00:30`}
          </div>
          <button className="mx-4 w-7 h-7">
            <FontAwesomeIcon
              icon={faList}
              className={
                isList
                  ? 'w-7 h-7 text-yesrae-0 '
                  : 'w-6 h-6  hover:w-7 hover:h-7 '
              }
              onClick={showList}
            />
          </button>
        </div>
      </div>
    </div>
  );
};

export default MusicPlayer;
