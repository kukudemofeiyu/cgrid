/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-23 14:19:52
 * @LastEditTime: 2024-04-09 13:55:04
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\components\interceptor.ts
 */

import { parseToArray } from '@/utils';
import { default as utilsRequest } from '@/utils/request';

export const flat = (key: string, data: string) => {
  const result: Record<string, any> = {};
  parseToArray(data)?.forEach?.((item, index) => {
    Object.keys(item)?.forEach?.((itemKey) => {
      result[`${key}[${index}]['${itemKey}']`] = item[itemKey];
    });
  });
  return result;
};

export const request = (url: string, params: Record<string, any>) => {
  return utilsRequest(url, {
    method: 'GET',
    params,
  }).then((res) => {
    return res?.data || {};
  });
};
