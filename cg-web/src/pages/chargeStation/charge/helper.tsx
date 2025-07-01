/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-20 15:47:09
 * @LastEditTime: 2025-02-20 16:08:52
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/chargeStation/charge/helper.tsx
 */

import { ProColumns, ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage } from "@/utils";
import { chargeTypeOption, enableStatus, feeOption } from "@/utils/dict";

export const columns: ProColumns[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '充电桩编号' }),
    dataIndex: 'a',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电桩名称' }),
    dataIndex: 'b',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充电桩类型' }),
    dataIndex: 'c',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '站点名称' }),
    dataIndex: 'd',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '所属代理商' }),
    dataIndex: 'e',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '设备型号' }),
    dataIndex: 'f',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '状态' }),
    dataIndex: 'g',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '运行状态' }),
    dataIndex: 'h',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '总耗电量' }),
    dataIndex: 'i',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '创建时间' }),
    dataIndex: 'j',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '是否收费' }),
    dataIndex: 'k',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
];

export const formColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '充电桩编号' }),
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
    title: formatMessage({ id: '', defaultMessage: '充电桩名称' }),
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
    title: formatMessage({ id: '', defaultMessage: '充电桩类型' }),
    dataIndex: 'c',
    valueType: 'select',
    valueEnum: chargeTypeOption,
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '电流信息(A)' }),
    dataIndex: 'd',
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
    title: formatMessage({ id: '', defaultMessage: '电压信息(V)' }),
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
    title: formatMessage({ id: '', defaultMessage: '枪口数' }),
    dataIndex: 'f',
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
    title: formatMessage({ id: '', defaultMessage: '最大功率(KW)' }),
    dataIndex: 'g',
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
    title: formatMessage({ id: '', defaultMessage: '状态' }),
    dataIndex: 'h',
    valueType: 'select',
    valueEnum: enableStatus,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '所属代理商' }),
    dataIndex: 'i',
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
    title: formatMessage({ id: '', defaultMessage: '所属站点' }),
    dataIndex: 'j',
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
    title: formatMessage({ id: '', defaultMessage: '是否收费' }),
    dataIndex: 'k',
    valueType: 'select',
    valueEnum: feeOption,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '厂商设备型号' }),
    dataIndex: 'k',
    valueType: 'select',
  },
];