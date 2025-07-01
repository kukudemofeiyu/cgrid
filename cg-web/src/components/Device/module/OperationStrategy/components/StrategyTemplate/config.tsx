import { formatMessage } from '@/utils';
import type { ProFormColumnsType } from '@ant-design/pro-components';

import {
  modeOptions,
  enableOptions,
  getEnable,
  getoperatingMode,
  ModeEnum,
} from '../StrategyDetail/config';

export const schemaColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: 'siteManage.1101', defaultMessage: '模版名称' }),
    dataIndex: 'name',
    formItemProps: {
      rules: [
        {
          required: true,
          message: formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }),
        },
      ],
    },
  },
  {
    title: formatMessage({ id: 'device.1081', defaultMessage: '工作模式' }),
    dataIndex: 'systemOperatingMode',
    valueType: 'select',
    formItemProps: {
      rules: [
        {
          required: true,
          message: formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }),
        },
      ],
    },
    fieldProps: {
      options: modeOptions,
    },
  },
  {
    valueType: 'dependency',
    name: ['systemOperatingMode'],
    columns: ({ systemOperatingMode }) => {
      return systemOperatingMode !== ModeEnum.SCHEDUL
        ? [
            {
              title: formatMessage({ id: 'device.1082', defaultMessage: '防逆流使能' }),
              dataIndex: ['config', 'antiReflux'],
              valueType: 'select',
              formItemProps: {
                rules: [
                  {
                    required: true,
                    message: formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }),
                  },
                ],
              },
              fieldProps: {
                options: enableOptions,
              },
            },

            {
              title: formatMessage({ id: 'device.1083', defaultMessage: '光伏消纳使能' }),
              dataIndex: ['config', 'photovoltaicAbsorption'],
              valueType: 'select',
              formItemProps: {
                rules: [
                  {
                    required: true,
                    message: formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }),
                  },
                ],
              },
              fieldProps: {
                options: enableOptions,
              },
            },

            {
              title: formatMessage({ id: 'device.1084', defaultMessage: '防过载使能' }),
              dataIndex: ['config', 'antiOverload'],
              valueType: 'select',
              formItemProps: {
                rules: [
                  {
                    required: true,
                    message: formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }),
                  },
                ],
              },
              fieldProps: {
                options: enableOptions,
              },
            },

            {
              title: formatMessage({ id: 'device.1085', defaultMessage: '需量启动使能' }),
              dataIndex: ['config', 'demandStart'],
              valueType: 'select',
              formItemProps: {
                rules: [
                  {
                    required: true,
                    message: formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }),
                  },
                ],
              },
              fieldProps: {
                options: enableOptions,
              },
            },
            {
              title: formatMessage({ id: 'device.1087', defaultMessage: 'SOC上限(%)' }),
              dataIndex: ['config', 'socUpperLimit'],
              valueType: 'digit',
              formItemProps: {
                rules: [
                  {
                    required: true,
                    message: formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }),
                  },
                ],
              },
            },

            {
              title: formatMessage({ id: 'device.1088', defaultMessage: 'SOC下限(%)' }),
              dataIndex: ['config', 'socLowerLimit'],
              valueType: 'digit',
              formItemProps: {
                rules: [
                  {
                    required: true,
                    message: formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }),
                  },
                ],
              },
            },
          ]
        : [];
    },
  },
  {
    valueType: 'dependency',
    name: ['systemOperatingMode'],
    columns: ({ systemOperatingMode }) => {
      return ![ModeEnum.SELF_USE, ModeEnum.SCHEDUL].includes(systemOperatingMode)
        ? [
            {
              title:
                systemOperatingMode == ModeEnum.BACKUP
                  ? formatMessage({
                      id: 'device.1092',
                      defaultMessage: '充电执行功率',
                    })
                  : formatMessage({
                      id: 'device.1089',
                      defaultMessage: '执行功率(正功率为放电，负功率为充电)',
                    }),
              dataIndex: ['config', 'executionPower'],
              valueType: 'text',
              fieldProps: {
                type: 'number',
              },
              formItemProps: {
                rules: [
                  {
                    required: true,
                    message: formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }),
                  },
                ],
              },
            },
          ]
        : [];
    },
  },
];

export const detailItem = (mode: ModeEnum = ModeEnum.PEAK_VALLEY) => {
  let item: any[] = [
    {
      label: formatMessage({ id: 'device.1081', defaultMessage: '工作模式' }),
      field: 'systemOperatingMode',
      format: (value: number) => getoperatingMode(value),
      valueStyle: { color: '#1D2129' },
      span: 2,
    },
  ];
  if (mode !== ModeEnum.SCHEDUL) {
    item = item.concat([
      {
        label: formatMessage({ id: 'device.1082', defaultMessage: '防逆流使能' }),
        field: 'antiReflux',
        format: (value: number) => getEnable(value),
        valueStyle: { color: '#1D2129' },
      },

      {
        label: formatMessage({ id: 'device.1083', defaultMessage: '光伏消纳使能' }),
        field: 'photovoltaicAbsorption',
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
    ]);
    if (![ModeEnum.SELF_USE, ModeEnum.SCHEDUL].includes(mode)) {
      item.push({
        label:
          mode == ModeEnum.BACKUP
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
  return item;
};
