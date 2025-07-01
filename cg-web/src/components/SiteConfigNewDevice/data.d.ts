/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-10 11:19:07
 * @LastEditTime: 2023-05-11 10:49:08
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EquipForm\data.d.ts
 */
import type { AnyMapType } from '@/utils/dictionary';
import { FileType } from '@/utils/dictionary';

export type EquipFormType = AnyMapType & {
  deviceId?: string;
  siteId?: string;
  subsystemId?: string;
  productType?: string;
  productId?: string;
  name?: string;
  sn?: string;
  url?: string;
  photos?: string;
  photosList?: fileType[];
  paramConfigType?: string;
};
