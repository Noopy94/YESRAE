import axios from 'axios';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const KakaoRedirect = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    // const code = searchParams.get('code');
    // if (code) {
    //   handleOAuthKakao(code);
    // }
    const accessToken = searchParams.get('accessToken');
    if (accessToken) {
      localStorage.setItem('accessToken', accessToken);
    }
    navigate('/');
  }, []);

  return <></>;
};
export default KakaoRedirect;
