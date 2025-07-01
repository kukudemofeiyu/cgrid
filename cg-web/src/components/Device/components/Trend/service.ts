/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-08 11:08:28
 * @LastEditTime: 2024-03-08 11:08:29
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\components\Trend\service.ts
 */

import request, { ResponseCommonData } from '@/utils/request';
import { TrendDataType } from './typing';

export const getPower = (params: any) => {
  return request<ResponseCommonData<TrendDataType>>('/iot/ytcharging/getChargeElecAndCount', {
    method: 'GET',
    params,
  });
};
