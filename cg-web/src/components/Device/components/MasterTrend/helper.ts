/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-05 15:19:49
 * @LastEditTime: 2024-03-05 16:52:23
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\components\Trend\helper.ts
 */

import { formatMessage } from '@/utils';

export const options = {
  grid: {
    left: 0,
    top: 30,
    right: 0,
    bottom: 30,
  },
  dataZoom: [
    {
      type: 'inside',
    },
    {
      start: 0,
      end: 100,
      height: 15,
      bottom: 10,
    },
  ],
  yAxis: [
    {
      name: formatMessage({ id: 'device.chargingPower1', defaultMessage: '充电功率' }) + '（kW）',
      nameTextStyle: {
        align: 'left',
      },
      alignTicks: true,
    },
    {
      name: formatMessage({ id: 'device.chargeCapacity', defaultMessage: '充电电量' }) + '（kWh）',
      nameTextStyle: {
        align: 'right',
      },
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
  ],
};
