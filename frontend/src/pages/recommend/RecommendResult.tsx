import { useEffect, useState } from 'react';
import React from 'react';
import RecommendResultInfo from '../../components/recommend/RecommendResultInfo.tsx';
import { useRecoilValue } from 'recoil';
import { tournamentResult } from '../../recoil/tournament/tournamentResult';

import { getRecommendSong } from '../../api/recommendApi.ts';
import { Link } from 'react-router-dom';
import Loading from '../../components/recommend/Loading.tsx';

interface ISongData {
  title: string;
  singer: string;
  album_img: string;
  id: string;
}

export default function RecommendResult() {
  const result = useRecoilValue(tournamentResult);
  const [recommendData, setRecommendData] = useState<ISongData[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const recommendSong = async () => {
      try {
        const data = await getRecommendSong(result.songs);
        console.log(data);
        setRecommendData(data);
        setLoading(false);
      } catch (error) {
        console.error('API 요청 중 오류 발생:', error);
      }
    };

    recommendSong();
  }, []);

  return (
    <div className="w-full h-full">
      <div className="flex items-center justify-center mt-20 text-4xl font-bold mb-30">
        추천 노래
      </div>
      {loading ? (
        <Loading />
      ) : (
        <div className="mt-40">
          <div className="mt-30">
            {recommendData.map((item, idx) => {
              return (
                <RecommendResultInfo contentInfo={item} key={idx} idx={idx} />
              );
            })}
          </div>
          <div className="flex justify-center mb-20">
            <Link to="/">
              <button
                type="button"
                className="flex items-center justify-center w-40 h-12 text-xl mx-28 rounded-xl bg-yesrae-900 hover:bg-gray-800"
              >
                플레이리스트 보기
              </button>
            </Link>
          </div>
        </div>
      )}
    </div>
  );
}
