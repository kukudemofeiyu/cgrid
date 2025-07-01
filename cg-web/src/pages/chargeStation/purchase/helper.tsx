/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-20 16:54:21
 * @LastEditTime: 2025-02-20 17:08:43
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/chargeStation/purchase/helper.tsx
 */

import { ProColumns, ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage } from "@/utils";
import { chargeTypeOption, enableStatus, feeOption } from "@/utils/dict";
import { getAgentCol } from "@/utils/column";

export const columns: ProColumns[] = [
  getAgentCol(),
  {
    title: formatMessage({ id: '', defaultMessage: '设备名称' }),
    dataIndex: 'a',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '设备型号' }),
    dataIndex: 'b',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '设备厂商名称' }),
    dataIndex: 'c',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '采购数量' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '采购金额(元)' }),
    dataIndex: 'f',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '采购日期' }),
    dataIndex: 'g',
    valueType: 'dateRange',
    width: 150,
    ellipsis: true,
    render: (_, record) => record.createTime,
    search: {
      transform: (value) => {
        return {
          startTime: value[0],
          endTime: value[1],
        };
      },
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '采购人' }),
    dataIndex: 'h',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
];

export const formColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '厂商设备型号' }),
    dataIndex: 'a',
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
    title: formatMessage({ id: '', defaultMessage: '采购数量' }),
    dataIndex: 'b',
    valueType: 'digit',
    fieldProps: {
      precision: 0,
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
    title: formatMessage({ id: '', defaultMessage: '采购金额(元)' }),
    dataIndex: 'c',
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
    title: formatMessage({ id: '', defaultMessage: '采购日期' }),
    dataIndex: 'd',
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
    title: formatMessage({ id: '', defaultMessage: '采购人' }),
    dataIndex: 'e',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
];