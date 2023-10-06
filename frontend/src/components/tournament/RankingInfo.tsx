import { useEffect, useState } from 'react';

interface IRankingInfo {
  ranking: {
    rank: number;
    imgUrl: string;
    title: string;
    singer: string;
    proportion: number;
  };
}

export default function RankingResultInfo({ ranking }: IRankingInfo) {
  return (
    <div className="flex items-center mt-8 text-xl font-semibold text-center">
      <div className="w-1/5 ">{ranking.rank}</div>
      <div className="w-1/5">
        <img src={ranking.imgUrl} alt="" />
      </div>
      <div className="w-1/5 ">{ranking.title}</div>
      <div className="w-1/5">{ranking.singer}</div>
      <div className="w-1/5 ">
        <div className="mb-3 ">{ranking.proportion}%</div>
        <div className="flex justify-center">
          <div className="flex items-center w-4/6 h-full bg-white">
            <div
              className="flex items-center h-5 bg-gradient-to-r from-yesrae-0 to-yesrae-100"
              style={{ width: `${ranking.proportion}%` }}
            ></div>
          </div>
        </div>
      </div>
    </div>
  );
}
