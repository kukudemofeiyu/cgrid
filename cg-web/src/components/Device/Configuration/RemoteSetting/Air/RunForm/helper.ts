/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-25 11:00:00
 * @LastEditTime: 2023-09-25 11:01:53
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\RemoteSetting\Air\helper.ts
 */
import { formatMessage } from '@/utils';
import { ProFormColumnsType } from '@ant-design/pro-components';

export const columns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: 'device.refrigerationPoint', defaultMessage: '制冷点' }),
    dataIndex: 'RefrigerationPoint',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.refrigerationPoint', defaultMessage: '制冷点' }),
        },
      ],
    },
    fieldProps: {
      addonAfter: '℃',
      className: 'w-full',
    },
  },
  {
    title: formatMessage({ id: 'device.refrigerationDifference', defaultMessage: '制冷回差' }),
    dataIndex: 'CoolingReturnDifference',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.refrigerationDifference', defaultMessage: '制冷回差' }),
        },
      ],
    },
    fieldProps: {
      addonAfter: '℃',
      className: 'w-full',
    },
  },
  {
    title: formatMessage({ id: 'device.hotSpot', defaultMessage: '加热点' }),
    dataIndex: 'HeatingPoint',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.hotSpot', defaultMessage: '加热点' }),
        },
      ],
    },
    fieldProps: {
      addonAfter: '℃',
      className: 'w-full',
    },
  },
  {
    title: formatMessage({ id: 'device.hotSpotDifference', defaultMessage: '加热回差' }),
    dataIndex: 'HeatingReturnDifference',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.hotSpotDifference', defaultMessage: '加热回差' }),
        },
      ],
    },
    fieldProps: {
      addonAfter: '℃',
      className: 'w-full',
    },
  },
  {
    title: formatMessage({ id: 'device.dehumidiyPoint', defaultMessage: '除湿点' }),
    dataIndex: 'DehumidificationPoint',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.dehumidiyPoint', defaultMessage: '除湿点' }),
        },
      ],
    },
    fieldProps: {
      addonAfter: '%',
      className: 'w-full',
    },
  },
  {
    title: formatMessage({ id: 'device.dehumidiyDifference', defaultMessage: '除湿回差' }),
    dataIndex: 'DehumidificationReturnDifference',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.dehumidiyDifference', defaultMessage: '除湿回差' }),
        },
      ],
    },
    fieldProps: {
      addonAfter: '%',
      className: 'w-full',
    },
  },
  {
    title: formatMessage({ id: 'device.highTemperaturePoint', defaultMessage: '高温点' }),
    dataIndex: 'HighTemperaturePoint',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.highTemperaturePoint', defaultMessage: '高温点' }),
        },
      ],
    },
    fieldProps: {
      addonAfter: '℃',
      className: 'w-full',
    },
  },
  {
    title: formatMessage({ id: 'device.cryogenicPoint', defaultMessage: '低温点' }),
    dataIndex: 'LowTemperaturePoint',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.cryogenicPoint', defaultMessage: '低温点' }),
        },
      ],
    },
    fieldProps: {
      addonAfter: '℃',
      className: 'w-full',
    },
  },
  {
    title: formatMessage({ id: 'device.highHumidityPoint', defaultMessage: '高湿点' }),
    dataIndex: 'HighHumidityPoint',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.highHumidityPoint', defaultMessage: '高湿点' }),
        },
      ],
    },
    fieldProps: {
      addonAfter: '℃',
      className: 'w-full',
    },
  },
  {
    title: formatMessage({ id: 'device.internalFanStop', defaultMessage: '内风机停止点' }),
    dataIndex: 'InternalFanStopPoint',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.internalFanStop', defaultMessage: '内风机停止点' }),
        },
      ],
    },
    fieldProps: {
      addonAfter: '℃',
      className: 'w-full',
    },
  },
  {
    title: formatMessage({ id: 'device.highTemperatureOpen', defaultMessage: '高温开启值' }),
    dataIndex: 'HighTemperatureOpeningValue',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.highTemperatureOpen', defaultMessage: '高温开启值' }),
        },
      ],
    },
    fieldProps: {
      addonAfter: '℃',
      className: 'w-full',
    },
  },
  {
    title: formatMessage({ id: 'device.highTemperatureClosed', defaultMessage: '高温关闭值' }),
    dataIndex: 'HighTemperatureShutdownValue',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.highTemperatureClosed', defaultMessage: '高温关闭值' }),
        },
      ],
    },
    fieldProps: {
      addonAfter: '℃',
      className: 'w-full',
    },
  },
  {
    title: formatMessage({ id: 'device.lowTemperatureOpen', defaultMessage: '低温开启值' }),
    dataIndex: 'LowTemperatureOpeningValue',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.lowTemperatureOpen', defaultMessage: '低温开启值' }),
        },
      ],
    },
    fieldProps: {
      addonAfter: '℃',
      className: 'w-full',
    },
  },
  {
    title: formatMessage({ id: 'device.lowTemperatureClosed', defaultMessage: '低温关闭值' }),
    dataIndex: 'LowTemperatureShutdownValue',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.lowTemperatureClosed', defaultMessage: '低温关闭值' }),
        },
      ],
    },
    fieldProps: {
      addonAfter: '℃',
      className: 'w-full',
    },
  },
];
