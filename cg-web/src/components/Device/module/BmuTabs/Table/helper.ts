/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-07-10 11:14:21
 * @LastEditTime: 2024-07-10 18:16:13
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\module\BmuTabs\Table\helper.ts
 */

import { DetailItem } from '@/components/Detail';
import { formatMessage } from '@/utils';

export const items: DetailItem[] = [
  {
    dataIndex: 'maxCell',
    title: formatMessage({ id: 'siteMonitor.maximumVoltage', defaultMessage: '最高单体电压' }),
    format: (value, data) => `${value}(${data.cell.max.bmu}#-${data.cell.max.index})`,
  },
  {
    dataIndex: 'minCell',
    title: formatMessage({ id: 'siteMonitor.minimumVoltage', defaultMessage: '最低单体电压' }),
    format: (value, data) => `${value}(${data.cell.min.bmu}#-${data.cell.min.index})`,
  },
  {
    dataIndex: 'maxTemp',
    title: formatMessage({ id: 'siteMonitor.maximumTemperature', defaultMessage: '最高单体温度' }),
    format: (value, data) => `${value}(${data.temp.max.bmu}#-${data.temp.max.index})`,
  },
  {
    dataIndex: 'minTemp',
    title: formatMessage({ id: 'siteMonitor.minimumTemperature', defaultMessage: '最低单体温度' }),
    format: (value, data) => `${value}(${data.temp.min.bmu}#-${data.temp.min.index})`,
  },
  {
    dataIndex: 'maxPoleTemp',
    title: formatMessage({ id: 'device.1106', defaultMessage: '最高极柱温度' }),
    format: (value, data) => `${value}(${data.temp.max.bmu}#-${data.temp.max.index})`,
  },
  {
    dataIndex: 'minPoleTemp',
    title: formatMessage({ id: 'device.1107', defaultMessage: '最低极柱温度' }),
    format: (value, data) => `${value}(${data.poleTemp.min.bmu}#-${data.poleTemp.min.index})`,
  },
];

export const initMaxData = {
  cell: {
    max: {
      bmu: 0,
      index: 0,
      value: Number.MIN_SAFE_INTEGER,
    },
    min: {
      bmu: 0,
      index: 0,
      value: Number.MAX_SAFE_INTEGER,
    },
  },
  temp: {
    max: {
      bmu: 0,
      index: 0,
      value: Number.MIN_SAFE_INTEGER,
    },
    min: {
      bmu: 0,
      index: 0,
      value: Number.MAX_SAFE_INTEGER,
    },
  },
  poleTemp: {
    max: {
      bmu: 0,
      index: 0,
      value: Number.MIN_SAFE_INTEGER,
    },
    min: {
      bmu: 0,
      index: 0,
      value: Number.MAX_SAFE_INTEGER,
    },
  },
};