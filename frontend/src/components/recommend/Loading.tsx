import { useEffect, useState } from 'react';
import { SyncLoader } from 'react-spinners';

interface IRecommendResultInfo {
  contentInfo: {
    id: string;
    singer: string;
    title: string;
    album_img: string;
  };
}

export default function Loading() {
  return (
    <div className="flex flex-col items-center justify-center mt-40 h-1/4">
      <div className="text-3xl font-bold h-30 white">
        추천 노래를 선별하고 있습니다. 잠시만 기다려 주세요
      </div>
      <SyncLoader color={'white'} className="mt-20" />
    </div>
  );
}
