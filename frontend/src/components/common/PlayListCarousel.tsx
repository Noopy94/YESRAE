import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowLeft, faArrowRight } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';
import { PlayList } from '../../recoil/defaultdata/data';

interface playListProps {
  playLists: PlayList[];
}

const PlayListCarousel: React.FC<playListProps> = ({ playLists }) => {
  const [currentGroup, setCurrentGroup] = useState(0);

  const slidesPerGroup = 4;
  const groupCount = Math.ceil(playLists.length / slidesPerGroup);

  const nextGroup = () => {
    setCurrentGroup((prevGroup) => (prevGroup + 1) % groupCount);
  };

  const prevGroup = () => {
    setCurrentGroup((prevGroup) => (prevGroup - 1) % groupCount);
  };

  const startIndex = currentGroup * slidesPerGroup;
  const endIndex = startIndex + slidesPerGroup;
  let leftButton = null;
  let rightButton = null;

  if (currentGroup !== 0) {
    leftButton = (
      <button
        onClick={prevGroup}
        className="absolute left-0 z-10 w-8 h-8 text-white transform -translate-y-1/2 bg-gray-900 rounded-full top-1/2"
      >
        <FontAwesomeIcon icon={faArrowLeft} />
      </button>
    );
  }

  if (currentGroup !== groupCount - 1) {
    rightButton = (
      <button
        onClick={nextGroup}
        className="absolute right-0 z-10 w-8 h-8 text-white transform -translate-y-1/2 bg-gray-900 rounded-full top-1/2"
      >
        <FontAwesomeIcon icon={faArrowRight} />
      </button>
    );
  }

  return (
    <div className="song-carousel">
      <div className="relative flex carousel-container">
        {leftButton}
        <div className="flex items-center w-full">
          {playLists.slice(startIndex, endIndex).map((playList) => (
            <div
              key={playList.playListId}
              className="relative px-3 carousel-slide"
            >
              <Link to={`/playlistdetail/${playList.playListId}`}>
                <img
                  src={
                    playList.playListImageUrl
                      ? playList.playListImageUrl
                      : '/src/assets/defaultUserImg.png'
                  }
                  alt={playList.playListTitle}
                  className="w-64 h-64"
                />
              </Link>
              <h2 className="w-64 overflow-hidden text-lg font-semibold overflow-ellipsis whitespace-nowrap">
                {playList.playListTitle}
              </h2>
              <p className="text-gray-400">{playList.playListUserNickName}</p>
            </div>
          ))}
        </div>
        {rightButton}
      </div>
    </div>
  );
};

export default PlayListCarousel;
