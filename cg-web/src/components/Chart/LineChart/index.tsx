/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-12 14:51:23
 * @LastEditTime: 2023-07-20 19:21:21
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Chart\LineChart\index.tsx
 */
import React, { useState, useEffect, useMemo } from 'react';
import { Axis, Chart, LineAdvance, Legend, Annotation, Tooltip, Interval } from 'bizcharts';
import { TooltipCfg } from 'bizcharts/node_modules/@antv/g2/lib/interface';
import { ILegend } from 'bizcharts/lib/components/Legend/index';
import { useToolTip } from '@/hooks';
import moment, { Moment } from 'moment';
import { chartTypeEnum } from '../config';
import { merge } from 'lodash';

export type ChartDataType = {
  time?: string;
  value?: number;
  [key: string]: any;
};

export type LineChartDataType = {
  [key: string]: ChartDataType[];
};

export type LineChartProps = {
  type?: chartTypeEnum;
  date: Moment;
  legendMap: Map<string, string>;
  data?: LineChartDataType;
  labelKey?: string;
  valueKey?: string;
  valueTitle?: string;
  className?: string;
  height?: number;
  step?: number;
  allLabel?: string[];
  colors?: string[];
  showLine?: boolean;
  toolTipProps?: TooltipCfg;
  legendProps?: ILegend;
  labelFormatter?: (value: any) => string;
};

const getAllMinute = (step = 2) => {
  return Array.from({ length: (24 * 60) / step }).map((_, index) => {
    return moment()
      .startOf('day')
      .add(index * step, 'minute')
      .format('HH:mm');
  });
};

const getAllDay = (date: Moment) => {
  return Array.from({ length: moment(date).daysInMonth() }).map((_, index) => {
    return index + 1 + '';
  });
};

const getAllMonth = () => {
  return Array.from({ length: 12 }).map((_, index) => {
    return index + 1 + '';
  });
};

export const typeMap = new Map([
  [chartTypeEnum.Day, { format: 'HH:mm', fun: getAllMinute }],
  [chartTypeEnum.Month, { format: 'D', fun: getAllDay }],
  [chartTypeEnum.Year, { format: 'M', fun: getAllMonth }],
]);

const defaultColors = ['#007DFF', '#FF974A', '#159AFF', '#11DA81', '#00C9EC'];

const LineChart: React.FC<LineChartProps> = (props) => {
  const {
    type = chartTypeEnum.Day,
    date,
    step = 2,
    className,
    children,
    legendMap,
    height = 254,
    data,
    labelKey = 'time',
    valueKey = 'value',
    valueTitle = '单位（KW）',
    colors = defaultColors,
    allLabel,
    showLine = true,
    toolTipProps,
    legendProps,
    labelFormatter,
  } = props;
  const [chartData, setChartData] = useState<ChartDataType[]>();
  const [ticks, setTicks] = useState<string[]>();

  const [chartRef] = useToolTip();

  const valueScale = useMemo(() => {
    try {
      const max = Math.max(...(chartData?.map?.((item) => item.value || 0) || [20]));
      let tickInterval = Math.ceil(max / 5);
      if (tickInterval > 5) {
        tickInterval = Math.ceil(tickInterval / 5) * 5;
      }
      return {
        max: tickInterval * 5,
        tickInterval: tickInterval,
      };
    } catch (e) {
      return {
        max: 25,
        tickInterval: 5,
      };
    }
  }, [chartData]);

  const mergeToolTipProps = useMemo(() => {
    const defaultOptions: TooltipCfg = {
      domStyles: {
        'g2-tooltip': {
          backgroundColor: 'rgba(9,12,21,0.8)',
          boxShadow: 'none',
          color: 'white',
          opacity: 1,
        },
      },
    };
    return merge(defaultOptions, toolTipProps || {});
  }, [toolTipProps]);

  useEffect(() => {
    const labels =
      typeMap.get(type)?.fun?.((type == chartTypeEnum.Day ? step : date) as any) || allLabel;

    const result: ChartDataType[] = [];
    legendMap.forEach((itemField, key) => {
      const valueMap = new Map(
        data?.[key]?.map?.((item) => {
          if (type == chartTypeEnum.Label) {
            return [item?.[labelKey], item?.[valueKey]];
          } else {
            return [moment(item?.[labelKey]).format(typeMap.get(type)?.format), item?.[valueKey]];
          }
        }) || [],
      );
      result.push(
        ...(labels?.map((item) => {
          return {
            time: item,
            value: valueMap.get(item),
            field: itemField,
          };
        }) || []),
      );
    });
    setChartData(result);
  }, [data, date, step, type, labelKey, valueKey]);

  return (
    <>
      <div className={className} style={{ height }}>
        <Chart
          ref={chartRef}
          height={height}
          scale={{
            time: {
              formatter: labelFormatter,
              //ticks: ticks,
            },
            value: {
              max: valueScale.max,
              tickInterval: valueScale.tickInterval,
            },
          }}
          data={chartData}
          autoFit
        >
          <Tooltip {...mergeToolTipProps} />
          <Legend
            position="top"
            marker={{
              symbol: showLine ? 'circle' : 'square',
            }}
            itemSpacing={24}
            {...(legendProps || {})}
          />
          {showLine ? (
            <LineAdvance shape="smooth" color={['field', colors]} position="time*value" />
          ) : (
            <Interval
              size={10}
              adjust={[
                {
                  type: 'dodge',
                  marginRatio: 0,
                },
              ]}
              color={['field', colors]}
              position="time*value"
            />
          )}
          <Axis
            name="value"
            title={{
              text: valueTitle,
              position: 'end',
              autoRotate: false,
              offset: 0,
              style: {
                rotate: 90,
                y: 15,
                x: 40,
              },
            }}
            grid={{
              line: {
                type: 'line',
              },
            }}
          />
          <Axis name="time" tickLine={null} />
          {children}
        </Chart>
      </div>
    </>
  );
};

export default LineChart;
