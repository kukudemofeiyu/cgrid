/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-01-06 15:45:35
 * @LastEditTime: 2024-07-09 16:27:17
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\module\BmuTabs\helper.ts
 */

import { formatMessage } from '@/utils';
import { DeviceTypeEnum } from '@/utils/dictionary';

export const cellText = formatMessage({ id: 'siteMonitor.cell', defaultMessage: '电芯' });
export const tempText = formatMessage({ id: 'siteMonitor.temperature', defaultMessage: '温度' });
export const poleTempText = formatMessage({ id: 'device.1105', defaultMessage: '极柱温度' });
const voltageText = formatMessage({ id: 'siteMonitor.voltage', defaultMessage: '电压' });

export const chartOptions = {
  grid: {
    bottom: 50,
  },
  legend: {
    icon: 'rect',
  },
  tooltip: {
    formatter: (params: any) => {
      const { value, name } = (params || {})[0];
      return (
        name + '：' + (value[1] === '' ? (value[2] === '' ? '' : value[2] + '℃') : value[1] + 'V')
      );
    },
  },
  xAxis: {
    triggerEvent: true,
  },
  yAxis: [
    {
      name: voltageText + '(V)',
      nameTextStyle: {
        align: 'left',
      },
      alignTicks: true,
    },
    {
      name: tempText + '(℃)',
      nameTextStyle: {
        align: 'right',
      },
    },
  ],
  series: [
    {
      type: 'bar',
      barMaxWidth: 10,
      stack: 'Total',
      itemStyle: {
        color: 'rgba(0, 125, 255, 1)',
      },
    },
    {
      type: 'bar',
      yAxisIndex: 1,
      barMaxWidth: 10,
      stack: 'Total',
      itemStyle: {
        color: 'rgba(61, 213, 152, 1)',
      },
    },
  ],
  dataZoom: [
    {
      type: 'inside',
    },
    {
      start: 0,
      end: 100,
      height: 15,
    },
  ],
};

export const getFieldByLabel = (label: string) => {
  const num = label.replace(cellText, '').replace(tempText, '');
  const field = label.indexOf(cellText) > -1 ? 'Voltage' : 'Temperature';
  return field + num;
};

export const defaultLables = {
  voltage: 24,
  temperature: 13,
};

const lables24_12 = {
  voltage: 24,
  temperature: 12,
};

const lables52_29 = {
  voltage: 52,
  temperature: 29,
};

const lables48_16 = {
  voltage: 48,
  temperature: 16,
};

const lables48_29 = {
  voltage: 48,
  temperature: 29,
};

export type BumConfig = {
  voltage?: number;
  temperature?: number;
  poleTemperature?: number;
};

export const bumConfigMap = new Map<
  DeviceTypeEnum | undefined,
  { bmuNum: number; labels: BumConfig }
>([
  [DeviceTypeEnum.Device, { bmuNum: 10, labels: defaultLables }],
  [DeviceTypeEnum.LiquidEnergy232BatteryPack, { bmuNum: 5, labels: lables52_29 }],
  [DeviceTypeEnum.Liquid2Battery16, { bmuNum: 5, labels: lables48_16 }],
  [DeviceTypeEnum.LiquidEnergy232ABatteryPack, { bmuNum: 5, labels: lables52_29 }],

  [DeviceTypeEnum.LiquidEnergyBatteryStack, { bmuNum: 5, labels: lables48_29 }],
  [DeviceTypeEnum.Liquid2EnergyBatteryCluster, { bmuNum: 5, labels: lables48_29 }],

  [DeviceTypeEnum.SmallEnergyBatteryCluster, { bmuNum: 10, labels: lables24_12 }],
  [DeviceTypeEnum.PvEnergyBms, { bmuNum: 9, labels: lables24_12 }],
  [DeviceTypeEnum.FGCCEnergyBatteryCluster, { bmuNum: 7, labels: defaultLables }],
]);
