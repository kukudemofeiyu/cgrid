/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-10-17 15:53:59
 * @LastEditTime: 2024-10-08 11:46:29
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\CollectionModal\CollectionChart.tsx
 */

import React, { memo, useEffect, useMemo, useRef, useState } from 'react';
import { getCollectionData } from '@/services/data';
import { useRequest } from 'umi';
import TypeChart, { TypeChartDataType } from '../Chart/TypeChart';
import { chartTypeEnum } from '@/components/Chart/config';
import moment from 'moment';
import EChartsReact from 'echarts-for-react';
import { DeviceModelTypeEnum } from '@/utils';
import { parseToObj } from '@/utils';
import { CollectionChartType } from './helper';
import { getAllMinute } from '@/components/Chart/TypeChart/config';

const CollectionChart: React.FC<CollectionChartType> = (props) => {
  const {
    deviceId,
    collection,
    model,
    title,
    height = 400,
    onLoadingChange,
    containClassName,
    searchParams,
  } = props;

  const [chartData, setChartData] = useState<TypeChartDataType[]>();
  const { run } = useRequest(getCollectionData, {
    manual: true,
  });

  const modelData = useMemo(() => {
    const enumObj: any = parseToObj(model?.specs);
    let enumKeys: string[] = [];
    if (model?.type === DeviceModelTypeEnum.Enum) {
      enumKeys = ['wahaha'].concat(Object.keys(enumObj));
    }
    return {
      type: model?.type,
      keys: enumKeys,
      data: enumObj,
      unit: enumObj?.unit,
    };
  }, [model]);

  const allLabel = useMemo(() => {
    if (searchParams?.aggregationPeriod) {
      if (searchParams?.startTime && searchParams?.endTime) {
        const allMinuteLabel: string[] = [];
        const allMinute = getAllMinute(Number(searchParams?.aggregationPeriod));
        const startTime = moment(moment(searchParams.startTime).format('YYYY-MM-DD 00:00:00'));
        while (
          startTime.isSameOrBefore(
            moment(moment(searchParams.endTime).format('YYYY-MM-DD 00:00:00')),
          )
        ) {
          allMinuteLabel.push(
            ...allMinute.map((item) => startTime.format(`YYYY-MM-DD ${item}:00`)),
          );
          startTime.add(1, 'day');
        }
        return allMinuteLabel;
      } else {
        return [];
      }
    } else {
      return chartData?.[0]?.data?.map?.((item) => item.label);
    }
  }, [chartData, searchParams?.aggregationPeriod]);

  const chartOption = useMemo(() => {
    return {
      grid: {
        top: 50,
        bottom: 50,
        right: 35,
      },
      legend: {
        show: false,
      },
      tooltip: {
        trigger: 'axis',
        formatter: (params: any) => {
          const { value, name } = (params || [{}])[0];
          const result = [name];
          result.push(
            (title ?? '') +
              (modelData.unit ? `（${modelData.unit}）` : '') +
              '：' +
              ((modelData.type === DeviceModelTypeEnum.Enum
                ? modelData.data[modelData.keys[value?.[1]]] ?? '-'
                : value?.[1]) ?? ''),
          );
          return result.join('<br/>');
        },
      },
      xAxis: {
        axisLabel: {
          formatter: (value: string) => {
            return value.replace(' ', '\n');
          },
        },
      },
      yAxis:
        modelData.type === DeviceModelTypeEnum.Enum
          ? {
              interval: 1,
              axisLabel: {
                formatter: (value: number) => {
                  return modelData.data?.[modelData.keys[value]] || '';
                },
              },
            }
          : {},
      dataZoom: [
        {
          type: 'inside',
          realtime: false,
        },
        {
          start: 0,
          end: 100,
          height: 15,
          realtime: false,
          labelFormatter: '',
        },
      ],
      series: [
        {
          type: 'line',
          ...(modelData.type == 'enum'
            ? {}
            : {
                markPoint: {
                  emphasis: {
                    label: {
                      formatter: (params: any) => {
                        const maxData = chartData?.[params?.seriesIndex]?.data?.find(
                          (item) => item.value == params?.value,
                        );
                        return `${maxData?.label?.replace(' ', '\n')}\n${params?.value}`;
                      },
                    },
                  },
                  data: [
                    { type: 'max', name: 'Max' },
                    { type: 'min', name: 'Min' },
                  ],
                },
              }),
        },
      ],
    };
  }, [title, modelData, chartData]);

  useEffect(() => {
    // let timer: NodeJS.Timer;
    if (deviceId && collection && searchParams?.startTime && searchParams?.endTime) {
      const request = () => {
        onLoadingChange?.(true);
        run({
          ...searchParams,
          deviceId: deviceId,
          key: collection,
          // msgType: 1,
        })
          .then((data) => {
            const result: TypeChartDataType = {
              name: title || '',
              data: [],
            };
            data?.forEach?.((collectionValue) => {
              if (modelData.type == DeviceModelTypeEnum.Enum) {
                const index = modelData.keys.findIndex(
                  (item) => item == (collectionValue?.value as any),
                );
                result?.data?.push?.({
                  label: collectionValue?.eventTs || '',
                  value: index,
                });
              } else {
                result?.data?.push?.({
                  label: collectionValue?.eventTs || '',
                  value: collectionValue?.value,
                });
              }
            });
            setChartData([result]);
            return true;
          })
          .finally(() => {
            onLoadingChange?.(false);
          });
      };
      request();
      // timer = setInterval(() => { // 占带宽暂时去掉定时请求
      //   request();
      // }, (searchParams?.aggregationPeriod || 2) * 60 * 1000);
    }
    return () => {
      // clearInterval(timer);
      onLoadingChange?.(false);
    };
  }, [deviceId, collection, searchParams]);

  useEffect(() => {
    const { date, aggregationPeriod } = searchParams || {};
    if (date && date[0] && date[1]) {
      if (
        moment(date[0]).format('YYYY-MM-DD') == moment(date[1]).format('YYYY-MM-DD') &&
        aggregationPeriod
      ) {
      } else {
      }
    }
  }, [searchParams]);

  return (
    <>
      <TypeChart
        type={chartTypeEnum.Label}
        option={chartOption}
        style={{ height }}
        data={chartData}
        allLabel={allLabel}
        max={modelData.type == DeviceModelTypeEnum.Enum ? modelData.keys.length - 1 : 0}
        containClassName={containClassName}
      />
    </>
  );
};

export default CollectionChart;
