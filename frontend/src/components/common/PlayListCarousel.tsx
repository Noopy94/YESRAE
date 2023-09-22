import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowLeft, faArrowRight } from '@fortawesome/free-solid-svg-icons';

interface playList {
  id: number;
  imageUrl: string;
  title: string;
  userNickName: string;
}

interface playListProps {
  playLists: playList[];
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
        className="absolute left-0 z-10 text-white transform -translate-y-1/2 bg-gray-900 rounded-full top-1/2"
      >
        <FontAwesomeIcon icon={faArrowLeft} />
      </button>
    );
  }

  if (currentGroup !== groupCount - 1) {
    rightButton = (
      <button
        onClick={nextGroup}
        className="absolute right-0 z-10 text-white transform -translate-y-1/2 bg-gray-900 rounded-full top-1/2"
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
            <div key={playList.id} className="relative px-3 carousel-slide">
              <img
                src={playList.imageUrl}
                alt={playList.title}
                className="w-64 h-64"
              />
              <h2 className="text-xl font-semibold">{playList.title}</h2>
              <p className="text-gray-600">{playList.userNickName}</p>
            </div>
          ))}
        </div>
        {rightButton}
      </div>
    </div>
  );
};

export default PlayListCarousel;
