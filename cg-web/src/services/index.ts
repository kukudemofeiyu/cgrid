// @ts-ignore
/* eslint-disable */
// API 更新时间：
// API 唯一标识：
import { AreaDataType, MoneyUnitDataType } from '@/types';
import * as api from './api';
import * as login from './session';
import request, { ResponseCommonData, ResponsePageData } from '@/utils/request';
import { request as umiRequest } from 'umi';

export const requestEmptyPage = <ResponseData = Record<string, any>>(): Promise<
  ResponsePageData<ResponseData>
> => {
  return Promise.resolve({
    code: '200',
    data: {
      list: [],
      total: 0,
    },
    msg: '',
  });
};

export const requestEmpty = <ResponseData = Record<string, any>>(): Promise<
  ResponseCommonData<ResponseData>
> => {
  return Promise.resolve({
    code: '200',
    data: {} as any,
    msg: '',
  });
};

export const getAreaConfig = (params: any) => {
  return umiRequest<ResponseCommonData<AreaDataType[]>>(
    'https://link.yotaienergy.com/prod-api/uc/area',
    {
      method: 'get',
      params,
      skipErrorHandler: true,
    },
  );
};

export const getMonetaryUnit = () => {
  return request<ResponseCommonData<MoneyUnitDataType[]>>(`/uc/site/monetaryUnit`, {
    method: 'GET',
  });
};

export const getMonthHolidays = (params: any) => {
  return request(`/iot/date/holidaySection`, {
    method: 'GET',
    params,
  });
};

export { api };

export default {
  api,
  login,
};
