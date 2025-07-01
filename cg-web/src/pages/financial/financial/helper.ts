/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-21 11:38:52
 * @LastEditTime: 2025-02-21 11:38:52
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/financial/financial/helper.ts
 */

import { ProColumns } from "@ant-design/pro-components";
import { formatMessage } from '@/utils';
import { getAgentCol } from "@/utils/column";


export const columns: ProColumns[] = [
  getAgentCol(),
  {
    title: formatMessage({ id: '', defaultMessage: '手机号' }),
    dataIndex: 'c',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '日销售额' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '月销售额' }),
    dataIndex: 'e',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
];