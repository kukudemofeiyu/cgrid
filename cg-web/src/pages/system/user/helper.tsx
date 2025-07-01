/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-21 10:11:07
 * @LastEditTime: 2025-02-21 10:41:25
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/agent/agent/helper.tsx
 */

import { ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage, isEmpty } from "@/utils";
import { YTProColumns } from "@/components/ProTable/typing";
import { verifyPhone } from "@/utils/reg";

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
    title: formatMessage({ id: '', defaultMessage: '创建时间' }),
    dataIndex: 'g',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '所属岗位' }),
    dataIndex: 'h',
    width: 100,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '用户类型' }),
    dataIndex: 'i',
    width: 100,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '状态' }),
    dataIndex: 'k',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
];

export const formColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '登录用户名' }),
    dataIndex: 'c',
    formItemProps: {
      extra: formatMessage({ id: '', defaultMessage: '用户初始密码: admin123456' }),
      rules: [
        {
          required: true,
        },
      ],
    },
  },
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
];