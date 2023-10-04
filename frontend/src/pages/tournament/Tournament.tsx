import { Link } from 'react-router-dom';
import ButtonComponent from '../../components/common/ButtonComponent';

const Tournament = () => {
  return (
    <>
      <div className="flex justify-center w-screen mt-10 mb-3 text-6xl">
        이상형 월드컵
      </div>
      <div className="flex justify-center items-center w-screen h-[24rem] mt-10 mb-3 text-6xl">
        그림
      </div>
      <div>
        <div className="flex justify-center w-screen mt-10 mb-3 text-2xl">
          여기에는 이상형 월드컵에 관련된 설명이 들어갈 예정이랍니다.
        </div>
        <Link to="/">
          <ButtonComponent type="ismiddle">시작</ButtonComponent>
        </Link>
      </div>
    </>
  );
};

export default Tournament;
