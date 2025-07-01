/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-27 14:50:00
 * @LastEditTime: 2023-10-19 09:45:24
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\PvInverter\RealTime.tsx
 */

import React, { useMemo } from 'react';
import { Skeleton } from 'antd';
import { RealTimeProps } from '@/components/ScreenDialog';
import Label from '@/components/Detail/DotLabel';
import useSubscribe from '@/pages/screen/useSubscribe';
import Detail from '@/components/Detail';
import type { DetailItem } from '@/components/Detail';
import { LabelTypeEnum } from '@/components/ScreenDialog';
import {
  voltageFormat,
  currentFormat,
  powerFormat,
  noPowerFormat,
  powerHourFormat,
  frequencyFormat,
  tempFormat,
  mohmFormat,
  timeFormat,
  outputMethodFormat,
} from '@/utils/format';
import { formatMessage } from '@/utils';
import { DeviceTypeEnum } from '@/utils/dictionary';
import PVTable from '@/components/Device/module/PVTable';

const RealTime: React.FC<
  RealTimeProps & {
    productId?: DeviceTypeEnum;
    loopNum: number;
  }
> = (props) => {
  const {
    id,
    productId,
    loading,
    open = true,
    detailProps,
    labelType = LabelTypeEnum.DotLabel,
    loopNum,
  } = props;
  const equipmentData = useSubscribe(id, open);

  const runItems = useMemo<DetailItem[]>(() => {
    return [
      {
        label:
          formatMessage({ id: 'siteMonitor.powerGrid', defaultMessage: '电网' }) +
          'A' +
          formatMessage({ id: 'siteMonitor.phase', defaultMessage: '相' }) +
          formatMessage({ id: 'siteMonitor.voltage', defaultMessage: '电压' }),
        field: 'Ua',
        format: voltageFormat,
      },
      {
        label:
          formatMessage({ id: 'siteMonitor.powerGrid', defaultMessage: '电网' }) +
          'A' +
          formatMessage({ id: 'siteMonitor.phase', defaultMessage: '相' }) +
          formatMessage({ id: 'siteMonitor.current', defaultMessage: '电流' }),
        field: 'Ia',
        format: currentFormat,
      },
      {
        label:
          formatMessage({ id: 'siteMonitor.powerGrid', defaultMessage: '电网' }) +
          'B' +
          formatMessage({ id: 'siteMonitor.phase', defaultMessage: '相' }) +
          formatMessage({ id: 'siteMonitor.voltage', defaultMessage: '电压' }),
        field: 'Ub',
        format: voltageFormat,
      },
      {
        label:
          formatMessage({ id: 'siteMonitor.powerGrid', defaultMessage: '电网' }) +
          'B' +
          formatMessage({ id: 'siteMonitor.phase', defaultMessage: '相' }) +
          formatMessage({ id: 'siteMonitor.current', defaultMessage: '电流' }),
        field: 'Ib',
        format: currentFormat,
      },
      {
        label:
          formatMessage({ id: 'siteMonitor.powerGrid', defaultMessage: '电网' }) +
          'C' +
          formatMessage({ id: 'siteMonitor.phase', defaultMessage: '相' }) +
          formatMessage({ id: 'siteMonitor.voltage', defaultMessage: '电压' }),
        field: 'Uc',
        format: voltageFormat,
      },
      {
        label:
          formatMessage({ id: 'siteMonitor.powerGrid', defaultMessage: '电网' }) +
          'C' +
          formatMessage({ id: 'siteMonitor.phase', defaultMessage: '相' }) +
          formatMessage({ id: 'siteMonitor.current', defaultMessage: '电流' }),
        field: 'Ic',
        format: currentFormat,
      },
      {
        label: formatMessage({ id: 'siteMonitor.activePower', defaultMessage: '有功功率' }),
        field: 'P',
        format: powerFormat,
      },
      {
        label: formatMessage({
          id: 'device.todayElectricitygeneration',
          defaultMessage: '今日发电量',
        }),
        field: 'dayCap',
        format: powerHourFormat,
      },
      {
        label: formatMessage({ id: 'siteMonitor.reactivePower', defaultMessage: '无功功率' }),
        field: 'reactivePower',
        format: noPowerFormat,
      },
      {
        label: formatMessage({
          id: 'siteMonitor.cumulativePowerGeneration',
          defaultMessage: '累计发电量',
        }),
        field: 'totalCap',
        format: powerHourFormat,
      },
      {
        label: formatMessage({ id: 'siteMonitor.powerFactor', defaultMessage: '功率因数' }),
        field: 'powerFactor',
      },
      {
        label: formatMessage({
          id: 'siteMonitor.inverterRatedPower',
          defaultMessage: '逆变器额定功率',
        }),
        field: 'ratedPowerOfInverter',
        format: powerFormat,
        show: (productId as DeviceTypeEnum) != DeviceTypeEnum.GRWTPvInverter,
      },
      {
        label: formatMessage({ id: 'siteMonitor.outputMode', defaultMessage: '输出方式' }),
        field: 'outputMethod',
        showExtra: false,
        format: outputMethodFormat,
      },
      {
        label: formatMessage({ id: 'siteMonitor.gridFrequency', defaultMessage: '电网频率' }),
        field: 'elecFreq',
        format: frequencyFormat,
      },
      {
        label: formatMessage({ id: 'siteMonitor.internalTemperature', defaultMessage: '内部温度' }),
        field: 'temperature',
        format: tempFormat,
      },
      {
        label: formatMessage({
          id: 'siteMonitor.inverterStartupTime',
          defaultMessage: '逆变器开机时间',
        }),
        field: 'openTime',
        format: timeFormat,
        showExtra: false,
        show: (productId as DeviceTypeEnum) != DeviceTypeEnum.GRWTPvInverter,
      },
      {
        label: formatMessage({
          id: 'siteMonitor.insulationImpedanceValue',
          defaultMessage: '绝缘阻抗值',
        }),
        field: 'insulationImpedanceValue',
        format: mohmFormat,
        show: (productId as DeviceTypeEnum) != DeviceTypeEnum.GRWTPvInverter,
      },
      {
        label: formatMessage({
          id: 'siteMonitor.inverterShutdownTime',
          defaultMessage: '逆变器关机时间',
        }),
        field: 'closeTime',
        format: timeFormat,
        showExtra: false,
        show: (productId as DeviceTypeEnum) != DeviceTypeEnum.GRWTPvInverter,
      },
    ];
  }, [productId]);

  return (
    <>
      {loading ? (
        <>
          <Skeleton className="mb24" paragraph={{ rows: 3 }} active />
          <Skeleton paragraph={{ rows: 6 }} active />
        </>
      ) : (
        <>
          {labelType == LabelTypeEnum.DotLabel ? (
            <Label title={formatMessage({ id: 'siteMonitor.dcSide', defaultMessage: '直流侧' })} />
          ) : (
            <Detail.Label
              title={formatMessage({ id: 'siteMonitor.dcSide', defaultMessage: '直流侧' })}
            />
          )}
          <PVTable id={id} open={open} loopNum={loopNum} />
          {labelType == LabelTypeEnum.DotLabel ? (
            <Label title={formatMessage({ id: 'siteMonitor.acSide', defaultMessage: '交流侧' })} />
          ) : (
            <Detail.Label
              title={formatMessage({ id: 'siteMonitor.acSide', defaultMessage: '交流侧' })}
            />
          )}
          <Detail data={equipmentData} items={runItems} {...(detailProps || {})} />
        </>
      )}
    </>
  );
};

export default RealTime;
