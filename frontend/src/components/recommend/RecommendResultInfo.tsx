import { useEffect, useState } from 'react';

interface IRecommendResultInfo {
  contentInfo: {
    id: string;
    singer: string;
    title: string;
    album_img: string;
  };
}

export default function RecommendResultInfo({
  contentInfo,
  idx,
}: IRecommendResultInfo & { idx: number }) {
  return (
    <div className="flex flex-col items-center justify-center mx-10 mb-20">
      <div className="mb-2 text-3xl font-bold text-yesrae-0">#{idx + 1}</div>
      <img src={contentInfo.album_img} className="mb-8 rounded-lg w-80" />
      <div className="mb-10 text-2xl font-bold ">{contentInfo.singer}</div>
      <div className="mb-10 text-2xl ">{contentInfo.title}</div>
    </div>
  );
}
