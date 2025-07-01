import { useEffect, useRef } from 'react';
import lottie from 'lottie-web';
import { assign } from 'lodash';

interface LottieProps {
  animationData: any;
  width: number;
  height: number;
  path?: string;
  className?: string;
}

export const Lottie = ({ animationData, width, height, path, className }: LottieProps) => {
  const element = useRef<HTMLDivElement>(null);
  const lottieInstance = useRef<any>();

  useEffect(() => {
    if (element.current) {
      const config = {
        animationData,
        container: element.current,
        loop: true,
        autoplay: true,
      };
      if (path) {
        assign(config, { path });
      }
      lottieInstance.current = lottie.loadAnimation(config);
    }
    return () => {
      lottieInstance.current?.destroy();
    };
  }, [animationData, path]);

  return <div className={className} style={{ width, height }} ref={element} />;
};
