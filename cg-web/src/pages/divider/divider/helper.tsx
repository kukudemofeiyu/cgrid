/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-21 14:16:52
 * @LastEditTime: 2025-02-21 14:35:22
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/divider/divider/helper.tsx
 */

import { ProColumns, ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage, isEmpty } from "@/utils";
import { enableStatus, gunStatus } from "@/utils/dict";
import { YTProColumns } from "@/components/ProTable/typing";
import { QrcodeOutlined } from "@ant-design/icons";
import { verifyPhone } from "@/utils/reg";
import { Switch } from "antd";

export const columns: YTProColumns<any, any>[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '登录名' }),
    dataIndex: 'c',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '真实姓名' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '手机号' }),
    dataIndex: 'e',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '地址' }),
    dataIndex: 'g',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '订购日期' }),
    dataIndex: 'h',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '金额' }),
    dataIndex: 'i',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '分成者状态' }),
    dataIndex: 'k',
    valueEnum: enableStatus,
    width: 120,
    ellipsis: true,
    hideInSearch: true,
    renderWithEmit: (_, { status, emit }) => {
      return <Switch
        checked={status}
        onChange={() => emit('status', { status })}
      />
    },
  },
];

export const formColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '真实姓名' }),
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
    title: formatMessage({ id: '', defaultMessage: '手机号' }),
    dataIndex: 'a',
    formItemProps: {
      rules: [
        {
          required: true,
        },
        () => {
          return {
            validator: (_: any, value: string) => {
              if (isEmpty(value)) {
                return Promise.resolve();
              } else if (verifyPhone(value)) {
                return Promise.resolve();
              } else {
                return Promise.reject(
                  formatMessage({ id: '', defaultMessage: '手机号格式错误' }),
                );
              }
            },
          };
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '登录用户名' }),
    dataIndex: 'c1',
    formItemProps: {
      extra: formatMessage({ id: '', defaultMessage: '新增分成者初始密码: admin123456' }),
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '分成比例' }),
    dataIndex: 'd',
    dependencies: ['remain'],
    valueType: 'digit',
    fieldProps: {
      className: 'w-full',
      max: 100,
    },
    formItemProps: (form) => {
      return {
        extra: formatMessage({ id: '', defaultMessage: `当前剩余分成比例：${form?.getFieldValue?.('remain')}` }),
        rules: [
          {
            required: true,
          },
        ],
      }
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '地址' }),
    dataIndex: 'd1',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '订购日期' }),
    dataIndex: 'c',
    valueType: 'date',
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
    dataIndex: 'remain',
    formItemProps: {
      hidden: true,
    }
  }
];