import KakaoLogo from '../../assets/kakao_logo.png';
import GoogleLogo from '../../assets/google_logo.png';
import { useState } from 'react';
import { login } from '../../api/userApi';
import { useSetRecoilState } from 'recoil';
import { userState } from '../../recoil/user/user';
import {
  Card,
  Input,
  Button,
  Typography,
  IconButton,
} from '@material-tailwind/react';
import { useNavigate } from 'react-router-dom';

const BASE_URL = 'http://localhost:8080/';

const Login = () => {
  const setUser = useSetRecoilState(userState);

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const navigate = useNavigate();

  const handleLogin = async () => {
    const user = await login(email, password);

    if (user) {
      setUser(user);

      navigate('/');
    }
  };

  const handleKakaoLogin = () => {
    window.location.href = BASE_URL + 'oauth2/authorization/kakao';
  };

  const handleGoogleLogin = () => {
    window.location.href = BASE_URL + 'oauth2/authorization/google';
  };

  return (
    <div className="flex justify-center w-screen my-36">
      <Card color="transparent" shadow={false}>
        <Typography variant="h4" color="white" className="font-[pretendard]">
          로그인
        </Typography>
        <Typography className="mt-1 font-normal text-gray-500 font-[pretendard]">
          정보를 입력해주세요
        </Typography>
        <form className="max-w-screen-lg mt-8 mb-2 w-80 sm:w-96">
          <div className="flex flex-col gap-6 mb-4">
            <Input
              size="lg"
              label="이메일"
              crossOrigin={null}
              color="white"
              onChange={(e) => {
                setEmail(e.target.value);
              }}
            />
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
          </div>
          <Button
            className="mt-6 text-sm font-[pretendard]"
            size="sm"
            fullWidth
            onClick={handleLogin}
          >
            로그인
          </Button>
          <div className="flex items-center mt-2">
            <hr className="flex-auto h-px bg-white border-none"></hr>
            <span className="p-3 text-white">OR</span>
            <hr className="flex-auto h-px bg-white border-none"></hr>
          </div>
          <div className="flex justify-center">
            <div className="flex gap-6">
              <IconButton
                size="lg"
                className="rounded bg-[#fae100] hover:shadow-[#ffffff]/20 focus:shadow-[#ffffff]/20 active:shadow-[#ffffff]/10"
                onClick={handleKakaoLogin}
              >
                <img src={KakaoLogo} />
              </IconButton>
              <IconButton
                size="lg"
                className="rounded bg-[#ffffff] hover:shadow-[#ffffff]/20 focus:shadow-[#ffffff]/20 active:shadow-[#ffffff]/10"
                onClick={handleGoogleLogin}
              >
                <img src={GoogleLogo} />
              </IconButton>
            </div>
          </div>
          <Typography className="mt-4 font-normal text-center text-gray-500 font-[pretendard]">
            계정이 없으신가요?{' '}
            <a href="#" className="font-medium text-white">
              회원가입
            </a>
          </Typography>
        </form>
      </Card>
    </div>
  );
};
export default Login;
