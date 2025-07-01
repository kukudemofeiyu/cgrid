/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-10-19 10:59:47
 * @LastEditTime: 2023-10-19 11:00:00
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\hooks\useFullScreen.ts
 */
import { useFullscreen as aUseFullscreen, useEventListener } from 'ahooks';

export type useFullScreenType = typeof aUseFullscreen;

const useFullScreen: useFullScreenType = (target, options) => {
  const result = aUseFullscreen(target, options);

  useEventListener('keydown', (e) => {
    if (e.key == 'F11') {
      e.preventDefault();
      result?.[1]?.enterFullscreen?.();
    }
  });

  return result;
};

export default useFullScreen;
