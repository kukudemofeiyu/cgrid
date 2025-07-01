/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-24 14:11:26
 * @LastEditTime: 2025-02-24 14:11:26
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/system/log/helper.tsx
 */

import { formatMessage } from "@/utils";
import { YTProColumns } from "@/components/ProTable/typing";

export const columns: YTProColumns<any, any>[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '访问用户' }),
    dataIndex: 'c',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: 'IP' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '访问URL' }),
    dataIndex: 'g',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '模块' }),
    dataIndex: 'h',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '功能方法' }),
    dataIndex: 'i',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '请求参数' }),
    dataIndex: 'i',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '访问时间' }),
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
