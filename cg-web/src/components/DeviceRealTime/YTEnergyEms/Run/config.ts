/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-14 00:28:59
 * @LastEditTime: 2023-12-15 14:41:59
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\YTEnergyEms\Run\config.ts
 */

import type { DetailItem } from '@/components/Detail';
import { formatMessage } from '@/utils';
import {
  closeFormat,
  systemOperatingModeFormat,
  converterFormat,
  converterStauesFormat,
  batteryWorkFormat,
  batteryWorkingStatusFormat,
  systemRunFormat,
  singleEffectFormat,
  openFormat,
} from '@/utils/format';

export const emsOperationItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'device.systemOperatingMode', defaultMessage: '系统工作模式' }),
    field: 'systemOperatingMode',
    format: systemOperatingModeFormat,
  },
  {
    label: formatMessage({ id: 'device.systemOperatingState', defaultMessage: '系统工作状态' }),
    field: 'systemWorkingStatus',
    format: systemRunFormat,
  },
  {
    label: formatMessage({ id: 'device.converterOperatingMode', defaultMessage: '变流器工作模式' }),
    field: 'converterOperatingMode',
    format: converterFormat,
  },
  {
    label: formatMessage({ id: 'device.converterWorkingState', defaultMessage: '变流器工作状态' }),
    field: 'converterWorkingStatus',
    format: converterStauesFormat,
  },
  {
    label: formatMessage({
      id: 'device.batteryPackOperatingMode',
      defaultMessage: '电池组工作模式',
    }),
    field: 'batteryPackOperatingMode',
    format: batteryWorkFormat,
  },
  {
    label: formatMessage({
      id: 'device.batteryStringOperatingStatus',
      defaultMessage: '电池组工作状态',
    }),
    field: 'batteryPackWorkingStatus',
    format: batteryWorkingStatusFormat,
  },
  {
    label: formatMessage({ id: 'device.mainContactorStatus', defaultMessage: '主接触器状态' }),
    field: 'bmsOpenAndClose',
    format: closeFormat,
  },
  {
    label: formatMessage({ id: 'device.prechargedContactStatus', defaultMessage: '预充触器状态' }),
    field: 'prechargeContactStatus',
    format: closeFormat,
  },
  {
    label: formatMessage({
      id: 'device.dcCircuitBreakerCondition',
      defaultMessage: '直流断路器状态',
    }),
    field: 'DCCircuitBreakerStatus',
    format: closeFormat,
  },
  {
    label: formatMessage({
      id: 'device.acCircuitBreakerCondition',
      defaultMessage: '交流断路器状态',
    }),
    field: 'ACCircuitBreakerStatus',
    format: openFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.BmsStopSignal',
        defaultMessage: 'BMS急停信号',
      },
      {
        name: 'BMS',
      },
    ),
    field: 'BmsStopSignal',
    format: singleEffectFormat,
  },
  {
    label: formatMessage({
      id: 'device.systemEmergencyStopSignal',
      defaultMessage: '系统急停信号',
    }),
    field: 'EmergencyStopSignal',
    format: singleEffectFormat,
  },
  {
    label: formatMessage({
      id: 'device.fireSpraySignal',
      defaultMessage: '消防喷射信号',
    }),
    field: 'fsig',
    format: singleEffectFormat,
  },
];
