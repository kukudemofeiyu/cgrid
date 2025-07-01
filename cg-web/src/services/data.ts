import request, { ResponseCommonData, ResponsePageData } from '@/utils/request';

export type CollectionValueType = {
  eventTs?: string;
  value?: number;
};

export type CollectionDataType = {
  details?: {
    data?: {
      time?: string;
      collection?: CollectionValueType[];
    }[];
    deviceName?: string;
    startTime?: string;
    endTime?: string;
  }[];
};

export type CollectionSearchType = {
  msgType?: number;
  deviceId?: string;
  key?: string;
  devices?: {
    deviceId?: string;
    keys?: string[];
  }[];
  startTime?: string;
  endTime?: string;
  current?: number;
  pageSize?: number;
};

export const getCollectionData = (params: CollectionSearchType) => {
  return request<ResponseCommonData<CollectionValueType[]>>(
    `/iot/processRealData/queryDeviceDataForHistoryList`,
    {
      method: 'GET',
      params,
    },
  );
};
