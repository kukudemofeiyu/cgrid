/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-14 00:28:59
 * @LastEditTime: 2024-03-26 16:42:57
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\Ems\Run\config.ts
 */

import type { DetailItem } from '@/components/Detail';
import { formatMessage } from '@/utils';
import { DeviceTypeEnum } from '@/utils/dictionary';
import {
  closeFormat,
  modelFormat,
  parallelGridFormat,
  runFormat,
  singleBFormat,
  singleFormat,
  voltageFormat,
} from '@/utils/format';

export const controlItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'siteMonitor.systemModel', defaultMessage: '系统模式' }),
    field: 'sysModel',
    format: modelFormat,
  },
  {
    label: 'EMS' + formatMessage({ id: 'siteMonitor.runningState', defaultMessage: '运行状态' }),
    field: 'emsSysStatus',
    format: runFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.mainContactorStatus', defaultMessage: '主接触器状态' }),
    field: 'MainContactorStatus',
    format: closeFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.aerosolSignal', defaultMessage: '气溶胶信号' }),
    field: 'AerosolSignal',
    format: singleBFormat,
  },
  {
    label: 'BMS' + formatMessage({ id: 'siteMonitor.scramSignal', defaultMessage: '急停信号' }),
    field: 'BmsStopSignal',
    format: singleBFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.electricalScramSignal',
      defaultMessage: '电气急停信号',
    }),
    field: 'EmergencyStopSignal',
    format: singleFormat,
  },
  {
    label: formatMessage({
      id: 'device.parallelOffGridMode',
      defaultMessage: '并离网模式',
    }),
    field: 'parallelOffGridMode',
    format: parallelGridFormat,
    show: (value, data) => data?.productId == DeviceTypeEnum.React100XEmsEnergy,
  },
];

export const protectItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'siteMonitor.overchargeProtection', defaultMessage: '过充保护' }),
    field: 'OverchargeProtection',
    format: voltageFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.overchargeRelease', defaultMessage: '过充释放' }),
    field: 'OverchargeRelease',
    format: voltageFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.overDischargeProtection', defaultMessage: '过放保护' }),
    field: 'OverdischargeProtection',
    format: voltageFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.overrelease', defaultMessage: '过放释放' }),
    field: 'Overrelease',
    format: voltageFormat,
  },
];
