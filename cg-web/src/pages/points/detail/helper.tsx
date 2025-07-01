/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-22 15:48:27
 * @LastEditTime: 2025-02-24 14:06:11
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/points/detail/helper.tsx
 */

import { formatMessage } from "@/utils";
import { YTProColumns } from "@/components/ProTable/typing";

export const columns: YTProColumns<any, any>[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '订单号' }),
    dataIndex: 'c',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '用户名' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '消费金额' }),
    dataIndex: 'g',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充值手机号' }),
    dataIndex: 'h',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '获得积分' }),
    dataIndex: 'i',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '时间' }),
    dataIndex: 'j',
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
