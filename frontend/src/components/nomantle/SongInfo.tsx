
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

        songinfo.map((item, idx) => {
          let percent: number = ((item.rank || 0) / 10) * 100;
          console.log(percent)
  
          return (
            <div key={idx} className="flex mb-10">
              <div className="flex items-center justify-center w-1/5 mx-16">#{item.index}</div>
              <img src={item.album_img} alt="앨범 아트" className="w-1/5 mx-8 " />
              <div className="flex items-center justify-center w-1/5 mx-16">{item.title}</div>
              <div className="flex items-center justify-center w-1/5 mx-16">{item.similarity} %</div>
              {item.rank === 1 ? (
                <div className="flex items-center justify-center w-1/5 mx-16">
                  <span>정답 !</span>
                </div>
                
              ) : (
                <div className="flex w-1/5 mx-16">
                  <div className="flex items-center justify-center mb-2 text-center">{item.rank}</div>
                  <div className="h-5 bg-white">
                    <div
                      className="h-5 bg-gradient-to-r from-yesrae-0 to-yesrae-100"
                      style={{ width: `${percent}%` }}
                    ></div>
                  </div>
                </div>
              )}
            </div>
          );
        })

    );
}
  
  
  
  
  