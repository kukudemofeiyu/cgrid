/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-04-25 14:26:08
 * @LastEditTime: 2024-04-25 14:26:08
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Chart\ChartDate\helper.ts
 */

import { formatMessage, getLocale } from '@/utils';
import { chartTypeEnum } from '../config';

export const typeMap = [
  {
    value: chartTypeEnum.Day,
    label: formatMessage({ id: 'common.time.day', defaultMessage: '日' }),
    dateType: 'date',
    format: getLocale().dateFormat,
  },
  {
    value: chartTypeEnum.Month,
    label: formatMessage({ id: 'common.time.month', defaultMessage: '月' }),
    dateType: 'month',
    format: getLocale().monthYearFormat,
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
