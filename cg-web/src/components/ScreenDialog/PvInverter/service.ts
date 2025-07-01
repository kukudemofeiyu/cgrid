/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-10 14:02:19
 * @LastEditTime: 2023-05-10 14:02:24
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\pages\screen\components\PvInverter\service.ts
 */

import request from '@/utils/request';

export const getDcIn = () => {
  return request(`/screen/pvinverter/dcin`, {
    method: 'GET',
  });
};
