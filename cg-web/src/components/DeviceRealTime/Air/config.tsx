/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-14 14:25:54
 * @LastEditTime: 2023-09-20 11:25:26
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\Air\config.tsx
 */

import type { DetailItem } from '@/components/Detail';
import { formatMessage } from '@/utils';
import {
  percentageFormat,
  tempFormat,
  openFormat,
  airsetFormat,
  airAlarmFormat,
  airWorkFormat,
  airSwitchFormat,
  airRunFormat,
} from '@/utils/format';

export const controlItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'siteMonitor.indoorFanSwitch', defaultMessage: '室内风机开关' }),
    field: 'IndoorFanSwitch',
    format: openFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.compressorSwitch', defaultMessage: '压缩机开关' }),
    field: 'compressorSwitch',
    format: openFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.electricHeatingSwitch', defaultMessage: '电加热开关' }),
    field: 'ElectricHeatingSwitch',
    format: openFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.bulletinAlarmSwitch', defaultMessage: '公告告警开关' }),
    field: 'AnnouncementAlarmSwitch',
    format: openFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.refrigeratedCondition', defaultMessage: '制冷状态' }),
    field: 'CoolingState',
    format: openFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.heatingCondition', defaultMessage: '制热状态' }),
    field: 'HeatingState',
    format: openFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.dehumidificationCondition',
      defaultMessage: '除湿状态',
    }),
    field: 'DehumidificationState',
    format: openFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.airSupplyState', defaultMessage: '送风状态' }),
    field: 'AirSupplyStatus',
    format: openFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.standBy', defaultMessage: '待机状态' }),
    field: 'PositionInReadiness',
    format: airRunFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.airConditioningUnitOperationStatus',
      defaultMessage: '空调机组运行状态',
    }),
    field: 'AirConditioningUnitOperationStatus',
    format: airsetFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.airConditioningAlarmStatus',
      defaultMessage: '空调报警状态',
    }),
    field: 'AirConditioningAlarmStatus',
    format: airAlarmFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.airConditioningSwitchOutput',
      defaultMessage: '空调开关量输出',
    }),
    field: 'AirConditioningSwitchOutput',
    format: airSwitchFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.airConditioningWorkingState',
      defaultMessage: '空调工作状态',
    }),
    field: 'AirConditioningWorkingStatus',
    format: airWorkFormat,
  },
];

export const statusItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'siteMonitor.returnAirHumidity', defaultMessage: '回风湿度' }),
    field: 'ReturnAirHumidity',
    format: percentageFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.supplyAirTemperature', defaultMessage: '送风温度' }),
    field: 'SupplyAirTemperature',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.condensingTemperature', defaultMessage: '冷凝温度' }),
    field: 'CondensingTemperature',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.evaporationTemperature', defaultMessage: '蒸发温度' }),
    field: 'EvaporatingTemperature',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.returnAirTemperature', defaultMessage: '回风温度' }),
    field: 'ReturnAirTemperature',
    format: tempFormat,
  },
];
