/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-01 10:30:35
 * @LastEditTime: 2023-09-01 11:13:30
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceDetail\Configuration\Device\ConfigForm\config.ts
 */

import { DeviceDataType } from '@/services/equipment';
import { formatMessage } from '@/utils';
import { ProColumns } from '@ant-design/pro-components';

export type ConfigDataType = {
  associateDevices?: DeviceDataType[];
  associateId?: string;
  associateIds?: string[];
  productConfigType?: number;
};

export type TreeDataType = {
  deviceName: string;
  deviceSN: string;
  id: string;
  parentId: string;
  children: TreeDataType[];
  selectFlag: boolean;
  productId: number;
};

export const tableSelectColumns: ProColumns[] = [
  {
    title: formatMessage({
      id: 'siteManage.set.dataCollectionPoints',
      defaultMessage: '数据采集点',
    }),
    dataIndex: 'modelName',
    width: 200,
    ellipsis: true,
    hideInTable: true,
  },
  {
    title: formatMessage({
      id: 'siteManage.set.dataCollectionPoints',
      defaultMessage: '数据采集点',
    }),
    dataIndex: 'paramName',
    width: 200,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({
      id: 'siteManage.set.dataCollectionPointIdIdentify',
      defaultMessage: '数据采集点标识',
    }),
    dataIndex: 'paramCode',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
];
