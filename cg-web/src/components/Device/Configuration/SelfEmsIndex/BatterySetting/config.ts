/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-14 00:28:59
 * @LastEditTime: 2023-12-18 11:26:45
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\SelfEmsIndex\BatterySetting\config.ts
 */

import type { DetailItem } from '@/components/Detail';
import { currentFormat, powerFormat, tempFormat, voltageFormat } from '@/utils/format';
import type { ProFormColumnsType } from '@ant-design/pro-components';
import { enableFormat } from '@/utils/format';
import { formatMessage } from '@/utils';

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

export const protectParamsItems: DetailItem[] = [
  {
    label: formatMessage({
      id: 'device.singleMaximumChargingVoltage',
      defaultMessage: '单体最高允许充电电压',
    }),
    field: 'maxAllowableChargingVoltage',
    format: voltageFormat,
  },
  {
    label: formatMessage({
      id: 'device.overvoltageOvervoltageEliminationValue',
      defaultMessage: '单体过压故障消除回差值',
    }),
    field: 'overVoltageErrorClearDifference',
    format: voltageFormat,
  },
  {
    label: formatMessage({
      id: 'device.singleMinimumChargingVoltage',
      defaultMessage: '单体最低允许充电电压',
    }),
    field: 'minAllowableChargingVoltage',
    format: voltageFormat,
  },
  {
    label: formatMessage({
      id: 'device.overvoltageUndervoltageEliminationValue',
      defaultMessage: '单体欠压故障消除回差值',
    }),
    field: 'lowVoltageErrorClearDifference',
    format: voltageFormat,
  },
  {
    label: formatMessage({
      id: 'device.maximumAllowableTemperatureCell',
      defaultMessage: '单体最高允许温度',
    }),
    field: 'maxAllowableTemperature',
    format: tempFormat,
  },
  {
    label: formatMessage({
      id: 'device.cellOvertemperatureEliminationDifference',
      defaultMessage: '单体过温故障消除回差值',
    }),
    field: 'overTempErrorClearDifference',
    format: tempFormat,
  },
  {
    label: formatMessage({
      id: 'device.minimumAllowableTemperatureCell',
      defaultMessage: '单体最低允许温度',
    }),
    field: 'minAllowableTemp',
    format: tempFormat,
  },
  {
    label: formatMessage({
      id: 'device.undertemperatureEliminationDifference',
      defaultMessage: '单体欠温故障消除回差值',
    }),
    field: 'lowTempErrorClearDifference',
    format: tempFormat,
  },
  {
    label: formatMessage({
      id: 'device.maximumAllowableVoltageBattery',
      defaultMessage: '电池组最高允许电压',
    }),
    field: 'maxAllowableVoltageOfBatteryPack',
    format: voltageFormat,
  },
  {
    label: formatMessage({
      id: 'device.batteryStringOvervoltageErrorDifference',
      defaultMessage: '电池组过压故障回差值',
    }),
    field: 'overVoltageErrorDifferenceOfBatteryPack',
    format: voltageFormat,
  },
  {
    label: formatMessage({
      id: 'device.maximumAllowableChargingCurrentBattery',
      defaultMessage: '电池组最高允许充电电流',
    }),
    field: 'maxAllowableChargingCurrentOfBatteryPack',
    format: currentFormat,
  },
  {
    label: formatMessage({
      id: 'device.maximumAllowableDischargingCurrentBattery',
      defaultMessage: '电池组最高允许放电电流',
    }),
    field: 'maxAllowableDischargeCurrentOfBatteryPack',
    format: currentFormat,
  },
  {
    label: formatMessage({ id: 'device.generalOverpressureLimit', defaultMessage: '一般过压限值' }),
    field: 'generalOvervoltageLimit',
    format: voltageFormat,
  },
  {
    label: formatMessage({ id: 'device.severeOvervoltageLimit', defaultMessage: '严重过压限值' }),
    field: 'severeOvervoltageLimit',
    format: voltageFormat,
  },
  {
    label: formatMessage({ id: 'device.generalUndervoltageLimit', defaultMessage: '一般欠压限值' }),
    field: 'generalUndervoltageLimit',
    format: voltageFormat,
  },
  {
    label: formatMessage({ id: 'device.severeUndervoltageLimit', defaultMessage: '严重欠压限值' }),
    field: 'severeUndervoltageLimit',
    format: voltageFormat,
  },
  {
    label: formatMessage({
      id: 'device.generalChargingCurrentLimit',
      defaultMessage: '一般充电电流限值',
    }),
    field: 'generalChargingCurrentLimit',
    format: currentFormat,
  },
  {
    label: formatMessage({
      id: 'device.severeChargingCurrentLimit',
      defaultMessage: '严重充电电流限值',
    }),
    field: 'severeChargingCurrentLimit',
    format: currentFormat,
  },
  {
    label: formatMessage({
      id: 'device.generalDischargingCurrentLimit',
      defaultMessage: '一般放电电流限值',
    }),
    field: 'generalDischargeCurrentLimit',
    format: currentFormat,
  },
  {
    label: formatMessage({
      id: 'device.severeDischargeCurrentLimit',
      defaultMessage: '严重放电电流限值',
    }),
    field: 'severeDischargeCurrentLimit',
    format: currentFormat,
  },
  {
    label: formatMessage({
      id: 'device.generalOvertemperatureLimit',
      defaultMessage: '一般过温限值',
    }),
    field: 'generalOverTemperatureLimit',
    format: tempFormat,
  },
  {
    label: formatMessage({
      id: 'device.severeOvertemperatureLimit',
      defaultMessage: '严重过温限值',
    }),
    field: 'severeOverTemperatureLimit',
    format: tempFormat,
  },
  {
    label: formatMessage({
      id: 'device.generalLowTemperatureLimit',
      defaultMessage: '一般低温限值',
    }),
    field: 'generalLowTemperatureLimit',
    format: tempFormat,
  },
  {
    label: formatMessage({
      id: 'device.severeLowTemperatureLimit',
      defaultMessage: '严重低温限值',
    }),
    field: 'severeLowTemperatureLimit',
    format: tempFormat,
  },
];
export const protectParamsColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({
      id: 'device.singleMaximumChargingVoltage',
      defaultMessage: '单体最高允许充电电压',
    }),
    dataIndex: 'maxAllowableChargingVoltage',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.singleMaximumChargingVoltage',
              defaultMessage: '单体最高允许充电电压',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.overvoltageOvervoltageEliminationValue',
      defaultMessage: '单体过压故障消除回差值',
    }),
    dataIndex: 'overVoltageErrorClearDifference',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.overvoltageOvervoltageEliminationValue',
              defaultMessage: '单体过压故障消除回差值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.singleMinimumChargingVoltage',
      defaultMessage: '单体最低允许充电电压',
    }),
    dataIndex: 'minAllowableChargingVoltage',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.singleMinimumChargingVoltage',
              defaultMessage: '单体最低允许充电电压',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.overvoltageUndervoltageEliminationValue',
      defaultMessage: '单体欠压故障消除回差值',
    }),
    dataIndex: 'lowVoltageErrorClearDifference',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.overvoltageUndervoltageEliminationValue',
              defaultMessage: '单体欠压故障消除回差值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.maximumAllowableTemperatureCell',
      defaultMessage: '单体最高允许温度',
    }),
    dataIndex: 'maxAllowableTemperature',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.maximumAllowableTemperatureCell',
              defaultMessage: '单体最高允许温度',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.cellOvertemperatureEliminationDifference',
      defaultMessage: '单体过温故障消除回差值',
    }),
    dataIndex: 'overTempErrorClearDifference',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.cellOvertemperatureEliminationDifference',
              defaultMessage: '单体过温故障消除回差值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.minimumAllowableTemperatureCell',
      defaultMessage: '单体最低允许温度',
    }),
    dataIndex: 'minAllowableTemp',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.minimumAllowableTemperatureCell',
              defaultMessage: '单体单体最低允许温度过温故障消除回差值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.undertemperatureEliminationDifference',
      defaultMessage: '单体欠温故障消除回差值',
    }),
    dataIndex: 'lowTempErrorClearDifference',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.undertemperatureEliminationDifference',
              defaultMessage: '单体欠温故障消除回差值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.maximumAllowableVoltageBattery',
      defaultMessage: '电池组最高允许电压',
    }),
    dataIndex: 'maxAllowableVoltageOfBatteryPack',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.maximumAllowableVoltageBattery',
              defaultMessage: '电池组最高允许电压',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.batteryStringOvervoltageErrorDifference',
      defaultMessage: '电池组过压故障回差值',
    }),
    dataIndex: 'overVoltageErrorDifferenceOfBatteryPack',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.batteryStringOvervoltageErrorDifference',
              defaultMessage: '电池组过压故障回差值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.maximumAllowableChargingCurrentBattery',
      defaultMessage: '电池组最高允许充电电流',
    }),
    dataIndex: 'maxAllowableChargingCurrentOfBatteryPack',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.maximumAllowableChargingCurrentBattery',
              defaultMessage: '电池组最高允许充电电流',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.maximumAllowableDischargingCurrentBattery',
      defaultMessage: '电池组最高允许放电电流',
    }),
    dataIndex: 'maxAllowableDischargeCurrentOfBatteryPack',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.maximumAllowableDischargingCurrentBattery',
              defaultMessage: '电池组最高允许放电电流',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({ id: 'device.generalOverpressureLimit', defaultMessage: '一般过压限值' }),
    dataIndex: 'generalOvervoltageLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.generalOverpressureLimit',
              defaultMessage: '一般过压限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({ id: 'device.severeOvervoltageLimit', defaultMessage: '严重过压限值' }),
    dataIndex: 'severeOvervoltageLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.severeOvervoltageLimit', defaultMessage: '严重过压限值' }),
        },
      ],
    },
  },
  {
    title: formatMessage({ id: 'device.generalUndervoltageLimit', defaultMessage: '一般欠压限值' }),
    dataIndex: 'generalUndervoltageLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.generalUndervoltageLimit',
              defaultMessage: '一般欠压限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({ id: 'device.severeUndervoltageLimit', defaultMessage: '严重欠压限值' }),
    dataIndex: 'severeUndervoltageLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.severeUndervoltageLimit', defaultMessage: '严重欠压限值' }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.generalChargingCurrentLimit',
      defaultMessage: '一般充电电流限值',
    }),
    dataIndex: 'generalChargingCurrentLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.generalChargingCurrentLimit',
              defaultMessage: '一般充电电流限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.severeChargingCurrentLimit',
      defaultMessage: '严重充电电流限值',
    }),
    dataIndex: 'severeChargingCurrentLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.severeChargingCurrentLimit',
              defaultMessage: '严重充电电流限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.generalDischargingCurrentLimit',
      defaultMessage: '一般放电电流限值',
    }),
    dataIndex: 'generalDischargeCurrentLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.generalDischargingCurrentLimit',
              defaultMessage: '一般放电电流限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.severeDischargeCurrentLimit',
      defaultMessage: '严重放电电流限值',
    }),
    dataIndex: 'severeDischargeCurrentLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.severeDischargeCurrentLimit',
              defaultMessage: '严重放电电流限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.generalOvertemperatureLimit',
      defaultMessage: '一般过温限值',
    }),
    dataIndex: 'generalOverTemperatureLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.generalOvertemperatureLimit',
              defaultMessage: '一般过温限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.severeOvertemperatureLimit',
      defaultMessage: '严重过温限值',
    }),
    dataIndex: 'severeOverTemperatureLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.severeOvertemperatureLimit',
              defaultMessage: '严重过温限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.generalLowTemperatureLimit',
      defaultMessage: '一般低温限值',
    }),
    dataIndex: 'generalLowTemperatureLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.generalLowTemperatureLimit',
              defaultMessage: '一般低温限值',
            }),
        },
      ],
    },
  },
  {
    title: formatMessage({
      id: 'device.severeLowTemperatureLimit',
      defaultMessage: '严重低温限值',
    }),
    dataIndex: 'severeLowTemperatureLimit',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.severeLowTemperatureLimit',
              defaultMessage: '严重低温限值',
            }),
        },
      ],
    },
  },
];

export const powerParamsItems: DetailItem[] = [
  {
    label: formatMessage({
      id: 'device.batterySystemSelfStartEnabled',
      defaultMessage: '电池系统自启动功能使能',
    }),
    field: 'EnableBatterySystemSelfStartFunction',
    format: enableFormat,
  },
];

export const powerParamsColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({
      id: 'device.batterySystemSelfStartEnabled',
      defaultMessage: '电池系统自启动功能使能',
    }),
    dataIndex: 'EnableBatterySystemSelfStartFunction',
    valueType: 'select',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({
              id: 'device.batterySystemSelfStartEnabled',
              defaultMessage: '电池系统自启动功能使能',
            }),
        },
      ],
    },
    fieldProps: {
      options,
    },
    colProps: {
      span: 24,
    },
  },
];
