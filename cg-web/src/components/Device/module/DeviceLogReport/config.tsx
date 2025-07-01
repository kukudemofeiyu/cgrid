import { formatMessage } from '@/utils';
import type { ProColumns } from '@ant-design/pro-components';
import type { ConfigDataType } from './data';
import type { YTDATERANGEVALUETYPE } from '@/components/YTDateRange';
import { getLocale } from '@/utils';

export enum OnlineStatusEnum {
  None,
  Online,
}
export const onlineStatus = {
  [OnlineStatusEnum.None]: {
    text: '离线',
    icon: 'red',
    status: 'Default',
  },
  [OnlineStatusEnum.Online]: {
    text: '在线',
    icon: 'green',
    status: 'Processing',
  },
};
export const columns: ProColumns<ConfigDataType, YTDATERANGEVALUETYPE>[] = [
  {
    title: formatMessage({ id: 'common.index', defaultMessage: '序号' }),
    dataIndex: 'index',
    valueType: 'index',
    width: 50,
  },
  {
    title: formatMessage({ id: 'device.1024', defaultMessage: '导出时间' }),
    dataIndex: 'time',
    valueType: 'dateTimeRange',
    renderFormat: getLocale().dateTimeFormat,
    hideInTable: true,
    search: {
      transform: (value: any[]) => {
        return {
          startTime: value[0],
          endTime: value[1],
        };
      },
    },
    ellipsis: true,
  } as any,
  {
    title: formatMessage({ id: 'device.1040', defaultMessage: '日志时间' }),
    dataIndex: 'logTime',
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: 'device.1041', defaultMessage: '日志内容' }),
    dataIndex: 'content',
    hideInSearch: true,
  },
];
