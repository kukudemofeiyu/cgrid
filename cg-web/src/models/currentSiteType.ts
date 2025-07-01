/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-09-11 14:51:59
 * @LastEditTime: 2024-09-11 15:03:46
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/models/currentSiteType.ts
 */

import { useCallback, useState } from 'react';

const useCurrentSiteTypeModel = () => {
  const [currentSiteType, changeCurrentSiteType] = useState('');
  const [currentSiteMonitor, changeCurrentSiteMonitor] = useState('');

  const change = useCallback((siteType: string, siteMonitor: string) => {
    changeCurrentSiteType(siteType);
    changeCurrentSiteMonitor(siteMonitor);
  }, []);

  return {
    currentSiteType,
    currentSiteMonitor,
    change,
  };
};

export default useCurrentSiteTypeModel;
