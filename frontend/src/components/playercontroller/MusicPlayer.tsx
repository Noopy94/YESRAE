import React, { useState, useRef, useEffect } from 'react';
import { Howl, Howler } from 'howler';

const MusicPlayer: React.FC = () => {
  const [sound, setSound] = useState<Howl | null>(null);
  const [isPlaying, setIsPlaying] = useState<boolean>(false);
  const [currentTime, setCurrentTime] = useState<number>(0); // 현재 시간을 저장하는 상태
  const soundRef = useRef<Howl | null>(null);

  //api로 song을 가져오는데 song의 id, title, artist, imgurl, songurl 가져와야함

  // 해당 유저가 이 곡을 like를 했는지 아닌지 판단하는 로직 필요

  // 음악 파일의 URL
  const musicUrl =
    'https://p.scdn.co/mp3-preview/ab879698733f3b9ff65a70a4be0b60b92b94bb59?cid=b76e1a72191a49e1bd4cc3b5aaa2511b.mp3';

  const playMusic = () => {
    if (!soundRef.current) {
      // Howler로 음악 파일을 로드합니다.
      const newSound = new Howl({
        src: [musicUrl],
        onend: () => {
          // 음악 재생이 끝나면 실행할 작업을 여기에 추가하세요.
        },
        onplay: () => {
          // 음악 재생이 시작될 때 현재 시간을 업데이트합니다.
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

  useEffect(() => {
    const intervalId = setInterval(updateCurrentTime, 100);
    return () => clearInterval(intervalId);
  }, []);

  return (
    <div className="fixed bottom-0 left-0 flex w-full p-2 bg-gray-900 shadow-md ">
      <img
        src="https://i.scdn.co/image/ab67616d0000b273034c3a8ba89c6a5ecfda3175"
        className="w-12 h-12"
      ></img>
      <div className="px-4">
        <div>노래 제목</div>
        <div className="text-sm">가수 이름</div>
      </div>
      <button
        className={`bg-${
          isPlaying ? 'red-500' : 'blue-500'
        } text-white px-4 py-2 rounded cursor-pointer`}
        onClick={playMusic}
      >
        {isPlaying ? 'Pause' : 'Play'}
      </button>
      <div className="mx-12 my-auto text-center">
        Current Time: {currentTime.toFixed(1)} seconds
      </div>
    </div>
  );
};

export default MusicPlayer;
