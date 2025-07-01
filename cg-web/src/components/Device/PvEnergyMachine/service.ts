/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-11-16 17:41:00
 * @LastEditTime: 2023-11-16 17:41:22
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\PvEnergyMachine\service.ts
 */

import request, { ResponseCommonData } from '@/utils/request';
import { searchType, PowerDataType, ElectricDataType } from './typing';

export const getPower = (params: searchType) => {
  return request<ResponseCommonData<PowerDataType>>('/iot/device/es/getPowerCurve', {
    method: 'GET',
    params,
  });
};

export const getElectic = (params: searchType) => {
  return request<ResponseCommonData<ElectricDataType>>('/iot/device/essChargeDischargeTrends', {
    method: 'GET',
    params,
  });
};
