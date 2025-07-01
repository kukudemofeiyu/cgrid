/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-08 11:05:25
 * @LastEditTime: 2024-03-08 11:05:35
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\components\Power\service.ts
 */

import request, { ResponseCommonData } from '@/utils/request';
import { PowerDataType } from './typing';

export const getPower = (params: any) => {
  return request<ResponseCommonData<PowerDataType>>('/iot/ytcharging/getPowerChart', {
    method: 'GET',
    params,
  });
};
