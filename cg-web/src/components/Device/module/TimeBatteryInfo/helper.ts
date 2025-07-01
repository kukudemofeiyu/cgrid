/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-05-29 16:12:26
 * @LastEditTime: 2024-05-30 11:21:17
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\module\TimeBatteryInfo\helper.ts
 */

import { ProColumns } from '@ant-design/pro-components';
import { formatMessage } from '@/utils';
import { DetailItem } from '@/components/Detail';

export const detailItems: DetailItem[] = [
  {
    title: formatMessage({ id: 'device.1008', defaultMessage: '总充电量' }),
    dataIndex: 'charge',
    unit: 'kWh',
  },
  {
    title: formatMessage({ id: 'device.1009', defaultMessage: '总放电量' }),
    dataIndex: 'discharge',
    unit: 'kWh',
  },
];

export const columns: ProColumns[] = [
  {
    title: formatMessage({ id: 'common.periodTime', defaultMessage: '时段' }),
    dataIndex: 'time0',
    width: 96,
  },
  {
    title: formatMessage({ id: 'device.chargingCapacity', defaultMessage: '充电量' }) + '(kWh)',
    dataIndex: 'charge0',
    width: 120,
  },
  {
    title: formatMessage({ id: 'device.1005', defaultMessage: '放电量' }) + '(kWh)',
    dataIndex: 'discharge0',
    width: 120,
  },
  {
    title: formatMessage({ id: 'common.periodTime', defaultMessage: '时段' }),
    dataIndex: 'time1',
    width: 96,
  },
  {
    title: formatMessage({ id: 'device.chargingCapacity', defaultMessage: '充电量' }) + '(kWh)',
    dataIndex: 'charge1',
    width: 120,
  },
  {
    title: formatMessage({ id: 'device.1005', defaultMessage: '放电量' }) + '(kWh)',
    dataIndex: 'discharge1',
    width: 120,
  },
  {
    title: formatMessage({ id: 'common.periodTime', defaultMessage: '时段' }),
    dataIndex: 'time2',
    width: 96,
  },
  {
    title: formatMessage({ id: 'device.chargingCapacity', defaultMessage: '充电量' }) + '(kWh)',
    dataIndex: 'charge2',
    width: 120,
  },
  {
    title: formatMessage({ id: 'device.1005', defaultMessage: '放电量' }) + '(kWh)',
    dataIndex: 'discharge2',
    width: 120,
  },
  {
    title: formatMessage({ id: 'common.periodTime', defaultMessage: '时段' }),
    dataIndex: 'time3',
    width: 96,
  },
  {
    title: formatMessage({ id: 'device.chargingCapacity', defaultMessage: '充电量' }) + '(kWh)',
    dataIndex: 'charge3',
    width: 120,
  },
  {
    title: formatMessage({ id: 'device.1005', defaultMessage: '放电量' }) + '(kWh)',
    dataIndex: 'discharge3',
    width: 120,
  },
  {
    title: formatMessage({ id: 'device.1008', defaultMessage: '总充电量' }) + '(kWh)',
    dataIndex: 'totalCharge',
    width: 120,
  },
  {
    title: formatMessage({ id: 'device.1009', defaultMessage: '总放电量' }) + '(kWh)',
    dataIndex: 'totalDischarge',
    width: 120,
  },
];
