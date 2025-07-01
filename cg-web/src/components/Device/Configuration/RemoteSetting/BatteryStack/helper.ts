/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-01-05 16:43:06
 * @LastEditTime: 2024-01-05 16:43:06
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\RemoteSetting\BatteryStack\helper.ts
 */

import { GroupItem } from '@/components/Detail';
import { FormAndDetailType } from '@/components/Detail/Detail';
import { enableOptions } from '@/utils/dict';
import {
  currentFormat,
  enableFormat,
  kohmFormat,
  mvFormat,
  percentageFormat,
  tempFormat,
} from '@/utils/format';

export const batteryPackFireColumns: FormAndDetailType[] = [
  {
    title: '自启动功能使能',
    dataIndex: 'EnableBatterySystemSelfStartFunction',
    valueType: 'select',
    fieldProps: {
      options: enableOptions,
    },
    formItemProps: {
      rules: [{ required: true }],
    },
    format: enableFormat,
  },
];

export const batteryPackColumns: FormAndDetailType[] = [
  {
    title: '电池组使能状态',
    dataIndex: 'enabledStatus',
    valueType: 'select',
    fieldProps: {
      options: enableOptions,
    },
    formItemProps: {
      rules: [{ required: true }],
    },
    format: enableFormat,
  },
];

export const protectOneColumns: FormAndDetailType[] = [
  {
    title: '单体过压一级报警门限',
    dataIndex: 'CellOverVoltageWarnThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体过压一级恢复门限',
    dataIndex: 'CellOverVoltageWarnRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体欠压一级报警门限',
    dataIndex: 'CellUnderVoltageWarnThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体欠压一级恢复门限',
    dataIndex: 'CellUnderVoltageWarnRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压过压一级报警门限',
    dataIndex: 'SystemOverVoltageWarnThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压过压一级恢复门限',
    dataIndex: 'SystemOverVoltageWarnRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压欠压一级报警门限',
    dataIndex: 'SystemUnderVoltageWarnThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压欠压一级恢复门限',
    dataIndex: 'SystemUnderVoltageWarnRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体压差过高一级报警门限',
    dataIndex: 'CellVolDiffOverBigWarnThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体压差过高一级恢复门限',
    dataIndex: 'CellVolDiffOverBigWarnRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压压差过高一级报警门限',
    dataIndex: 'SystemVolDiffOverBigWarnThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压压差过高一级恢复门限',
    dataIndex: 'SystemVolDiffOverBigWarnRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '充电过流一级报警门限',
    dataIndex: 'ChargeOverCurrentWarnThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'A',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: currentFormat,
  },
  {
    title: '充电过流一级恢复门限',
    dataIndex: 'ChargeOverCurrentWarnRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'A',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: currentFormat,
  },
  {
    title: '放电过流一级报警门限',
    dataIndex: 'DischargeOverCurrentWarnThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'A',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: currentFormat,
  },
  {
    title: '放电过流一级恢复门限',
    dataIndex: 'DischargeOverCurrentWarnRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'A',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: currentFormat,
  },
  {
    title: '充电过温一级报警门限',
    dataIndex: 'ChargeOverTempWarnThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '充电过温一级恢复门限',
    dataIndex: 'ChargeOverTempWarnRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '充电欠温一级报警门限',
    dataIndex: 'ChargeUnderTempWarnThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '充电欠温一级恢复门限',
    dataIndex: 'ChargeUnderTempWarnRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '放电过温一级报警门限',
    dataIndex: 'DischargeOverTempWarnThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '放电过温一级恢复门限',
    dataIndex: 'DischargeOverTempWarnRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '放电欠温一级报警门限',
    dataIndex: 'DischargeUnderTempWarnThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '放电欠温一级恢复门限',
    dataIndex: 'DischargeUnderTempWarnRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '温差过高一级报警门限',
    dataIndex: 'TempDiffOverBigWarnThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '温差过高一级恢复门限',
    dataIndex: 'TempVolDiffOverBigWarnRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '极柱温度过高一级报警门限',
    dataIndex: 'PoleOverTempWarnThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '极柱温度过高一级恢复门限',
    dataIndex: 'PoleOverTempWarnRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: 'SOC 过低一级报警门限',
    dataIndex: 'SocLowerWarnThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '%',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: percentageFormat,
  },
  {
    title: 'SOC 过低一级恢复门限',
    dataIndex: 'SocLowerWarnRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '%',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: percentageFormat,
  },
  {
    title: '绝缘失效一级报警门限',
    dataIndex: 'InsulationFailureWarnThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'kΩ',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: kohmFormat,
  },
  {
    title: '绝缘失效一级恢复门限',
    dataIndex: 'InsulationFailureWarnRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'kΩ',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: kohmFormat,
  },
  {
    title: '高压箱连接器温度过高一级报警门限',
    dataIndex: 'HvbOverTempWarnThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '高压箱连接器温度过高一级恢复门限',
    dataIndex: 'HvbOverTempWarnRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
];

export const protectTwoColumns: FormAndDetailType[] = [
  {
    title: '单体过压二级报警门限',
    dataIndex: 'CellOverVoltageAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体过压二级恢复门限',
    dataIndex: 'CellOverVoltageAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体欠压二级报警门限',
    dataIndex: 'CellUnderVoltageAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体欠压二级恢复门限',
    dataIndex: 'CellUnderVoltageAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压过压二级报警门限',
    dataIndex: 'SystemOverVoltageAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压过压二级恢复门限',
    dataIndex: 'SystemOverVoltageAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压欠压二级报警门限',
    dataIndex: 'SystemUnderVoltageAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压欠压二级恢复门限',
    dataIndex: 'SystemUnderVoltageAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体压差过高二级报警门限',
    dataIndex: 'CellVolDiffOverBigAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体压差过高二级恢复门限',
    dataIndex: 'CellVolDiffOverBigAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压压差过高二级报警门限',
    dataIndex: 'SystemVolDiffOverBigAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压压差过高二级恢复门限',
    dataIndex: 'SystemVolDiffOverBigAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '充电过流二级报警门限',
    dataIndex: 'ChargeOverCurrentAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'A',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: currentFormat,
  },
  {
    title: '充电过流二级恢复门限',
    dataIndex: 'ChargeOverCurrentAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'A',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: currentFormat,
  },
  {
    title: '放电过流二级报警门限',
    dataIndex: 'DischargeOverCurrentAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'A',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: currentFormat,
  },
  {
    title: '放电过流二级恢复门限',
    dataIndex: 'DischargeOverCurrentAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'A',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: currentFormat,
  },
  {
    title: '充电过温二级报警门限',
    dataIndex: 'ChargeOverTempAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '充电过温二级恢复门限',
    dataIndex: 'ChargeOverTempAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '充电欠温二级报警门限',
    dataIndex: 'ChargeUnderTempAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '充电欠温二级恢复门限',
    dataIndex: 'ChargeUnderTempAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '放电过温二级报警门限',
    dataIndex: 'DischargeOverTempAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '放电过温二级恢复门限',
    dataIndex: 'DischargeOverTempAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '放电欠温二级报警门限',
    dataIndex: 'DischargeUnderTempAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '放电欠温二级恢复门限',
    dataIndex: 'DischargeUnderTempAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '温差过高二级报警门限',
    dataIndex: 'TempDiffOverBigAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '温差过高二级恢复门限',
    dataIndex: 'TempVolDiffOverBigAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '极柱温度过高二级报警门限',
    dataIndex: 'PoleOverTempAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '极柱温度过高二级恢复门限',
    dataIndex: 'PoleOverTempAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: 'SOC 过低二级报警门限',
    dataIndex: 'SocLowerAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '%',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: percentageFormat,
  },
  {
    title: 'SOC 过低二级恢复门限',
    dataIndex: 'SocLowerAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '%',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: percentageFormat,
  },
  {
    title: '绝缘失效二级报警门限',
    dataIndex: 'InsulationFailureAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'kΩ',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: kohmFormat,
  },
  {
    title: '绝缘失效二级恢复门限',
    dataIndex: 'InsulationFailureAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'kΩ',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: kohmFormat,
  },
  {
    title: '高压箱连接器温度过高二级报警门限',
    dataIndex: 'HvbOverTempAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '高压箱连接器温度过高二级恢复门限',
    dataIndex: 'HvbOverTempAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
];

export const protectThreeColumns: FormAndDetailType[] = [
  {
    title: '单体过压三级报警门限',
    dataIndex: 'CellOverVoltageCriticalAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体过压三级恢复门限',
    dataIndex: 'CellOverVoltageCriticalAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体欠压三级报警门限',
    dataIndex: 'CellUnderVoltageCriticalAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体欠压三级恢复门限',
    dataIndex: 'CellUnderVoltageCriticalAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压过压三级报警门限',
    dataIndex: 'SystemOverVoltageCriticalAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压过压三级恢复门限',
    dataIndex: 'SystemOverVoltageCriticalAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压欠压三级报警门限',
    dataIndex: 'SystemUnderVoltageCriticalAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压欠压三级恢复门限',
    dataIndex: 'SystemUnderVoltageCriticalAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体压差过高三级报警门限',
    dataIndex: 'CellVolDiffOverBigCriticalAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体压差过高三级恢复门限',
    dataIndex: 'CellVolDiffOverBigCriticalAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压压差过高三级报警门限',
    dataIndex: 'SystemVolDiffOverBigCriticalAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压压差过高三级恢复门限',
    dataIndex: 'SystemVolDiffOverBigCriticalAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '充电过流三级报警门限',
    dataIndex: 'ChargeOverCurrentCriticalAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'A',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: currentFormat,
  },
  {
    title: '充电过流三级恢复门限',
    dataIndex: 'ChargeOverCurrentCriticalAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'A',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: currentFormat,
  },
  {
    title: '放电过流三级报警门限',
    dataIndex: 'DischargeOverCurrentCriticalAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'A',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: currentFormat,
  },
  {
    title: '放电过流三级恢复门限',
    dataIndex: 'DischargeOverCurrentCriticalAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'A',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: currentFormat,
  },
  {
    title: '充电过温三级报警门限',
    dataIndex: 'ChargeOverTempCriticalAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '充电过温三级恢复门限',
    dataIndex: 'ChargeOverTempCriticalAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '充电欠温三级报警门限',
    dataIndex: 'ChargeUnderTempCriticalAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '充电欠温三级恢复门限',
    dataIndex: 'ChargeUnderTempCriticalAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '放电过温三级报警门限',
    dataIndex: 'DischargeOverTempCriticalAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '放电过温三级恢复门限',
    dataIndex: 'DischargeOverTempCriticalAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '放电欠温三级报警门限',
    dataIndex: 'DischargeUnderTempCriticalAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '放电欠温三级恢复门限',
    dataIndex: 'DischargeUnderTempCriticalAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '温差过高三级报警门限',
    dataIndex: 'TempDiffOverBigCriticalAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '温差过高三级恢复门限',
    dataIndex: 'TempVolDiffOverBigCriticalAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '极柱温度过高三级报警门限',
    dataIndex: 'PoleOverTempCriticalAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '极柱温度过高三级恢复门限',
    dataIndex: 'PoleOverTempCriticalAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: 'SOC 过低三级报警门限',
    dataIndex: 'SocLowerCriticalAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '%',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: percentageFormat,
  },
  {
    title: 'SOC 过低三级恢复门限',
    dataIndex: 'SocLowerCriticalAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '%',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: percentageFormat,
  },
  {
    title: '绝缘失效三级报警门限',
    dataIndex: 'InsulationFailureCriticalAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'kΩ',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: kohmFormat,
  },
  {
    title: '绝缘失效三级恢复门限',
    dataIndex: 'InsulationFailureCriticalAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'kΩ',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: kohmFormat,
  },
  {
    title: '高压箱连接器温度过高三级报警门限',
    dataIndex: 'HvbOverTempCriticalAlarmThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '高压箱连接器温度过高三级恢复门限',
    dataIndex: 'HvbOverTempCriticalAlarmRecThd',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
];

export const protectFourColumns: FormAndDetailType[] = [
  {
    title: '单体过压四级保护限值',
    dataIndex: 'CellOverVoltageLevelFourProtectionLimit',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体过压四级保护恢复值',
    dataIndex: 'CellOverVoltageLevelFourProtectionRec',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体欠压四级保护限值',
    dataIndex: 'CellUnderVoltageLevelFourProtectionLimit',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体欠压四级保护恢复值',
    dataIndex: 'CellUnderVoltageLevelFourProtectionRec',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压过压四级保护限值',
    dataIndex: 'SystemOverVoltageLevelFourProtectionLimit',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压过压四级保护恢复值',
    dataIndex: 'SystemOverVoltageLevelFourProtectionRec',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压欠压四级保护限值',
    dataIndex: 'SystemUnderVoltageLevelFourProtectionLimit',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压欠压四级保护恢复值',
    dataIndex: 'SystemUnderVoltageLevelFourProtectionRec',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体压差过高四级保护限值',
    dataIndex: 'CellVolDiffOverBigLevelFourProtectionLimit',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '单体压差过高四级保护恢复值',
    dataIndex: 'CellVolDiffOverBigLevelFourProtectionRec',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压压差过高四级保护限值',
    dataIndex: 'SystemVolDiffOverBigLevelFourProtectionLimit',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '总电压压差过高四级保护恢复值',
    dataIndex: 'SystemVolDiffOverBigLevelFourProtectionRec',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'mV',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: mvFormat,
  },
  {
    title: '充电过流四级保护限值',
    dataIndex: 'ChargeOverCurrentLevelFourProtectionLimit',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'A',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: currentFormat,
  },
  {
    title: '充电过流四级保护恢复值',
    dataIndex: 'ChargeOverCurrentLevelFourProtectionRec',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'A',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: currentFormat,
  },
  {
    title: '放电过流四级保护限值',
    dataIndex: 'DischargeOverCurrentLevelFourProtectionLimit',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'A',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: currentFormat,
  },
  {
    title: '放电过流四级保护恢复值',
    dataIndex: 'DischargeOverCurrentLevelFourProtectionRec',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'A',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: currentFormat,
  },
  {
    title: '充电过温四级保护限值',
    dataIndex: 'ChargeOverTempLevelFourProtectionLimit',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '充电过温四级保护恢复值',
    dataIndex: 'ChargeOverTempLevelFourProtectionRec',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '充电欠温四级保护限值',
    dataIndex: 'ChargeUnderTempLevelFourProtectionLimit',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '充电欠温四级保护恢复值',
    dataIndex: 'ChargeUnderTempLevelFourProtectionRec',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '放电过温四级保护限值',
    dataIndex: 'DischargeOverTempLevelFourProtectionLimit',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '放电过温四级保护恢复值',
    dataIndex: 'DischargeOverTempLevelFourProtectionRec',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '放电欠温四级保护限值',
    dataIndex: 'DischargeUnderTempLevelFourProtectionLimit',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '放电欠温四级保护恢复值',
    dataIndex: 'DischargeUnderTempLevelFourProtectionRec',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '温差过高四级保护限值',
    dataIndex: 'TempDiffOverBigLevelFourProtectionLimit',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '温差过高四级保护恢复值',
    dataIndex: 'TempDiffOverBigLevelFourProtectionRec',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '极柱温度过高四级保护限值',
    dataIndex: 'PoleOverTempLevelFourProtectionLimit',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '极柱温度过高四级保护恢复值',
    dataIndex: 'PoleOverTempLevelFourProtectionRec',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: 'SOC 过低四级保护限值',
    dataIndex: 'SocLowerLevelFourProtectionLimit',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '%',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: percentageFormat,
  },
  {
    title: 'SOC 过低四级保护恢复值',
    dataIndex: 'SocLowerLevelFourProtectionRec',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '%',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: percentageFormat,
  },
  {
    title: '绝缘失效四级保护限值',
    dataIndex: 'InsulationFailureLevelFourProtectionLimit',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'kΩ',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: kohmFormat,
  },
  {
    title: '绝缘失效四级保护恢复值',
    dataIndex: 'InsulationFailureLevelFourProtectionRec',
    valueType: 'digit',
    fieldProps: {
      addonAfter: 'kΩ',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: kohmFormat,
  },
  {
    title: '高压箱连接器温度过高四级保护限值',
    dataIndex: 'HvbOverTempLevelFourProtectionLimit',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
  {
    title: '高压箱连接器温度过高四级保护恢复值',
    dataIndex: 'HvbOverTempLevelFourProtectionRec',
    valueType: 'digit',
    fieldProps: {
      addonAfter: '℃',
      min: Number.MIN_SAFE_INTEGER,
    },
    format: tempFormat,
  },
];

export const batteryPackFireGroupItems: GroupItem[] = [
  {
    items: [...batteryPackFireColumns],
  },
];

export const batteryPackGroupItems: GroupItem[] = [
  {
    items: [...batteryPackColumns],
  },
];

export const protectOneGroupItems: GroupItem[] = [
  {
    items: protectOneColumns,
  },
];

export const protectTwoGroupItems: GroupItem[] = [
  {
    items: protectTwoColumns,
  },
];

export const protectThreeGroupItems: GroupItem[] = [
  {
    items: protectThreeColumns,
  },
];

export const protectFourGroupItems: GroupItem[] = [
  {
    items: protectFourColumns,
  },
];
