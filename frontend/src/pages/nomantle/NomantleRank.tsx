/*

import NomantleLogo from '../../assets/nomantle_logo.svg';
import { useEffect, useState } from "react";
import { getRank } from '../../api/nomantle';




export default function NomantleRank(){

    const [data, setData] = useState([]); // 데이터를 저장할 상태


    const ranks = async () => {
      //input 값에 따라 연관된 검색어 밑에 보여주기
      try{
        const rankData = await getRank();
    
          
        }catch(error){
          console.error('API 요청 중 오류 발생:', error);
        }
        
    };



    return (
      <div className="flex flex-col items-center justify-center h-full">
        <img src={NomantleLogo} alt="NOMANTLE LOGO" className="p-4 mt-16" />
        <div className="flex w-full h-20 mt-14">
          <div className='w-1/3'></div>
          <div className="flex items-center justify-center w-1/3 h-20 text-2xl font-bold text-yesrae-800">
          YESRAE의 노래 유사도 추측 게임
          </div>

          <div className="flex mt-20">
          {data.map((item, index) => (
                <div key={index}>
                <p>{item.someProperty}</p>
                </div>
            ))}
          </div>
                  
        </div>
      </div>
      );
}
*/