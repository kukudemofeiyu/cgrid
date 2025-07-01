/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-14 14:25:54
 * @LastEditTime: 2023-07-25 16:06:40
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceMonitor\Pcs\config.tsx
 */

import type { DetailItem } from '@/components/Detail';
import { formatMessage } from '@/utils';
import {
  currentFormat,
  voltageFormat,
  tempFormat,
  powerFormat,
  frequencyFormat,
  faultFormat,
  workFormat,
  electricModelFormat,
  kVAFormat,
  noPowerFormat,
} from '@/utils/format';

export const runItems: DetailItem[] = [
  {
    label: formatMessage({
      id: 'siteMonitor.currentActualChargeDischargeWorkingMode',
      defaultMessage: '当前实际充放电工作模式',
    }),
    field: 'CurrentChargingAndDischargingModel',
    format: electricModelFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.workingCondition', defaultMessage: '工作状态' }),
    field: 'WorkStatus',
    format: workFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.systemFaultWord', defaultMessage: '系统故障字' }),
    field: 'SystemFault',
    format: faultFormat,
  },
  {
    label:
      formatMessage({ id: 'siteMonitor.hardwareFaultWord', defaultMessage: '硬件故障字' }) + '1',
    field: 'HardwareFault1',
    format: faultFormat,
  },
  {
    label:
      formatMessage({ id: 'siteMonitor.hardwareFaultWord', defaultMessage: '硬件故障字' }) + '2',
    field: 'HardwareFault2',
    format: faultFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.gridFaultWord', defaultMessage: '电网故障字' }),
    field: 'GridFault',
    format: faultFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.busFaultWord', defaultMessage: '母线故障字' }),
    field: 'BusFault',
    format: faultFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.acCapacitorFaultWord',
      defaultMessage: '交流电容故障字',
    }),
    field: 'AcCapacitorFault',
    format: faultFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.switchFaultWord', defaultMessage: '开关故障字' }),
    field: 'SwitchFault',
    format: faultFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.otherFaultWord', defaultMessage: '其他故障字' }),
    field: 'OtherFault',
    format: faultFormat,
  },
];

export const exchargeItems: DetailItem[] = [
  {
    label: formatMessage(
      {
        id: 'device.outputLineVoltage',
        defaultMessage: '输出AB线电压',
      },
      {
        name: 'AB',
      },
    ),
    field: 'Uab',
    format: voltageFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.outputLineVoltage',
        defaultMessage: '输出BC线电压',
      },
      {
        name: 'BC',
      },
    ),
    field: 'Ubc',
    format: voltageFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.outputLineVoltage',
        defaultMessage: '输出CA线电压',
      },
      {
        name: 'CA',
      },
    ),
    field: 'Uca',
    format: voltageFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.outputPhaseVoltage',
        defaultMessage: '输出A相电压',
      },
      {
        name: 'A',
      },
    ),
    field: 'Ua',
    format: voltageFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.outputPhaseVoltage',
        defaultMessage: '输出B相电压',
      },
      {
        name: 'B',
      },
    ),
    field: 'Ub',
    format: voltageFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.outputPhaseVoltage',
        defaultMessage: '输出C相电压',
      },
      {
        name: 'C',
      },
    ),
    field: 'Uc',
    format: voltageFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.outputPhaseCurrent',
        defaultMessage: '输出A相电流',
      },
      {
        name: 'A',
      },
    ),
    field: 'OutputIa',
    format: currentFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.outputPhaseCurrent',
        defaultMessage: '输出B相电流',
      },
      {
        name: 'B',
      },
    ),
    field: 'OutputIb',
    format: currentFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.outputPhaseCurrent',
        defaultMessage: '输出C相电流',
      },
      {
        name: 'C',
      },
    ),
    field: 'OutputIc',
    format: currentFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.inductancePhaseCurrent',
        defaultMessage: '电感A相电流',
      },
      {
        name: 'A',
      },
    ),
    field: 'inductanceIa',
    format: currentFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.inductancePhaseCurrent',
        defaultMessage: '电感B相电流',
      },
      {
        name: 'B',
      },
    ),
    field: 'inductanceIb',
    format: currentFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.inductancePhaseCurrent',
        defaultMessage: '电感C相电流',
      },
      {
        name: 'C',
      },
    ),
    field: 'inductanceIc',
    format: currentFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.acActivePowerPhase',
        defaultMessage: '交流A相有功功率',
      },
      {
        name: 'A',
      },
    ),
    field: 'Pa',
    format: powerFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.acActivePowerPhase',
        defaultMessage: '交流B相有功功率',
      },
      {
        name: 'B',
      },
    ),
    field: 'Pb',
    format: powerFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.acActivePowerPhase',
        defaultMessage: '交流C相有功功率',
      },
      {
        name: 'C',
      },
    ),
    field: 'Pc',
    format: powerFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.acPhaseApparentPower',
        defaultMessage: '交流A相视在功率',
      },
      {
        name: 'A',
      },
    ),
    field: 'Sa',
    format: kVAFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.acPhaseApparentPower',
        defaultMessage: '交流B相视在功率',
      },
      {
        name: 'B',
      },
    ),
    field: 'Sb',
    format: kVAFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.acPhaseApparentPower',
        defaultMessage: '交流C相视在功率',
      },
      {
        name: 'C',
      },
    ),
    field: 'Sc',
    format: kVAFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.acReactivePowerPhase',
        defaultMessage: '交流A相无功功率',
      },
      {
        name: 'A',
      },
    ),
    field: 'Qa',
    format: noPowerFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.acReactivePowerPhase',
        defaultMessage: '交流B相无功功率',
      },
      {
        name: 'B',
      },
    ),
    field: 'Qb',
    format: noPowerFormat,
  },
  {
    label: formatMessage(
      {
        id: 'device.acReactivePowerPhase',
        defaultMessage: '交流C相无功功率',
      },
      {
        name: 'C',
      },
    ),
    field: 'Qc',
    format: noPowerFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.acOutputTotalActivePower',
      defaultMessage: '交流输出总有功功率',
    }),
    field: 'P',
    format: powerFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.acOutputTotalReactivePower',
      defaultMessage: '交流输出总无功功率',
    }),
    field: 'Q',
    format: noPowerFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.acOutputTotalApparentPower',
      defaultMessage: '交流输出总视在功率',
    }),
    field: 'S',
    format: kVAFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.gridFrequency', defaultMessage: '电网频率' }),
    field: 'Freq',
    format: frequencyFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.currentPhasePowerGrid',
      defaultMessage: '电网当前相序',
    }),
    field: 'GridPhaseSequence',
  },
  {
    label: formatMessage({ id: 'siteMonitor.acPowerFactor', defaultMessage: '交流功率因数' }),
    field: 'COS',
  },
];

export const directCurrentItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'siteMonitor.totalBusVoltage', defaultMessage: '总母线电压' }),
    field: 'totalBusVoltage',
    format: voltageFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.positiveBusVoltage', defaultMessage: '正母线电压' }),
    field: 'positiveBusVoltage',
    format: voltageFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.negativeBusVoltage', defaultMessage: '负母线电压' }),
    field: 'negativeBusVoltage',
    format: voltageFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.batteryVoltage', defaultMessage: '电池电压' }),
    field: 'BatteryVoltage',
    format: voltageFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.batteryCurrent', defaultMessage: '电池电流' }),
    field: 'batteryCurrent',
    format: currentFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.dcPower', defaultMessage: '直流功率' }),
    field: 'PDC',
    format: powerFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.totalDcCurrent', defaultMessage: '直流总电流' }),
    field: 'TotalDcI',
    format: currentFormat,
  },
];

export const tempItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'siteMonitor.ambientTemperature', defaultMessage: '环境温度' }),
    field: 'EnvironmentTemperature',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'device.IGBTTemp', defaultMessage: 'IGBT温度' }),
    field: 'IgbtTemperature',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.inductanceTemperature', defaultMessage: '电感温度' }),
    field: 'InductanceTemperature',
    format: tempFormat,
  },
];

export const versionItems: DetailItem[] = [
  {
    label: formatMessage(
      { id: 'common.versionSentence', defaultMessage: 'DSP-V版本' },
      { name: 'DSP-V' },
    ),
    field: 'DspVVersion',
  },
  {
    label: formatMessage(
      { id: 'common.versionSentence', defaultMessage: 'DSP-B版本' },
      { name: 'DSP-B' },
    ),
    field: 'DspBVersion',
  },
  {
    label: formatMessage(
      { id: 'common.versionSentence', defaultMessage: 'DSP-D版本' },
      { name: 'DSP-D' },
    ),
    field: 'DspDVersion',
  },
  {
    label: formatMessage(
      { id: 'common.versionSentence', defaultMessage: 'CPLD-V版本' },
      { name: 'CPLD-V' },
    ),
    field: 'CpldVVersion',
  },
  {
    label: formatMessage(
      { id: 'common.versionSentence', defaultMessage: 'CPLD-B版本' },
      { name: 'CPLD-B' },
    ),
    field: 'CpldBVersion',
  },
  {
    label: formatMessage(
      { id: 'common.versionSentence', defaultMessage: 'CPLD-D版本' },
      { name: 'CPLD-D' },
    ),
    field: 'CpldDVersion',
  },
];
