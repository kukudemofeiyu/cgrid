/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-12 17:39:51
 * @LastEditTime: 2024-06-07 10:33:13
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Chart\index.tsx
 */
import React, { useMemo, useEffect, useState } from 'react';
import { useBoolean } from 'ahooks';
import EChartsReact from 'echarts-for-react';
import { defaultOption, defaultRtlOption, defaultDataZoomOption } from './config';
import type { ChartProps } from './config';
import { merge } from 'lodash';
import { useDir } from '@/hooks';

const Chart: React.FC<ChartProps> = (props) => {
  const {
    option,
    height,
    min,
    max,
    chartRef,
    calculateMax = true,
    isZoom = false,
    ...restProps
  } = props;

  const [show, { setTrue, setFalse }] = useBoolean(false);
  const [key, setKey] = useState('1');
  const { isRtl } = useDir();

  const chartOptions = useMemo(() => {
    if (calculateMax) {
      const result: any = { yAxis: {} };
      let valueMax = 0,
        valueMin = 0;
      option?.dataset?.source?.map?.((item: any) => {
        item?.forEach?.((value: any) => {
          if (typeof value === 'number') {
            valueMax = Math.max(valueMax, value);
            valueMin = Math.min(valueMin, value);
          }
        });
      });

      if (valueMax > (max || 10)) {
        result.yAxis.max = undefined;
      } else {
        if (!valueMax) {
          //没数据的时候展示1-10默认轴线
          result.yAxis.max = max || 10;
        } else {
          //有数据的时候且小于10或者max时，不显示最大轴线，为了让轴线均匀分布
          result.yAxis.max = undefined;
        }
      }
      if (valueMin < (min || 0)) {
        result.yAxis.min = undefined;
      } else {
        result.yAxis.min = min || 0;
      }
      option?.yAxis?.forEach?.((item: any) => {
        item.min = result.yAxis.min;
        item.max = result.yAxis.max;
      });
      merge(
        result,
        defaultOption,
        option,
        isRtl ? defaultRtlOption : {},
        isZoom ? defaultDataZoomOption : {},
      );
      return result;
    } else {
      return merge(
        {},
        defaultOption,
        option,
        isRtl ? defaultRtlOption : {},
        isZoom ? defaultDataZoomOption : {},
      );
    }
  }, [calculateMax, isRtl, isZoom, max, min, option]);

  useEffect(() => {
    setTimeout(() => {
      setKey(Math.random().toFixed(9));
      setTrue();
    }, 10);
    return () => {
      setFalse();
    };
  }, []);

  return (
    <>
      {show && (
        <>
          <EChartsReact
            key={key}
            ref={chartRef as any}
            option={chartOptions}
            style={{ height: height || 254, direction: isRtl ? 'rtl' : 'ltr' }}
            {...restProps}
          />
        </>
      )}
    </>
  );
};

export default Chart;
