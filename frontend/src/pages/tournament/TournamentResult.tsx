import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import SongResultInfo from '../../components/tournament/SongResultInfo';
import Trophy from '../../assets/trophy.svg';
import { tournamentResult } from '../../recoil/tournament/tournamentResult';
import { useRecoilValue } from 'recoil';
import { getTournamentResult } from '../../api/tournamentApi';

export default function TournamentResult() {
  const result = useRecoilValue(tournamentResult);

  const [resultInfo, setResultInfo] = useState([]);

  useEffect(() => {
    const getResult = async (result: {
      tournamentId: number;
      firstSongId: number;
      secondSongId: number;
      semiFinalSongOneId: number;
      semiFinalSongTwoId: number;
    }) => {
      const response = await getTournamentResult(result);
      setResultInfo(response);
    };
    getResult({
      tournamentId: result.tournamentId,
      firstSongId: result.firstSongId,
      secondSongId: result.secondSongId,
      semiFinalSongOneId: result.semiFinalSongOneId,
      semiFinalSongTwoId: result.semiFinalSongTwoId,
    });
  }, []);

  // 더미 데이터 -> 이후에 api 연결 필요
  // const resultInfo = [
  //   {
  //     songSinger: 'New Jeans',
  //     songTitle: 'Hype boy',
  //     ranking: 1,
  //     imgUrl:
  //       'https://i.scdn.co/image/ab67616d0000b2739d28fd01859073a3ae6ea209',
  //   },
  //   {
  //     songSinger: '태양',
  //     songTitle: 'shoong',
  //     ranking: 2,
  //     imgUrl:
  //       'https://i.scdn.co/image/ab67616d0000b2733e39c67040f721ffe568d448',
  //   },
  //   {
  //     songSinger: '아이유',
  //     songTitle: '내 손을 잡아',
  //     ranking: 3,
  //     imgUrl:
  //       'https://i.scdn.co/image/ab67616d0000b273f1efb467ac4c748630ffd22f',
  //   },
  //   {
  //     songSinger: 'Charlie Puth',
  //     songTitle: 'The Way I Am',
  //     ranking: 3,
  //     imgUrl:
  //       'https://i.scdn.co/image/ab67616d0000b273897f73256b9128a9d70eaf66',
  //   },
  // ];

  return (
    <div className="w-full h-full ">
      <div className="flex items-center justify-center mt-20 text-4xl font-bold">
        결과
      </div>
      <div className="flex items-center justify-center mt-12 text-2xl ">
        당신의 취향을 나타내는 곡입니다.
      </div>
      <div className="flex justify-center w-1/4">
        <img src={Trophy} alt="Trophy" className="flex justify-center px-16" />
      </div>
      <div className="flex justify-center">
        {resultInfo.map((item, idx) => {
          return <SongResultInfo contentInfo={item} key={idx} />;
        })}
      </div>
      <div className="flex justify-center mt-10">
        <Link to="/recommend">
          <button
            type="button"
            className="flex items-center justify-center h-12 text-xl w-36 mx-28 rounded-xl bg-yesrae-900 hover:bg-gray-800"
          >
            노래 추천 받기
          </button>
        </Link>
        <Link to="/tournament/ranking">
          <button
            type="button"
            className="flex items-center justify-center h-12 text-xl w-36 mx-28 rounded-xl bg-yesrae-900 hover:bg-gray-800"
          >
            랭킹 보기
          </button>
        </Link>
      </div>
    </div>
  );
}
