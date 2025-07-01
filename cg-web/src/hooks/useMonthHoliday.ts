/*
 *@Author: aoshilin
 *@Date: 2025-02-17 11:07:08
 *@Description: 获取月的节假日和周末
 */
import { useState, useEffect, useCallback } from 'react';
import { useRequest } from 'umi';
import { getMonthHolidays } from '@/services/index';
import moment from 'moment';

type MonthHolidayType = {
  date?: string; //YYYY-MM
  isRange?: boolean; //返回数据以期间格式展示
};

const useMonthHoliday = (props?: MonthHolidayType) => {
  const { date = moment().format('YYYY-MM'), isRange = true } = props || {};
  const [holiday, setHoliday] = useState<any>([]); //节假日
  const [weekend, setWeekend] = useState<any>([]); //周末
  const [allDay, setAllDay] = useState<any>([]); //当月所有天

  const { run, data } = useRequest(getMonthHolidays, {
    manual: true,
  });

  /*
   *@Author: aoshilin
   *@Date: 2025-02-17 11:44:08
   *@parms:
   *@Description: 获取当月所有天
   */
  const getAllDay = useCallback(() => {
    const startDate = moment(date).startOf('month');
    const endDate = moment(date).endOf('month');
    let curAllDay: string[] | string = [];
    if (isRange) {
      curAllDay = `${startDate.format('YYYY-MM-DD')}～${endDate.format('YYYY-MM-DD')}`;
    } else {
      for (let i = 0; i <= moment(endDate).diff(moment(startDate), 'days'); i++) {
        const day = moment(startDate).add(i, 'days');
        curAllDay.push(day.format('YYYY-MM-DD'));
      }
    }
    setAllDay(curAllDay);
  }, [date, isRange]);

  /*
   *@Author: aoshilin
   *@Date: 2025-02-17 10:32:02
   *@parms:
   *@Description: 获取周末
   */
  const getWeekend = useCallback(() => {
    const startDate = moment(date).startOf('month');
    const endDate = moment(date).endOf('month');
    const curWeekend: string[] = [];
    for (let i = 0; i < moment(endDate).diff(moment(startDate), 'days'); i++) {
      const day = moment(startDate).add(i, 'days');
      const dayOfWeek = day.isoWeekday(); // 获取当天是星期几（0: 周日, 6: 周六）
      if ([6, 7].includes(dayOfWeek)) {
        if (
          isRange &&
          dayOfWeek == 7 &&
          curWeekend.length > 0 &&
          moment(curWeekend[curWeekend.length - 1]).isoWeekday() == 6
        ) {
          curWeekend[curWeekend.length - 1] += `～${day.format('YYYY-MM-DD')}`;
        } else {
          curWeekend.push(day.format('YYYY-MM-DD'));
        }
      }
    }
    setWeekend(curWeekend);
  }, [date, isRange]);

  /*
   *@Author: aoshilin
   *@Date: 2025-02-17 14:33:55
   *@parms:
   *@Description: 获取连续天数
   */
  const getContinuityDay = useCallback((startDate, endDate) => {
    const datesArray = [];
    let currentDate = moment(startDate); // 初始化为开始日期
    const end = moment(endDate); // 结束日期
    // 遍历从开始日期到结束日期的所有日期
    while (currentDate <= end) {
      datesArray.push(moment(currentDate).format('YYYY-MM-DD')); // 格式化日期并加入数组
      currentDate = moment(currentDate).add(1, 'days'); // 进入下一天
    }

    return datesArray;
  }, []);

  useEffect(() => {
    if (!date) return;
    const year = date.split('-')[0];
    getAllDay();
    getWeekend();
    run({ year });
  }, [date, getAllDay, getWeekend, run]);

  /*
   *@Author: aoshilin
   *@Date: 2025-02-17 14:34:34
   *@parms:
   *@Description: 获取节假日
   */
  useEffect(() => {
    if (data) {
      const month = data
        .filter((item: any) => item.startDate.includes(date) || item.endDate.includes(date))
        .map((item: any) => {
          if (moment(item.startDate) < moment(date).startOf('month')) {
            item.startDate = moment(date).startOf('month').format('YYYY-MM-DD');
          }
          if (moment(item.endDate) > moment(date).endOf('month')) {
            item.endDate = moment(date).endOf('month').format('YYYY-MM-DD');
          }
          return `${item.startDate}～${item.endDate}`;
        });
      if (isRange) {
        setHoliday(month);
      } else {
        const monthArray: string[] = [];
        month.forEach((time: any) => {
          const [startDate, endDate] = time.split('～');
          monthArray.push(...getContinuityDay(startDate, endDate));
        });
        setHoliday(monthArray);
      }
    }
  }, [data, date, getContinuityDay, isRange]);

  /*
   *@Author: aoshilin
   *@Date: 2025-02-17 14:33:34
   *@parms:
   *@Description: 将连续日期合并为字符串
   */
  const mergeContinuousDates = (datesArray: string[]) => {
    const merged = [];
    let start: any = null;
    let end: any = null;
    for (let i = 0; i < datesArray.length; i++) {
      const current = moment(datesArray[i]);
      if (!start) {
        start = current;
        end = current;
      } else {
        const next = moment(end).add(1, 'days');
        if (current.isSame(next, 'day')) {
          end = current; // 如果连续，则更新结束日期
        } else {
          // 如果不连续，则保存当前范围并开始新范围
          if (start.isSame(end, 'day')) {
            merged.push(start.format('YYYY-MM-DD'));
          } else {
            merged.push(`${start.format('YYYY-MM-DD')}～${end.format('YYYY-MM-DD')}`);
          }
          start = current;
          end = current;
        }
      }
    }
    // 处理最后一个范围
    if (start) {
      if (start.isSame(end, 'day')) {
        merged.push(start.format('YYYY-MM-DD'));
      } else {
        merged.push(`${start.format('YYYY-MM-DD')}～${end.format('YYYY-MM-DD')}`);
      }
    }
    return merged;
  };

  return {
    holiday,
    weekend,
    allDay,
    getContinuityDay,
    mergeContinuousDates,
  };
};

export default useMonthHoliday;
