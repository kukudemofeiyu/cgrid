/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-22 15:15:03
 * @LastEditTime: 2025-02-22 15:15:03
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/market/siteTemplate/helper.tsx
 */

import { ProColumns, ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage, isEmpty } from "@/utils";
import { discountStatus, effectTime, enableStatus, gunStatus, siteDimention } from "@/utils/dict";
import { YTProColumns } from "@/components/ProTable/typing";
import { QrcodeOutlined } from "@ant-design/icons";
import { verifyPhone } from "@/utils/reg";

export const columns: YTProColumns<any, any>[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '模板名称' }),
    dataIndex: 'c',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '活动类型' }),
    dataIndex: 'd',
    valueEnum: discountStatus,
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '折扣数值' }),
    dataIndex: 'e',
    width: 120,
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
    title: formatMessage({ id: '', defaultMessage: '模板名称' }),
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
    title: formatMessage({ id: '', defaultMessage: '活动类型' }),
    dataIndex: 'd1',
    valueEnum: discountStatus,
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '折扣数值' }),
    dataIndex: 'e',
    valueType: 'digit',
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
    title: formatMessage({ id: '', defaultMessage: '折扣生效类型' }),
    dataIndex: 'f',
    valueType: 'radio',
    valueEnum: effectTime,
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
];
