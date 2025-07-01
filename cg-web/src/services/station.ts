/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-22 15:43:33
 * @LastEditTime: 2024-05-28 09:01:00
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\services\station.ts
 */
import type { ResponseCommonData, ResponsePageData } from '@/utils/request';
import request from '@/utils/request';
import { SiteTypeEnumType } from '@/utils/dict';
import { SiteOptionType } from '@/types';
import { SiteMonitorEnum } from '@/utils/enum';

export type SiteDataType = {
  id?: string;
  name?: string;
  siteType?: SiteTypeEnumType;
  energyOptions?: string;
  label?: string;
  value?: string;
  isLoad?: boolean;
  longitude?: number;
  latitude?: number;
  operationDays?: number;
  monetaryUnit?: number;
  collectDataTypes?: SiteMonitorEnum[];
};

export type SiteTypeOptionType = {
  name?: string;
  value?: string;
  collectDataTypes?: SiteMonitorEnum[];
};

export type ConfigDataType = {
  siteId?: string;
  status?: number;
  energyFlowDiagramIds?: string;
  screen?: {
    url?: string;
  }[];
};

export type AllLargeScreenConfigType = {
  status: number;
  active: number;
  config: {
    alarmShow: number;
    templates: {
      name: number;
      thirdId: string;
      id: number;
      chartId: number;
      energyFlowDiagramId: number;
      status: number;
      url?: string;
    }[];
  };
};

export type AlarmConfigDataType = {
  siteId?: string;
  alarmShow?: number;
};

export type VideoMonitorDataType = {
  monitorStatus?: string;
  jumpMethod?: string;
  config?: string;
  code?: string;
  url?: string;
  factoryId?: number;
};

export type VideoMonitorTokenType = {
  authorization?: string;
  refreshToken?: string;
};

export type ExportDataListType = {
  name: string;
  siteFlag: number;
  timeFlag: number;
  type?: number;
};

export const getSitesList = (params?: any) => {
  return request<ResponseCommonData<SiteDataType[]>>(`/oss/site/getList`, {
    method: 'GET',
    params,
  });
};

export const getExportDataList = () => {
  return request<ResponseCommonData<ExportDataListType[]>>(`/iot/device/getExportDataList`, {
    method: 'GET',
  });
};

export const getStations = (params?: any) => {
  return request<ResponseCommonData<SiteDataType[]>>(`/uc/site/page`, {
    method: 'GET',
    params: {
      current: 1,
      pageSize: 999,
      ...params,
    },
  });
};

export const getDeviceListSites = (params?: any) => {
  return request(`/oss/site/getListByDevice`, {
    method: 'GET',
    params,
  });
};

export const getSiteType = () => {
  return request<ResponseCommonData<SiteTypeOptionType[]>>(`/oss/site/index/siteType`, {
    method: 'GET',
  });
};

export const getStation = (id: string) => {
  return request(`/oss/site/details`, {
    method: 'GET',
    params: {
      siteId: id,
    },
  });
};

export const getDefaultPage = (id: string) => {
  return request(`/oss/siteHomeConfig`, {
    method: 'GET',
    params: {
      siteId: id,
    },
  });
};

export const getSiteList = (params?: any) => {
  return request<ResponseCommonData<SiteDataType[]>>(`/oss/site/monitor/overview/getSiteList`, {
    method: 'GET',
    params,
  });
};

export const getSiteScreenConfig = (params: any) => {
  return request<ResponseCommonData<ConfigDataType>>(`/oss/site/energyFlowDiagram/getSiteConfig`, {
    method: 'GET',
    params,
  });
};

export const allLargeScreenConfig = (params: any) => {
  return request<ResponseCommonData<AllLargeScreenConfigType>>(
    `/oss/siteLargeScreen/allLargeScreenConfig`,
    {
      method: 'GET',
      params,
    },
  );
};

export const getRoleSiteList = () => {
  return request<ResponseCommonData<SiteOptionType[]>>(`/uc/site/allSiteType`, {
    method: 'GET',
  });
};

export const getVideoMonitorData = (params: any) => {
  return request<ResponseCommonData<VideoMonitorDataType>>(`/uc/site/videoMonitor`, {
    method: 'GET',
    params,
  });
};

export const getVideoMonitorToken = (params: any) => {
  return request<ResponseCommonData<VideoMonitorTokenType>>(`/uc/site/videoMonitor/token`, {
    method: 'GET',
    params,
  });
};

export const distributeElectricityPrice = (params: any) => {
  return request<ResponseCommonData<VideoMonitorTokenType>>(`/uc/site/videoMonitor/token1`, {
    method: 'GET',
    params,
  });
};

export const updateIncomeByRuleIdAndType = (data: any) => {
  return request<ResponseCommonData<VideoMonitorTokenType>>(
    `/oss/site/energyFlowDiagram/updateIncomeByRuleIdAndType`,
    {
      method: 'POST',
      data,
    },
  );
};

export const getSitePage = (params: any) => {
  return request<ResponsePageData<SiteDataType>>(`/uc/site/page`, {
    method: 'GET',
    params,
  });
};

export const getSiteCenter = (params: any) => {
  return request(`/oss/electrovalence/center/getSiteCenter`, {
    method: 'GET',
    params,
  });
};
