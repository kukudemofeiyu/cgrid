/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-05 15:19:49
 * @LastEditTime: 2024-04-23 15:15:39
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\components\Trend\helper.tsx
 */

import { DetailItem } from '@/components/Detail';
import { formatMessage } from '@/utils';
import styles from './index.less';

export const options = {
  grid: {
    left: 0,
    top: 10,
    right: 20,
    bottom: 70,
  },
  legend: {
    top: 'bottom',
  },
  dataZoom: [
    {
      type: 'inside',
    },
    {
      start: 0,
      end: 100,
      height: 15,
      bottom: 50,
    },
  ],
  yAxis: [
    {
      name: '',
    },
  ],
  series: [
    {
      type: 'line',
      color: 'rgba(21, 154, 255, 1)',
    },
    {
      type: 'line',
      color: 'rgba(255, 151, 74, 1)',
    },
    {
      type: 'line',
      color: '#01cfa1',
    },
    {
      type: 'line',
      color: '#FFC542',
    },
  ],
};

export const detailItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'device.numberOfChargingOrders', defaultMessage: '充电订单数' }),
    field: 'chargeCountTotal',
    className: styles.charge,
    unit: formatMessage({ id: 'siteMonitor.order', defaultMessage: '单' }),
  },
  {
    label: formatMessage({ id: 'device.chargeDuration', defaultMessage: '充电时长' }),
    field: 'todayChargeTime',
    unit: formatMessage({ id: 'device.hour', defaultMessage: '小时' }),
  },
  {
    label: formatMessage({ id: 'device.chargingCapacity', defaultMessage: '充电量' }),
    field: 'chargeElectricityTotal',
    className: styles.charge,
    unit: 'kWh',
  },
  {
    label: formatMessage({ id: 'device.chargingFee', defaultMessage: '充电费用' }),
    field: 'todayMoney',
    unit: formatMessage({ id: 'common.rmb', defaultMessage: '元' }),
  },
];
