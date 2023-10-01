interface ISongInfoItemProps {
  songInfoItem: {
    index: number;
    id: string;
    title: string;
    similarity: number;
    rank: number;
    album_img: string;
    answer: boolean;
  };
}

export default function Song({ songInfoItem }: ISongInfoItemProps) {
  return (
    <div key={songInfoItem.index} className="flex items-center mb-10 text-lg">
      <div className="flex items-center justify-center w-1/5 mx-16 text-xl">
        #{songInfoItem.index}
      </div>
      <div className="w-1/5 mx-16">
        <img src={songInfoItem.album_img} alt="앨범 아트" className="" />
      </div>
      <div className="flex items-center justify-center w-1/5 mx-16 text-xl">
        {songInfoItem.title}
      </div>
      {songInfoItem.rank == 1 ? (
        <div className="flex items-center justify-center w-1/5 mx-16 text-xl">
          {songInfoItem.similarity
            ? Math.floor(songInfoItem.similarity)
            : 'N/A'}{' '}
          %
        </div>
      ) : (
        <div className="flex items-center justify-center w-1/5 mx-16 text-xl">
          {songInfoItem.similarity ? songInfoItem.similarity.toFixed(2) : 'N/A'}{' '}
          %
        </div>
      )}

      <div className="w-1/5 mx-16">
        <div className="mb-2 text-xl text-center">
          {songInfoItem.rank !== null
            ? songInfoItem.rank.toString() + '위'
            : '순위밖'}
        </div>
        <div className="flex justify-center">
          {songInfoItem.rank !== null ? (
            <div className="flex items-center w-10/12 h-full bg-white">
              <div
                className="flex items-center h-5 bg-gradient-to-r from-yesrae-0 to-yesrae-100"
                style={{ width: `${100 - songInfoItem.rank}%` }}
              ></div>
            </div>
          ) : null}
        </div>
      </div>
    </div>
  );
}
