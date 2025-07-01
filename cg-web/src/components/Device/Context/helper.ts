/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-05-21 15:07:48
 * @LastEditTime: 2024-05-21 15:07:48
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Context\helper.ts
 */

import { flatMap } from 'lodash';
import { DeviceDataType } from '@/services/equipment';

export const flatTree = (data?: DeviceDataType[]) => {
  const result: DeviceDataType[] = flatMap(data || [], (item) => {
    const children = item.children;
    if (children && children.length > 0) {
      return [item, ...flatTree(children)];
    }
    return [item];
  });
  return result;
};
