import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { userState } from '../../recoil/user/user';
import { useSetRecoilState } from 'recoil';

const KakaoRedirect = () => {
  const navigate = useNavigate();
  const setUser = useSetRecoilState(userState);

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    const accessToken = searchParams.get('accessToken');
    const refreshToken = searchParams.get('refreshToken');
    const user = {
      accessToken: accessToken ?? '',
      refreshToken: refreshToken ?? '',
    };
    setUser(user);
    navigate('/');
  }, []);

  return <></>;
};
export default KakaoRedirect;
