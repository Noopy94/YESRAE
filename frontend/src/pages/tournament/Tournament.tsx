import { Link } from 'react-router-dom';
import HeadPhone from '../../assets/headphone_icon.svg';
import Mike from '../../assets/mike_icon.svg';
import Note from '../../assets/note_icon.svg';
import Play from '../../assets/play_icon.svg';
import { useEffect, useState } from 'react';

const Tournament = () => {
  // 버튼 클릭시 모달
  const [isModalOpen, setIsModalOpen] = useState(false);

  const [selectedValue, setSelectedValue] = useState('');

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const openModal = () => {
    setIsModalOpen(true);
  };

  const handleSelectChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedValue(event.target.value);
  };

  // 몇강인지 선택해서 보내기

  const category = [4, 8, 16, 32, 64];

  return (
    <div className="h-full mb-10">
      <div className="mt-20 mb-20">
        <div className="flex items-center justify-center text-6xl font-bold mb-30">
          이상형 월드컵
        </div>
      </div>

      <div className="flex justify-center m-10 mb-10 text-6xl mt-30">
        <div className="mx-10 mt-20">
          <img src={HeadPhone} alt="HeadPhone" className="flex" />
        </div>
        <div className="mx-10 mt-25">
          <img src={Mike} alt="Mike" className="flex" />
        </div>
        <div className="mx-10 mt-44">
          <img src={Note} alt="Note" className="flex" />
        </div>
        <div className="mx-10 mt-10">
          <img src={Play} alt="Play" className="flex " />
        </div>
      </div>
      <div className="mt-40 mb-30">
        <div className="flex justify-center w-screen mt-10 mb-20 text-2xl">
          이상형 월드컵 지금 시작해보세요!
        </div>
        <div className="flex items-center justify-center">
          <button
            className="h-12 text-xl w-36 mx-28 rounded-xl bg-yesrae-900 hover:bg-gray-800"
            onClick={openModal}
          >
            시작
          </button>
        </div>
        {isModalOpen && (
          <div className="fixed top-0 bottom-0 left-0 right-0 flex flex-col items-center justify-center w-1/3 m-auto bg-gray-900 border border-gray-700 rounded-lg modal h-2/4 ">
            <div className="modal-content ">
              <span
                className="absolute top-0 right-0 p-4 text-lg cursor-pointer close"
                onClick={closeModal}
              >
                &times;
              </span>

              <div className="flex flex-col items-center h-full">
                <p className="mt-4 mb-10 text-xl">몇 강을 할지 선택해주세요!</p>
                <select
                  className="h-12 pl-4 mb-10 rounded-md w-36 text-yesrae-900 "
                  onChange={handleSelectChange}
                  value={selectedValue}
                >
                  {category.map((item, index) => (
                    <option key={index} value={item.toString()}>
                      {item}
                    </option>
                  ))}
                </select>
                <Link to={`/tournament?category=${selectedValue}`}>
                  <button className="w-24 h-12 text-xl mx-28 rounded-xl bg-yesrae-900 hover:bg-gray-800">
                    선택
                  </button>
                </Link>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Tournament;
