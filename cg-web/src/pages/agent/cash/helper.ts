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
  getAgentCol({
    hideInTable: true,
  }),
  {
    title: formatMessage({ id: '', defaultMessage: '用户' }),
    dataIndex: 'a',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '用户名' }),
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
  },
  {
    title: formatMessage({ id: '', defaultMessage: '金额' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '时间' }),
    dataIndex: 'e',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '状态' }),
    dataIndex: 'f',
    valueEnum: logSelect,
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
];
