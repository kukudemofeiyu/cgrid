/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-05-29 16:02:05
 * @LastEditTime: 2024-05-29 17:00:05
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\module\TimeBatteryInfo\service.ts
 */

import request, { ResponseCommonData } from '@/utils/request';
import { TimeBatteryDataType } from './type';

export const getTimeBatteryData = (params: any) => {
  return request<ResponseCommonData<TimeBatteryDataType[]>>('/oss/device/ems/getTimeBattery', {
    method: 'GET',
    params,
  });
};
