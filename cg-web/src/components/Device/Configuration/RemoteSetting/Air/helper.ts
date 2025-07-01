/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-25 13:52:36
 * @LastEditTime: 2023-09-25 13:52:37
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\RemoteSetting\Air\helper.ts
 */

import { DetailItem } from '@/components/Detail';
import { formatMessage } from '@/utils';
import { tempFormat, percentageFormat } from '@/utils/format';

export const runItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'device.refrigerationPoint', defaultMessage: '制冷点' }),
    field: 'RefrigerationPoint',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'device.refrigerationDifference', defaultMessage: '制冷回差' }),
    field: 'CoolingReturnDifference',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'device.hotSpot', defaultMessage: '加热点' }),
    field: 'HeatingPoint',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'device.hotSpotDifference', defaultMessage: '加热回差' }),
    field: 'HeatingReturnDifference',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'device.dehumidiyPoint', defaultMessage: '除湿点' }),
    field: 'DehumidificationPoint',
    format: percentageFormat,
  },
  {
    label: formatMessage({ id: 'device.dehumidiyDifference', defaultMessage: '除湿回差' }),
    field: 'DehumidificationReturnDifference',
    format: percentageFormat,
  },
  {
    label: formatMessage({ id: 'device.highTemperaturePoint', defaultMessage: '高温点' }),
    field: 'HighTemperaturePoint',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'device.cryogenicPoint', defaultMessage: '低温点' }),
    field: 'LowTemperaturePoint',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'device.highHumidityPoint', defaultMessage: '高湿点' }),
    field: 'HighHumidityPoint',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'device.internalFanStop', defaultMessage: '内风机停止点' }),
    field: 'InternalFanStopPoint',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'device.highTemperatureOpen', defaultMessage: '高温开启值' }),
    field: 'HighTemperatureOpeningValue',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'device.highTemperatureClosed', defaultMessage: '高温关闭值' }),
    field: 'HighTemperatureShutdownValue',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'device.lowTemperatureOpen', defaultMessage: '低温开启值' }),
    field: 'LowTemperatureOpeningValue',
    format: tempFormat,
  },
  {
    label: formatMessage({ id: 'device.lowTemperatureClosed', defaultMessage: '低温关闭值' }),
    field: 'LowTemperatureShutdownValue',
    format: tempFormat,
  },
];
