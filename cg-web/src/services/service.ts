/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-13 19:40:32
 * @LastEditTime: 2023-07-13 19:40:32
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\services\service.ts
 */

import request, { ResponseCommonData, ResponsePageData } from '@/utils/request';

export const getServicePage = (params: any) => {
  return request(`/uc/customerUser/serviceProvider/page`, {
    method: 'GET',
    params,
  });
};
