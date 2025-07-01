/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-01-29 17:35:58
 * @LastEditTime: 2024-03-06 18:25:15
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\Cabinet\Entity\SmallEnergy\index.tsx
 */

import React, { useMemo } from 'react';
import { EntityType } from '../../type';
import Model from '../../Model';
import EnergyImg from '@/assets/image/station/small-energy/energy.png';
import { ConfigType } from '../../type';
import { formatMessage, parseToObj } from '@/utils';
import { DeviceProductTypeEnum } from '@/utils/dictionary';
import DoorImg from '@/assets/image/station/energy/door.png';
import StackImg from '@/assets/image/station/energy/stack.png';
import FireFightImg from '@/assets/image/station/energy/fire-fight.png';
import AirImg from '@/assets/image/station/energy/air.png';
import BmsLineImg from '@/assets/image/station/small-energy/bms-line.svg';
import PeakLineImg from '@/assets/image/station/small-energy/peak-line.svg';
import PcsLineImg from '@/assets/image/station/small-energy/pcs-line.png';
import FirefightLineImg from '@/assets/image/station/small-energy/firefight-line.svg';
import AirLineImg from '@/assets/image/station/small-energy/air-line.svg';
import DoorLineImg from '@/assets/image/station/small-energy/door-line.svg';
import EmsLineImg from '@/assets/image/station/small-energy/ems-line.svg';
import MeterLineImg from '@/assets/image/station/small-energy/meter-line.svg';
import PvEnergyEmsImg from '@/assets/image/station/pv-energy/ems.png';
import PvEnergyMeterImg from '@/assets/image/station/pv-energy/meter.png';
import PvEnergyPeakImg from '@/assets/image/station/pv-energy/peak.png';
import PvEnergyInverterImg from '@/assets/image/station/pv-energy/inverter.png';

const configs: ConfigType[] = [
  {
    label: formatMessage({ id: 'device.airConditioner', defaultMessage: '空调' }),
    productTypeId: DeviceProductTypeEnum.Air,
    position: { top: 90, left: 784 },
    icon: AirImg,
    line: AirLineImg,
    linePosition: { top: 12, left: -198 },
    data: [
      {
        label: formatMessage({ id: 'siteMonitor.workingMode', defaultMessage: '工作模式' }),
        field: 'ctlmd',
      },
      {
        label: formatMessage({ id: 'siteMonitor.workingCondition', defaultMessage: '工作状态' }),
        field: 'WorkStatus',
      },
    ],
  },
  {
    label: formatMessage({ id: 'siteMonitor.storageDoor', defaultMessage: '储能仓门' }),
    productTypeId: DeviceProductTypeEnum.Ems,
    showLabel: false,
    position: { top: 257, left: 784 },
    icon: DoorImg,
    line: DoorLineImg,
    linePosition: { top: 7, left: -56 },
    data: [
      {
        label: formatMessage({ id: 'siteMonitor.storageDoor', defaultMessage: '储能仓门' }),
        field: 'AccessControlStatus',
      },
    ],
  },
  {
    label: formatMessage({ id: 'device.batteryPack', defaultMessage: '电池组' }),
    productTypeId: DeviceProductTypeEnum.BatteryCluster,
    dataProductTypeIds: [DeviceProductTypeEnum.Ems],
    position: { top: 77, left: 2 },
    icon: StackImg,
    line: BmsLineImg,
    linePosition: { top: 11, left: 92 },
    data: [
      {
        label: formatMessage({ id: 'siteMonitor.workingMode', defaultMessage: '工作模式' }),
        field: 'batteryPackOperatingMode',
      },
      {
        label: formatMessage({ id: 'siteMonitor.workingCondition', defaultMessage: '工作状态' }),
        field: 'batteryPackWorkingStatus',
      },
    ],
  },
  {
    label: formatMessage({ id: 'siteMonitor.monomerInformation', defaultMessage: '单体极值信息' }),
    productTypeId: DeviceProductTypeEnum.BatteryCluster,
    position: { top: 191, left: 2 },
    icon: PvEnergyPeakImg,
    line: PeakLineImg,
    linePosition: { top: 7, left: 140 },
    data: [
      {
        label: formatMessage({ id: 'siteMonitor.maxVoltage', defaultMessage: '最高电压' }),
        field: 'MVVOASU',
      },
      {
        label: formatMessage({ id: 'device.cellNumber', defaultMessage: '电芯编号' }),
        field: 'MaxNOIV',
        customFormat: (value, data) => `#${data?.macvm}-${value}`,
      },
      {
        label: formatMessage({ id: 'siteMonitor.minVoltage', defaultMessage: '最低电压' }),
        field: 'MVVOSU',
      },
      {
        label: formatMessage({ id: 'device.cellNumber', defaultMessage: '电芯编号' }),
        field: 'MNOIV',
        customFormat: (value, data) => `#${data?.micvb}-${value}`,
      },
      {
        label: formatMessage({ id: 'siteMonitor.maxTemperature', defaultMessage: '最高温度' }),
        field: 'MaximumIndividualTemperature',
      },
      {
        label: formatMessage({ id: 'device.temperaturePoint', defaultMessage: '温度点' }),
        field: 'MITN',
        customFormat: (value, data) => `#${data?.mactb}-${value}`,
      },
      {
        label: formatMessage({ id: 'siteMonitor.minTemperature', defaultMessage: '最低温度' }),
        field: 'LVOMT',
      },
      {
        label: formatMessage({ id: 'device.temperaturePoint', defaultMessage: '温度点' }),
        field: 'MNOIT',
        customFormat: (value, data) => `#${data?.mictm}-${value}`,
      },
    ],
  },
  {
    label: 'EMS',
    productTypeId: DeviceProductTypeEnum.Ems,
    position: { top: 354, left: 784 },
    icon: PvEnergyEmsImg,
    line: EmsLineImg,
    linePosition: { top: 7, left: -109 },
    data: [
      {
        label: formatMessage({ id: 'siteMonitor.workingMode', defaultMessage: '工作模式' }),
        field: 'systemOperatingMode',
      },
      {
        label: formatMessage({ id: 'siteMonitor.workingCondition', defaultMessage: '工作状态' }),
        field: 'systemWorkingStatus',
      },
    ],
  },
  {
    label: formatMessage({ id: 'device.energyStorageInverter', defaultMessage: '储能变流器' }),
    productTypeId: DeviceProductTypeEnum.Pcs,
    dataProductTypeIds: [DeviceProductTypeEnum.Ems],
    position: { top: 440, left: 2 },
    icon: PvEnergyInverterImg,
    line: PcsLineImg,
    linePosition: { top: 12, left: 124 },
    data: [
      {
        label: formatMessage({ id: 'siteMonitor.workingMode', defaultMessage: '工作模式' }),
        field: 'converterOperatingMode',
      },
      {
        label: formatMessage({ id: 'siteMonitor.workingCondition', defaultMessage: '工作状态' }),
        field: 'converterWorkingStatus',
      },
    ],
  },
  {
    label: formatMessage({ id: 'device.ammeter', defaultMessage: '电表' }),
    productTypeId: DeviceProductTypeEnum.EnergyElectricMeter,
    position: { top: 516, left: 784 },
    icon: PvEnergyMeterImg,
    line: MeterLineImg,
    linePosition: { top: 7, left: -304 },
    data: [
      {
        label: formatMessage({
          id: 'siteMonitor.chargingVolumeToday',
          defaultMessage: '今日充电量',
        }),
        field: 'DACC',
      },
      {
        label: formatMessage({
          id: 'siteMonitor.todayDischargeCapacity',
          defaultMessage: '今日放电量',
        }),
        field: 'DADC',
      },
      {
        label: formatMessage({ id: 'siteMonitor.totalActivePower', defaultMessage: '总有功功率' }),
        field: 'P',
      },
    ],
  },
  {
    label: formatMessage({ id: 'device.fireFight', defaultMessage: '消防' }),
    productTypeId: DeviceProductTypeEnum.FireFight,
    position: { top: 556, left: 2 },
    icon: FireFightImg,
    line: FirefightLineImg,
    linePosition: { top: 6, left: 76 },
    data: [
      {
        label: formatMessage({ id: 'device.alarmStatus', defaultMessage: '告警状态' }),
        field: 'alms',
      },
      {
        label: formatMessage({ id: 'device.startOverallStatus', defaultMessage: '启动总状态' }),
        field: 'x23',
      },
    ],
  },
];

const PvEnergy: React.FC<EntityType> = (props) => {
  const { deviceData } = props;

  const deviceImg = useMemo(() => {
    const config = parseToObj(deviceData?.productConfig);
    return config?.views?.openFrontView || EnergyImg;
  }, [deviceData]);

  return (
    <>
      <Model
        modelStyle={{
          backgroundImage: `url(${deviceImg})`,
          backgroundSize: '63%',
        }}
        configs={configs}
        {...props}
      />
    </>
  );
};

export default PvEnergy;
