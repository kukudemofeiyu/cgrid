/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-15 09:14:39
 * @LastEditTime: 2023-06-15 09:14:43
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\Alarm\service.ts
 */

import request from '@/utils/request';

export const getPage = (params: any) => {
  return request(`/oss/alarm/home/unprocessedAlarm/page`, {
    method: 'GET',
    params,
  });
};
