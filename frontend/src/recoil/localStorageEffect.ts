const localStorageEffect =
  (key: string) =>
  ({ setSelf, onSet }: any) => {
    const savedValue = localStorage.getItem(key);
    if (savedValue != null) {
      setSelf(JSON.parse(savedValue));
    } else {
      localStorage.removeItem(key);
      //   if (
      //     window.location.pathname !== '/user/login' &&
      //     window.location.pathname !== '/'
      //   ) {
      //     window.location.href = '/user/login';
      //   }
    }
    onSet((newValue: any, _: any, isReset: any) => {
      isReset
        ? localStorage.removeItem(key)
        : localStorage.setItem(key, JSON.stringify(newValue));
    });
  };

export default localStorageEffect;
