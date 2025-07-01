import { formatMessage } from '@/utils';
import type { ProColumns } from '@ant-design/pro-components';
import type { ConfigDataType } from './data';
import { Tooltip } from 'antd';
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
    title: formatMessage({ id: 'device.logName', defaultMessage: '日志名称' }),
    dataIndex: 'logName',
    hideInSearch: true,
  },

  {
    title: formatMessage({ id: 'device.1023', defaultMessage: '导出人' }),
    dataIndex: 'createByName',
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: 'device.1024', defaultMessage: '导出时间' }),
    dataIndex: 'createTime',
    valueType: 'dateRange',
    renderFormat: getLocale().dateTimeFormat,
    search: {
      transform: (value: any[]) => {
        return {
          startDate: value[0],
          endDate: value[1],
        };
      },
    },
    ellipsis: true,
  } as any,
  {
    title: formatMessage({ id: 'device.1025', defaultMessage: '导出状态' }),
    dataIndex: 'status',
    hideInSearch: true,
    render: (text: number, record: any) => {
      switch (text) {
        case 0:
          return formatMessage({ id: 'device.1026', defaultMessage: '正在获取日志' });
        case 1:
          return formatMessage({ id: 'device.1027', defaultMessage: '成功' });
        case 2:
          return (
            <Tooltip title={record.failReason}>
              {formatMessage({ id: 'device.1028', defaultMessage: '失败' })}
            </Tooltip>
          );
        default:
          return '--';
      }
    },
  },
];
export const schemaColumns = [
  {
    title: formatMessage({ id: 'device.selectDate', defaultMessage: '请选择日期' }),
    dataIndex: 'logDate',
    valueType: 'date',
    width: '100%',
    formItemProps: {
      rules: [
        {
          required: true,
          message: formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }),
        },
      ],
    },
    fieldProps: {
      with: '100%',
      format: getLocale().dateFormat,
    },
  },
];

export const emsCcolumns: ProColumns<ConfigDataType, YTDATERANGEVALUETYPE>[] = [
  {
    title: formatMessage({ id: 'common.index', defaultMessage: '序号' }),
    dataIndex: 'index',
    valueType: 'index',
    width: 50,
  },
  {
    title: formatMessage({ id: 'device.logName', defaultMessage: '日志名称' }),
    dataIndex: 'logName',
    hideInSearch: true,
  },
  {
    title: `${formatMessage({ id: 'device.1038', defaultMessage: '文件大小' })}（M）`,
    dataIndex: 'size',
    hideInSearch: true,
  },
];
