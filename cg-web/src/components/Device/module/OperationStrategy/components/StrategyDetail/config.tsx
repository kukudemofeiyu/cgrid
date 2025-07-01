import type { TypeChartDataType } from '@/components/Chart/TypeChart';
import { formatMessage } from '@/utils';

export const barConfig = (data: TypeChartDataType[] = []) => ({
  grid: {
    top: 40,
    bottom: 33,
    right: 40,
    left: 30,
  },
  xAxis: {
    type: 'category',
  },
  yAxis: {
    name: formatMessage({ id: 'common.unit', defaultMessage: '单位' }) + '（kW）',
    nameTextStyle: {
      align: 'center',
      padding: [2, 2, 2, 25],
    },
  },
  legend: {
    show: true,
    top: 'top',
  },
  tooltip: {
    trigger: 'axis',
  },
  series: data.map((i) => ({ type: 'line', color: i.color })),
});

export enum ModeEnum {
  PEAK_VALLEY,
  BACKUP,
  MANUAL,
  SELF_USE,
  SCHEDUL = 6,
}

export const modeOptions = [
  {
    label: formatMessage({ id: 'device.1074', defaultMessage: '削峰填谷' }),
    value: ModeEnum.PEAK_VALLEY,
  },
  { label: formatMessage({ id: 'device.1075', defaultMessage: '备电' }), value: ModeEnum.BACKUP },
  {
    label: formatMessage({ id: 'device.1076', defaultMessage: '手动控制' }),
    value: ModeEnum.MANUAL,
  },
  {
    label: formatMessage({ id: 'device.1077', defaultMessage: '自发自用' }),
    value: ModeEnum.SELF_USE,
  },
  {
    label: formatMessage({ id: 'device.1078', defaultMessage: '调度充放电' }),
    value: ModeEnum.SCHEDUL,
  },
];

export const getoperatingMode = (value: any) => {
  return modeOptions.find((i) => i.value === value)?.label || '--';
};

export const enableOptions = [
  {
    value: 0,
    label: formatMessage({ id: 'common.disable', defaultMessage: '禁用' }),
  },
  {
    value: 1,
    label: formatMessage({ id: 'common.enabled', defaultMessage: '使能' }),
  },
];

export const getEnable = (value: number) => {
  return enableOptions.find((i) => i.value === value)?.label || '--';
};

export const detailItem = (systemOperatingMode: ModeEnum) => {
  const result: any = [
    {
      label: formatMessage({ id: 'device.1081', defaultMessage: '工作模式' }),
      field: 'systemOperatingMode',
      format: (value: number) => getoperatingMode(value),
      valueStyle: { color: '#1D2129' },
      span: 2,
    },
  ];
  if (systemOperatingMode !== ModeEnum.SCHEDUL) {
    result.push(
      {
        label: formatMessage({ id: 'device.1083', defaultMessage: '光伏消纳使能' }),
        field: 'photovoltaicAbsorption',
        format: (value: number) => getEnable(value),
        valueStyle: { color: '#1D2129' },
      },
      {
        label: formatMessage({ id: 'device.1082', defaultMessage: '防逆流使能' }),
        field: 'antiReflux',
        format: (value: number) => getEnable(value),
        valueStyle: { color: '#1D2129' },
      },
      {
        label: formatMessage({ id: 'device.1084', defaultMessage: '防过载使能' }),
        field: 'antiOverload',
        format: (value: number) => getEnable(value),
        valueStyle: { color: '#1D2129' },
      },
      {
        label: formatMessage({ id: 'device.1085', defaultMessage: '需量启动使能' }),
        field: 'demandStart',
        format: (value: number) => getEnable(value),
        valueStyle: { color: '#1D2129' },
      },
      {
        label: formatMessage({ id: 'device.1087', defaultMessage: 'SOC上限(%)' }),
        field: 'socUpperLimit',
        valueStyle: { color: '#1D2129' },
      },
      {
        label: formatMessage({ id: 'device.1088', defaultMessage: 'SOC下限(%)' }),
        field: 'socLowerLimit',
        valueStyle: { color: '#1D2129' },
        span: 2,
      },
    );
    if (![ModeEnum.SELF_USE, ModeEnum.SCHEDUL].includes(systemOperatingMode)) {
      result.push({
        label:
          systemOperatingMode == ModeEnum.BACKUP
            ? formatMessage({
                id: 'device.1092',
                defaultMessage: '充电执行功率',
              })
            : formatMessage({
                id: 'device.1089',
                defaultMessage: '执行功率(正功率为放电，负功率为充电)',
              }),
        field: 'executionPower',
        span: 2,
        valueStyle: { color: '#1D2129' },
      });
    }
  }
  return result;
};
