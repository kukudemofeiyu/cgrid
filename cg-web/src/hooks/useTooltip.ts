/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-28 14:19:47
 * @LastEditTime: 2023-06-29 15:45:28
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\hooks\tooltip.ts
 */
import { Ref, useRef, useEffect, useState, useMemo, useCallback } from 'react';
import { ChartRefConfig } from '@ant-design/charts';
import { useInterval } from 'ahooks';

export enum ChartTypeEnum {
  Line,
  Pie,
}

type UseToolTip = {
  (props?: { type?: ChartTypeEnum }): [
    Ref<ChartRefConfig | undefined>,
    { run: () => void; clear: () => void },
  ];
};

const useToolTip: UseToolTip = (props = {}) => {
  const { type = ChartTypeEnum.Line } = props;

  const chartRef = useRef<any>();
  const tipRef = useRef<number>(0);
  const clearRef = useRef<NodeJS.Timer>();

  const clear = () => clearInterval(clearRef.current);

  const run = useCallback(() => {
    clear();
    clearRef.current = setInterval(() => {
      const chart =
        type == ChartTypeEnum.Pie
          ? chartRef?.current?.getChart?.()?.chart
          : chartRef?.current?.chartHelper?.chart;
      if (chart) {
        const chartData = chart?.getData?.();
        if (chartData && chartData.length) {
          if (type == ChartTypeEnum.Pie) {
            const { elements = [] } = chart?.geometries[0] || {};
            const element = elements[tipRef.current];
            let { x, y } = element?.getModel?.() || {};
            //chartRef?.current?.getChart?.().setState("selected", (item) => item.type === chartData[tipNum].type);
            x = Array.isArray(x) ? x : [x];
            y = Array.isArray(y) ? x : [y];
            chart.showTooltip({ x: (x[0] + x[0]) / 2, y: (y[0] + y[0]) / 2 });
          } else {
            const { x, y } = chart?.getXY?.(chartData[tipRef.current]) || {};
            chart?.showTooltip?.({ x, y });
          }
          if (tipRef.current > chartData.length) {
            tipRef.current = 0;
          } else {
            tipRef.current++;
          }
        }
      }
    }, 3000);
    return clear;
  }, [type]);

  useEffect(() => {
    const stop = run();
    tipRef.current = 0;
    return () => {
      stop();
    };
  }, []);

  return [
    chartRef,
    {
      run,
      clear,
    },
  ];
};

export default useToolTip;
