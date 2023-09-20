import NomantleLogo from '../../assets/nomantle_logo.svg';
import QuestionLogo from '../../assets//nomantle_question.svg';
import { useEffect, useState } from "react";
import Category from "../../components/nomantle/Category";
import SongInfo from '../../components/nomantle/SongInfo';


export default function Nomantle(){

  const [search, setSearch] = useState('');
  
  useEffect(() => {
    handleSearch();
  }, [search]);

  const onChangeSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearch(event.target.value);
  };

  const handleSearch = () => {
    //search 값에 따라 연관된 검색어 밑에 보여주기
    console.log('검색어:', search);
  };

  const onHandleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      handleSearch();
    }
  };


  const category = ["#", "앨범 아트", "추측한 노래", "유사도", "유사도 순위"];

  const song_info = [
    { index: 2, rank: 5, album_img: 'https://i.scdn.co/image/ab67616d0000b273da4d50ff045b2a463f56035a', title: '상남자', similarity: 90 }
  ]

    return (
        <div className="flex flex-col items-center justify-center h-full">
          <img src={NomantleLogo} alt="NOMANTLE LOGO" className="p-4 mt-16" />
          <div className="flex items-center mt-14">
            <div className="text-2xl font-bold text-yesrae-800">
              YESRAE의 노래 유사도 추측 게임
            </div>
            <img src={QuestionLogo} alt="" className="w-8 mx-8"/>
          </div>
          <div className="flex mt-20">
          <input
                type= "text"
                className="h-12 text-lg text-white rounded-xl w-96 pl-11 bg-yesrae-900"
                value= ""
                placeholder="추측할 노래 제목을 입력하세요"
                onChange={onChangeSearch}
                onKeyDown={onHandleKeyDown}
            />
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