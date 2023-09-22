import NomantleLogo from '../../assets/nomantle_logo.svg';
import QuestionLogo from '../../assets//nomantle_question.svg';
import { useEffect, useState } from "react";
import Category from "../../components/nomantle/Category";
import SongInfo from '../../components/nomantle/SongInfo';
import { searchSong } from '../../api/nomantle';

interface Title{
  title : string;
}


export default function Nomantle(){

  // input -> 연관된 노래들 input 태그 밑에 보여주기
  const [inputValue, setInputValue] = useState('');
  // 해당 노래와 연관된 제목의 노래 input 밑에 연관 검색어로 보여주기
  const [titleList, setTitleList] = useState<Title[]>([]);
  // hover 시 노맨틀 페이지에 대한 설명 보여주기 
  const [isHovered, setisHovered] = useState(false);

  // TODO : 정답일 경우 모달 보여주기 및 순위 보기 버튼으로 변경
  const [isAnswer, setAnswer] = useState(false);

  // TODO : 추측하기 클릭시 유사도 결과 받기
  

  // TODO :  유사도 정보 보여줄때 가장 최근이 위로 오고
  // TODO : 그 밑에는 지금까지 정보 유사도 높은 순으로 정렬되어서 쌓기
  
  useEffect(() => {
    
    if(inputValue){
      handleInput(inputValue);
    }else{
      setTitleList([]);
    }
  }, [inputValue]);

  const onChangeSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(event.target.value);
  };

  const handleInput = async (input : string) => {
    //input 값에 따라 연관된 검색어 밑에 보여주기
    try{
      const data = await searchSong(input);
      setTitleList(data);
    }catch(error){
      console.error('API 요청 중 오류 발생:', error);
    }
    
  };

  const onHandleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      handleInput(inputValue);
    }
  };

  // ? 호버시 노맨틀 설명 보여주기
  const handleMouseEnter = () => {
    setisHovered(true)
  }
  // ? 호버 해제시 노맨틀 설명 보여주지 X
  const handleMouseLeave = () => {
    setisHovered(false)
  }


  const category = ["#", "앨범 아트", "추측한 노래", "유사도", "유사도 순위"];

  // 임시 데이터
  const song_info = [
    { index: 2, rank: 5, album_img: 'https://i.scdn.co/image/ab67616d0000b273da4d50ff045b2a463f56035a', title: '상남자', similarity: 90 },
    { index : 5, rank : 1, album_img : 'https://i.scdn.co/image/ab67616d0000b27367c2fc3f114b250085a4fab6', title : 'A', similarity : 100},
  ]

    return (
      <div className="flex flex-col items-center justify-center h-full">
        <img src={NomantleLogo} alt="NOMANTLE LOGO" className="p-4 mt-16" />
        <div className="flex w-full h-20 mt-14">
          <div className='w-1/3'></div>
          <div className="flex items-center justify-center w-1/3 h-20 text-2xl font-bold text-yesrae-800">
          YESRAE의 노래 유사도 추측 게임
          </div>
          <div className='flex items-center w-1/3 h-20'>
            
            <img src={QuestionLogo} alt="" className="w-8" onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave} />
            { isHovered && (
              <div className='w-1/2 mx-8 ml-4 bg-white rounded-md h-30'>
                <p className='m-8 text-black'>노맨틀은 오늘의 노래를 맞히는 게임입니다. <br/>
                정답 노래를 추측하면 정답 노래와 얼마나 유사한지 유사도를 알려줍니다. <br/> 
                정답 노래를 맞혀보세요.</p>
              </div>
            )}
          </div>
        </div>
          <div className="flex mt-20">
          <input
                type= "text"
                className="h-12 text-lg text-white rounded-xl w-96 pl-11 bg-yesrae-900"
                placeholder="추측할 노래 제목을 입력하세요"
                onChange={onChangeSearch}
                onKeyDown={onHandleKeyDown}
            />
            <ul>
              {titleList.map((song, idx) => (
                <li key={idx}>{song.title}</li>
              ))}
            </ul>
            <button type="button" className="flex items-center justify-center w-24 h-12 mx-28 rounded-xl bg-yesrae-900" >
                추측하기
            </button>
          </div>
          <div className="mt-24">
            <Category categories={category}/>
            <hr/>
          </div>
          <div className="mt-8">

            <SongInfo songinfo = {song_info} />
          </div>
          <button type="button" className="flex items-center justify-center w-24 h-12 my-28 rounded-xl bg-yesrae-900" >
                포기하기
          </button>
        </div>
      );
}