/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-05 14:40:05
 * @LastEditTime: 2024-05-10 17:49:33
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\components\Trend\index.tsx
 */
import React, { useState, useEffect, useCallback, useMemo } from 'react';
import { useRequest } from 'umi';
import TypeChart, { TypeChartDataType } from '@/components/Chart/TypeChart';
import moment, { Moment } from 'moment';
import { formatMessage, getPlaceholder, getUnit, isEmpty } from '@/utils';
import { DeviceDataType } from '@/services/equipment';
import { detailItems, options } from './helper';
import { getPower } from './service';
import Detail from '@/components/Detail';
import ChartDate from '@/components/Chart/ChartDate';
import { chartTypeEnum } from '@/components/Chart/config';
import { merge } from 'lodash';

type PowerType = {
  className?: string;
  label?: string;
  deviceData?: DeviceDataType;
};

const Trend: React.FC<PowerType> = (props) => {
  const { label, className, deviceData } = props;

  const [date, setDate] = useState<Moment>();
  const [chartType, setChartType] = useState<chartTypeEnum>();
  const [chartData, setChartData] = useState<TypeChartDataType[]>();
  const {
    loading: loading,
    data: powerData,
    run,
  } = useRequest(getPower, {
    manual: true,
    pollingInterval: 60 * 60 * 1000,
  });

  const onChange = useCallback((value: Moment | null) => {
    setDate(value || moment());
  }, []);

  const onTypeChange = useCallback((value) => {
    setChartType(value);
  }, []);

  useEffect(() => {
    if (deviceData?.deviceId && date && !isEmpty(chartType)) {
      run({
        deviceId: deviceData?.deviceId,
        date: date.format('YYYY-MM-DD'),
        type: chartType,
      });
    }
  }, [deviceData?.deviceId, date, chartType]);

  useEffect(() => {
    const result: Required<TypeChartDataType>[] = [
      {
        name: formatMessage({ id: 'device.numberOfChargingOrders', defaultMessage: '充电订单数' }),
        data: [],
        unit: formatMessage({ id: 'siteMonitor.order', defaultMessage: '单' }),
      },
      {
        name: formatMessage({ id: 'device.chargeDuration', defaultMessage: '充电时长' }),
        data: [],
        unit: formatMessage({ id: 'device.hour', defaultMessage: '小时' }),
      },
      {
        name: formatMessage({ id: 'device.chargingCapacity', defaultMessage: '充电量' }),
        data: [],
        unit: 'kWh',
      },
      {
        name: formatMessage({ id: 'device.chargingFee', defaultMessage: '充电费用' }),
        data: [],
        unit: formatMessage({ id: 'common.rmb', defaultMessage: '元' }),
      },
    ];
    powerData?.list?.forEach?.((item) => {
      result[0].data.push({ label: item.time, value: item.chargeCount });
      result[1].data.push({ label: item.time, value: item.chargeDuration });
      result[2].data.push({ label: item.time, value: item.chargeElectricity });
      result[3].data.push({ label: item.time, value: item.chargeMoney });
    });
    setChartData(result);
  }, [powerData]);

  return (
    <>
      <div className={`card-wrap shadow p20 mb20 ${className}`}>
        <div className="flex mb16">
          <label className={`flex1`}>
            {label ||
              formatMessage({ id: 'device.chargingStatistics', defaultMessage: '充电统计' })}
          </label>
          <ChartDate onChange={onChange} onTypeChange={onTypeChange} loading={loading} />
        </div>
        <div>
          <Detail items={detailItems} data={powerData} column={2} unitInLabel />
        </div>
        <TypeChart step={60} type={chartType} date={date} option={options} data={chartData} />
      </div>
    </>
  );
};

export default Trend;
