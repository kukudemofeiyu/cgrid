/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-12 09:30:59
 * @LastEditTime: 2023-09-12 09:31:03
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\RemoteSetting\typing.ts
 */

import { DeviceDataType } from '@/services/equipment';
import { DeviceTypeEnum } from '@/utils/dictionary';

export type RemoteSettingType = {
  deviceData: DeviceDataType;
};

export type RemoteSettingProductType = {
  component: string;
  props?: Record<string, any>;
};

export type RemoteSettingDataType<T = Record<string, any>> = {
  deviceId?: string;
  input?: T;
  serviceId?: string;
};
