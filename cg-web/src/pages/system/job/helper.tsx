/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-24 14:28:08
 * @LastEditTime: 2025-02-24 14:28:08
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/system/job/helper.tsx
 */

import { ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage, isEmpty } from "@/utils";
import { YTProColumns } from "@/components/ProTable/typing";
import { verifyPhone } from "@/utils/reg";

export const columns: YTProColumns<any, any>[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '岗位名称' }),
    dataIndex: 'c',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '岗位描述' }),
    dataIndex: 'd',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '创建时间' }),
    dataIndex: 'g',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
];

export const formColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '岗位描述' }),
    dataIndex: 'c',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '岗位名称' }),
    dataIndex: 'b',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '权限' }),
    dataIndex: 'c',
    valueType: 'treeSelect',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
];