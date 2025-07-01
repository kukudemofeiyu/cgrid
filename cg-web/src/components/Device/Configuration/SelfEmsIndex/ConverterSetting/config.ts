/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-14 00:28:59
 * @LastEditTime: 2023-12-18 13:41:23
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\SelfEmsIndex\ConverterSetting\config.ts
 */

import type { DetailItem } from '@/components/Detail';
import { formatMessage } from '@/utils';
import { currentFormat, powerFormat, tempFormat, voltageFormat } from '@/utils/format';
import type { ProFormColumnsType } from '@ant-design/pro-components';

export const protectParamsItems: DetailItem[] = [
  {
    label: formatMessage({
      id: 'device.generalChargingPowerLimit',
      defaultMessage: '一般充电功率限值',
    }),
    field: 'generalChargingPowerLimit',
    format: powerFormat,
  },
  {
    label: formatMessage({
      id: 'device.severeChargingPowerLimit',
      defaultMessage: '严重充电功率限值',
    }),
    field: 'severeChargingPowerLimit',
    format: powerFormat,
  },
  {
    label: formatMessage({
      id: 'device.generalInverterPowerLimits',
      defaultMessage: '一般逆变功率限值',
    }),
    field: 'generalInverterPowerLimit',
    format: powerFormat,
  },
  {
    label: formatMessage({
      id: 'device.severeInverterPowerLimitation',
      defaultMessage: '严重逆变功率限制',
    }),
    field: 'severeInverterPowerLimitation',
    format: powerFormat,
  },
  {
    label: formatMessage({
      id: 'device.converterGeneralOvervoltageLimit',
      defaultMessage: '一般过压限值',
    }),
    field: 'converterGeneralOvervoltageLimit',
    format: voltageFormat,
  },
  {
    label: formatMessage({
      id: 'device.converterSeriouslyOvervoltageLimit',
      defaultMessage: '严重过压限值',
    }),
    field: 'converterSevereOvervoltageLimit',
    format: voltageFormat,
  },
  {
    label: formatMessage({
      id: 'device.converterGeneralUndervoltageLimit',
      defaultMessage: '一般欠压限值',
    }),
    field: 'converterGeneralUndervoltageLimit',
    format: voltageFormat,
  },
  {
    label: formatMessage({
      id: 'device.converterSeverelyUndervoltageLimit',
      defaultMessage: '严重欠压限值',
    }),
    field: 'converterSevereUndervoltageLimit',
    format: voltageFormat,
  },
  {
    label: formatMessage({
      id: 'device.converterGeneralChargeCurrentLimit',
      defaultMessage: '一般充电电流限值',
    }),
    field: 'converterGeneralChargingCurrentLimit',
    format: currentFormat,
  },
  {
    label: formatMessage({
      id: 'device.converterSevereChargeCurrentLimit',
      defaultMessage: '严重充电电流限值',
    }),
    field: 'converterSevereChargingCurrentLimit',
    format: currentFormat,
  },
  {
    label: formatMessage({
      id: 'device.generalInverterCurrentLimitValue',
      defaultMessage: '一般逆变电流限值',
    }),
    field: 'generalInverterCurrentLimit',
    format: currentFormat,
  },
  {
    label: formatMessage({
      id: 'device.seriousInverterCurrentLimit',
      defaultMessage: '严重逆变电流限值',
    }),
    field: 'severeInverterCurrentLimit',
    format: currentFormat,
  },
  {
    label: formatMessage({
      id: 'device.converterGeneralOvertemperatureLimit',
      defaultMessage: '一般过温限值',
    }),
    field: 'converterGeneralOverTemperatureLimit',
    format: tempFormat,
  },
  {
    label: formatMessage({
      id: 'device.converterSevereOvertemperatureLimit',
      defaultMessage: '严重过温限值',
    }),
    field: 'converterSevereOverTemperatureLimit',
    format: tempFormat,
  },
  {
    label: formatMessage({
      id: 'device.converterGeneralLowTemperatureLimit',
      defaultMessage: '一般低温限值',
    }),
    field: 'converterGeneralLowTemperatureLimit',
    format: tempFormat,
  },
  {
    label: formatMessage({
      id: 'device.converterSevereLowTemperatureLimit',
      defaultMessage: '严重低温限值',
    }),
    field: 'converterSevereLowTemperatureLimit',
    format: tempFormat,
  },
];

export const protectParamsColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({
      id: 'device.generalChargingPowerLimit',
      defaultMessage: '一般充电功率限值',
    }),
    dataIndex: 'generalChargingPowerLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.generalChargingPowerLimit',
              defaultMessage: '一般充电功率限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.severeChargingPowerLimit',
      defaultMessage: '严重充电功率限值',
    }),
    dataIndex: 'severeChargingPowerLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.severeChargingPowerLimit',
              defaultMessage: '严重充电功率限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.generalInverterPowerLimits',
      defaultMessage: '一般逆变功率限值',
    }),
    dataIndex: 'generalInverterPowerLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.generalInverterPowerLimits',
              defaultMessage: '一般逆变功率限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.severeInverterPowerLimitation',
      defaultMessage: '严重逆变功率限制',
    }),
    dataIndex: 'severeInverterPowerLimitation',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.severeInverterPowerLimitation',
              defaultMessage: '严重逆变功率限制',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.converterGeneralOvervoltageLimit',
      defaultMessage: '一般过压限值',
    }),
    dataIndex: 'converterGeneralOvervoltageLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.converterGeneralOvervoltageLimit',
              defaultMessage: '一般过压限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.converterSeriouslyOvervoltageLimit',
      defaultMessage: '严重过压限值',
    }),
    dataIndex: 'converterSevereOvervoltageLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.converterSeriouslyOvervoltageLimit',
              defaultMessage: '严重过压限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.converterGeneralUndervoltageLimit',
      defaultMessage: '一般欠压限值',
    }),
    dataIndex: 'converterGeneralUndervoltageLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.converterGeneralUndervoltageLimit',
              defaultMessage: '一般欠压限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.converterSeverelyUndervoltageLimit',
      defaultMessage: '严重欠压限值',
    }),
    dataIndex: 'converterSevereUndervoltageLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.converterSeverelyUndervoltageLimit',
              defaultMessage: '严重欠压限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.converterGeneralChargeCurrentLimit',
      defaultMessage: '一般充电电流限值',
    }),
    dataIndex: 'converterGeneralChargingCurrentLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.converterGeneralChargeCurrentLimit',
              defaultMessage: '一般充电电流限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.converterSevereChargeCurrentLimit',
      defaultMessage: '严重充电电流限值',
    }),
    dataIndex: 'converterSevereChargingCurrentLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.converterSevereChargeCurrentLimit',
              defaultMessage: '严重充电电流限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.generalInverterCurrentLimitValue',
      defaultMessage: '一般逆变电流限值',
    }),
    dataIndex: 'generalInverterCurrentLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.generalInverterCurrentLimitValue',
              defaultMessage: '一般逆变电流限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.seriousInverterCurrentLimit',
      defaultMessage: '严重逆变电流限值',
    }),
    dataIndex: 'severeInverterCurrentLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.seriousInverterCurrentLimit',
              defaultMessage: '严重逆变电流限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.converterGeneralOvertemperatureLimit',
      defaultMessage: '一般过温限值',
    }),
    dataIndex: 'converterGeneralOverTemperatureLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.converterGeneralOvertemperatureLimit',
              defaultMessage: '一般过温限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.converterSevereOvertemperatureLimit',
      defaultMessage: '严重过温限值',
    }),
    dataIndex: 'converterSevereOverTemperatureLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.converterSevereOvertemperatureLimit',
              defaultMessage: '严重过温限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.converterGeneralLowTemperatureLimit',
      defaultMessage: '一般低温限值',
    }),
    dataIndex: 'converterGeneralLowTemperatureLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.converterGeneralLowTemperatureLimit',
              defaultMessage: '一般低温限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.converterSevereLowTemperatureLimit',
      defaultMessage: '严重低温限值',
    }),
    dataIndex: 'converterSevereLowTemperatureLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.converterSevereLowTemperatureLimit',
              defaultMessage: '严重低温限值',
            }),
        },
      ],
    },
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
