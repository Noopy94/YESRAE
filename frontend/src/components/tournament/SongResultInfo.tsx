import { useEffect, useState } from 'react';

interface ISongResultInfo {
  contentInfo: {
    data: string;
    songTitle: string;
    ranking: number;
    album_img: string;
  };
}

export default function SongResultInfo({ contentInfo }: ISongResultInfo) {
  return (
    <div className="w-1/4 p-16 mt-10 text-2xl text-center">
      <img src={contentInfo.album_img} alt="" className="mb-8 rounded-lg" />
      <div className="mb-10 font-extrabold"># {contentInfo.ranking}</div>
      <div className="mb-10 font-bold">{contentInfo.data}</div>
      <div className="mb-10">{contentInfo.songTitle}</div>
    </div>
  );
}
