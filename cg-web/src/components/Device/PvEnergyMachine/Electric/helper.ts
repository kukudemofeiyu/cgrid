/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-11-17 09:41:24
 * @LastEditTime: 2023-11-17 09:41:24
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\PvEnergyMachine\Electric\helper.ts
 */

import { formatMessage } from '@/utils';

export const chartOption = {
  yAxis: {
    name: formatMessage({ id: 'common.unit', defaultMessage: '单位' }) + '（kWh）',
  },
  series: [
    {
      type: 'bar',
      color: '#FF7B7B',
    },
    {
      type: 'bar',
      color: '#FFC542',
    },
    {
      type: 'bar',
      color: '#007DFF',
    },
    {
      type: 'bar',
      color: '#FF974A',
    },
    {
      type: 'bar',
      color: '#3DD598',
    },
  ],
};
