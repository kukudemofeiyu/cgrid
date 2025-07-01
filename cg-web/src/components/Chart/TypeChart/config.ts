/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-08-01 11:28:24
 * @LastEditTime: 2024-05-10 16:19:12
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Chart\TypeChart\config.ts
 */
import moment from 'moment';
import type { Moment } from 'moment';
import { chartTypeEnum } from '../config';

export const getAllMinute = (step = 2, hourRange?: boolean): string[] => {
  return Array.from({ length: (24 * 60) / step }).map((_, index) => {
    const time = moment()
      .startOf('day')
      .add(index * step, 'minute');
    return hourRange && step == 60
      ? `${time.format('HH:mm')}-${time.add(1, 'h').format('HH:mm')}`
      : time.format('HH:mm');
  });
};

const getAllDay = (date: Moment) => {
  return Array.from({ length: moment(date).daysInMonth() }).map((_, index) => {
    return moment(date).startOf('M').add(index, 'd').format('YYYY-MM-DD');
  });
};

const getAllMonth = (date: Moment) => {
  return Array.from({ length: 12 }).map((_, index) => {
    return moment(date).startOf('y').add(index, 'M').format('YYYY-MM');
  });
};

export const typeMap = new Map([
  [chartTypeEnum.Day, { format: 'HH:mm', fun: getAllMinute }],
  [chartTypeEnum.Month, { format: 'YYYY-MM-DD', fun: getAllDay }],
  [chartTypeEnum.Year, { format: 'YYYY-MM', fun: getAllMonth }],
]);
