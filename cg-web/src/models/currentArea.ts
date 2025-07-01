/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-12 16:30:57
 * @LastEditTime: 2025-02-12 16:30:58
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/models/currentArea.ts
 */
import { useCallback, useState } from 'react';
import { AreaType } from './area';

const useCurrentAreaModel = () => {
  const [currentArea, changeCurrentArea] = useState<AreaType | null>(null);

  const change = useCallback((area: AreaType | null) => {
    changeCurrentArea(area);
  }, []);

  return {
    currentArea,
    change,
  };
};

export default useCurrentAreaModel;
