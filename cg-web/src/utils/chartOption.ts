import type { AreaConfig } from '@ant-design/charts';
import { merge } from 'lodash';

export const getAreaOption = (options?: AreaConfig): AreaConfig => {
  const yField = options?.yField || 'value';

  return merge(
    {
      tooltip: {
        formatter: (data: any) => {
          return { value: data.value, name: yField };
        },
      },
      smooth: true,
      xField: 'time',
      yField: yField,
      line: {
        color: '#35FFF4',
      },
      xAxis: {
        tickLine: null,
        line: {
          style: {
            stroke: '#6C8097',
            opacity: 1,
          },
        },
        label: {
          style: {
            fill: '#6C8097',
            opacity: 1,
          },
        },
      },
      yAxis: {
        grid: {
          line: {
            style: {
              lineDash: [4, 4],
              stroke: '#6C8097',
              opacity: 1,
            },
          },
        },
        label: {
          style: {
            fill: '#6C8097',
            opacity: 1,
          },
        },
      },
      areaStyle: () => {
        return {
          fill: 'l(270) 0:#35FFF400 1:#35FFF466',
        };
      },
    },
    options,
  );
};
