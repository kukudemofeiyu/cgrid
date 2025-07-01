/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-11-15 15:22:37
 * @LastEditTime: 2023-11-17 09:55:42
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\PvEnergyMachine\Electric\index.tsx
 */

import React, { useCallback, useEffect, useMemo, useState } from 'react';
import Detail from '@/components/Detail';
import styles from '../index.less';
import { DatePicker, Select } from 'antd';
import { chartTypeEnum } from '@/components/Chart/config';
import moment, { Moment } from 'moment';
import { DeviceDataType } from '@/services/equipment';
import { useRequest } from 'umi';
import { getElectic } from '../service';
import TypeChart, { TypeChartDataType } from '@/components/Chart/TypeChart';
import { ElectricDataType } from '../typing';
import { chartOption } from './helper';
import { formatMessage } from '@/utils';

export type ElectricType = {
  className?: string;
  deviceData?: DeviceDataType;
};

const typeMap = [
  {
    value: chartTypeEnum.Month,
    label: formatMessage({ id: 'common.time.month', defaultMessage: '月' }),
  },
  {
    value: chartTypeEnum.Year,
    label: formatMessage({ id: 'common.time.year', defaultMessage: '年' }),
  },
];

const seriesMap = new Map([
  ['elec', formatMessage({ id: 'device.electricSupply', defaultMessage: '市电' })],
  ['pv', formatMessage({ id: 'device.pv', defaultMessage: '光伏' })],
  ['energyCharge', formatMessage({ id: 'dataManage.storageCharging', defaultMessage: '储能充电' })],
  ['energyPut', formatMessage({ id: 'dataManage.storageDischarge', defaultMessage: '储能放电' })],
  ['load', formatMessage({ id: 'device.load', defaultMessage: '负载' })],
]);

const Electric: React.FC<ElectricType> = (props) => {
  const { className, deviceData } = props;

  const [chartType, setChartType] = useState<chartTypeEnum>(chartTypeEnum.Month);
  const [date, setDate] = useState<Moment>(moment());
  const { data: electricData, run } = useRequest(getElectic, { manual: true });

  const onTypeSelect = useCallback((value) => {
    setChartType(value);
  }, []);

  const onChange = useCallback((value: Moment | null) => {
    setDate(value || moment());
  }, []);

  const chartData = useMemo<TypeChartDataType[]>(() => {
    const result: TypeChartDataType[] = [];
    seriesMap.forEach((name, field) => {
      result.push({
        name: name,
        data: electricData?.[field as any as keyof ElectricDataType]?.map?.((item) => ({
          label: item.eventTs,
          value: item.doubleVal,
        })),
      });
    });
    return result;
  }, [electricData]);

  useEffect(() => {
    if (deviceData?.deviceId) {
      run({
        deviceId: deviceData?.deviceId,
        type: chartType,
        date: date.format('YYYY-MM-DD'),
      });
    }
  }, [deviceData?.deviceId, chartType, date]);

  return (
    <>
      <div className={`card-wrap shadow p20 ${styles.chart} ${className}`}>
        <Detail.Label
          className="mb12"
          title={formatMessage({ id: 'dataManage.electricQuantity', defaultMessage: '电量' })}
          size="small"
          showLine={false}
        >
          <Select
            className="mr8"
            defaultValue={chartType}
            options={typeMap}
            onSelect={onTypeSelect}
          />
          <DatePicker
            picker={chartType == chartTypeEnum.Month ? 'month' : 'year'}
            defaultValue={date}
            format={chartType == chartTypeEnum.Month ? 'YYYY-MM' : 'YYYY'}
            onChange={onChange}
            allowClear={false}
          />
        </Detail.Label>
        <TypeChart
          style={{ height: 313 }}
          type={chartType}
          date={date}
          option={chartOption}
          data={chartData}
        />
      </div>
    </>
  );
};

export default Electric;
