/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-08-01 13:56:07
 * @LastEditTime: 2025-02-05 14:46:30
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/Chart/config.ts
 */
import type React from 'react';
import type EChartsReact from 'echarts-for-react';
import type { EChartsReactProps } from 'echarts-for-react';
import { formatMessage } from '@/utils';
import { isEmpty } from '@/utils';

export enum chartTypeEnum {
  Day,
  Month,
  Year,
  Label,
}

export type ChartProps = Omit<EChartsReactProps, 'option'> & {
  option?: any;
  min?: number;
  max?: number;
  chartRef?: React.MutableRefObject<EChartsReact | undefined>;
  calculateMax?: boolean;
  loading?: boolean;
  containClassName?: string;
  height?: number;
  isZoom?: boolean;
};

export const defaultOption: any = {};

export const defaultLineOption: any = {
  grid: {
    left: 10,
    top: 60,
    right: 25,
    bottom: 10,
    containLabel: true,
  },
  legend: {
    show: true,
    orient: 'horizontal',
    icon: 'circle',
    itemWidth: 8,
    itemHeight: 8,
    textStyle: {
      fontSize: 12,
    },
    left: 'center',
    top: 'top',
  },
  xAxis: {
    type: 'category',
    axisLine: {
      show: true,
    },
    axisLabel: {
      showMaxLabel: true,
    },
    axisTick: {
      alignWithLabel: true,
      show: true,
    },
  },
  yAxis: {
    name: '',
    type: 'value',
    axisLine: {
      show: false,
    },
    axisTick: {
      show: false,
    },
  },
  tooltip: {
    show: true,
    trigger: 'axis',
    axisPointer: {
      type: 'line',
    },
  },
  series: [
    {
      type: 'line',
      color: 'rgba(21, 154, 255, 1)',
    },
  ],
};

export const defaultSankey: any = {
  series: {
    type: 'sankey',
    layout: 'none',
    layoutIterations: 0,
    nodeGap: 20,
    left: 0,
    right: 0,
    lineStyle: {
      color: 'gradient',
      opacity: 0.5,
      curveness: 0.5,
    },
    data: [],
    links: [],
  },
};

export const defaultPolarBar: any = {
  polar: {
    radius: ['40%', '100%'],
  },
  title: [
    {
      text: '',
      x: 'center',
      top: '50%',
      textStyle: {
        color: '#ACCCEC',
        fontSize: 12,
        width: 110,
        overflow: 'break',
      },
    },
    {
      text: '',
      x: 'center',
      top: '38%',
      textStyle: {
        fontSize: '12',
        color: '#fff',
        overflow: 'break',
        width: 90,
      },
    },
  ],
  angleAxis: {
    clockwise: false,
    max: 12,
    startAngle: 90,
    axisLabel: {
      show: false,
    },
    axisTick: {
      show: false,
    },
    axisLine: {
      show: false,
    },
    splitLine: {
      show: false,
    },
  },
  radiusAxis: {
    type: 'category',
    data: [], //['a', 'b', 'c', 'd', 'e']
    axisTick: {
      show: false,
    },
    axisLine: {
      show: false,
    },
    axisLabel: {
      show: false,
    },
  },
  tooltip: {
    backgroundColor: 'rgba(9, 12, 21, 0.8)',
    borderColor: 'rgba(21,154,255,0.8)',
    extraCssText: 'box-shadow: 0 0 6px 0 rgba(21,154,255,0.7);',
    textStyle: {
      color: 'white',
    },
  },
  series: {
    type: 'bar',
    barWidth: 6,
    barGap: 8,
    barMinAngle: 4,
    coordinateSystem: 'polar',
    roundCap: true,
    data: [
      // {
      //   value: 2,
      //   itemStyle: {
      //     color: 'red'
      //   },
      // }
    ],
    label: {
      show: false,
      // formatter: '{b}: {c}',
      // position: 'insideStart',
      // rotate: 0,
      // color: 'white',
    },
    showBackground: true,
    backgroundStyle: {
      color: '#1c2b42',
    },
  },
};

export const defaultMapOption: any = {
  tooltip: {
    trigger: 'item',
    backgroundColor: 'rgba(12,23,39,0.8)',
    padding: [16, 30],
    borderColor: '#00DEFF',
    borderWidth: 1,
    textStyle: {
      color: '#fff',
      fontSize: 18,
    },
    formatter: (params: any) => {
      const { data } = params;
      return `${data?.name}-${formatMessage(
        { id: 'map.siteSentence', defaultMessage: '站点' },
        { name: data?.value?.[2] },
      )}`;
    },
  },
  geo: {
    map: 'china1',
    show: true,
    animationDurationUpdate: 0,
    roam: true, //可以通过鼠标拖动进行缩放
    center: [104.37536882744293, 28.480215435],
    zoom: 1.5,
    top: 210,
    z: 100,
    label: {
      show: true,
      color: 'rgba(118,138,162)',
    },
    itemStyle: {
      areaColor: '#082b56',
      borderColor: '#4873a6', //线
      shadowColor: 'none', //外发光
      shadowBlur: 0,
    },
    emphasis: {
      label: {
        show: true,
        color: 'white',
      },
      itemStyle: {
        areaColor: 'rgba(52,185,255,1)', //悬浮区背景
      },
    },
  },
  series: [
    // 标记点
    {
      type: 'scatter',
      coordinateSystem: 'geo',
      //symbol: 'image://http://localhost:8000/dot.png',
      symbolSize: [30, 30],
      z: 110,
      label: {
        show: true,
        padding: [2, 12],
        backgroundColor: {
          type: 'linear',
          x: 0,
          y: 0,
          x2: 1,
          y2: 0,
          colorStops: [
            {
              offset: 0,
              color: 'rgba(0,22,48,0.1)', // 0% 处的颜色
            },
            {
              offset: 0.5,
              color: '#001630',
            },
            {
              offset: 1,
              color: 'rgba(0,22,48,0.1)', // 100% 处的颜色
            },
          ],
          global: false, // 缺省为 false
        },
        color: '#34E1B6',
        fontWeight: 500,
        offset: [0, -30],
        fontSize: 16,
        formatter(value: any) {
          return value.data.value[2];
        },
      },
      itemStyle: {
        opacity: 1,
      },
      roam: true,
      showEffectOn: 'render',
      rippleEffect: {
        brushType: 'stroke',
      },
      hoverAnimation: true,
    },
    {
      map: 'chinaMapOutline',
      silent: true,
      type: 'map',
      center: [104.37536882744293, 28.480215435],
      zoom: 1,
      top: 210,
      z: 15,
      label: {
        normal: {
          show: false,
          textStyle: {
            color: '#fff',
          },
        },
        emphasis: {
          textStyle: {
            color: '#fff',
          },
        },
      },
      roam: false,
      animationDurationUpdate: 0,
      itemStyle: {
        normal: {
          areaColor: 'transparent',
          borderColor: '#1390f0',
          borderWidth: 1.5,
          shadowBlur: 10,
        },
        emphasis: {
          areaColor: 'transparent', //悬浮背景
          textStyle: {
            color: '#fff',
          },
        },
      },
    },
    {
      map: 'chinaMapOutline1',
      silent: true,
      type: 'map',
      center: [104.37536882744293, 28.480215435],
      top: 220,
      zoom: 1,
      label: {
        normal: {
          show: false,
          textStyle: {
            color: '#fff',
          },
        },
        emphasis: {
          textStyle: {
            color: '#fff',
          },
        },
      },
      roam: false,
      animationDurationUpdate: 0,
      itemStyle: {
        normal: {
          areaColor: '#0d1c29',
          borderColor: '#094981',
          borderWidth: 1.5,
          shadowBlur: 0,
        },
        emphasis: {
          areaColor: 'transparent', //悬浮背景
          textStyle: {
            color: '#fff',
          },
        },
      },
    },
  ],
};

export const defaultPieOption: any = {
  tooltip: {
    trigger: 'item',
  },
  legend: {
    bottom: '5%',
    left: 'center',
    icon: 'circle',
  },
  series: [
    {
      type: 'pie',
      center: ['50%', '40%'],
      radius: ['30%', '50%'],
      avoidLabelOverlap: false,
      label: {
        show: false,
        position: 'center',
      },
      labelLine: {
        show: false,
      },
    },
  ],
};

export const defaultRtlOption = {
  tooltip: {
    formatter: function (params: any) {
      const curparams = Array.isArray(params) ? params : [params];
      let tooltipData = curparams[0].name;
      curparams.forEach((item, index) => {
        let value = item.data?.[item.seriesIndex + 1];
        let seriesName = item.seriesName;
        const seriesType = item.seriesType;
        if (seriesType == 'pie') {
          if (!index) tooltipData = '';
          value = item.value;
          seriesName = item.name;
        }
        const lable = `<div><span style="display: inline-block;">${
          item.marker
        } ${seriesName}</span> : ${isEmpty(value) ? '-' : value}</div>`;
        tooltipData += lable;
      });
      return tooltipData;
    },
  },
  legend: {
    align: 'right',
  },
};

export const defaultDataZoomOption = {
  grid: {
    right: 40,
    left: 40,
    bottom: 35,
  },
  dataZoom: [
    {
      type: 'slider', // 定义为滑动条型dataZoom
      show: true, // 显示滑动条
      xAxisIndex: [0], // 作用于X轴
      start: 0, // 数据窗口范围的起始百分比
      end: 100, // 数据窗口范围的结束百分比
      height: 15,
      bottom: 10,
    },
    {
      type: 'inside', // 内置型dataZoom
      xAxisIndex: [0],
      start: 0,
      end: 100,
      zoomOnMouseWheel: true, // 允许通过鼠标滚轮缩放
      moveOnMouseMove: true, // 允许通过鼠标拖拽平移
      moveOnMouseWheel: false, // 禁止通过滚轮移动视图（只允许缩放）
    },
  ],
};
