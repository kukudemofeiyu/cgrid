/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-21 10:44:13
 * @LastEditTime: 2025-02-21 10:46:50
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/agent/cash/helper.ts
 */

import { ProColumns } from "@ant-design/pro-components";
import { formatMessage } from '@/utils';
import { getAgentCol } from "@/utils/column";
import { connectStatus, logSelect } from "@/utils/dict";


export const columns: ProColumns[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '车牌号码' }),
    dataIndex: 'a',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '车牌颜色' }),
    dataIndex: 'b',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '手机号' }),
    dataIndex: 'c',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '认证时间' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
];
