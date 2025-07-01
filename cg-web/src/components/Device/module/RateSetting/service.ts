/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-05-29 16:02:05
 * @LastEditTime: 2024-05-29 17:00:05
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\module\TimeBatteryInfo\service.ts
 */

import request, { ResponseCommonData } from '@/utils/request';
import { MeterDataTYpe } from './type';


export const getMeterRateData = (params: any) => {
  return request<ResponseCommonData<MeterDataTYpe>>('/iot/deviceSendData/getLatestDeviceSendDada', {
    method: 'GET',
    params,
  });
};

export const setMeterRateData = (data: any) => {
 
  return request<ResponseCommonData<any>>('/iot/deviceSendData/deviceSend', {
    method: 'POST',
    data:data,
  });
};
