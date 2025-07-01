/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-22 15:59:33
 * @LastEditTime: 2025-02-22 15:59:33
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/market/couponDetail/helper.ts
 */

import { ProColumns } from "@ant-design/pro-components";
import { formatMessage } from '@/utils';
import { getAgentCol } from "@/utils/column";
import { connectStatus, couponUseType, deviceType, logSelect, orderStatus, siteStatus, supportOption } from "@/utils/dict";


export const columns: ProColumns[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '用户手机号' }),
    dataIndex: 'a',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '优惠卷编号' }),
    dataIndex: 'b',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '活动名称' }),
    dataIndex: 'c',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '活动类型' }),
    dataIndex: 'd',
    valueType: 'select',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '有效开始时间' }),
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
    title: formatMessage({ id: '', defaultMessage: '有效结束时间' }),
    dataIndex: 'f',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '使用时间' }),
    dataIndex: 'g',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '状态' }),
    dataIndex: 'h',
    valueEnum: couponUseType,
    width: 120,
    ellipsis: true,
  },
];
