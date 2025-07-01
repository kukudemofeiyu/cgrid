/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-22 11:40:56
 * @LastEditTime: 2025-02-22 11:40:56
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/teamCharge/order/helper.ts
 */

import { ProColumns } from "@ant-design/pro-components";
import { formatMessage } from '@/utils';
import { getAgentCol } from "@/utils/column";
import { connectStatus, deviceType, logSelect, orderStatus, siteStatus, supportOption } from "@/utils/dict";


export const columns: ProColumns[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '订单号' }),
    dataIndex: 'a',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '用户' }),
    dataIndex: 'b',
    valueEnum: supportOption,
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '用户手机号' }),
    dataIndex: 'c',
    valueEnum: supportOption,
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '团体名称' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '卡号' }),
    dataIndex: 'e',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '订单金额(元)' }),
    dataIndex: 'f',
    valueEnum: deviceType,
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '下单状态' }),
    dataIndex: 'g',
    valueEnum: orderStatus,
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '订单开始时间' }),
    dataIndex: 'h',
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
];