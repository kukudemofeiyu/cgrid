/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-11-15 15:21:29
 * @LastEditTime: 2023-11-17 09:54:17
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\PvEnergyMachine\Power\index.tsx
 */

import React, { useCallback, useEffect, useMemo, useState } from 'react';
import Detail from '@/components/Detail';
import styles from '../index.less';
import { DatePicker } from 'antd';
import moment, { Moment } from 'moment';
import TypeChart, { TypeChartDataType } from '@/components/Chart/TypeChart';
import { useRequest } from 'umi';
import { getPower } from '../service';
import { DeviceDataType } from '@/services/equipment';
import { PowerDataType } from '../typing';
import { formatMessage } from '@/utils';

export type PowerType = {
  className?: string;
  deviceData?: DeviceDataType;
};

const seriesMap = new Map([
  ['elec', formatMessage({ id: 'device.electricSupply', defaultMessage: '市电' })],
  ['pv', formatMessage({ id: 'device.pv', defaultMessage: '光伏' })],
  ['energy', formatMessage({ id: 'device.storage', defaultMessage: '储能' })],
  ['load', formatMessage({ id: 'device.load', defaultMessage: '负载' })],
]);

const Power: React.FC<PowerType> = (props) => {
  const { className, deviceData } = props;

  const [date, setDate] = useState<Moment>(moment());
  const { data: powerData, run } = useRequest(getPower, {
    manual: true,
    pollingInterval: 2 * 60 * 1000,
  });

  const onChange = useCallback((value: Moment | null) => {
    setDate(value || moment());
  }, []);

  const chartOption = useMemo(() => {
    return {
      yAxis: {
        name: formatMessage({ id: 'common.unit', defaultMessage: '单位' }) + '（kW）',
      },
      series: [
        {
          type: 'line',
          color: '#FF7B7B',
        },
        {
          type: 'line',
          color: '#FFC542',
        },
        {
          type: 'line',
          color: '#007DFF',
        },
        {
          type: 'line',
          color: '#3DD598',
        },
      ],
    };
  }, []);

  const chartData = useMemo<TypeChartDataType[]>(() => {
    const result: TypeChartDataType[] = [];
    seriesMap.forEach((name, field) => {
      result.push({
        name: name,
        data: powerData?.[field as any as keyof PowerDataType]?.map?.((item) => ({
          label: item.eventTs,
          value: item.doubleVal,
        })),
      });
    });
    return result;
  }, [powerData]);

  useEffect(() => {
    if (deviceData?.deviceId) {
      run({
        deviceId: deviceData?.deviceId,
        date: date.format('YYYY-MM-DD'),
      });
    }
  }, [deviceData?.deviceId, date]);

  return (
    <>
      <div className={`card-wrap shadow p20 mb20 ${styles.chart} ${className}`}>
        <Detail.Label
          className="mb12"
          title={formatMessage({ id: 'device.realTimePower', defaultMessage: '实时功率' })}
          size="small"
          showLine={false}
        >
          <DatePicker
            defaultValue={date}
            format="YYYY-MM-DD"
            onChange={onChange}
            allowClear={false}
          />
        </Detail.Label>
        <TypeChart style={{ height: 313 }} date={date} option={chartOption} data={chartData} />
      </div>
    </>
  );
};

export default Power;
