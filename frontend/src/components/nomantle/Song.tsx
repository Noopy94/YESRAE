

interface SongInfoItemProps{
    songInfoItem : {
        index : number;
        id : string;
        title : string;
        similarity : number;
        rank: number;
        album_img : string;
        answer : boolean;
    }
}

export default function Song({songInfoItem} : SongInfoItemProps){


    return(
        <div key={songInfoItem.index} className="flex items-center mb-10 text-lg">
        <div className="flex items-center justify-center w-1/5 mx-16">#{songInfoItem.index}</div>
        <div className='w-1/5 mx-16'>
            <img src={songInfoItem.album_img} alt="앨범 아트" className="" />
        </div>
        <div className="flex items-center justify-center w-1/5 mx-16">{songInfoItem.title}</div>
        {songInfoItem.rank == 1 ? (
            <div className="flex items-center justify-center w-1/5 mx-16">{songInfoItem.similarity ? Math.floor(songInfoItem.similarity) : 'N/A'} %</div>
          ): (
            <div className="flex items-center justify-center w-1/5 mx-16">{songInfoItem.similarity ? songInfoItem.similarity.toFixed(2) : 'N/A'} %</div>
          )}
        
        {songInfoItem.answer === true? (
          <div className="flex items-center justify-center w-1/5 mx-16">
            <span>정답 !</span>
          </div>
        ) : (
          <div className="flex w-1/5 mx-16">
            <div className="flex items-center justify-center mb-2 text-center">{songInfoItem.rank !== null ? songInfoItem.rank : "순위밖"}</div>
            <div className="h-full bg-white">
              <div
                className={`h-5 bg-gradient-to-r from-yesrae-0 to-yesrae-100 w-[${songInfoItem.similarity}]`}
                style={{ width: `${songInfoItem.similarity}%` }}
              ></div>
            </div>
          </div>
        )}
      </div>
    )
}