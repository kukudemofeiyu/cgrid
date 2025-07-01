import { formatMessage } from '@/utils';
import type { ProColumns } from '@ant-design/pro-components';
import type { ConfigDataType } from './data';
export enum OnlineStatusEnum {
  None,
  Online,
}
export const onlineStatus = {
  [OnlineStatusEnum.None]: {
    text: formatMessage({ id: 'common.offline', defaultMessage: '离线' }),
    title: formatMessage({ id: 'common.offline', defaultMessage: '离线' }),
    icon: 'red',
    status: 'Default',
  },
  [OnlineStatusEnum.Online]: {
    text: formatMessage({ id: 'common.onLine', defaultMessage: '在线' }),
    title: formatMessage({ id: 'common.onLine', defaultMessage: '在线' }),
    icon: 'green',
    status: 'Processing',
  },
};
export const columns: ProColumns<ConfigDataType>[] = [
  {
    title: formatMessage({ id: 'common.index', defaultMessage: '序号' }),
    dataIndex: 'index',
    valueType: 'index',
    width: 50,
  },
  {
    title: formatMessage({ id: 'device.serialNumber', defaultMessage: '序列号' }),
    dataIndex: 'sn',
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: 'device.ConfigName', defaultMessage: '名称' }),
    hideInSearch: true,
    dataIndex: 'deviceName',
  },
  {
    title: formatMessage({ id: 'device.networkStatus', defaultMessage: '网络状态' }),
    hideInSearch: true,
    dataIndex: 'status',
    valueEnum: onlineStatus,
  },
  {
    title: formatMessage({ id: 'device.ratedCurrent', defaultMessage: '额定电流' }) + '(A)',
    hideInSearch: true,
    dataIndex: 'ratedCur',
  },
];
