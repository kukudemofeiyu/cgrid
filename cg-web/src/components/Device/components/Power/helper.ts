/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-05 14:51:12
 * @LastEditTime: 2025-02-05 14:41:11
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/Device/components/Power/helper.ts
 */

import { formatMessage } from '@/utils';

export const options = {
  grid: {
    left: 0,
    top: 30,
    right: 20,
    bottom: 50,
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
      bottom: 30,
    },
  ],
  yAxis: [
    {
      name: formatMessage({ id: 'device.chargingPower', defaultMessage: '充电功率' }) + '（kW）',
      alignTicks: true,
    },
    {
      name:
        formatMessage({ id: 'device.powerUtilization', defaultMessage: '功率利用率' }) + '（%）',
    },
  ],
  series: [
    {
      type: 'line',
      color: 'rgba(21, 154, 255, 1)',
    },
    {
      type: 'line',
      yAxisIndex: 1,
      color: 'rgba(255, 151, 74, 1)',
    },
  ],
};
