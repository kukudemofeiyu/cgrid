/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-20 16:46:09
 * @LastEditTime: 2025-02-20 16:46:10
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/chargeStation/factory/helper.tsx
 */

import { ProColumns, ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage, isEmpty } from "@/utils";
import { agreementOption, chargeTypeOption, enableStatus, feeOption } from "@/utils/dict";
import { verifyPhone } from "@/utils/reg";

export const columns: ProColumns[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '代理商' }),
    dataIndex: 'a',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '设备厂商名称' }),
    dataIndex: 'b',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '联系人' }),
    dataIndex: 'c',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '联系电话' }),
    dataIndex: 'd',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '设备型号' }),
    dataIndex: 'e',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '设备名称' }),
    dataIndex: 'f',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '协议类型' }),
    dataIndex: 'g',
    width: 100,
    ellipsis: true,
  },
];

export const formColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '所属代理商' }),
    dataIndex: 'g',
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
    title: formatMessage({ id: '', defaultMessage: '设备厂商名称' }),
    dataIndex: 'a',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '联系人' }),
    dataIndex: 'b',
  },
  {
    title: formatMessage({ id: '', defaultMessage: '联系电话' }),
    dataIndex: 'c',
    formItemProps: {
      rules: [
        () => {
          return {
            validator: (_: any, value: string) => {
              if (isEmpty(value)) {
                return Promise.resolve();
              } else if (verifyPhone(value)) {
                return Promise.resolve();
              } else {
                return Promise.reject(
                  formatMessage({ id: '', defaultMessage: '电话格式错误' }),
                );
              }
            },
          };
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '设备名称' }),
    dataIndex: 'd',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '设备型号' }),
    dataIndex: 'e',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '协议类型' }),
    dataIndex: 'f',
    valueType: 'select',
    valueEnum: agreementOption,
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
];