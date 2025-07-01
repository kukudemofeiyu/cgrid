/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-19 17:21:49
 * @LastEditTime: 2025-02-05 14:37:29
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/Chart/TypeChart/index.tsx
 */
import React, { useEffect, useMemo, useState } from 'react';
import { defaultLineOption, chartTypeEnum } from '../config';
import type { ChartProps } from '../config';
import Chart from '..';
import { merge } from 'lodash';
import moment from 'moment';
import type { Moment } from 'moment';
import { typeMap } from './config';
import { isHourRange } from '@/utils/reg';
import { useDir } from '@/hooks';

export type TypeChartDataType = {
  name?: string; //系列名称
  unit?: string;
  data?: {
    //系列对应的数据
    label: string;
    value?: number;
  }[];
  [key: string]: any;
};

export type TypeChartProps = ChartProps & {
  type?: chartTypeEnum;
  date?: Moment;
  data?: TypeChartDataType[];
  step?: number;
  allLabel?: string[];
  hourRange?: boolean;
};

const getLabelByData = (data?: TypeChartDataType[]): string[] => {
  const set: Set<string> = new Set();
  data?.forEach?.((dataItem) => {
    dataItem?.data?.forEach?.((item) => {
      set.add(item.label);
    });
  });
  return Array.from(set);
};

const TypeChart: React.FC<TypeChartProps> = (props) => {
  const {
    option,
    type = chartTypeEnum.Day,
    date,
    data,
    step,
    allLabel,
    hourRange = true,
    ...restProps
  } = props;

  const [xLabels, setXLables] = useState<string[]>();
  const { isRtl } = useDir();
  const chartOptions = useMemo(() => {
    const categoryMap = new Map([['product', '']]);
    const valueMap = new Map<string, (string | number)[]>(xLabels?.map?.((item) => [item, [item]])); // ['x轴','100','200']

    data?.forEach?.((seriesItem: TypeChartDataType) => {
      if (seriesItem.name) {
        const nameKey = seriesItem?.unit
          ? seriesItem.name + `(${seriesItem.unit})`
          : seriesItem.name;
        categoryMap.set(nameKey, seriesItem?.unit || '');
      }

      const seriesDataMap = new Map<string, number | string>();
      seriesItem?.data?.forEach?.((dataItem) => {
        if (type === chartTypeEnum.Label) {
          seriesDataMap.set(dataItem.label, dataItem?.value ?? '');
        } else if (type == chartTypeEnum.Day) {
          const format = typeMap.get(type)?.format;
          if (step == 60 && hourRange) {
            seriesDataMap.set(
              `${moment(dataItem.label).format(format)}-${moment(dataItem.label)
                .add(1, 'h')
                .format(format)}`,
              dataItem?.value ?? '',
            );
          } else {
            seriesDataMap.set(moment(dataItem.label).format(format), dataItem?.value ?? '');
          }
        } else {
          const format = typeMap.get(type)?.format;
          seriesDataMap.set(moment(dataItem.label).format(format), dataItem?.value ?? '');
        }
      });
      xLabels?.forEach?.((key) => {
        const valueMapData = valueMap.get(key) || [];
        valueMap.set(key, [...valueMapData, seriesDataMap.get(key) ?? '']);
      });
    });

    const source = Array.from(valueMap.values());
    source.splice(0, 0, Array.from(categoryMap.keys()));

    const result = {
      legend: {
        formatter: (name: string) => {
          const unit = categoryMap.get(name);
          return unit ? name.replace(`(${unit})`, '') : name;
        },
      },
      xAxis: {
        inverse: isRtl,
        axisLabel: {
          formatter: (value: string) => {
            if (hourRange && isHourRange(value) && step == 60) {
              return value?.split?.('-')?.[0];
            } else {
              return value;
            }
          },
        },
      },
      yAxis: Array.isArray(option?.yAxis)
        ? option?.yAxis?.map?.((item: any, index: number) => {
          return {
            ...item,
            position: index ? (isRtl ? 'left' : 'right') : (isRtl ? 'right' : 'left'),
            nameTextStyle: {
              align: index ? (isRtl ? 'left' : 'right') : (isRtl ? 'right' : 'left'),
            },
          };
        })
        : {
          type: 'value',
          position: isRtl ? 'right' : 'left',
          nameTextStyle: {
            align: isRtl ? 'right' : 'left',
          },
        },
      dataset: {
        source,
      },
    };
    merge(result, defaultLineOption, option);
    return result;
  }, [xLabels, data, isRtl, option, type, step, hourRange]);

  useEffect(() => {
    const labels =
      typeMap.get(type)?.fun?.((type == chartTypeEnum.Day ? step : date) as any, hourRange) ||
      allLabel ||
      getLabelByData(data);
    setXLables(labels);
  }, [type, step, date, allLabel, data, hourRange]);

  return (
    <>
      <Chart option={chartOptions} {...restProps} />
    </>
  );
};
export default TypeChart;
