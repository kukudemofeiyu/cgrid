/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-12 14:14:19
 * @LastEditTime: 2024-06-12 17:51:16
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\Electric\index.tsx
 */
import React, { useState, useEffect, useCallback, useMemo } from 'react';
import { DatePicker, Select, Spin } from 'antd';
import { chartTypeEnum } from '@/components/Chart/config';
import TypeChart from '@/components/Chart/TypeChart';
import type { TypeChartDataType } from '@/components/Chart/TypeChart';
import { getElectic, getUnitElectic } from '../service';
import styles from '../index.less';
import moment from 'moment';
import type { Moment } from 'moment';
import type { ComProps } from '../type';
import { formatMessage, isEmpty } from '@/utils';
import { merge } from 'lodash';
import { useBoolean } from 'ahooks';
import Detail from '@/components/Detail';
import { chartOption, detailItems, typeMap } from './helper';
import { DeviceProductTypeEnum } from '@/utils/dictionary';

export enum EnergySourceEnum {
  SiteMonitor,
  DeviceManage,
}

const Electric: React.FC<ComProps> = (props) => {
  const { deviceData, source } = props;

  const [chartType, setChartType] = useState<chartTypeEnum>(chartTypeEnum.Day);
  const [date, setDate] = useState<Moment>(moment());
  const [chartData, setChartData] = useState<TypeChartDataType[]>();
  const [loading, { setTrue, setFalse }] = useBoolean(false);
  const [statisInfo, setStatisInfo] = useState<Record<string, any>>({});
  const [allLabel, setAllLabel] = useState<string[]>([]);

  const onTypeSelect = useCallback((value) => {
    setChartType(value);
  }, []);

  const onChange = useCallback((value: Moment | null) => {
    setDate(value || moment());
  }, []);

  const options = useMemo(() => {
    const result = merge({}, chartOption);
    if (source === EnergySourceEnum.SiteMonitor) {
      result.series.push({
        type: 'bar',
        color: '#FF7B7B',
      });
    }
    return result;
  }, [source]);

  useEffect(() => {
    setChartData([]);
    setStatisInfo({});
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
      const totalNum = chartTypeEnum.Month == chartType ? 7 : 4;
      let dates: { start: Moment; end: Moment }[] = [];
      if (chartType == chartTypeEnum.Month) {
        const nowWeek = 4 - (moment(date).startOf('M').day() || 7);
        dates = [
          {
            start: moment(date).startOf('M'),
            end: moment(date)
              .startOf('M')
              .add(nowWeek > 0 ? nowWeek - 1 : 7 + nowWeek - 1, 'd'),
          },
        ];

        while (
          dates[dates.length - 1].end.isBefore(moment(date).endOf('M'), 'day') &&
          dates[dates.length - 1].end.isBefore(moment(), 'day')
        ) {
          dates.push({
            start: moment(dates[dates.length - 1].end).add(1, 'd'),
            end: moment(dates[dates.length - 1].end).add(7, 'd'),
          });
          if (dates[dates.length - 1].end.isAfter(moment(date).endOf('M'), 'day')) {
            dates[dates.length - 1].end = moment(date).endOf('M');
          }
          if (dates[dates.length - 1].end.isAfter(moment(), 'day')) {
            dates[dates.length - 1].end = moment().endOf('d');
          }
        }
      } else if (chartType == chartTypeEnum.Year) {
        dates = [
          {
            start: moment(date).startOf('y'),
            end: moment(date).startOf('y').add(2, 'M').endOf('M'),
          },
        ];
        while (dates.length < totalNum && dates[dates.length - 1].end.isBefore(moment(), 'month')) {
          dates.push({
            start: moment(dates[dates.length - 1].end)
              .add(1, 'M')
              .startOf('M'),
            end: moment(dates[dates.length - 1].end)
              .add(3, 'M')
              .endOf('M'),
          });
        }
      } else {
        dates = [
          {
            start: date,
            end: date,
          },
        ];
      }

      let requestNum = 0;
      const result: TypeChartDataType[] = [
        {
          name: formatMessage({ id: 'siteMonitor.chargingCapacity', defaultMessage: '充电量' }),
          data: [],
          unit: 'kWh',
        },
        {
          name: formatMessage({ id: 'siteMonitor.disChargingCapacity', defaultMessage: '放电量' }),
          data: [],
          unit: 'kWh',
        },
      ];
      if (source == EnergySourceEnum.SiteMonitor) {
        result.push({
          name: formatMessage({ id: 'siteMonitor.1009', defaultMessage: '收益' }),
          data: [],
          unit: formatMessage({ id: 'common.rmb', defaultMessage: '元' }),
        });
      }
      const totalResult: Record<string, any> = {};
      dates.reverse();

      const request = () => {
        if (requestNum < dates.length) {
          const requestData = source == EnergySourceEnum.SiteMonitor ? getUnitElectic : getElectic;
          requestData({
            deviceId: deviceData?.deviceId,
            type: chartType,
            startDate: dates[requestNum].start?.format?.('YYYY-MM-DD'),
            endDate: dates[requestNum].end?.format?.('YYYY-MM-DD'),
            visitType: source == EnergySourceEnum.SiteMonitor ? 0 : 1,
          }).then(({ data }) => {
            if (requestNum < dates.length) {
              result[0].data?.push?.(
                ...(data?.charge?.map?.((item) => ({
                  label: item.time || item.eventTs,
                  value: item.value || item.doubleVal,
                })) || []),
              );
              result[1].data?.push?.(
                ...(data?.discharge?.map?.((item) => ({
                  label: item.time || item.eventTs,
                  value: item.value || item.doubleVal,
                })) || []),
              );
              if (source == EnergySourceEnum.SiteMonitor) {
                result[2].data?.push?.(
                  ...(data?.income?.map?.((item) => ({
                    label: item.time || '',
                    value: item.value,
                  })) || []),
                );
              }
              if (!isEmpty(data?.totalCharge)) {
                totalResult.totalCharge = Number(
                  ((totalResult.totalCharge ?? 0) + data?.totalCharge).toFixed(2),
                );
              }
              if (!isEmpty(data?.totalDischarge)) {
                totalResult.totalDischarge = Number(
                  ((totalResult.totalDischarge ?? 0) + data?.totalDischarge).toFixed(2),
                );
              }
              if (!isEmpty(data?.totalIncome)) {
                totalResult.totalIncome = Number(
                  ((totalResult.totalIncome ?? 0) + data?.totalIncome).toFixed(2),
                );
              }
              setChartData(merge([], result));
              setStatisInfo(merge({}, totalResult));
              if (chartType == chartTypeEnum.Label) {
                setAllLabel(result[0].data?.map?.((item) => item.label) || []);
              }
              requestNum++;
              request();
            }
          });
        } else {
          setFalse();
        }
      };

      setTrue();
      request();

      return () => {
        requestNum = 100;
      };
    }
  }, [deviceData?.deviceId, chartType, date]);

  return (
    <>
      <div className="card-wrap shadow p20">
        <div className="flex mb16">
          <label className={`flex1 ${styles.label}`}>
            {source == EnergySourceEnum.SiteMonitor
              ? formatMessage({
                  id: 'siteMonitor.chargeTrendUnit',
                  defaultMessage: '储能单元充放电趋势',
                })
              : formatMessage({
                  id: 'siteMonitor.chargeTrend',
                  defaultMessage: '储能充放电趋势',
                })}
          </label>
          <Select
            className="mr8"
            defaultValue={chartType}
            options={typeMap}
            onSelect={onTypeSelect}
          />
          {chartType != chartTypeEnum.Label && (
            <DatePicker
              picker={typeMap.find((item) => chartType == item.value)?.dateType}
              defaultValue={date}
              format={typeMap.find((item) => chartType == item.value)?.format}
              onChange={onChange}
              allowClear={false}
            />
          )}
          {loading && <Spin className="ml12" />}
        </div>
        <Detail
          className="detail-center"
          items={detailItems}
          data={{ ...statisInfo, source }}
          column={source == EnergySourceEnum.SiteMonitor ? 3 : 2}
          unitInLabel={true}
        />
        <TypeChart
          type={chartType}
          date={date}
          option={options}
          data={chartData}
          step={60}
          allLabel={allLabel}
        />
      </div>
    </>
  );
};

export default Electric;
