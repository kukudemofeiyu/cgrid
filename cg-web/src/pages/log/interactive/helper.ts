/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-22 14:39:04
 * @LastEditTime: 2025-02-22 14:39:04
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/log/interactive/helper.ts
 */

import { ProColumns } from "@ant-design/pro-components";
import { formatMessage } from '@/utils';
import { getAgentCol } from "@/utils/column";
import { connectStatus, deviceType, logSelect, orderStatus, siteStatus, supportOption } from "@/utils/dict";


export const columns: ProColumns[] = [
  getAgentCol(),
  {
    title: formatMessage({ id: '', defaultMessage: '充电桩编号' }),
    dataIndex: 'a',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '创建时间' }),
    dataIndex: 'b',
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
    title: formatMessage({ id: '', defaultMessage: '设备指令' }),
    dataIndex: 'c',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '数据指令' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '备注' }),
    dataIndex: 'e',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
];
