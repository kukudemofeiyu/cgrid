/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-24 11:00:03
 * @LastEditTime: 2025-02-24 11:13:52
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/websiteConfig/Transaction/helper.ts
 */

import { formatMessage } from "@/utils";
import { ProFormColumnsType } from "@ant-design/pro-components";

export const formColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '自动退款' }),
    dataIndex: 'c',
    valueType: 'switch',
  },
  {
    title: formatMessage({ id: '', defaultMessage: '开启充电最低余额' }),
    dataIndex: 'd',
    valueType: 'money',
    fieldProps: {
      className: 'w-full',
    },
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '结束充电最低余额' }),
    dataIndex: 'e',
    valueType: 'money',
    fieldProps: {
      className: 'w-full',
    },
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充值最低余额' }),
    dataIndex: 'f',
    valueType: 'money',
    fieldProps: {
      className: 'w-full',
    },
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '余额充值金额' }),
    dataIndex: 'g',
    valueType: 'select',
    fieldProps: {
      mode: 'tags'
    },
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
];