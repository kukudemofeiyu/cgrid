/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-22 16:39:00
 * @LastEditTime: 2025-02-22 16:39:00
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/feedback/helper.tsx
 */

import { ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage } from "@/utils";
import { dealStatus, siteDimention } from "@/utils/dict";
import { YTProColumns } from "@/components/ProTable/typing";
import { getAgentCol } from "@/utils/column";

export const columns: YTProColumns<any, any>[] = [
  getAgentCol(),
  {
    title: formatMessage({ id: '', defaultMessage: '用户昵称' }),
    dataIndex: 'c',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '用户手机号' }),
    dataIndex: 'h',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '问题类型' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '处理状态' }),
    dataIndex: 'e',
    valueEnum: dealStatus,
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '问题描述' }),
    dataIndex: 'g',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '处理人' }),
    dataIndex: 'j',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '处理时间' }),
    dataIndex: 'k',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '设备编号' }),
    dataIndex: 'l',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '创建时间' }),
    dataIndex: 'm',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '批注' }),
    dataIndex: 'n',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
];

export const formColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '批注' }),
    dataIndex: 'c',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
];
