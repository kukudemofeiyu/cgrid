/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-11-16 16:38:41
 * @LastEditTime: 2023-11-16 16:38:41
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\PvEnergyMachine\Run\helper.ts
 */

import { DetailItem } from '@/components/Detail';
import { formatMessage } from '@/utils';
import { alarmFormat, modelFormat, startFormat, tempFormat } from '@/utils/format';

export const runItems: DetailItem[] = [
  {
    label: formatMessage({
      id: 'device.fireProtectionHostFailureStatus',
      defaultMessage: '消防主机故障状态',
    }),
    field: 'a',
    format: alarmFormat,
  },
  {
    label: formatMessage({
      id: 'device.fireProtectionHostStartupStatus',
      defaultMessage: '消防主机启动状态',
    }),
    field: 'b',
    format: startFormat,
  },
  {
    label: formatMessage({
      id: 'device.operationModeFireProtectionHost',
      defaultMessage: '消防主机运行模式',
    }),
    field: 'c',
    format: modelFormat,
  },
  {
    label: formatMessage({
      id: 'device.smokeWarningOutdoorDetectors',
      defaultMessage: '箱外探测器烟雾预警',
    }),
    field: 'd',
    format: alarmFormat,
  },
  {
    label: formatMessage({
      id: 'device.smokeWarningOutdoorDetectors',
      defaultMessage: '箱外探测器烟雾预警',
    }),
    field: 'e',
    format: alarmFormat,
  },
  {
    label: formatMessage({
      id: 'device.smokeWarningOutdoorDetectors',
      defaultMessage: '箱外探测器烟雾预警',
    }),
    field: 'f',
    format: alarmFormat,
  },
  {
    label: formatMessage({
      id: 'device.smokeWarningOutdoorDetectors',
      defaultMessage: '箱外探测器烟雾预警',
    }),
    field: 'g',
    format: alarmFormat,
  },
  {
    label:
      formatMessage({
        id: 'device.externalDetectorTemperature',
        defaultMessage: '箱外探测器温度',
      }) + '01',
    field: 'h',
    format: tempFormat,
  },
  {
    label:
      formatMessage({
        id: 'device.externalDetectorTemperature',
        defaultMessage: '箱外探测器温度',
      }) + '02',
    field: 'i',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'device.detectedOutsideH2Value', defaultMessage: '箱外探测H2值' }),
    field: 'j',
  },
  {
    label: formatMessage({ id: 'device.detectedOutsideCOValue', defaultMessage: '箱外探测CO值' }),
    field: 'k',
  },
  {
    label: formatMessage({
      id: 'device.stationControlFireFeedback',
      defaultMessage: '站控消防反馈',
    }),
    field: 'l',
    format: alarmFormat,
  },
  {
    label: formatMessage({ id: 'device.fanFeedback', defaultMessage: '风机反馈' }),
    field: 'm',
    format: alarmFormat,
  },
  {
    label: formatMessage({ id: 'device.firePreventionControl', defaultMessage: '消防火灾' }),
    field: 'n',
    format: alarmFormat,
  },
  {
    label: formatMessage({ id: 'device.fireLinkage', defaultMessage: '消防联动' }),
    field: 'o',
    format: alarmFormat,
  },
  {
    label:
      formatMessage({ id: 'device.waterImmersion', defaultMessage: '水浸' }) +
      '1' +
      formatMessage({ id: 'common.warning', defaultMessage: '告警' }),
    field: 'p',
    format: alarmFormat,
  },
  {
    label:
      formatMessage({ id: 'device.waterImmersion', defaultMessage: '水浸' }) +
      '2' +
      formatMessage({ id: 'common.warning', defaultMessage: '告警' }),
    field: 'q',
    format: alarmFormat,
  },
  {
    label:
      formatMessage({ id: 'device.accessControl', defaultMessage: '门禁' }) +
      '1' +
      formatMessage({ id: 'common.warning', defaultMessage: '告警' }),
    field: 'r',
    format: alarmFormat,
  },
  {
    label:
      formatMessage({ id: 'device.accessControl', defaultMessage: '门禁' }) +
      '2' +
      formatMessage({ id: 'common.warning', defaultMessage: '告警' }),
    field: 's',
    format: alarmFormat,
  },
];
