/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-01-29 10:30:06
 * @LastEditTime: 2024-01-29 10:30:06
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\Cabinet\type.ts
 */

import { DeviceDataType } from '@/services/equipment';
import { DeviceProductTypeEnum, DeviceTypeEnum } from '@/utils/dictionary';
import { EnergySourceEnum } from '..';
import { DetailItem } from '@/components/Detail';

export type ConfigType = {
  label: string;
  showLabel?: false;
  productTypeId?: DeviceProductTypeEnum;
  fixValue?: string;
  dataProductTypeIds?: DeviceProductTypeEnum[];
  dataProductIds?: DeviceTypeEnum[];
  position: {
    top: number;
    left: number;
  };
  icon: string;
  line: string;
  linePosition: {
    top: number;
    left: number;
    width?: number;
  };
  data: (DetailItem & { customFormat?: (value: any, data?: any) => React.ReactNode })[];
};

export type ProductIdMapType = Record<
  string,
  {
    deviceId?: string;
    productId?: DeviceTypeEnum;
  }
>;

export type EntityType = {
  deviceData?: DeviceDataType;
  showLabel?: boolean;
  loading?: boolean;
  source?: EnergySourceEnum;
};
