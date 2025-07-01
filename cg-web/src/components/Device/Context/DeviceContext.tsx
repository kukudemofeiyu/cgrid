/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-12-22 10:58:23
 * @LastEditTime: 2024-03-07 18:54:09
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Context\DeviceContext.tsx
 */
import { DeviceDataType } from '@/services/equipment';
import { ResponseCommonData } from '@/utils/request';
import { createContext } from 'react';

export type RefreshRequestParams = {
  deviceId: string;
  input: {
    queryList: string[];
  };
  serviceId?: string;
};

export type DeviceContextType = {
  data?: DeviceDataType;
  updateData?: any;
  onSelect?: (deviceId: string) => void;
  loading?: boolean;
  refreshDataByRequest?: (
    params: RefreshRequestParams,
    showMessage?: boolean,
  ) => Promise<ResponseCommonData<string[]>>;
  treeData?: DeviceDataType[];
};

const DeviceContext = createContext<DeviceContextType>({
  data: {},
  loading: false,
  updateData: () => {},
  // onSelect: () => { },
  refreshDataByRequest: () => Promise.resolve({ code: '200', data: [], msg: '' }),
  treeData: [],
});

export default DeviceContext;
