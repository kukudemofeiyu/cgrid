/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-22 14:45:00
 * @LastEditTime: 2025-02-22 14:49:23
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/alarm/helper.ts
 */

import { ProColumns } from "@ant-design/pro-components";
import { formatMessage } from '@/utils';
import { getAgentCol, getSiteCol } from "@/utils/column";
import { alarm2Status, connectStatus, deviceType, logSelect, orderStatus, siteStatus, supportOption } from "@/utils/dict";


export const columns: ProColumns[] = [
  getAgentCol({
    hideInTable: true
  }),
  getSiteCol({
    hideInTable: true
  }),
  {
    title: formatMessage({ id: '', defaultMessage: '设备编号' }),
    dataIndex: 'a',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '端口' }),
    dataIndex: 'b',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '告警类型' }),
    dataIndex: 'c',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '消息内容' }),
    dataIndex: 'd',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '告警时间' }),
    dataIndex: 'e',
    valueType: 'dateRange',
    width: 150,
    ellipsis: true,
    render: (_, record) => record.createTime,
    search: {
      transform: (value) => {
        return {
          startTime: value[0],
          endTime: value[1],
        };
      },
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '状态' }),
    dataIndex: 'f',
    valueEnum: alarm2Status,
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
];
