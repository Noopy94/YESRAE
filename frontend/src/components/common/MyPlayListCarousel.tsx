import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowLeft, faArrowRight } from '@fortawesome/free-solid-svg-icons';
import { Link } from 'react-router-dom';
import { PlayList } from '../../recoil/defaultdata/data';

interface playListProps {
  playLists: PlayList[];
}

const MyPlayListCarousel: React.FC<playListProps> = ({ playLists }) => {
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
              className="relative px-4 carousel-slide"
            >
              <Link to={`/playlistdetail/${playList.playListId}`}>
                <div className="relative flex items-center justify-center w-64 h-64 group">
                  <img
                    src={playList.playListImageUrl}
                    alt={playList.playListTitle}
                    className="w-48 h-48 transition-opacity duration-200 rounded-3xl group-hover:opacity-40"
                  />
                  <div className="absolute z-20 flex items-center justify-center text-lg transition-opacity duration-200 opacity-0 inset-6 group-hover:opacity-100">
                    {playList.playListTitle}
                  </div>
                </div>
              </Link>
            </div>
          ))}
        </div>
        {rightButton}
      </div>
    </div>
  );
};

export default MyPlayListCarousel;
