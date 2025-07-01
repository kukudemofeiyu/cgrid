/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-22 09:55:19
 * @LastEditTime: 2025-02-22 10:06:58
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/user/backlist/helper.tsx
 */

import { ProColumns, ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage, isEmpty } from "@/utils";
import { enableStatus, gunStatus, siteDimention } from "@/utils/dict";
import { YTProColumns } from "@/components/ProTable/typing";
import { QrcodeOutlined } from "@ant-design/icons";
import { verifyPhone } from "@/utils/reg";

export const columns: YTProColumns<any, any>[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '用户手机号' }),
    dataIndex: 'c',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '原因描述' }),
    dataIndex: 'd',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '解禁日期' }),
    dataIndex: 'e',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '修改人' }),
    dataIndex: 'g',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '修改时间' }),
    dataIndex: 'h',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
];

export const formColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '用户手机号' }),
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
    title: formatMessage({ id: '', defaultMessage: '解禁时间' }),
    dataIndex: 'd1',
    valueType: 'dateTime',
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
    title: formatMessage({ id: '', defaultMessage: '黑名单维度' }),
    dataIndex: 'dimention',
    valueType: 'radio',
    valueEnum: siteDimention,
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    valueType: 'dependency',
    name: ['dimention'],
    columns: ({ dimention }) => {
      return dimention ? [
        {
          title: formatMessage({ id: '', defaultMessage: '选择站点' }),
          dataIndex: 'e',
          valueType: 'treeSelect',
          formItemProps: {
            rules: [
              {
                required: true,
              },
            ],
          },
        }
      ] : [];
    }
  },
  {
    title: formatMessage({ id: '', defaultMessage: '原因描述' }),
    dataIndex: 'd',
    valueType: 'textarea',
  },
];
