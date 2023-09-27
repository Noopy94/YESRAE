import { useState } from 'react';
import { Button, Card, Input, Typography } from '@material-tailwind/react';
import { useNavigate } from 'react-router-dom';
import { emailCheck, nicknameCheck, regist } from '../../api/userApi';

const Regist = () => {
  const [email, setEmail] = useState('');
  const [nickname, setNickname] = useState('');
  const [password, setPassword] = useState('');
  const [age, setAge] = useState(0);

  const [isEmailChecked, setIsEmailChecked] = useState(false);
  const [isNicknameChecked, setIsNickNameChecked] = useState(false);

  const navigate = useNavigate();

  const handleEmailCheck = async () => {
    const result = await emailCheck(email);
    if (result.flag) {
      setIsEmailChecked(true);
      alert('사용 가능한 이메일입니다.');
    } else {
      alert('중복된 이메일입니다.');
    }
  };

  const handleNicknameCheck = async () => {
    const result = await nicknameCheck(nickname);
    if (result.flag) {
      setIsNickNameChecked(true);
      alert('사용 가능한 닉네임입니다.');
    } else {
      alert('중복된 닉네임입니다.');
    }
  };

  const handleRegist = async () => {
    await regist(email, nickname, password, age);

    navigate('/user/login');
  };

  return (
    <div className="flex justify-center w-screen my-36">
      <Card color="transparent" shadow={false}>
        <Typography variant="h4" color="white" className="font-[pretendard]">
          회원가입
        </Typography>
        <Typography className="mt-1 font-normal text-gray-500 font-[pretendard]">
          정보를 입력해주세요
        </Typography>
        <form className="max-w-screen-lg mt-8 mb-2 w-80 sm:w-96">
          <div className="flex flex-col gap-6 mb-4">
            <div className="relative flex">
              <Input
                type="email"
                size="lg"
                label="이메일"
                crossOrigin={null}
                color="white"
                onChange={(e) => {
                  setEmail(e.target.value);
                }}
                className="pr-20 h-max"
                containerProps={{
                  className: 'min-w-0',
                }}
              />
              <Button
                size="sm"
                className="!absolute right-2 top-[0.42rem] rounded"
                disabled={!email || isEmailChecked}
                onClick={handleEmailCheck}
              >
                {isEmailChecked ? '사용 가능' : '중복 확인'}
              </Button>
            </div>

            <div className="relative flex">
              <Input
                size="lg"
                label="닉네임"
                crossOrigin={null}
                color="white"
                onChange={(e) => {
                  setNickname(e.target.value);
                }}
              />
              <Button
                size="sm"
                className="!absolute right-2 top-[0.42rem] rounded"
                disabled={!nickname || isNicknameChecked}
                onClick={handleNicknameCheck}
              >
                {isNicknameChecked ? '사용 가능' : '중복 확인'}
              </Button>
            </div>

            <Input
              type="password"
              size="lg"
              label="비밀번호"
              crossOrigin={null}
              color="white"
              onChange={(e) => {
                setPassword(e.target.value);
              }}
            />
            <Input
              type="number"
              size="lg"
              label="나이"
              crossOrigin={null}
              color="white"
              onChange={(e) => {
                setAge(Number(e.target.value));
              }}
            />
          </div>
          <Button
            className="mt-6 text-sm font-[pretendard] hover:shadow-gray-800 hover:shadow-md"
            size="sm"
            fullWidth
            onClick={handleRegist}
            disabled={!isEmailChecked || !isNicknameChecked}
          >
            {!isEmailChecked || !isNicknameChecked
              ? '중복 검사를 해주세요'
              : '등록'}
          </Button>
        </form>
      </Card>
    </div>
  );
};
export default Regist;
