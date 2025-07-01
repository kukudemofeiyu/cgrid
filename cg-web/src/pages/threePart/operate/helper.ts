/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-22 10:20:42
 * @LastEditTime: 2025-02-22 10:24:38
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/threePart/operate/helper.ts
 */

import { ProColumns } from "@ant-design/pro-components";
import { formatMessage } from '@/utils';
import { getAgentCol } from "@/utils/column";
import { connectStatus, logSelect } from "@/utils/dict";


export const columns: ProColumns[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '组织机构代码' }),
    dataIndex: 'a',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '运营商统一社会信用代码' }),
    dataIndex: 'b',
    width: 180,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '运营商全称' }),
    dataIndex: 'c',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '运营商客服电话' }),
    dataIndex: 'd',
    width: 180,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '运营商注册地址' }),
    dataIndex: 'e',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '备注' }),
    dataIndex: 'f',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '用户ID' }),
    dataIndex: 'g',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '创建时间' }),
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
  {
    title: formatMessage({ id: '', defaultMessage: '是否推送' }),
    dataIndex: 'i',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
];