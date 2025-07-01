/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-14 00:28:59
 * @LastEditTime: 2023-12-06 10:10:53
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\BatterryStack\Stack\config.ts
 */

import type { DetailItem } from '@/components/Detail';
import type { ProColumns } from '@ant-design/pro-components';
import {
  chargeFormat,
  closeFormat,
  currentFormat,
  voltageFormat,
  percentageFormat,
  kohmFormat,
  powerHourFormat,
  tempFormat,
  faultFormat,
  singleFormat,
  doorFormat,
  hydrogenFormat,
  contactorFormat,
  alarmArrFormat,
} from '@/utils/format';
import { MaxUnitType } from './type';
import { formatMessage } from '@/utils';
import { DeviceDataType } from '@/services/equipment';
import { DeviceTypeEnum } from '@/utils/dictionary';

export const getControlItems = (data?: DeviceDataType) => {
  const result: DetailItem[] = [
    {
      label: formatMessage({
        id: 'siteMonitor.prechargeContactorStatus',
        defaultMessage: '预充接触器状态',
      }),
      field: 'PrechargeContactorStatus',
      format: closeFormat,
      show: data?.productId != DeviceTypeEnum.LiquidEnergyBatteryStack,
    },
    {
      label: formatMessage({
        id: 'siteMonitor.dcCircuitBreakerStatus',
        defaultMessage: '直流断路器状态',
      }),
      field: 'DCCircuitBreakerStatus',
      format: closeFormat,
    },
    {
      label: formatMessage({
        id: 'siteMonitor.acCircuitBreakerStatus',
        defaultMessage: '交流断路器状态',
      }),
      field: 'ACCircuitBreakerStatus',
      format: closeFormat,
    },
  ];
  return result;
};

export const controlItemsMain: DetailItem[] = [
  {
    label: formatMessage({ id: 'siteMonitor.mainContactorStatus', defaultMessage: '主接触器状态' }),
    field: 'contactorStatus',
    format: contactorFormat,
  },
];

export const controlItemsMainYT: DetailItem[] = [
  {
    label: formatMessage({ id: 'siteMonitor.mainContactorStatus', defaultMessage: '主接触器状态' }),
    field: 'bmsOpenAndClose',
    format: closeFormat,
  },
];

export const controlItemsMainLiquid: DetailItem[] = [
  {
    label: formatMessage({ id: 'siteMonitor.mainContactorStatus', defaultMessage: '主接触器状态' }),
    field: 'MainContactorStatus',
    format: closeFormat,
  },
];

export const controlItemsTow: DetailItem[] = [
  {
    label: formatMessage({ id: 'siteMonitor.externalFaultStatus', defaultMessage: '对外故障' }),
    field: 'externalFaultStatus',
    format: alarmArrFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.chargeDischargeIndication',
      defaultMessage: '充放电指示',
    }),
    field: 'CADI',
    format: chargeFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.firstLevelAlarm', defaultMessage: '一级报警' }),
    field: 'firstLevelAlarm',
    format: alarmArrFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.secondLevelAlarm', defaultMessage: '二级报警' }),
    field: 'secondLevelAlarm',
    format: alarmArrFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.threeLevelAlarm', defaultMessage: '三级报警' }),
    field: 'threeLevelAlarm',
    format: alarmArrFormat,
  },
];

export const statusItemsOne: DetailItem[] = [
  {
    label: formatMessage({ id: 'siteMonitor.current', defaultMessage: '电流' }),
    field: 'TotalBatteryCurrent',
    format: currentFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.voltage', defaultMessage: '电压' }),
    field: 'TotalBatteryVoltage',
    format: voltageFormat,
  },
  { label: 'SOC', field: 'SOC', format: percentageFormat },
  { label: 'SOH', field: 'SOH', format: percentageFormat },
  {
    label: formatMessage({ id: 'siteMonitor.insulationValue', defaultMessage: '绝缘值' }),
    field: 'InsulationValue',
    format: kohmFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.positiveInsulationValue',
      defaultMessage: '正极绝缘值',
    }),
    field: 'PositiveInsulationValue',
    format: kohmFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.negativeInsulationValue',
      defaultMessage: '负极绝缘值',
    }),
    field: 'NegativeInsulationValue',
    format: kohmFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.averageVoltage', defaultMessage: '平均电压' }),
    field: 'AverageVoltage',
    format: voltageFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.prechargeTotalPressure', defaultMessage: '预充总压' }),
    field: 'TPCP',
    format: voltageFormat,
  },
];

export const statusItemsH2: DetailItem[] = [
  {
    label: formatMessage({ id: 'siteMonitor.hydrogenConcentration', defaultMessage: '氢气浓度' }),
    field: 'HydrogenConcentration',
    format: hydrogenFormat,
  },
];

export const statusItemsTow: DetailItem[] = [
  {
    label:
      'BMU1-10' +
      formatMessage({ id: 'siteMonitor.communicationState', defaultMessage: '通信状态' }),
    field: 'BMU1CS',
    format: faultFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.accessControlStatus', defaultMessage: '门禁状态' }),
    field: 'AccessControlStatus',
    format: doorFormat,
  },
];

export const statusItemsWaterMine: DetailItem[] = [
  {
    label: formatMessage({ id: 'device.waterImmersionSignal', defaultMessage: '水浸信号' }),
    field: 'waterImmersionSignal',
    format: faultFormat,
  },
  {
    label: formatMessage({
      id: 'device.lightningArresterFailure',
      defaultMessage: '交流防雷器故障',
    }),
    field: 'aCLightningArresterFailure',
    format: faultFormat,
  },
  {
    label: formatMessage({
      id: 'device.abnormalDisconnectionSwitch',
      defaultMessage: '隔开开关异常断开',
    }),
    field: 'abnormalDisconnectionOfTheIsolationSwitch',
    format: faultFormat,
  },
];

export const historyItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'siteMonitor.singleCharge', defaultMessage: '单次充电量' }),
    field: 'LastChargeLevel',
    format: powerHourFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.singleDischarge', defaultMessage: '单次放电量' }),
    field: 'LastDischargeCapacity',
    format: powerHourFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.cumulativeCharge', defaultMessage: '累计充电量' }),
    field: 'ACC',
    format: powerHourFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.cumulativeDischarge', defaultMessage: '累计放电量' }),
    field: 'ADC',
    format: powerHourFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.numberBatteryCabinetDoors',
      defaultMessage: '电池柜开门次数',
    }),
    field: 'NOBCDO',
  },
];

export const tempItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'siteMonitor.averageTemperature', defaultMessage: '平均温度' }),
    field: 'AverageTemperature',
    format: tempFormat,
  },
  {
    label:
      formatMessage({ id: 'siteMonitor.highboxTemperature', defaultMessage: '高压箱温度' }) + '1',
    field: 'HighPressureBoxTemperature1',
    format: tempFormat,
  },
  {
    label:
      formatMessage({ id: 'siteMonitor.highboxTemperature', defaultMessage: '高压箱温度' }) + '2',
    field: 'HighPressureBoxTemperature2',
    format: tempFormat,
  },
  {
    label:
      formatMessage({ id: 'siteMonitor.highboxTemperature', defaultMessage: '高压箱温度' }) + '3',
    field: 'HighPressureBoxTemperature3',
    format: tempFormat,
  },
  {
    label:
      formatMessage({ id: 'siteMonitor.highboxTemperature', defaultMessage: '高压箱温度' }) + '4',
    field: 'HighPressureBoxTemperature4',
    format: tempFormat,
  },
];

export const abilityItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'siteMonitor.rechargeableCapacity', defaultMessage: '可充电量' }),
    field: 'RechargeableCapacity',
    format: powerHourFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.dischargeCapacity', defaultMessage: '可放电量' }),
    field: 'DischargeableCapacity',
    format: powerHourFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.maximumChargingCurrent',
      defaultMessage: '最大充电电流',
    }),
    field: 'MaximumChargingCurrent',
    format: currentFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.maximumDischargingCurrent',
      defaultMessage: '最大放电电流',
    }),
    field: 'maximumDischargeCurrent',
    format: currentFormat,
  },
];

export const maxUnitColumns: ProColumns<MaxUnitType>[] = [
  {
    title:
      formatMessage({ id: 'siteMonitor.maximumCellVoltage', defaultMessage: '最高电芯电压' }) +
      '（V）',
    dataIndex: 'maxVoltage',
    width: 150,
    ellipsis: true,
  },
  {
    title:
      formatMessage({ id: 'siteMonitor.minimumCellVoltage', defaultMessage: '最低电芯电压' }) +
      '（V）',
    dataIndex: 'minVoltage',
    width: 150,
    ellipsis: true,
  },
  {
    title:
      formatMessage({ id: 'siteMonitor.maximumTemperaturePoint', defaultMessage: '最高温度点' }) +
      '（℃）',
    dataIndex: 'maxTemp',
    width: 150,
    ellipsis: true,
  },
  {
    title:
      formatMessage({ id: 'siteMonitor.minimumTemperaturePoint', defaultMessage: '最低温度点' }) +
      '（℃）',
    dataIndex: 'minTemp',
    width: 150,
    ellipsis: true,
  },
];
