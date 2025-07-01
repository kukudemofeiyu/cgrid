/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-08 15:28:18
 * @LastEditTime: 2024-02-25 08:59:37
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\LogTable\index.tsx
 */
import React, { useRef, useContext, useEffect, useMemo } from 'react';
import YTProTable from '../ProTable';
import type { ProColumns, ActionType } from '@ant-design/pro-components';
import { format } from 'timeago.js';
import { Empty as AntEmpty } from 'antd';
import moment from 'moment';
import Empty from '../Empty';
import { DialogContext } from '@/components/Dialog';
import { formatMessage, getLocale } from '@/utils';
import type { YTDATERANGEVALUETYPE } from '@/components/YTDateRange';

export type LogTableProps = {
  params?: {
    id: string;
  };
  request?: (params: any) => Promise<any>;
};

export type LogType = {
  id: string;
  createByName: string;
  serviceName: string;
  input: string;
  createTime: string;
};

const AlarmTable: React.FC<LogTableProps> = (props) => {
  const { params, request } = props;

  const actionRef = useRef<ActionType>();
  const dialogContext = useContext(DialogContext);
  const searchParams = {
    ...(params || {}),
    deviceId: params?.id,
  };

  const columns = useMemo<ProColumns<LogType, YTDATERANGEVALUETYPE>[]>(() => {
    return [
      {
        title: formatMessage({ id: 'common.time', defaultMessage: '时间' }),
        dataIndex: 'createTime',
        valueType: 'dateRange',
        width: 200,
        ellipsis: true,
        render: (_, record) =>
          `${record.createTime} (${format(record.createTime, getLocale().locale)})`,
        search: {
          transform: (value) => {
            return {
              startTime: value[0],
              endTime: value[1],
            };
          },
        },
        initialValue: [moment(), moment()],
        fieldProps: {
          // className: 'ant-picker-rtl',
          getPopupContainer: (triggerNode: any) => triggerNode.parentElement,
          ranges: {
            [formatMessage({ id: 'date.nearly24Hours', defaultMessage: '近24小时' })]: [
              moment().subtract(1, 'day'),
              moment(),
            ],
            [formatMessage({ id: 'date.last7Days', defaultMessage: '最近7天' })]: [
              moment().subtract(6, 'day'),
              moment(),
            ],
            [formatMessage({ id: 'date.thisMonth', defaultMessage: '本月' })]: [
              moment().startOf('month'),
              moment(),
            ],
            [formatMessage({ id: 'date.last3Months', defaultMessage: '最近3个月' })]: [
              moment().subtract(3, 'month'),
              moment(),
            ],
          },
        },
      },
      {
        title: formatMessage({ id: 'common.logContent', defaultMessage: '日志内容' }),
        dataIndex: 'serviceName',
        width: 100,
        ellipsis: true,
        hideInSearch: true,
      },
      {
        title: formatMessage({ id: 'common.logCode', defaultMessage: '日志编码' }),
        dataIndex: 'id',
        width: 100,
        ellipsis: true,
        hideInSearch: true,
      },
      {
        title: formatMessage({ id: 'common.operatingUser', defaultMessage: '操作用户' }),
        dataIndex: 'createByName',
        width: 150,
        ellipsis: true,
        hideInSearch: true,
      },
    ];
  }, []);

  useEffect(() => {
    actionRef?.current?.reloadAndRest?.();
  }, [params]);

  return (
    <>
      <YTProTable
        className="log-table"
        actionRef={actionRef}
        columns={columns}
        toolBarRender={() => [<></>]}
        params={searchParams}
        request={request && params?.id ? (query) => request(query) : undefined}
        locale={{
          emptyText: dialogContext.model == 'screen' ? <Empty /> : <AntEmpty />,
        }}
      />
    </>
  );
};

export default AlarmTable;
