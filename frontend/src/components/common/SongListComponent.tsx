import { useRecoilState } from 'recoil';
import {
  currentSongListState,
  currentSongState,
} from '../../recoil/currentsong/currentSong';
import { faEllipsisH } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useState } from 'react';
import { Song } from '../../recoil/defaultdata/data';

interface SongListComponentProps {
  songs: Song[];
}

const SongListComponent: React.FC<SongListComponentProps> = ({ songs }) => {
  const [currentSongList, setCurrentSongList] =
    useRecoilState(currentSongListState);

  const [songTooltips, setSongTooltips] = useState(songs.map(() => false));
  const [currentSong, setCurrentSong] = useRecoilState(currentSongState);
  const [isTooltipVisible, setTooltipVisible] = useState(false);

  const toggleTooltip = (index: number) => {
    const updatedTooltips = songs.map(() => false);
    updatedTooltips[index] = !songTooltips[index];
    setSongTooltips(updatedTooltips);
  };

  const onChangeSong = (song: Song) => {
    setCurrentSong(song);
  };

  return (
    <div className="w-full bg-black ">
      {songs.map((song: Song, index: number) => (
        <div
          key={index}
          className="relative flex items-center h-12 my-2 text-white hover:bg-gray-800 group"
        >
          <button>
            <img
              src={song.songImgUrl}
              className="w-10 h-10 group-hover:opacity-50"
              onClick={() => onChangeSong(song)}
              alt={song.songTitle}
            />
          </button>
          <div className="w-1/2 px-4 group-hover:text-white max-w-96">
            {song.songTitle}
          </div>
          <div className="px-4 w-96 group-hover:text-white max-w-96">
            {song.songArtist}
          </div>
          <button>
            <div
              className={`flex items-center justify-center w-8 h-8 ml-8 bg-black border-2 border-gray-700 rounded-full hover:text-gray-700 ${
                songTooltips[index] ? 'text-gray-700' : ''
              }`}
              onClick={() => toggleTooltip(index)}
            >
              <FontAwesomeIcon
                icon={faEllipsisH}
                className="relative w-5 h-5"
              />
            </div>
          </button>
          {songTooltips[index] && (
            <div className="absolute z-20 w-48 py-2 mx-2 bg-gray-900 rounded-lg shadow-lg right-24">
              <div className="px-2 py-1 hover:bg-gray-800">
                💘 플레이 리스트에 추가
              </div>
              <div className="px-2 py-1 hover:bg-gray-800">🥰 좋아요</div>
              <div className="px-2 py-1 hover:bg-gray-800">👩‍❤️‍👩 공유</div>
            </div>
          )}
        </div>
      ))}
    </div>
  );
};

export default SongListComponent;
