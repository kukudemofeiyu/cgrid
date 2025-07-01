/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-21 11:45:37
 * @LastEditTime: 2025-02-21 11:45:37
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/financial/component/Seperate/helper.ts
 */

import { ProColumns } from "@ant-design/pro-components";
import { formatMessage } from '@/utils';
import { invoiceStatus } from "@/utils/dict";

export const columns: ProColumns[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '昵称' }),
    dataIndex: 'a3',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '手机' }),
    dataIndex: 'a1',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '开票订单数' }),
    dataIndex: 'a2',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '开票抬头' }),
    dataIndex: 'a',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '开票状态' }),
    dataIndex: 'b',
    valueEnum: invoiceStatus,
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '开票时间' }),
    dataIndex: 'c',
    valueType: 'dateRange',
    width: 150,
    ellipsis: true,
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
    title: formatMessage({ id: '', defaultMessage: '备注' }),
    dataIndex: 'd',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '操作员' }),
    dataIndex: 'e',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '发票流水号' }),
    dataIndex: 'e',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
];
