import { formatMessage } from '@/utils';
export const Voption = {
  color: ['#3DD598', '#007DFF'],
  yAxis: [
    {
      type: 'value',
      name: formatMessage({ id: 'device.voltage', defaultMessage: '电压' }) + '（v）',
      nameLocation: 'end',
      splitLine: {
        lineStyle: {
          type: 'dashed', //虚线
        },
      },
      alignTicks: true,
    },
    {
      type: 'value',
      name: formatMessage({ id: 'device.current', defaultMessage: '电流' }) + '（A）',
      nameLocation: 'end',
      splitLine: {
        lineStyle: {
          type: 'dashed', //虚线
        },
      },
    },
  ],
  grid: {
    top: 60,
    bottom: 50,
    right: 30,
    left: 15,
  },
  legend: {
    show: true,
    type: 'scroll',
    icon: 'rect',
    itemHeight: 10,
    itemWidth: 10,
  },
  tooltip: {
    trigger: 'axis',
  },
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
    },
  ],
  series: [
    {
      name: formatMessage({ id: 'device.maxAllowVoltage', defaultMessage: '最高允许充电电压' }),
      color: '#1890FF',
      type: 'line',
      yAxisIndex: 0,
    },
    {
      name: formatMessage({ id: 'device.maxAllowCurrent', defaultMessage: '最高允许充电电流' }),
      color: '#67D7FC',
      type: 'line',
      yAxisIndex: 1,
    },
    {
      name: formatMessage({ id: 'device.demandVoltage', defaultMessage: '需求电压' }),
      color: '#2FC25B',
      type: 'line',
      yAxisIndex: 0,
    },
    {
      name: formatMessage({ id: 'device.demandCurrent', defaultMessage: '需求电流' }),
      color: '#FF7070',
      type: 'line',
      yAxisIndex: 1,
    },
    {
      name: formatMessage({ id: 'device.chargeOutputVoltage', defaultMessage: '充电输出电压' }),
      color: '#5D7092',
      type: 'line',
      yAxisIndex: 0,
    },
    {
      name: formatMessage({ id: 'device.outputCurrent', defaultMessage: '充电输出电流' }),
      color: '#8080FF',
      type: 'line',
      yAxisIndex: 1,
    },
    {
      name: formatMessage({ id: 'device.bMSMeasureVoltage', defaultMessage: 'BMS测量电压' }),
      color: '#FAC958',
      type: 'line',
      yAxisIndex: 0,
    },
    {
      name: formatMessage({ id: 'device.bMSMeasureCurrent', defaultMessage: 'BMS测量电流' }),
      color: '#F79631',
      type: 'line',
      yAxisIndex: 1,
    },
  ],
};
export const Coption = {
  color: ['#3DD598', '#007DFF'],
  yAxis: {
    type: 'value',
    name: formatMessage({ id: 'device.temp', defaultMessage: '温度' }) + '（℃）',
    nameLocation: 'end',
    splitLine: {
      lineStyle: {
        type: 'dashed', //虚线
      },
    },
  },
  grid: {
    top: 60,
    bottom: 50,
    right: 15,
    left: 15,
  },
  legend: {
    show: true,
    type: 'scroll',
    icon: 'rect',
    itemHeight: 10,
    itemWidth: 10,
  },
  tooltip: {
    trigger: 'axis',
  },
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
    },
  ],
  series: [
    {
      name: formatMessage({ id: 'device.gunTemp', defaultMessage: '枪温度' }),
      color: '#1890FF',
      type: 'line',
    },
    {
      name: formatMessage({
        id: 'device.singleBatteryMaxtemp',
        defaultMessage: '单体电池最高温度',
      }),
      color: '#2FC25B',
      type: 'line',
    },
    {
      name: formatMessage({
        id: 'device.singleBatteryMintemp',
        defaultMessage: '单体电池最低温度',
      }),
      color: '#67D7FC',
      type: 'line',
    },
    {
      name: formatMessage({ id: 'device.chargeGunAnodetemp', defaultMessage: '充电枪正极温度' }),
      color: '#F79631',
      type: 'line',
    },
    {
      name: formatMessage({
        id: 'device.chargeGunCathodetemp',
        defaultMessage: '充电枪负极温度',
      }),
      color: '#FAC958',
      type: 'line',
    },
  ],
};
