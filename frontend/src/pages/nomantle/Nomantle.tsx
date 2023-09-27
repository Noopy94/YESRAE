import NomantleLogo from '../../assets/nomantle_logo.svg';
import QuestionLogo from '../../assets//nomantle_question.svg';
import { useEffect, useState } from "react";
import Category from "../../components/nomantle/Category";
import SongInfo from '../../components/nomantle/SongInfo';
import { searchSong, getSongResult } from '../../api/nomantle';
import { json } from 'react-router-dom';
import Song from '../../components/nomantle/Song';

interface Title{
  title : string;
}

interface SongInfo{
  index : number;
  id : string;
	title : string;
	similarity : number;
	rank: number;
	album_img : string;
	answer : boolean;
}

export default function Nomantle(){

  // input -> 연관된 노래들 input 태그 밑에 보여주기
  const [inputValue, setInputValue] = useState('');

  // 해당 노래와 연관된 제목의 노래 input 밑에 연관 검색어로 보여주기
  const [titleList, setTitleList] = useState<Title[]>([]);

  // hover 시 노맨틀 페이지에 대한 설명 보여주기 
  const [isHovered, setisHovered] = useState(false);

  // 입력이 존재하지 않을 경우
  const [errorMsg, setErrorMsg] = useState('');


  // TODO : 정답일 경우 모달 보여주기 및 순위 보기 버튼으로 변경
  //const [isAnswer, setAnswer] = useState(false);

  // TODO : 포기하기 모달 -> 버튼 위치 변경되는 문제
  const [isModalOpen, setIsModalOpen] = useState(false);


  // TODO : 안녕 입력 후 클릭했을 때 errorMsg 가 뜨는 문제!! reponse 제대로 받았는데 없다고 뜨는 문제!!!!!!
  const [songInfoLocalStorage, setSongInfoLocalStorage] = useState<SongInfo[]>([]);


  // input 입력시 목록 관련
  // input 에 해당하는 검색이 없을 경우 errorMsg 보여주기
  useEffect(() => {
    
    if(inputValue){
      handleInput(inputValue);
    }else{
      setTitleList([]);
    }
  }, [inputValue, errorMsg]);

  // input 에 입력 변경
  const onChangeSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(event.target.value);
  };

  const handleInput = async (song_name : string) => {
    //input 값에 따라 연관된 검색어 밑에 보여주기
    try{
      const data = await searchSong(song_name);

      setTitleList(data.song);
      console.log("제목 : ", titleList);
    }catch(error){
      console.error('API 요청 중 오류 발생:', error);
    }
    
  };

  // enter 입력
  const onHandleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      handleInput(inputValue);
    }
  };

  // list 에 해당하는 item 클릭
  const handleListItemClick = async (song_name : string) => {
    setErrorMsg('');
    setInputValue(song_name);
    await handleGuess(inputValue);
  };


  // ? 호버시 노맨틀 설명 보여주기
  const handleMouseEnter = () => {
    setisHovered(true)
  }
  // ? 호버 해제시 노맨틀 설명 보여주지 X
  const handleMouseLeave = () => {
    setisHovered(false)
  }

  const handleGuess = async (song_name : string) => {
    try {
      const data = await getSongResult(song_name);

      console.log("검색 결과 데이터입니다", data);

      if (Array.isArray(data) && data.length > 0) {
        // data 중에서 similarity 가 가장 높은 데이터 찾기
          const maxSimilarityItem = data.reduce((maxItem, currentItem) => {
            return currentItem.similarity > maxItem.similarity ? currentItem : maxItem;
          });
          
          // localStorage 에서 꺼내오기
          const storedData = localStorage.getItem('song');
          let localStorageData : SongInfo[]= storedData ? JSON.parse(storedData) : [];

          // 예전에 검색한 건지 확인하기
          const existingItemIndex = localStorageData.findIndex(
            (item) => item.id === maxSimilarityItem.id
          )
          // 로컬스토리지에 저장되어 있지 않은 경우
          if(existingItemIndex !== -1){
            const existingItem = localStorageData[existingItemIndex];
            localStorageData.splice(existingItemIndex, 1);
            localStorageData.unshift(existingItem)
          }else{
            // 최신이어서 가장 앞에 추가
            localStorageData.unshift(maxSimilarityItem);
          }
          // 로컬 스토리지 저장
        localStorage.setItem('song', JSON.stringify(localStorageData));

        setSongInfoLocalStorage(localStorageData);

        // input 목록 지우기
        setTitleList([]);

        setErrorMsg('');
      } else {
        setErrorMsg(`${song_name}은 존재하지 않는 곡 제목입니다.`);
      }
    } catch (error) {
      console.error('API 요청 중 오류 발생:', error);
    }
  }

  const handleButtonClick = () =>{
    const song_title = document.getElementById("song_title_input") as HTMLInputElement;

    handleGuess(song_title.value);
  }

  // 엔터 입력 시
  const handleInputKeyPress = (event : React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      // Enter 키를 눌렀을 때 handleButtonClick 함수 호출
      handleButtonClick();
    }
  };

  // 모달 열기
  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  // TODO localStorage 에 검색 목록 , 정답 여부 저장
  

    useEffect(() => {
      // localstorage 에 저장되어 있는 데이터 가져오기
      const storedData = localStorage.getItem('song'); 

      if (storedData) {
        const data :SongInfo[] = JSON.parse(storedData);

          data.sort((a, b) => {
            if(a === data[0]) return -1;
            if(b === data[0]) return 1;
            return b.similarity - a.similarity;
          })
 
        setSongInfoLocalStorage(data);
      }else{

        setSongInfoLocalStorage([]);
      }
    }, []);


  const category = ["#", "앨범 아트", "추측한 노래", "유사도", "유사도 순위"];

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
                <p className='m-8 text-center text-black'>노맨틀은 오늘의 노래를 맞히는 게임입니다. <br/>
                정답 노래를 추측하면 정답 노래와 얼마나 유사한지 유사도를 알려줍니다. <br/> 
                정답 노래를 맞혀보세요.</p>
              </div>
            )}
          </div>
        </div>
          <div className="flex mt-20">
            <div className='flex flex-col items-start'>
            <input
                  id = "song_title_input"
                  type= "text"
                  className="h-12 text-lg text-white rounded-xl w-96 pl-11 bg-yesrae-900 "
                  placeholder="추측할 노래 제목을 입력하세요"
                  onChange={onChangeSearch}
                  onKeyDown={onHandleKeyDown}
                  onKeyPress={handleInputKeyPress}
                  value = {inputValue}
              />
              {titleList.length > 0 && (
                <ul>
                {titleList.map((song, idx) => (
                  <li key={idx} className='p-2 border border-gray-300 rounded-sm w-96 hover:bg-gray-800' onClick={() => handleListItemClick(song.title)}>{song.title}</li>                 
                ))}
                </ul>
              )}
            </div>
            
            <button type="button" className="flex items-center justify-center w-24 h-12 mx-28 rounded-xl bg-yesrae-900 hover:bg-gray-800" onClick={handleButtonClick}>
                추측하기
            </button>
          </div>
          <div className='w-4/6'>
            {errorMsg && <p className='mt-10 text-center text-yesrae-0'>{errorMsg}</p>}
            <div className="mt-24">
              <Category categories={category}/>
              <hr/>
            </div>
            <div className="mt-8">
              {songInfoLocalStorage.length > 0 && <SongInfo song = {songInfoLocalStorage} />}
            </div>
          </div>
          <div>
              <button type="button" className="flex items-center justify-center w-24 h-12 my-28 rounded-xl bg-yesrae-900 hover:bg-gray-800" onClick={openModal}>
                    포기하기
              </button>
              {isModalOpen && (
                <div className="modal">
                  <div className="modal-content">
                    <span className="close" onClick={closeModal}>&times;</span>
                    <div>
                      <p>순위를 보시겠습니까? </p>
                      <button type="button" className="flex items-center justify-center w-24 h-12 my-28 rounded-xl bg-yesrae-900 hover:bg-gray-800" >
                            순위보기 
                      </button>
                    </div>
                  </div>
                </div>
              )}
          </div>
        </div>
      );
}