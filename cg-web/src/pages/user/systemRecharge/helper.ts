/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-21 17:05:30
 * @LastEditTime: 2025-02-21 17:05:30
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/user/systemRecharge/helper.ts
 */

import { formatMessage } from "@/utils";
import { ProFormColumnsType } from "@ant-design/pro-components";

export const formColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '充值用户' }),
    dataIndex: 'c',
    valueType: 'select',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充值金额' }),
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
];
