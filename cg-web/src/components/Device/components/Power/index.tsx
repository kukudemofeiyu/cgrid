/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-12 14:14:19
 * @LastEditTime: 2024-04-19 16:28:37
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\components\Power\index.tsx
 */
import React, { useState, useEffect, useCallback } from 'react';
import { DatePicker, Spin } from 'antd';
import { useRequest } from 'umi';
import TypeChart, { TypeChartDataType } from '@/components/Chart/TypeChart';
import moment, { Moment } from 'moment';
import { formatMessage } from '@/utils';
import { DeviceDataType } from '@/services/equipment';
import { options } from './helper';
import { getPower } from './service';

type PowerType = {
  deviceData?: DeviceDataType;
};

const Power: React.FC<PowerType> = (props) => {
  const { deviceData } = props;

  const [date, setDate] = useState<Moment>(moment());
  const [chartData, setChartData] = useState<TypeChartDataType[]>();
  const {
    loading: loading,
    data: powerData,
    run,
  } = useRequest(getPower, {
    manual: true,
    pollingInterval: 4 * 60 * 1000,
  });

  const onChange = useCallback((value: Moment | null) => {
    setDate(value || moment());
  }, []);

  useEffect(() => {
    if (deviceData?.deviceId) {
      run({
        deviceId: deviceData?.deviceId,
        date: date.format('YYYY-MM-DD'),
      });
    }
  }, [deviceData?.deviceId, date]);

  useEffect(() => {
    const result: TypeChartDataType[] = [
      {
        name: formatMessage({ id: 'device.chargingPower', defaultMessage: '充电功率' }),
        data: [],
      },
      {
        name: formatMessage({ id: 'device.powerUtilization', defaultMessage: '功率利用率' }),
        data: [],
      },
    ];
    powerData?.list?.forEach?.((item) => {
      result[0].data?.push({ label: item.time, value: item.power });
      result[1].data?.push({ label: item.time, value: item.powerUseRate });
    });
    setChartData(result);
  }, [powerData]);

  return (
    <>
      <div className="card-wrap shadow p20 mb20">
        <div className="flex mb16">
          <label className={`flex1`}>
            {formatMessage({ id: 'device.realTimePower', defaultMessage: '实时功率' })}
          </label>
          <DatePicker
            defaultValue={date}
            format="YYYY-MM-DD"
            onChange={onChange}
            allowClear={false}
          />
          {loading && <Spin className="ml12" />}
        </div>
        <TypeChart date={date} option={options} data={chartData} />
      </div>
    </>
  );
};

export default Power;
