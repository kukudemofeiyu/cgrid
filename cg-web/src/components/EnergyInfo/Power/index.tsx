/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-12 14:14:19
 * @LastEditTime: 2024-11-11 14:03:10
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\Power\index.tsx
 */
import React, { useState, useEffect, useCallback, useMemo } from 'react';
import { Skeleton, DatePicker, Spin } from 'antd';
import { useRequest } from 'umi';
import TypeChart, { TypeChartDataType } from '@/components/Chart/TypeChart';
import { getPower } from '../service';
import styles from '../index.less';
import moment, { Moment } from 'moment';
import { ComProps } from '../type';
import { formatMessage } from '@/utils';
import { DeviceProductTypeEnum } from '@/utils/dictionary';

enum colorEnum {
  Charge = 'rgba(0, 125, 255, 1)',
  DisCharge = 'rgba(255, 151, 74, 1)',
}
export enum EnergySourceEnum {
  SiteMonitor,
  DeviceManage,
}
const Power: React.FC<ComProps> = (props) => {
  const { deviceData, source } = props;
  const [date, setDate] = useState<Moment>(moment());
  const [chartData, setChartData] = useState<TypeChartDataType[]>();
  const {
    loading: powerLoading,
    data: powerData,
    run,
  } = useRequest(getPower, {
    manual: true,
    pollingInterval: 2 * 60 * 1000,
  });

  const chartOption = useMemo(() => {
    return {
      grid: {
        top: 30,
        bottom: 30,
        right: 60,
      },
      tooltip: {
        trigger: 'axis',
        formatter: (params: any) => {
          const { value, name } = (params || [{}])[0];
          const result = [name];
          if (typeof value[1] === 'number') {
            if (value[1] < 0) {
              result.push(
                value[1] +
                  `kW（<span style="color:${colorEnum.DisCharge}">${formatMessage({
                    id: 'siteMonitor.discharge',
                    defaultMessage: '放电',
                  })}</span>）`,
              );
            } else if (value[1] > 0) {
              result.push(
                value[1] +
                  `kW（<span style="color:${colorEnum.Charge}">${formatMessage({
                    id: 'siteMonitor.charge',
                    defaultMessage: '充电',
                  })}</span>）`,
              );
            } else {
              result.push(value[1] + 'kW');
            }
          } else {
            result.push('-');
          }
          return result.join('<br/>');
        },
      },
      dataZoom: [
        {
          type: 'inside',
        },
        {
          start: 0,
          end: 100,
          height: 15,
          bottom: 10,
        },
      ],
      visualMap: {
        show: false,
        pieces: [
          {
            gt: 0.00000000001,
            color: colorEnum.Charge,
          },
          {
            lte: 0,
            color: colorEnum.DisCharge,
          },
        ],
      },
      yAxis: {
        name: formatMessage({ id: 'common.unit', defaultMessage: '单位' }) + '（kW）',
      },
      series: [
        {
          type: 'line',
          markLine: {
            data: [
              {
                yAxis: 0,
                label: {
                  show: true,
                  color: colorEnum.Charge,
                  formatter: `${formatMessage({
                    id: 'siteMonitor.charge',
                    defaultMessage: '充电',
                  })}\n\n\n\n\n{dis|${formatMessage({
                    id: 'siteMonitor.discharge',
                    defaultMessage: '放电',
                  })}}`,
                  rich: {
                    dis: {
                      color: colorEnum.DisCharge,
                    },
                  },
                },
                lineStyle: {
                  color: 'transparent',
                },
              },
            ],
          },
        },
      ],
    };
  }, [deviceData]);

  const onChange = useCallback((value: Moment | null) => {
    setDate(value || moment());
  }, []);

  useEffect(() => {
    if (
      deviceData?.deviceId &&
      (!deviceData?.productTypeId ||
        [
          DeviceProductTypeEnum.Energy,
          DeviceProductTypeEnum.PvEnergy,
          DeviceProductTypeEnum.SmallEnergy,
          DeviceProductTypeEnum.WindPvFirewoodEnergy,
          DeviceProductTypeEnum.BEnergy,
        ].includes(deviceData?.productTypeId))
    ) {
      run({
        deviceId: deviceData?.deviceId,
        date: date.format('YYYY-MM-DD'),
        visitType: source == EnergySourceEnum.SiteMonitor ? 0 : 1,
      });
    }
  }, [deviceData?.deviceId, date]);

  //数据
  useEffect(() => {
    const result: TypeChartDataType = {
      name: '',
      data: powerData?.map?.((item) => ({ label: item.eventTs, value: item.doubleVal })),
    };
    setChartData([result]);
  }, [powerData]);

  return (
    <>
      <div className="card-wrap shadow p20 mb20">
        <div className="flex mb16">
          <label className={`flex1 ${styles.label}`}>
            {source == EnergySourceEnum.SiteMonitor
              ? formatMessage({
                  id: 'siteMonitor.storageUnitPower',
                  defaultMessage: '储能单元功率',
                })
              : formatMessage({ id: 'siteMonitor.storagePower', defaultMessage: '储能功率' })}
          </label>
          <DatePicker
            defaultValue={date}
            format="YYYY-MM-DD"
            onChange={onChange}
            allowClear={false}
          />
          {powerLoading && <Spin className="ml12" />}
        </div>
        <TypeChart date={date} option={chartOption} step={4} data={chartData} min={-10} />
      </div>
    </>
  );
};

export default Power;
