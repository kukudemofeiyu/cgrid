/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-12-29 11:25:41
 * @LastEditTime: 2023-12-29 11:31:19
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Meter\helper.ts
 */

import { formatMessage } from '@/utils';

export type GridItemType = {
  title?: string;
  icon?: string;
  width?: string;
  item?: {
    label?: string;
    field?: string;
  }[];
};

export const gridConfig: GridItemType[] = [
  {
    title: formatMessage({ id: 'siteMonitor.current', defaultMessage: '电流' }) + '(A）',
    icon: '/static/img/current.png',
    item: [
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'A相',
          },
          {
            name: 'A',
          },
        ),
        field: 'Ia',
      },
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'B相',
          },
          {
            name: 'B',
          },
        ),
        field: 'Ib',
      },
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'C相',
          },
          {
            name: 'C',
          },
        ),
        field: 'Ic',
      },
    ],
  },
  {
    title: formatMessage({ id: 'siteMonitor.voltage', defaultMessage: '电压' }) + '(V）',
    icon: '/static/img/voltage.png',
    item: [
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'A相',
          },
          {
            name: 'A',
          },
        ),
        field: 'Ua',
      },
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'B相',
          },
          {
            name: 'B',
          },
        ),
        field: 'Ub',
      },
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'C相',
          },
          {
            name: 'C',
          },
        ),
        field: 'Uc',
      },
    ],
  },
  {
    title: formatMessage({ id: 'siteMonitor.lineVoltage', defaultMessage: '线电压' }) + '(V）',
    icon: '/static/img/line-voltage.png',
    item: [
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'AB相',
          },
          {
            name: 'AB',
          },
        ),
        field: 'Uab',
      },
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'BC相',
          },
          {
            name: 'BC',
          },
        ),
        field: 'Ubc',
      },
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'CA相',
          },
          {
            name: 'CA',
          },
        ),
        field: 'Uca',
      },
    ],
  },
  {
    title: formatMessage({ id: 'siteMonitor.activePower', defaultMessage: '有功功率' }) + '(kW）',
    icon: '/static/img/active-power.png',
    item: [
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'A相',
          },
          {
            name: 'A',
          },
        ),
        field: 'Pa',
      },
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'B相',
          },
          {
            name: 'B',
          },
        ),
        field: 'Pb',
      },
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'C相',
          },
          {
            name: 'C',
          },
        ),
        field: 'Pc',
      },
      { label: formatMessage({ id: 'siteMonitor.total', defaultMessage: '总' }), field: 'P' },
    ],
  },
  {
    title:
      formatMessage({ id: 'siteMonitor.reactivePower', defaultMessage: '无功功率' }) + '(kvar）',
    icon: '/static/img/reactive-power.png',
    item: [
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'A相',
          },
          {
            name: 'A',
          },
        ),
        field: 'Qa',
      },
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'B相',
          },
          {
            name: 'B',
          },
        ),
        field: 'Qb',
      },
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'C相',
          },
          {
            name: 'C',
          },
        ),
        field: 'Qc',
      },
      { label: formatMessage({ id: 'siteMonitor.total', defaultMessage: '总' }), field: 'Q' },
    ],
  },
  {
    title:
      formatMessage({ id: 'siteMonitor.apparentPower', defaultMessage: '视在功率' }) + '(kVA）',
    icon: '/static/img/apparent-power.png',
    item: [
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'A相',
          },
          {
            name: 'A',
          },
        ),
        field: 'Sa',
      },
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'B相',
          },
          {
            name: 'B',
          },
        ),
        field: 'Sb',
      },
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'C相',
          },
          {
            name: 'C',
          },
        ),
        field: 'Sc',
      },
      { label: formatMessage({ id: 'siteMonitor.total', defaultMessage: '总' }), field: 'S' },
    ],
  },
  {
    title:
      formatMessage({ id: 'siteMonitor.accumulatedElectricity', defaultMessage: '累计电量' }) +
      '(kWh）',
    icon: '/static/img/today-electric.png',
    item: [
      {
        label: formatMessage({
          id: 'siteMonitor.positiveActivePower',
          defaultMessage: '正向有功电量',
        }),
        field: 'Pimp',
      },
      {
        label: formatMessage({
          id: 'siteMonitor.reverseActivePower',
          defaultMessage: '反向有功电量',
        }),
        field: 'Pexp',
      },
      {
        label: formatMessage({
          id: 'siteMonitor.positiveReactivePower',
          defaultMessage: '正向无功电量',
        }),
        field: 'Qimp',
      },
      {
        label: formatMessage({
          id: 'siteMonitor.reverseReactivePower',
          defaultMessage: '反向无功电量',
        }),
        field: 'Qexp',
      },
    ],
  },
  {
    title: formatMessage({ id: 'siteMonitor.frequency', defaultMessage: '频率' }) + '(HZ)',
    icon: '/static/img/frequency.png',
    item: [
      {
        label: formatMessage({ id: 'siteMonitor.frequency', defaultMessage: '频率' }),
        field: 'Freq',
      },
    ],
  },
  {
    title: formatMessage({ id: 'siteMonitor.powerFactor', defaultMessage: '功率因数' }),
    icon: '/static/img/power-factor.png',
    item: [
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'A相',
          },
          {
            name: 'A',
          },
        ),
        field: 'COSa',
      },
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'B相',
          },
          {
            name: 'B',
          },
        ),
        field: 'COSb',
      },
      {
        label: formatMessage(
          {
            id: 'device.phaseSentence',
            defaultMessage: 'C相',
          },
          {
            name: 'C',
          },
        ),
        field: 'COSc',
      },
      { label: formatMessage({ id: 'siteMonitor.total', defaultMessage: '总' }), field: 'COS' },
    ],
  },
];
