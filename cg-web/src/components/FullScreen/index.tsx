/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-10-19 11:29:17
 * @LastEditTime: 2023-10-19 11:44:19
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\pages\workbench\FullScreen.tsx
 */

import React, { useEffect } from 'react';
import { useFullScreen } from '@/hooks';
import { FullscreenOutlined, FullscreenExitOutlined } from '@ant-design/icons';
import { Button } from 'antd';

export type FullScreenType = {
  className?: string;
  target: React.MutableRefObject<null>;
  onChange?: (value: boolean) => void;
};

const FullScreen: React.FC<FullScreenType> = (props) => {
  const { className, target, onChange } = props;

  const [isFullScreen, { enterFullscreen, exitFullscreen }] = useFullScreen(target);

  useEffect(() => {
    onChange?.(isFullScreen);
  }, [isFullScreen]);

  return (
    <>
      <span className={className}>
        {isFullScreen ? (
          <Button icon={<FullscreenExitOutlined />} onClick={exitFullscreen} />
        ) : (
          <Button icon={<FullscreenOutlined />} onClick={enterFullscreen} />
        )}
      </span>
    </>
  );
};

export default FullScreen;
