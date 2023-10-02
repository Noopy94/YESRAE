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

const MiniSongListComponent: React.FC<SongListComponentProps> = ({ songs }) => {
  const [currentSongList, setCurrentSongList] =
    useRecoilState(currentSongListState);
  const [songTooltips, setSongTooltips] = useState(songs.map(() => false));
  const [currentSong, setCurrentSong] = useRecoilState(currentSongState);

  const onChangeSong = (song: Song) => {
    setCurrentSong(song);
  };

  return (
    <div className="w-full max-h-full overflow-y-auto bg-black">
      {songs.map((song: Song, index: number) => (
        <div
          key={index}
          className="relative flex items-center h-12 px-4 my-1 text-white hover:bg-gray-800 group"
        >
          <button>
            <img
              src={song.songImgUrl}
              className="w-11 h-11 group-hover:opacity-50"
              onClick={() => onChangeSong(song)}
              alt={song.songTitle}
            />
          </button>
          <div className="w-56 px-4 overflow-hidden text-sm text-gray-400 max-w-56 whitespace-nowrap text-overflow-ellipsis group-hover:text-white">
            {song.songTitle}
          </div>
          <div className="w-24 px-4 overflow-hidden text-sm text-gray-400 max-w-24 whitespace-nowrap text-overflow-ellipsis group-hover:text-white">
            {song.songArtist}
          </div>
        </div>
      ))}
    </div>
  );
};

export default MiniSongListComponent;
