/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-07-09 14:50:35
 * @LastEditTime: 2024-07-09 14:50:35
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\module\BmuTabs\type.ts
 */

import { DeviceDataType } from '@/services/equipment';
import { DeviceModelType } from '@/types/device';

export type BmuType = {
  bmuData?: DeviceDataType[];
  modelMap?: Record<string, DeviceModelType>;
  onOpenChart?: (
    deviceId: string,
    collectionInfo: {
      title: string;
      collection: string;
    },
  ) => void;
};

export type MaxDataType = {
  cell: {
    max: {
      bmu?: number;
      index: number;
      value: number;
    };
    min: {
      bmu?: number;
      index: number;
      value: number;
    };
  };
  temp: {
    max: {
      bmu?: number;
      index: number;
      value: number;
    };
    min: {
      bmu?: number;
      index: number;
      value: number;
    };
  };
  poleTemp: {
    max: {
      bmu?: number;
      index: number;
      value: number;
    };
    min: {
      bmu?: number;
      index: number;
      value: number;
    };
  };
};
