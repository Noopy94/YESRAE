
interface ISongInfoProps {
    songinfo: {
      id : string,
      title : string,
      similarity : number,
      rank: number,
      album_img : string,
      answer : boolean
    }[];
}

export default function SongInfo({ songinfo }: ISongInfoProps) {
    let index = 1;
    return (

        songinfo.map((item, idx) => {
  
          return (
            <div key={idx} className="flex items-center mb-10">
              <div className="flex items-center justify-center w-1/5 mx-16">#{index++}</div>
              <img src={item.album_img} alt="앨범 아트" className="w-1/5 mx-8 " />
              <div className="flex items-center justify-center w-1/5 mx-16">{item.title}</div>
              {item.rank == 1 ? (
                  <div className="flex items-center justify-center w-1/5 mx-16">{Math.floor(item.similarity)} %</div>
                ): (
                  <div className="flex items-center justify-center w-1/5 mx-16">{item.similarity.toFixed(2)} %</div>
                )}
              
              {item.answer === true? (
                <div className="flex items-center justify-center w-1/5 mx-16">
                  <span>정답 !</span>
                </div>
              ) : (
                <div className="flex w-1/5 mx-16">
                  <div className="flex items-center justify-center mb-2 text-center">{item.rank !== null ? item.rank : "순위밖"}</div>
                  <div className="h-full bg-white">
                    <div
                      className="h-5 bg-gradient-to-r from-yesrae-0 to-yesrae-100"
                      style={{ width: `${item.similarity}%` }}
                    ></div>
                  </div>
                </div>
              )}
            </div>
          );
        })

    );
}
  
  
  
  
  