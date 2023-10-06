interface ISongResultInfo {
  contentInfo: {
    songSinger: string;
    songTitle: string;
    ranking: number;
    imgUrl: string;
  };
}

export default function SongResultInfo({ contentInfo }: ISongResultInfo) {
  return (
    <div className="w-1/4 p-16 mt-10 text-2xl text-center">
      <img src={contentInfo.imgUrl} alt="" className="mb-8 rounded-lg" />
      <div className="mb-10 font-extrabold"># {contentInfo.ranking}</div>
      <div className="mb-10 font-bold">{contentInfo.songSinger}</div>
      <div className="mb-10">{contentInfo.songTitle}</div>
    </div>
  );
}
