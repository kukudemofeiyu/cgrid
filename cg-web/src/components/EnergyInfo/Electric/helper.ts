/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-18 18:00:46
 * @LastEditTime: 2024-06-12 17:49:45
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\Electric\helper.ts
 */

import { chartTypeEnum } from '@/components/Chart/config';
import { DetailItem } from '@/components/Detail';
import { formatMessage } from '@/utils';
import { EnergySourceEnum } from '.';

export const chartOption = {
  grid: {
    top: 30,
    bottom: 20,
  },
  legend: {
    icon: 'rect',
    top: 'bottom',
  },
  yAxis: {
    // name: formatMessage({ id: 'common.unit', defaultMessage: '单位' }) + '（kWh）',
  },
  series: [
    {
      type: 'bar',
      color: 'rgba(21, 154, 255, 1)',
    },
    {
      type: 'bar',
      color: 'rgba(255, 151, 74, 1)',
    },
  ],
};

export const detailItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'siteMonitor.allCharge', defaultMessage: '总充电量' }),
    field: 'totalCharge',
    unit: 'kWh',
  },
  {
    label: formatMessage({ id: 'siteMonitor.allDisharge', defaultMessage: '总放电量' }),
    field: 'totalDischarge',
    unit: 'kWh',
  },
  {
    label: formatMessage({ id: 'siteMonitor.totalRevenue', defaultMessage: '总收益' }),
    field: 'totalIncome',
    unit: 1,
    show: (_, data) => data.source == EnergySourceEnum.SiteMonitor,
  },
];

export const typeMap = [
  {
    value: chartTypeEnum.Day,
    label: formatMessage({ id: 'common.time.day', defaultMessage: '日' }),
    dateType: 'date',
    format: 'YYYY-MM-DD',
  },
  {
    value: chartTypeEnum.Month,
    label: formatMessage({ id: 'common.time.month', defaultMessage: '月' }),
    dateType: 'month',
    format: 'YYYY-MM',
  },
  {
    value: chartTypeEnum.Year,
    label: formatMessage({ id: 'common.time.year', defaultMessage: '年' }),
    dateType: 'year',
    format: 'YYYY',
  },
  {
    value: chartTypeEnum.Label,
    label: formatMessage({ id: 'common.time.total', defaultMessage: '累计' }),
    dateType: 'year',
    format: 'YYYY',
  },
];
