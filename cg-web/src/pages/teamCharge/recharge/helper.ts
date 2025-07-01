/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-22 11:43:46
 * @LastEditTime: 2025-02-22 11:43:46
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/teamCharge/recharge/helper.ts
 */

import { ProColumns } from "@ant-design/pro-components";
import { formatMessage } from '@/utils';
import { getAgentCol } from "@/utils/column";
import { connectStatus, deviceType, logSelect, siteStatus, supportOption } from "@/utils/dict";


export const columns: ProColumns[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '团体ID' }),
    dataIndex: 'a',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '团体名称' }),
    dataIndex: 'b',
    valueEnum: supportOption,
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充值金额(元)' }),
    dataIndex: 'c',
    valueEnum: supportOption,
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充值时间' }),
    dataIndex: 'd',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充值人' }),
    dataIndex: 'e',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
];