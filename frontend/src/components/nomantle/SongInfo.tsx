
interface ISongInfoProps {
    songinfo: {
      index: number;
      rank: number;
      album_img: string;
      title: string;
      similarity: number;
    }[];
}

export default function SongInfo({ songinfo }: ISongInfoProps) {
    return (
      <div className="flex mb-7">
        {songinfo.map((item, idx) => {
          let percent: number = ((item.rank || 0) / 1000) * 100;
  
          return (
            <div key={idx} >
              <div className="mx-16">#{item.index}</div>
              <img src={item.album_img} alt="앨범 아트" className="w-20 mx-8" />
              <div className="mx-16">{item.title}</div>
              <div className="mx-16">{item.similarity}</div>
              {item.rank === 1 ? (
                <div className="mx-16">정답 !</div>
              ) : (
                <div className="flex mx-16">
                  <div>{item.rank}</div>
                  <div className="w-full h-2 bg-white">
                    <div
                      className="h-full bg-gradient-to-r from-yesrae-0 via-yesrae-100 to-yesrae-200"
                      style={{ width: `${percent}%` }}
                    ></div>
                  </div>
                </div>
              )}
            </div>
          );
        })}
      </div>
    );
}
  
  
  
  
  