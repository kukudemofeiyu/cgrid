/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-21 11:00:33
 * @LastEditTime: 2023-06-25 09:20:34
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\SiteTree\service.ts
 */
import request from '@/utils/request';
import type { ResponseCommonData } from '@/utils/request';
import { TreeData } from './type';

export const getOrgTree = (params: any) => {
  return request(`/uc/user/orgSiteTree`, {
    method: 'GET',
    params,
  });
};
