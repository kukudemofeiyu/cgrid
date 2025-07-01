/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-05-16 14:05:26
 * @LastEditTime: 2024-05-16 17:21:46
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Control\type.ts
 */

import { DeviceServiceModelType } from '@/types/device';

export enum FieldModeEnum {
  Edit,
  Read,
}

export type FieldType<T = any> = {
  value?: T;
  onChange?: (value: T) => void;
  field?: DeviceServiceModelType;
  type?: FieldModeEnum;
  data?: any;
};
