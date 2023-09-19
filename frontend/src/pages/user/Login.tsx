import KakaoLogin from '../../assets/kakao_login.png';
import NaverLogin from '../../assets/naver_login.png';
import GoogleLogin from '../../assets/google_login.png';
import {
  Card,
  CardBody,
  CardHeader,
  Typography,
} from '@material-tailwind/react';
import { useNavigate } from 'react-router-dom';
// import { kakaoLogin } from '../../api/userApi';

const BASE_URL = 'http://localhost:8080/';

const Login = () => {
  const navigate = useNavigate();

  const handleKakaoLogin = () => {
    window.location.href = BASE_URL + 'oauth2/authorization/kakao';
  };
  const handleNaverLogin = () => {
    window.location.href = BASE_URL + 'oauth2/authorization/naver';
  };
  const handleGoogleLogin = () => {
    window.location.href = BASE_URL + 'oauth2/authorization/google';
  };

  return (
    <div className="flex justify-center w-screen my-36">
      <Card className="w-[30rem]">
        <CardHeader
          variant="gradient"
          color="gray"
          className="grid mb-4 h-28 place-items-center"
        >
          <Typography variant="h3" color="white">
            회원가입
          </Typography>
        </CardHeader>
        <CardBody>
          <div className="flex flex-col gap-2">
            <img
              className="cursor-pointer"
              src={KakaoLogin}
              onClick={handleKakaoLogin}
            />
            <img
              className="cursor-pointer"
              src={NaverLogin}
              onClick={handleNaverLogin}
            />
            <img
              className="cursor-pointer"
              src={GoogleLogin}
              onClick={handleGoogleLogin}
            />
          </div>
        </CardBody>
      </Card>
    </div>
  );
};
export default Login;
