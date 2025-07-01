/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-14 00:28:59
 * @LastEditTime: 2023-11-29 11:08:35
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\SelfEmsIndex\SystemSetting\config.ts
 */

import type { DetailItem } from '@/components/Detail';
import { formatMessage } from '@/utils';
import { enableFormat, powerFormat } from '@/utils/format';
import type { ProFormColumnsType } from '@ant-design/pro-components';
import moment from 'moment';

const options = [
  {
    value: 1,
    label: formatMessage({ id: 'common.disable', defaultMessage: '禁用' }),
  },
  {
    value: 0,
    label: formatMessage({ id: 'common.enabled', defaultMessage: '使能' }),
  },
];

export const powerParamsItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'device.antiBackflowThreshold', defaultMessage: '防逆流阈值' }),
    field: 'antiBackflowThreshold',
    format: powerFormat,
  },
  {
    label: formatMessage({
      id: 'device.maximumLoadOfTransformer',
      defaultMessage: '变压器最大负荷',
    }),
    field: 'maximumLoadOfTransformer',
    format: powerFormat,
    span: 2,
  },
];

export const powerParamsColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: 'device.antiBackflowThreshold', defaultMessage: '防逆流阈值' }),
    dataIndex: 'antiBackflowThreshold',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.antiBackflowThreshold', defaultMessage: '防逆流阈值' }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.maximumLoadOfTransformer',
      defaultMessage: '变压器最大负荷',
    }),
    dataIndex: 'maximumLoadOfTransformer',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.maximumLoadOfTransformer',
              defaultMessage: '变压器最大负荷',
            }),
        },
      ],
    },
  },
];

export const emsSystemEnabletems: DetailItem[] = [
  {
    label: formatMessage({
      id: 'device.selfStartFunctionEnabled',
      defaultMessage: '自启动功能使能',
    }),
    field: 'SysEnableSelfStartFunction',
    format: enableFormat,
  },
  {
    label: formatMessage({
      id: 'device.mainsChargeFunctionEnables',
      defaultMessage: '市电充电功能使能',
    }),
    field: 'MunicipalChargingFunctionEnabled',
    format: enableFormat,
  },
  {
    label: formatMessage({
      id: 'device.enablingFffGridWorkFunction',
      defaultMessage: '离网工作功能使能',
    }),
    field: 'EnableOffGridOperationFunction',
    format: enableFormat,
  },
  {
    label: formatMessage({
      id: 'device.enablingGridConnectionFunction',
      defaultMessage: '并网工作功能使能',
    }),
    field: 'EnableGridConnectionFunction',
    format: enableFormat,
  },
];
export const systemColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({
      id: 'device.selfStartFunctionEnabled',
      defaultMessage: '自启动功能使能',
    }),
    dataIndex: 'SysEnableSelfStartFunction',
    valueType: 'select',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }) +
            formatMessage({
              id: 'device.selfStartFunctionEnabled',
              defaultMessage: '自启动功能使能',
            }),
        },
      ],
    },
    fieldProps: {
      options,
    },
  },
  {
    title: formatMessage({
      id: 'device.mainsChargeFunctionEnables',
      defaultMessage: '市电充电功能使能',
    }),
    dataIndex: 'MunicipalChargingFunctionEnabled',
    valueType: 'select',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }) +
            formatMessage({
              id: 'device.mainsChargeFunctionEnables',
              defaultMessage: '市电充电功能使能',
            }),
        },
      ],
    },
    fieldProps: {
      options,
    },
  },
  {
    title: formatMessage({
      id: 'device.enablingFffGridWorkFunction',
      defaultMessage: '离网工作功能使能',
    }),
    dataIndex: 'EnableOffGridOperationFunction',
    valueType: 'select',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }) +
            formatMessage({
              id: 'device.enablingFffGridWorkFunction',
              defaultMessage: '离网工作功能使能',
            }),
        },
      ],
    },
    fieldProps: {
      options,
    },
  },
  {
    title: formatMessage({
      id: 'device.enablingGridConnectionFunction',
      defaultMessage: '并网工作功能使能',
    }),
    dataIndex: 'EnableGridConnectionFunction',
    valueType: 'select',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }) +
            formatMessage({
              id: 'device.enablingGridConnectionFunction',
              defaultMessage: '并网工作功能使能',
            }),
        },
      ],
    },
    fieldProps: {
      options,
    },
  },
];

export const systemTimeItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'common.systemTime', defaultMessage: '系统时间' }),
    field: 'correctionTime',
    format: (time) => moment(time).format('YYYY-MM-DD HH:mm:ss'),
  },
];

export const systemTimeColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: 'common.systemTime', defaultMessage: '系统时间' }),
    dataIndex: 'correctionTime',
    valueType: 'dateTime',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }) +
            formatMessage({ id: 'common.systemTime', defaultMessage: '系统时间' }),
        },
      ],
    },
    colProps: {
      span: 24,
    },
    width: '100%',
  },
];
