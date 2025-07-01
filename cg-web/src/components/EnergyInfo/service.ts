/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-12 11:01:52
 * @LastEditTime: 2023-07-28 16:35:32
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\service.ts
 */

import request, { ResponseCommonData } from '@/utils/request';
import { searchType, energyType, PowerType, ElectricType } from './type';

export const getEnergy = (params: searchType) => {
  return request<ResponseCommonData<energyType>>('/iot/es/deviceTree', {
    method: 'GET',
    params,
  });
};

export const getPower = (params: searchType) => {
  return request<ResponseCommonData<PowerType[]>>('/iot/device/es/getPowerCurve', {
    method: 'GET',
    params,
  });
};

export const getElectic = (params: searchType) => {
  return request<ResponseCommonData<ElectricType>>('/iot/device/essChargeDischargeTrends', {
    method: 'GET',
    params,
  });
};

export const getUnitElectic = (params: searchType) => {
  return request<ResponseCommonData<ElectricType>>('/oss/device/essChargeDischargeTrends', {
    method: 'GET',
    params,
  });
};
