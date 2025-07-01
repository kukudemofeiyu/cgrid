/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-05 14:40:05
 * @LastEditTime: 2024-03-08 11:43:46
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\components\MasterTrend\index.tsx
 */
import React, { useState, useEffect, useCallback } from 'react';
import { DatePicker, Spin } from 'antd';
import { useRequest } from 'umi';
import TypeChart, { TypeChartDataType } from '@/components/Chart/TypeChart';
import moment, { Moment } from 'moment';
import { formatMessage } from '@/utils';
import { DeviceDataType } from '@/services/equipment';
import { ResponseCommonData, ResponsePromise } from '@/utils/request';
import { options } from './helper';

type PowerDataType = {
  eventTs: string;
  doubleVal: number;
};

type PowerType = {
  deviceData?: DeviceDataType;
  request: (params: any) => ResponsePromise<ResponseCommonData<PowerDataType[]>, any>;
};

const MasterTrend: React.FC<PowerType> = (props) => {
  const { deviceData, request } = props;

  const [date, setDate] = useState<Moment>(moment());
  const [chartData, setChartData] = useState<TypeChartDataType[]>();
  const {
    loading: loading,
    data: powerData,
    run,
  } = useRequest(request, {
    manual: true,
    pollingInterval: 2 * 60 * 1000,
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
        name: formatMessage({ id: 'device.chargingPower1', defaultMessage: '充电功率' }),
        data: powerData?.map?.((item) => ({ label: item.eventTs, value: item.doubleVal })),
      },
      {
        name: formatMessage({ id: 'device.chargeCapacity', defaultMessage: '充电电量' }),
        data: powerData?.map?.((item) => ({ label: item.eventTs, value: item.doubleVal })),
      },
    ];
    setChartData(result);
  }, [powerData]);

  return (
    <>
      <div className="card-wrap shadow p20 mb20">
        <div className="flex mb16">
          <label className={`flex1`}>
            {formatMessage({ id: 'device.hostChargingTrends', defaultMessage: '主机充电趋势' })}
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

export default MasterTrend;
