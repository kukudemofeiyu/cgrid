/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-12 11:02:29
 * @LastEditTime: 2023-07-13 18:58:44
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\pages\site-monitor\Energy\type.ts
 */

import { DeviceDataType } from '@/services/equipment';

export type ComProps = {
  deviceData?: DeviceDataType;
  className?: string;
  loading?: boolean;
  source?: any; //页面入口
  deviceKey?: any; //自研ems各单元设备id
};

export type searchType = {
  deviceId: string;
  date?: string;
  type?: number;
  visitType?: number;
};

export type energyType = {
  id?: string;
  name: string;
  productId: number;
  children?: energyType[];
};

export type PowerType = {
  eventTs: string;
  doubleVal: number;
  time?: string;
  value?: number;
};

export type ElectricType = {
  charge?: PowerType[];
  discharge?: PowerType[];
  income?: PowerType[];
  totalCharge?: number;
  totalDischarge?: number;
  totalIncome?: number;
};
