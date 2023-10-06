import Category from '../../components/nomantle/Category';
import RankingResultInfo from '../../components/tournament/RankingInfo';
// import { useEffect, useState } from 'react';
// import { getTournamentRanking } from '../../api/tournamentApi';

export default function TournamentRanking() {
  const category = ['순위', '앨범 아트', '제목', '가수', '우승 비율'];

  // const [info, setInfo] = useState([]);

  // useEffect(() => {
  //   const getRanking = async () => {
  //     const response = await getTournamentRanking();
  //     setInfo(response);
  //   };
  //   getRanking();
  // }, []);

  // 더미 데이터 -> 이후에 api 연결 필요
  const info = [
    {
      rank: 1,
      imgUrl:
        'https://i.scdn.co/image/ab67616d0000b273633a2d775747bccfbcb17a45',
      title: '1등 제목',
      singer: 'charlie puth',
      proportion: 50,
    },
    {
      rank: 2,
      imgUrl:
        'https://i.scdn.co/image/ab67616d0000b2730744690248ef3ba7b776ea7b',
      title: '2등 제목',
      singer: 'new jeans',
      proportion: 46,
    },
    {
      rank: 3,
      imgUrl:
        'https://i.scdn.co/image/ab67616d0000b273bdea30b86b37142ec99deb78',
      title: '3등 제목',
      singer: 'test',
      proportion: 30,
    },
    {
      rank: 4,
      imgUrl:
        'https://i.scdn.co/image/ab67616d0000b27330623d491400e1516d3fca59',
      title: '4등 제목',
      singer: '윤종신',
      proportion: 30,
    },
    {
      rank: 5,
      imgUrl:
        'https://i.scdn.co/image/ab67616d0000b2732918f236448bf544586e388a',
      title: '5등 제목',
      singer: '윤하',
      proportion: 30,
    },
  ];

  return (
    <div className="w-full h-full ">
      <div className="flex items-center justify-center mt-20 text-4xl font-bold">
        랭킹
      </div>
      <div className="flex items-center justify-center mt-12 text-2xl ">
        대중적인 취향을 나타내는 곡입니다.
      </div>
      <div className="flex justify-center mt-24">
        <Category categories={category} />
      </div>
      <div className="flex justify-center">
        <hr className="w-10/12" />
      </div>
      <div className="flex justify-center mt-24 ">
        <div className="w-10/12">
          {info.map((item, idx) => {
            return <RankingResultInfo ranking={item} />;
          })}
        </div>
      </div>
    </div>
  );
}
