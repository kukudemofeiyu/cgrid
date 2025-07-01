/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-07 11:26:48
 * @LastEditTime: 2023-07-07 11:26:52
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\hooks\useLocation.ts
 */
import { useLocation as useUmiLocation } from 'umi';
import { LocationType } from '@/types';
import * as H from 'history';

const useLocation = <Params = Record<string, string>>(): LocationType<Params> &
  H.Location<Params> => {
  return useUmiLocation<Params>();
};

export default useLocation;
