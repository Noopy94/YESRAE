import NomantleLogo from '../../assets/nomantle_logo.svg';
import { useEffect, useState } from 'react';
import { getRank } from '../../api/nomantleApi';

interface IRankInfo {
  title: string;
  similarity: number;
  rank: number;
}

export default function NomantleRank() {
  const [data, setData] = useState<IRankInfo[]>([]); // 데이터를 저장할 상태

  useEffect(() => {
    const ranks = async () => {
      try {
        const rankData = await getRank();
        console.log('rankData ', rankData);
        setData(rankData.rank);
      } catch (error) {
        console.error('API 요청 중 오류 발생:', error);
      }
    };
    ranks();
  }, []);

  return (
    <div className="flex flex-col items-center justify-center h-full">
      <img src={NomantleLogo} alt="NOMANTLE LOGO" className="p-4 mt-16" />
      <div className="flex w-full h-20 mt-14">
        <div className="w-1/3"></div>
        <div className="flex items-center justify-center w-1/3 h-20 text-3xl font-bold text-yesrae-800">
          YESRAE의 오늘의 순위
        </div>
      </div>
      <div className="flex justify-center mt-20">
        <div className="w-4/6 p-10">
          {data.map((item, index) => (
            <div key={index} className="flex items-center w-full mt-5 text-xl">
              <div className="flex justify-center">
                <div className="flex flex-col items-start">
                  <div className="font-bold">{item.rank}</div>
                </div>

                <div className="flex ml-5 ">
                  <div className="mx-10">{item.title}</div>
                  <div>{item.similarity.toFixed(2)}</div>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
