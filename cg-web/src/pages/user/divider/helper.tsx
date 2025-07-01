/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-21 10:11:07
 * @LastEditTime: 2025-02-21 16:29:56
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/user/user/helper.tsx
 */

import { ProColumns, ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage, isEmpty } from "@/utils";
import { enableStatus, gunStatus } from "@/utils/dict";
import { YTProColumns } from "@/components/ProTable/typing";
import { QrcodeOutlined } from "@ant-design/icons";
import { verifyPhone } from "@/utils/reg";

export const columns: YTProColumns<any, any>[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '昵称' }),
    dataIndex: 'c',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '头像' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '手机号' }),
    dataIndex: 'e',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '账号余额' }),
    dataIndex: 'g',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '卡余额' }),
    dataIndex: 'h',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '总余额' }),
    dataIndex: 'i',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '状态' }),
    dataIndex: 'k',
    width: 100,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '创建时间' }),
    dataIndex: 'j',
    width: 150,
    ellipsis: true,
  },
];

export const formColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '卡号' }),
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
    title: formatMessage({ id: '', defaultMessage: '转账金额' }),
    dataIndex: 'd',
    valueType: 'money',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
];
