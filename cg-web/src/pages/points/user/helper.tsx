/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-22 15:48:27
 * @LastEditTime: 2025-02-22 15:48:27
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/market/couponActivity/helper.tsx
 */

import { formatMessage } from "@/utils";
import { YTProColumns } from "@/components/ProTable/typing";

export const columns: YTProColumns<any, any>[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '昵称' }),
    dataIndex: 'c',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '用户ID' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '手机号' }),
    dataIndex: 'g',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '积分' }),
    dataIndex: 'h',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '消防金额' }),
    dataIndex: 'i',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '用户类型' }),
    dataIndex: 'j',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
];
