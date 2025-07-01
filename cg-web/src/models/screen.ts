import { useState } from 'react';

const useScreenModel = () => {
  const [outlined, setOutlined] = useState<boolean>(false);
  return {
    outlined,
    setOutlined,
  };
};

export default useScreenModel;
