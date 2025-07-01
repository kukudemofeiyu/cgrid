// An highlighted block
import { useState, useEffect } from 'react';
const useWindowSize = () => {
  // 第一步：声明能够体现视口大小变化的状态
  const [windowSize, setWindowSize] = useState({
    width: window.innerWidth,
    height: window.innerHeight,
    size: 'xxl',
  });

  // 第二步：通过生命周期 Hook 声明回调的绑定和解绑逻辑
  useEffect(() => {
    const updateSize = () => {
      const width = window.innerWidth;
      const height = window.innerHeight;
      let size = 'xxl';
      if (width < 576) size = 'xs';
      if (width >= 576) size = 'sm';
      if (width >= 768) size = 'md';
      if (width >= 992) size = 'lg';
      if (width >= 1200) size = 'xl';
      if (width >= 1600) size = 'xxl';
      setWindowSize({
        width,
        height,
        size,
      });
    };
    window.addEventListener('resize', updateSize);
    return () => window.removeEventListener('resize', updateSize);
  }, []);

  return windowSize;
};
export default useWindowSize;
