/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-14 10:56:00
 * @LastEditTime: 2023-12-28 14:29:05
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\BatterryStack\Cluster\config.tsx
 */

import { ProField } from '@ant-design/pro-components';
import type { DetailItem } from '@/components/Detail';
import { onlineStatus } from '@/utils/dict';
import {
  clusterFormat,
  percentageFormat,
  openFormat,
  pankFanAlarmFormat,
  deviceAlarmStatusFormat,
} from '@/utils/format';
import { formatMessage } from '@/utils';

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
      return name + '：' + (value[1] === '' ? value[2] + '℃' : value[1] + 'V');
    },
  },
  xAxis: {
    triggerEvent: true,
  },
  yAxis: [
    {
      name: formatMessage({ id: 'siteMonitor.voltage', defaultMessage: '电压' }) + '(V)',
      alignTicks: true,
    },
    {
      name: formatMessage({ id: 'siteMonitor.temperature', defaultMessage: '温度' }) + '(℃)',
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
  const num = label
    .replace(formatMessage({ id: 'siteMonitor.cell', defaultMessage: '电芯' }), '')
    .replace(formatMessage({ id: 'siteMonitor.temperature', defaultMessage: '温度' }), '');
  const field =
    label.indexOf(formatMessage({ id: 'siteMonitor.cell', defaultMessage: '电芯' })) > -1
      ? 'Voltage'
      : 'Temperature';
  return field + num;
};

export const runItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'siteMonitor.runningState', defaultMessage: '运行状态' }),
    field: 'runState',
    format: clusterFormat,
  },
  {
    label: formatMessage({ id: 'siteMonitor.communication', defaultMessage: '通信' }),
    field: 'connectStatus',
    format: (value) => <ProField text={value} mode="read" valueEnum={onlineStatus} />,
  },
  {
    label: formatMessage({ id: 'common.warning', defaultMessage: '告警' }),
    field: 'alarmStatus',
    format: deviceAlarmStatusFormat,
  },
];

export const statusItems: DetailItem[] = [
  {
    label:
      'PACK' +
      formatMessage({ id: 'siteMonitor.fan', defaultMessage: '风扇' }) +
      formatMessage({ id: 'siteMonitor.stateFeedback', defaultMessage: '状态反馈' }),
    field: 'packFanAlarmStatus',
    format: pankFanAlarmFormat,
  },
  {
    label: 'BMU1' + formatMessage({ id: 'siteMonitor.fan', defaultMessage: '风扇' }),
    field: 'BMU1Fan',
    format: openFormat,
  },
  {
    label: `PACK${formatMessage({
      id: 'siteMonitor.fan',
      defaultMessage: '风扇',
    })}PWM${formatMessage({ id: 'siteMonitor.dutyCycle', defaultMessage: '占空比' })}1`,
    field: 'PACKFPDC1',
    format: percentageFormat,
  },
  {
    label: 'BMU2' + formatMessage({ id: 'siteMonitor.fan', defaultMessage: '风扇' }),
    field: 'BMU2Fan',
    format: openFormat,
  },
  {
    label: `PACK${formatMessage({
      id: 'siteMonitor.fan',
      defaultMessage: '风扇',
    })}PWM${formatMessage({ id: 'siteMonitor.dutyCycle', defaultMessage: '占空比' })}2`,
    field: 'PACKFPDC2',
    format: percentageFormat,
  },
  {
    label: 'BMU3' + formatMessage({ id: 'siteMonitor.fan', defaultMessage: '风扇' }),
    field: 'BMU3Fan',
    format: openFormat,
  },
  {
    label: `PACK${formatMessage({
      id: 'siteMonitor.fan',
      defaultMessage: '风扇',
    })}PWM${formatMessage({ id: 'siteMonitor.dutyCycle', defaultMessage: '占空比' })}3`,
    field: 'PACKFPDC3',
    format: percentageFormat,
  },
  {
    label: 'BMU4' + formatMessage({ id: 'siteMonitor.fan', defaultMessage: '风扇' }),
    field: 'BMU4Fan',
    format: openFormat,
  },
  {
    label: `PACK${formatMessage({
      id: 'siteMonitor.fan',
      defaultMessage: '风扇',
    })}PWM${formatMessage({ id: 'siteMonitor.dutyCycle', defaultMessage: '占空比' })}4`,
    field: 'PACKFPDC4',
    format: percentageFormat,
  },
  {
    label: 'BMU5' + formatMessage({ id: 'siteMonitor.fan', defaultMessage: '风扇' }),
    field: 'BMU5Fan',
    format: openFormat,
  },
  {
    label: `PACK${formatMessage({
      id: 'siteMonitor.fan',
      defaultMessage: '风扇',
    })}PWM${formatMessage({ id: 'siteMonitor.dutyCycle', defaultMessage: '占空比' })}5`,
    field: 'PACKFPDC5',
    format: percentageFormat,
  },
  {
    label: 'BMU6' + formatMessage({ id: 'siteMonitor.fan', defaultMessage: '风扇' }),
    field: 'BMU6Fan',
    format: openFormat,
  },
  {
    label: `PACK${formatMessage({
      id: 'siteMonitor.fan',
      defaultMessage: '风扇',
    })}PWM${formatMessage({ id: 'siteMonitor.dutyCycle', defaultMessage: '占空比' })}6`,
    field: 'PACKFPDC6',
    format: percentageFormat,
  },
  {
    label: 'BMU7' + formatMessage({ id: 'siteMonitor.fan', defaultMessage: '风扇' }),
    field: 'BMU7Fan',
    format: openFormat,
  },
  {
    label: `PACK${formatMessage({
      id: 'siteMonitor.fan',
      defaultMessage: '风扇',
    })}PWM${formatMessage({ id: 'siteMonitor.dutyCycle', defaultMessage: '占空比' })}7`,
    field: 'PACKFPDC7',
    format: percentageFormat,
  },
  {
    label: 'BMU8' + formatMessage({ id: 'siteMonitor.fan', defaultMessage: '风扇' }),
    field: 'BMU8Fan',
    format: openFormat,
  },
  {
    label: `PACK${formatMessage({
      id: 'siteMonitor.fan',
      defaultMessage: '风扇',
    })}PWM${formatMessage({ id: 'siteMonitor.dutyCycle', defaultMessage: '占空比' })}8`,
    field: 'PACKFPDC8',
    format: percentageFormat,
  },
  {
    label: 'BMU9' + formatMessage({ id: 'siteMonitor.fan', defaultMessage: '风扇' }),
    field: 'BMU9Fan',
    format: openFormat,
  },
  {
    label: `PACK${formatMessage({
      id: 'siteMonitor.fan',
      defaultMessage: '风扇',
    })}PWM${formatMessage({ id: 'siteMonitor.dutyCycle', defaultMessage: '占空比' })}9`,
    field: 'PACKFPDC9',
    format: percentageFormat,
  },
  {
    label: 'BMU10' + formatMessage({ id: 'siteMonitor.fan', defaultMessage: '风扇' }),
    field: 'BMU10Fan',
    format: openFormat,
  },
  {
    label: `PACK${formatMessage({
      id: 'siteMonitor.fan',
      defaultMessage: '风扇',
    })}PWM${formatMessage({ id: 'siteMonitor.dutyCycle', defaultMessage: '占空比' })}10`,
    field: 'PACKFPDC10',
    format: percentageFormat,
  },
];
