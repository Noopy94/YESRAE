import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { userState } from '../../recoil/user/user';
import { useSetRecoilState } from 'recoil';
import { login } from '../../api/userApi';

const LoginRedirect = () => {
  const navigate = useNavigate();
  const setUser = useSetRecoilState(userState);

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    const accessToken = searchParams.get('accessToken') ?? '';
    // const refreshToken = searchParams.get('refreshToken');

    const getUser = async (accessToken: string) => {
      const user = await login(accessToken);

      setUser({ ...user });

      navigate('/');
    };

    getUser(accessToken);
  }, []);

  return <></>;
};
export default LoginRedirect;
