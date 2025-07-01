/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-11 16:56:37
 * @LastEditTime: 2024-02-25 18:25:42
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\EnergyDialog\equipmentItem.ts
 */
import type { DetailItem } from '@/components/Detail';
import { formatMessage } from '@/utils';
import {
  communicateFormat,
  runFormat,
  modelFormat,
  closeFormat,
  singleFormat,
  powerFormat,
  voltageFormat,
  currentFormat,
  frequencyFormat,
  tempFormat,
  workFormat,
  faultFormat,
  doorFormat,
  chargeFormat,
  openFormat,
  percentageFormat,
  kohmFormat,
  powerHourFormat,
  hydrogenFormat,
  outputFormat,
  openCloseFormat,
  booleanFormat,
  airsetFormat,
  electricModelFormat,
  alarmArrFormat,
  kVAFormat,
  noPowerFormat,
} from '@/utils/format';

export const statusItems: DetailItem[] = [
  {
    label: 'PCS' + formatMessage({ id: 'device.communicationStatus', defaultMessage: '通讯状态' }),
    field: 'pcsLoss',
    format: communicateFormat,
  },
  {
    label: 'BMS' + formatMessage({ id: 'device.communicationStatus', defaultMessage: '通讯状态' }),
    field: 'bmsLoss',
    format: communicateFormat,
  },
  {
    label:
      formatMessage({ id: 'device.ammeter', defaultMessage: '电表' }) +
      formatMessage({ id: 'device.communicationStatus', defaultMessage: '通讯状态' }),
    field: 'meterLoss',
    format: communicateFormat,
    span: 2,
  },
  {
    label: 'EMS' + formatMessage({ id: 'siteMonitor.runningState', defaultMessage: '运行状态' }),
    field: 'emsSysStatus',
    format: runFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.systemModel', defaultMessage: '系统模式' }),
    field: 'sysModel',
    format: modelFormat,
    span: 3,
  },
  {
    label:
      'BMS' +
      formatMessage({ id: 'siteMonitor.mainContactorStatus', defaultMessage: '主接触器状态' }),
    field: 'MainContactorStatus',
    format: closeFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.aerosolSignal', defaultMessage: '气溶胶信号' }),
    field: 'AerosolSignal',
    format: singleFormat,
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
    label: 'BMS' + formatMessage({ id: 'siteMonitor.scramSignal', defaultMessage: '急停信号' }),
    field: 'BmsStopSignal',
    format: singleFormat,
  },
];
export const settingItems: DetailItem[] = [
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
  {
    label: formatMessage({ id: 'siteMonitor.manualPCSPower', defaultMessage: '手动PCS功率' }),
    field: 'handOpePcsPower',
    format: powerFormat,
    span: 4,
  },
  {
    label: `${formatMessage({ id: 'device.timePeriod', defaultMessage: '时段' })}1`,
    field: 'AtTheBeginningOfThePeriodOne',
    format: (_, data) =>
      data.AtTheBeginningOfThePeriodOne
        ? `${data.AtTheBeginningOfThePeriodOne}:${data.StartingPointOfTimePeriodOne ?? ''}~${
            data.AtTheEndOfThePeriodOne ?? ''
          }:${data.PeriodEndMinuteOne ?? ''}`
        : '',
  },
  {
    label: formatMessage({ id: 'siteMonitor.executionPower', defaultMessage: '执行功率' }),
    field: 'PCSPeriodPowerOne',
    format: powerFormat,
  },
  {
    label: `${formatMessage({ id: 'device.timePeriod', defaultMessage: '时段' })}2`,
    field: 'AtTheBeginningOfThePeriodTwo',
    format: (_, data) =>
      data.AtTheBeginningOfThePeriodTwo
        ? `${data.AtTheBeginningOfThePeriodTwo}:${data.StartingPointOfTimePeriodTwo ?? ''}~${
            data.AtTheEndOfThePeriodTwo ?? ''
          }:${data.PeriodEndMinuteTwo ?? ''}`
        : '',
  },
  {
    label: formatMessage({ id: 'siteMonitor.executionPower', defaultMessage: '执行功率' }),
    field: 'PCSPeriodPowerTwo',
    format: powerFormat,
    span: 2,
  },
  {
    label: `${formatMessage({ id: 'device.timePeriod', defaultMessage: '时段' })}3`,
    field: 'AtTheBeginningOfThePeriodThree',
    format: (_, data) =>
      data.AtTheBeginningOfThePeriodThree
        ? `${data.AtTheBeginningOfThePeriodThree}:${data.StartingPointOfTimePeriodThree ?? ''}~${
            data.AtTheEndOfThePeriodThree ?? ''
          }:${data.PeriodEndMinuteThree ?? ''}`
        : '',
  },
  {
    label: formatMessage({ id: 'siteMonitor.executionPower', defaultMessage: '执行功率' }),
    field: 'PCSPeriodPowerThree',
    format: powerFormat,
  },
  {
    label: `${formatMessage({ id: 'device.timePeriod', defaultMessage: '时段' })}4`,
    field: 'AtTheBeginningOfThePeriodFour',
    format: (_, data) =>
      data.AtTheBeginningOfThePeriodFour
        ? `${data.AtTheBeginningOfThePeriodFour}:${data.StartingPointOfTimePeriodFour ?? ''}~${
            data.AtTheEndOfThePeriodFour ?? ''
          }:${data.PeriodEndMinuteFour ?? ''}`
        : '',
  },
  {
    label: formatMessage({ id: 'siteMonitor.executionPower', defaultMessage: '执行功率' }),
    field: 'PCSPeriodPowerFour',
    format: powerFormat,
    span: 2,
  },
  {
    label: `${formatMessage({ id: 'device.timePeriod', defaultMessage: '时段' })}5`,
    field: 'AtTheBeginningOfThePeriodFive',
    format: (_, data) =>
      data.AtTheBeginningOfThePeriodFive
        ? `${data.AtTheBeginningOfThePeriodFive}:${data.StartingPointOfTimePeriodFive ?? ''}~${
            data.AtTheEndOfThePeriodFive ?? ''
          }:${data.PeriodEndMinuteFive ?? ''}`
        : '',
  },
  {
    label: formatMessage({ id: 'siteMonitor.executionPower', defaultMessage: '执行功率' }),
    field: 'PCSPeriodPowerFive',
    format: powerFormat,
  },
];

export const pcsStatusItems: DetailItem[] = [
  {
    label: formatMessage({
      id: 'siteMonitor.currentActualChargeDischargeWorkingMode',
      defaultMessage: '当前实际充放电工作模式',
    }),
    field: 'CurrentChargingAndDischargingModel',
    format: electricModelFormat,
    labelStyle: {
      maxWidth: '116px',
    },
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
export const measureItems: DetailItem[] = [
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
        defaultMessage: '交流B相视在功率',
      },
      {
        name: 'B',
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
    label: formatMessage({ id: 'siteMonitor.acPowerFactor', defaultMessage: '交流功率因数' }),
    field: 'COS',
  },
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

export const bmsStatusItems: DetailItem[] = [
  {
    label: formatMessage({
      id: 'siteMonitor.prechargeContactorStatus',
      defaultMessage: '预充接触器状态',
    }),
    field: 'PrechargeContactorStatus',
    format: closeFormat,
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
  {
    label: formatMessage({ id: 'siteMonitor.accessControlStatus', defaultMessage: '门禁状态' }),
    field: 'AccessControlStatus',
    format: doorFormat,
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
    label: formatMessage(
      { id: 'device.fanSentence', defaultMessage: 'BMU1风扇' },
      { name: 'BMU1' },
    ),
    field: 'BMU1Fan',
    format: openFormat,
  },
  {
    label: formatMessage(
      { id: 'device.fanSentence', defaultMessage: 'BMU2风扇' },
      { name: 'BMU2' },
    ),
    field: 'BMU2Fan',
    format: openFormat,
  },
  {
    label: formatMessage(
      { id: 'device.fanSentence', defaultMessage: 'BMU3风扇' },
      { name: 'BMU3' },
    ),
    field: 'BMU3Fan',
    format: openFormat,
  },
  {
    label: formatMessage(
      { id: 'device.fanSentence', defaultMessage: 'BMU4风扇' },
      { name: 'BMU4' },
    ),
    field: 'BMU4Fan',
    format: openFormat,
  },
  {
    label: formatMessage(
      { id: 'device.fanSentence', defaultMessage: 'BMU5风扇' },
      { name: 'BMU5' },
    ),
    field: 'BMU5Fan',
    format: openFormat,
  },
  {
    label: formatMessage(
      { id: 'device.fanSentence', defaultMessage: 'BMU6风扇' },
      { name: 'BMU6' },
    ),
    field: 'BMU6Fan',
    format: openFormat,
  },
  {
    label: formatMessage(
      { id: 'device.fanSentence', defaultMessage: 'BMU7风扇' },
      { name: 'BMU7' },
    ),
    field: 'BMU7Fan',
    format: openFormat,
  },
  {
    label: formatMessage(
      { id: 'device.fanSentence', defaultMessage: 'BMU8风扇' },
      { name: 'BMU8' },
    ),
    field: 'BMU8Fan',
    format: openFormat,
  },
  {
    label: formatMessage(
      { id: 'device.fanSentence', defaultMessage: 'BMU9风扇' },
      { name: 'BMU9' },
    ),
    field: 'BMU9Fan',
    format: openFormat,
  },
  {
    label: formatMessage(
      { id: 'device.fanSentence', defaultMessage: 'BMU10风扇' },
      { name: 'BMU10' },
    ),
    field: 'BMU10Fan',
    format: openFormat,
  },
  {
    label: formatMessage(
      { id: 'device.communicationStatusSentence', defaultMessage: 'BMU1-10通信状态' },
      { name: 'BMU1-10' },
    ),
    field: 'BMU1CS',
    format: faultFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.externalFaultStatus', defaultMessage: '对外故障' }),
    field: 'externalFaultStatus',
    format: alarmArrFormat,
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
export const bmsMeasureItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'device.totalBatteryVoltage', defaultMessage: '电池总电压' }),
    field: 'TotalBatteryVoltage',
    format: voltageFormat,
  },
  {
    label: formatMessage({ id: 'device.totalBatteryCurrent', defaultMessage: '电池总电流' }),
    field: 'TotalBatteryCurrent',
    format: currentFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.prechargeTotalPressure', defaultMessage: '预充总压' }),
    field: 'TPCP',
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
      id: 'siteMonitor.negativeInsulationValue',
      defaultMessage: '负极绝缘值',
    }),
    field: 'NegativeInsulationValue',
    format: kohmFormat,
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
  {
    label: formatMessage({ id: 'device.maxCellVoltageNumber', defaultMessage: '单体电压最高号' }),
    field: 'MaxNOIV',
  },
  {
    label: formatMessage({ id: 'device.maxVoltageCell', defaultMessage: '单体最高电压值' }),
    field: 'MVVOASU',
    format: voltageFormat,
  },
  {
    label: formatMessage({
      id: 'device.minimumVoltageNumberCell',
      defaultMessage: '单体电压最低号',
    }),
    field: 'MNOIV',
  },
  {
    label: formatMessage({ id: 'device.minimumColtageCell', defaultMessage: '单体最低电压值' }),
    field: 'MVVOSU',
    format: voltageFormat,
  },
  {
    label: formatMessage({
      id: 'device.maxIndividualTemperatureNumber',
      defaultMessage: '单体温度最高号',
    }),
    field: 'MITN',
  },
  {
    label: formatMessage({
      id: 'device.maxIndividualTemperatureValue',
      defaultMessage: '单体温度最高值',
    }),
    field: 'MaximumIndividualTemperature',
    format: tempFormat,
  },
  {
    label: formatMessage({
      id: 'device.minCellTemperatureNumber',
      defaultMessage: '单体温度最低号',
    }),
    field: 'MNOIT',
  },
  {
    label: formatMessage({ id: 'device.minCellTemperature', defaultMessage: '单体温度最低值' }),
    field: 'LVOMT',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'device.averageVoltage', defaultMessage: '平均电压' }),
    field: 'AverageVoltage',
    format: voltageFormat,
  },
  {
    label: formatMessage({ id: 'device.averageTemperature', defaultMessage: '平均温度' }),
    field: 'AverageTemperature',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'device.highboxTemperature', defaultMessage: '高压箱温度' }) + '1',
    field: 'HighPressureBoxTemperature1',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'device.highboxTemperature', defaultMessage: '高压箱温度' }) + '2',
    field: 'HighPressureBoxTemperature2',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'device.highboxTemperature', defaultMessage: '高压箱温度' }) + '3',
    field: 'HighPressureBoxTemperature3',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'device.highboxTemperature', defaultMessage: '高压箱温度' }) + '4',
    field: 'HighPressureBoxTemperature4',
    format: tempFormat,
  },
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
    label: formatMessage({ id: 'device.lastChargeCharge', defaultMessage: '最近一次充电电量' }),
    field: 'LastChargeLevel',
    format: powerHourFormat,
  },
  {
    label: formatMessage({
      id: 'device.latestDischargeQuantity',
      defaultMessage: '最近一次放电电量',
    }),
    field: 'LastDischargeCapacity',
    format: powerHourFormat,
  },
  {
    label: formatMessage({
      id: 'device.accumulatedChargeCapacity',
      defaultMessage: '累计充电电量',
    }),
    field: 'ACC',
    format: powerHourFormat,
  },
  {
    label: formatMessage({
      id: 'device.accumulatedDischargeCapacity',
      defaultMessage: '累计放电电量',
    }),
    field: 'ADC',
    format: powerHourFormat,
  },
  {
    label: `${formatMessage({ id: 'device.packFanPwm', defaultMessage: 'PACK风扇PWM占空比' })}1`,
    field: 'PACKFPDC1',
    format: percentageFormat,
  },
  {
    label: `${formatMessage({ id: 'device.packFanPwm', defaultMessage: 'PACK风扇PWM占空比' })}2`,
    field: 'PACKFPDC2',
    format: percentageFormat,
  },
  {
    label: `${formatMessage({ id: 'device.packFanPwm', defaultMessage: 'PACK风扇PWM占空比' })}3`,
    field: 'PACKFPDC3',
    format: percentageFormat,
  },
  {
    label: `${formatMessage({ id: 'device.packFanPwm', defaultMessage: 'PACK风扇PWM占空比' })}4`,
    field: 'PACKFPDC4',
    format: percentageFormat,
  },
  {
    label: `${formatMessage({ id: 'device.packFanPwm', defaultMessage: 'PACK风扇PWM占空比' })}5`,
    field: 'PACKFPDC5',
    format: percentageFormat,
  },
  {
    label: `${formatMessage({ id: 'device.packFanPwm', defaultMessage: 'PACK风扇PWM占空比' })}6`,
    field: 'PACKFPDC6',
    format: percentageFormat,
  },
  {
    label: `${formatMessage({ id: 'device.packFanPwm', defaultMessage: 'PACK风扇PWM占空比' })}7`,
    field: 'PACKFPDC7',
    format: percentageFormat,
  },
  {
    label: `${formatMessage({ id: 'device.packFanPwm', defaultMessage: 'PACK风扇PWM占空比' })}8`,
    field: 'PACKFPDC8',
    format: percentageFormat,
  },
  {
    label: `${formatMessage({ id: 'device.packFanPwm', defaultMessage: 'PACK风扇PWM占空比' })}9`,
    field: 'PACKFPDC9',
    format: percentageFormat,
  },
  {
    label: `${formatMessage({ id: 'device.packFanPwm', defaultMessage: 'PACK风扇PWM占空比' })}10`,
    field: 'PACKFPDC10',
    format: percentageFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.numberBatteryCabinetDoors',
      defaultMessage: '电池柜开门次数',
    }),
    field: 'NOBCDO',
  },
  {
    label: formatMessage({ id: 'siteMonitor.hydrogenConcentration', defaultMessage: '氢气浓度' }),
    field: 'HydrogenConcentration',
    format: hydrogenFormat,
  },
];

export const airStatusItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'siteMonitor.indoorFanSwitch', defaultMessage: '室内风机开关' }),
    field: 'IndoorFanSwitch',
    format: outputFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.compressorSwitch', defaultMessage: '压缩机开关' }),
    field: 'compressorSwitch',
    format: outputFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.electricHeatingSwitch', defaultMessage: '电加热开关' }),
    field: 'ElectricHeatingSwitch',
    format: openCloseFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.bulletinAlarmSwitch', defaultMessage: '公告告警开关' }),
    field: 'AnnouncementAlarmSwitch',
    format: openFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.refrigeratedCondition', defaultMessage: '制冷状态' }),
    field: 'CoolingState',
    format: booleanFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.heatingCondition', defaultMessage: '制热状态' }),
    field: 'HeatingState',
    format: booleanFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.dehumidificationCondition',
      defaultMessage: '除湿状态',
    }),
    field: 'DehumidificationState',
    format: booleanFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.airSupplyState', defaultMessage: '送风状态' }),
    field: 'AirSupplyStatus',
    format: booleanFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.standBy', defaultMessage: '待机状态' }),
    field: 'PositionInReadiness',
    format: booleanFormat,
  },
  {
    label: formatMessage({
      id: 'siteMonitor.airConditioningUnitOperationStatus',
      defaultMessage: '空调机组运行状态',
    }),
    field: 'AirConditioningUnitOperationStatus',
    format: airsetFormat,
  },
];
export const airMeasureItems: DetailItem[] = [
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

export const electricMeasureItems: DetailItem[] = [
  {
    label: formatMessage(
      { id: 'device.phasePowerSentence', defaultMessage: 'A相功率' },
      { name: 'A' },
    ),
    field: 'APhasePower',
    format: powerFormat,
  },
  {
    label: formatMessage(
      { id: 'device.phasePowerSentence', defaultMessage: 'B相功率' },
      { name: 'B' },
    ),
    field: 'BPhasePower',
    format: powerFormat,
  },
  {
    label: formatMessage(
      { id: 'device.phasePowerSentence', defaultMessage: 'C相功率' },
      { name: 'C' },
    ),
    field: 'CPhasePower',
    format: powerFormat,
  },
  {
    label: formatMessage({ id: 'device.totalActivePower', defaultMessage: '总有功功率' }),
    field: 'TotalActivePower',
    format: powerFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.dayPowerConsumption', defaultMessage: '今日用电量' }),
    field: 'DailyElectricityConsumption',
    format: powerFormat,
  },
  {
    label: formatMessage({
      id: 'device.monthlyElectricityConsumption',
      defaultMessage: '当月用电量',
    }),
    field: 'MonthlyElectricityConsumption',
    format: powerHourFormat,
  },
  {
    label: formatMessage({
      id: 'device.accumulatedElectricityConsumption',
      defaultMessage: '累计用电量',
    }),
    field: 'AEC',
    format: powerHourFormat,
  },
  {
    label: formatMessage({ id: 'device.dailyPowerSupply', defaultMessage: '今日馈电量' }),
    field: 'DailyPowerSupply',
    format: powerHourFormat,
  },
  {
    label: formatMessage({ id: 'device.monthlyPowerSupply', defaultMessage: '当月馈电量' }),
    field: 'CurrentMonthPowerSupply',
    format: powerHourFormat,
  },
  {
    label: formatMessage({ id: 'device.accumulatedPowerSupply', defaultMessage: '累计馈电量' }),
    field: 'AccumulatedPowerSupply',
    format: powerHourFormat,
  },
];
