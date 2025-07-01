/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-08-31 16:23:48
 * @LastEditTime: 2024-04-08 15:21:29
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\Community\config.ts
 */

import type { DetailItem } from '@/components/Detail';
import { formatMessage } from '@/utils';
import { DeviceMasterMode } from '@/utils/dictionary';

export const accountItem: DetailItem[] = [
  {
    label: 'mqtt' + formatMessage({ id: 'common.userName', defaultMessage: '用户名' }),
    field: 'userName',
  },
  {
    label: 'mqtt' + formatMessage({ id: 'common.password', defaultMessage: '密码' }),
    field: 'a',
    span: 2,
  },
];

export const meterItem: DetailItem[] = [
  {
    label: 'mqtt' + formatMessage({ id: 'common.userName', defaultMessage: '用户名' }),
    field: 'userName',
  },
  { label: 'mqtt' + formatMessage({ id: 'common.password', defaultMessage: '密码' }), field: 'a' },
  {
    label: formatMessage({ id: 'device.currentRatio', defaultMessage: '电流变比' }),
    field: 'currentRatio',
  },
  {
    label: formatMessage({ id: 'device.voltageRatio', defaultMessage: '电压变比' }),
    field: 'voltageRatio',
  },
  {
    label: formatMessage({ id: 'device.electricRatio', defaultMessage: '电能变比' }),
    field: 'energyRatio',
  },
  {
    label: formatMessage({ id: 'device.powerRatio', defaultMessage: '功率变比' }),
    field: 'powerRatio',
  },
];
export const selfEnergyMeterItem: DetailItem[] = [
  {
    label: formatMessage({ id: 'device.currentRatio', defaultMessage: '电流变比' }),
    field: 'currentRatio',
  },
  {
    label: formatMessage({ id: 'device.voltageRatio', defaultMessage: '电压变比' }),
    field: 'voltageRatio',
    span: 2,
  },
];

export const thirtySiteItem: DetailItem[] = [
  {
    label: formatMessage({ id: 'device.thirdPartySite', defaultMessage: '第三方站点' }),
    field: 'thirdSiteName',
  },
];

export const thirtySiteGunItem: DetailItem[] = [
  {
    label: formatMessage({ id: 'device.thirdPartySite', defaultMessage: '第三方站点' }),
    field: 'thirdSiteName',
  },
  {
    label: formatMessage({
      id: 'device.anyChargingGunSequenceCode',
      defaultMessage: '任一充电枪序列码',
    }),
    field: 'anyGnSn',
    span: 2,
  },
];

const deviceTypeItems: DetailItem[] = [
  {
    label: formatMessage(
      { id: 'device.deviceTypeSentence', defaultMessage: '设备类型' },
      { name: 'BMS' },
    ),
    field: 'bmsDeviceType',
  },
  {
    label: formatMessage(
      { id: 'device.deviceTypeSentence', defaultMessage: '设备类型' },
      { name: 'EMS' },
    ),
    field: 'emsDeviceType',
  },
  {
    label: formatMessage(
      { id: 'device.deviceTypeSentence', defaultMessage: '设备类型' },
      { name: 'PCS' },
    ),
    field: 'pcsDeviceType',
  },
  {
    label: formatMessage(
      { id: 'device.deviceTypeSentence', defaultMessage: '设备类型' },
      {
        name: formatMessage({ id: 'device.cellTemperature', defaultMessage: '单体温度' }),
      },
    ),
    field: 'temperatureDeviceType',
  },
  {
    label: formatMessage(
      { id: 'device.deviceTypeSentence', defaultMessage: '设备类型' },
      {
        name: formatMessage({ id: 'device.cellVoltage', defaultMessage: '单体电压' }),
      },
    ),
    field: 'voltageDeviceType',
  },
  {
    label: formatMessage(
      { id: 'device.deviceTypeSentence', defaultMessage: '设备类型' },
      {
        name: formatMessage({ id: 'device.air', defaultMessage: '空调' }),
      },
    ),
    field: 'airConditionerDeviceType',
  },
  {
    label: formatMessage(
      { id: 'device.deviceTypeSentence', defaultMessage: '设备类型' },
      {
        name: formatMessage({ id: 'device.singleClock', defaultMessage: '单体时钟' }),
      },
    ),
    field: 'clockDeviceType',
  },
  {
    label: formatMessage(
      { id: 'device.deviceTypeSentence', defaultMessage: '设备类型' },
      {
        name: formatMessage({ id: 'device.Statistics', defaultMessage: '数据统计' }),
      },
    ),
    field: 'statisticsDeviceType',
  },
  {
    label: formatMessage(
      { id: 'device.deviceTypeSentence', defaultMessage: '设备类型' },
      {
        name: formatMessage({ id: 'device.gridMeter', defaultMessage: '电网侧电表' }),
      },
    ),
    field: 'powerGridSideDeviceType',
  },
  {
    label: formatMessage(
      { id: 'device.deviceTypeSentence', defaultMessage: '设备类型' },
      {
        name: formatMessage({ id: 'device.inverterMeter', defaultMessage: '逆变侧电表' }),
      },
    ),
    field: 'inverterSideDeviceType',
  },
  {
    label: formatMessage(
      { id: 'device.deviceTypeSentence', defaultMessage: '设备类型' },
      {
        name: formatMessage(
          { id: 'device.masterSentence', defaultMessage: '主机' },
          { name: 'EMS' },
        ),
      },
    ),
    field: 'masterEmsDeviceType',
    show: (_, data) => data.masterSlaveMode != DeviceMasterMode.Slave,
  },
  {
    label: formatMessage(
      { id: 'device.deviceTypeSentence', defaultMessage: '设备类型' },
      {
        name: formatMessage(
          { id: 'device.masterSentence', defaultMessage: '主机' },
          { name: formatMessage({ id: 'device.clock', defaultMessage: '时钟' }) },
        ),
      },
    ),
    field: 'masterClockDeviceType',
    show: (_, data) => data.masterSlaveMode != DeviceMasterMode.Slave,
  },
];

export const bWattItem: DetailItem[] = [
  {
    label: formatMessage({ id: 'device.item', defaultMessage: '项目' }) + ' ID',
    field: 'projectId',
  },
  { label: 'BMS ID', field: 'bmsId' },
  { label: 'EMS ID', field: 'emsId' },
  { label: 'PCS ID', field: 'pcsId' },
  {
    label: formatMessage({ id: 'device.cellTemperature', defaultMessage: '单体温度' }) + ' ID',
    field: 'temperatureDeviceId',
  },
  {
    label: formatMessage({ id: 'device.cellVoltage', defaultMessage: '单体电压' }) + ' ID',
    field: 'voltageDeviceId',
  },
  {
    label: formatMessage({ id: 'device.airConditioner', defaultMessage: '空调' }) + ' ID',
    field: 'airConditionerId',
  },
  {
    label: formatMessage({ id: 'device.systemClock', defaultMessage: '系统时钟' }) + ' ID',
    field: 'clockDeviceId',
  },
  {
    label:
      formatMessage({ id: 'device.energyConsumptionStatistics', defaultMessage: '能耗统计' }) +
      ' ID',
    field: 'statisticsDeviceId',
  },
  {
    label: formatMessage({ id: 'device.thirdPartySite', defaultMessage: '第三方站点' }),
    field: 'thirdSiteName',
  },
  {
    label: formatMessage({ id: 'device.gridMeter', defaultMessage: '电网侧电表' }) + ' ID',
    field: 'powerGridSideId',
  },
  {
    label: formatMessage({ id: 'device.inverterMeter', defaultMessage: '逆变侧电表' }) + ' ID',
    field: 'inverterSideId',
  },
  ...deviceTypeItems,
];

export const react100WItem: DetailItem[] = [
  {
    label: formatMessage({ id: 'device.item', defaultMessage: '项目' }) + ' ID',
    field: 'projectId',
  },
  {
    label: formatMessage(
      { id: 'device.masterSentence', defaultMessage: '主机' },
      { name: 'EMS ID' },
    ),
    field: 'masterEmsId',
  },
  {
    label: formatMessage(
      { id: 'device.masterSentence', defaultMessage: '主机' },
      {
        name: formatMessage(
          { id: 'device.systemClock', defaultMessage: '系统时钟' },
          { name: 'ID' },
        ),
      },
    ),
    field: 'masterClockDeviceId',
  },
  { label: 'BMS ID', field: 'bmsId' },
  {
    label: formatMessage(
      { id: 'device.slaveSentence', defaultMessage: '从机' },
      { name: 'EMS ID' },
    ),
    field: 'emsId',
  },
  { label: 'PCS ID', field: 'pcsId' },
  {
    label: formatMessage({ id: 'device.cellTemperature', defaultMessage: '单体温度' }) + ' ID',
    field: 'temperatureDeviceId',
  },
  {
    label: formatMessage({ id: 'device.cellVoltage', defaultMessage: '单体电压' }) + ' ID',
    field: 'voltageDeviceId',
  },
  {
    label: formatMessage({ id: 'device.airConditioner', defaultMessage: '空调' }) + ' ID',
    field: 'airConditionerId',
  },
  {
    label: formatMessage(
      { id: 'device.slaveSentence', defaultMessage: '从机' },
      {
        name: formatMessage(
          { id: 'device.systemClock', defaultMessage: '系统时钟' },
          { name: 'ID' },
        ),
      },
    ),
    field: 'clockDeviceId',
  },
  {
    label:
      formatMessage({ id: 'device.energyConsumptionStatistics', defaultMessage: '能耗统计' }) +
      ' ID',
    field: 'statisticsDeviceId',
  },
  {
    label: formatMessage({ id: 'device.thirdPartySite', defaultMessage: '第三方站点' }),
    field: 'thirdSiteName',
  },
  {
    label: formatMessage({ id: 'device.gridMeter', defaultMessage: '电网侧电表' }) + ' ID',
    field: 'powerGridSideId',
  },
  {
    label: formatMessage({ id: 'device.inverterMeter', defaultMessage: '逆变侧电表' }) + ' ID',
    field: 'inverterSideId',
  },
  ...deviceTypeItems,
];
