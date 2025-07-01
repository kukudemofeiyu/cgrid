/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-01-29 15:03:45
 * @LastEditTime: 2025-02-08 09:29:45
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/EnergyInfo/Cabinet/Entity/LiquidE/index.tsx
 */

import React, { useMemo } from 'react';
import { EntityType } from '../../type';
import Model from '../../Model';
import EnergyImg from '@/assets/image/station/liquid-energy/energy.png';
import { ConfigType } from '../../type';
import { formatMessage, parseToObj } from '@/utils';
import { DeviceProductTypeEnum, DeviceTypeEnum } from '@/utils/dictionary';
import DoorImg from '@/assets/image/station/energy/door.png';
import LiquidDoorLineImg from '@/assets/image/station/liquid-energy/door-line.png';
import PeakImg from '@/assets/image/station/energy/peak.png';
import EmsImg from '@/assets/image/station/energy/ems.png';
import StackImg from '@/assets/image/station/energy/stack.png';
import PcsImg from '@/assets/image/station/energy/pcs.png';
import LiquidAirImg from '@/assets/image/station/liquid-energy/air.png';
import LiquidAirLineImg from '@/assets/image/station/liquid-energy/air-line.png';
import LiquidEmsLineImg from '@/assets/image/station/liquid-energy/ems-line.png';
import LiquidBmsLineImg from '@/assets/image/station/liquid-energy/bms-line.png';
import FireFightImg from '@/assets/image/station/energy/fire-fight.png';
import LiquidFireFightLineImg from '@/assets/image/station/liquid-energy/firefight-line.png';
import LiquidStackLineImg from '@/assets/image/station/liquid-energy/stack-line.png';
import LiquidPcsLineImg from '@/assets/image/station/liquid-energy/pcs-line.png';
import DehumidiferImg from '@/assets/image/station/liquid-energy/dehumidifier.png';
import DehumidiferLineImg from '@/assets/image/station/liquid-energy/dehumidifier-line.png';

const configs: ConfigType[] = [
  {
    label: formatMessage({ id: 'device.liquidCoolingUnit', defaultMessage: '液冷机组' }),
    productTypeId: DeviceProductTypeEnum.LiquidCooled,
    position: { top: 557, left: 2 },
    icon: LiquidAirImg,
    line: LiquidAirLineImg,
    linePosition: { top: 20, left: 100 },
    data: [
      {
        label: formatMessage({ id: 'device.systemMode', defaultMessage: '系统模式' }),
        field: 'SystemMode',
      },
      {
        label: formatMessage({ id: 'siteMonitor.workingCondition', defaultMessage: '工作状态' }),
        field: 'WorkStatus',
      },
    ],
  },
  {
    label: formatMessage({ id: 'siteMonitor.storageDoor', defaultMessage: '储能仓门' }),
    productTypeId: DeviceProductTypeEnum.BatteryCluster,
    showLabel: false,
    position: { top: 70, left: 2 },
    icon: DoorImg,
    line: LiquidDoorLineImg,
    linePosition: { top: 20, left: 135 },
    data: [
      {
        label: formatMessage({ id: 'siteMonitor.storageDoor', defaultMessage: '储能仓门' }),
        field: 'AccessControlStatus',
      },
    ],
  },
  {
    label: 'EMS',
    productTypeId: DeviceProductTypeEnum.Ems,
    position: { top: 210, left: 2 },
    icon: EmsImg,
    line: LiquidEmsLineImg,
    linePosition: { top: 20, left: 77 },
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
    label: formatMessage({ id: 'device.batteryPack', defaultMessage: '电池组' }),
    productTypeId: DeviceProductTypeEnum.BatteryCluster,
    dataProductTypeIds: [DeviceProductTypeEnum.Ems],
    position: { top: 300, left: 768 },
    icon: StackImg,
    line: LiquidBmsLineImg,
    linePosition: { top: 20, left: -133 },
    data: [
      {
        label: formatMessage({ id: 'siteMonitor.workingMode', defaultMessage: '工作模式' }),
        field: 'batteryPackOperatingMode',
      },
      {
        label: formatMessage({ id: 'siteMonitor.workingCondition', defaultMessage: '工作状态' }),
        field: 'batteryPackWorkingStatus',
      },
      {
        field: 'SOC',
      },
    ],
  },
  {
    label: formatMessage({ id: 'device.fireFight', defaultMessage: '消防' }),
    productTypeId: DeviceProductTypeEnum.FireFight,
    position: { top: 60, left: 768 },
    icon: FireFightImg,
    line: LiquidFireFightLineImg,
    linePosition: { top: 25, left: -137 },
    data: [
      {
        label: formatMessage({ id: 'device.sensorStatus', defaultMessage: '传感器状态' }),
        field: 'SensorStatus',
        show: (value, data) => data?.productId != DeviceTypeEnum.LiquidEnergy232FireFight,
      },
      {
        label: formatMessage({ id: 'device.coConcentration', defaultMessage: 'CO浓度' }),
        field: 'DetectorCo',
        show: (value, data) => data?.productId != DeviceTypeEnum.LiquidEnergy232FireFight,
      },
      {
        field: 'x8',
        show: (value, data) => data?.productId == DeviceTypeEnum.LiquidEnergy232FireFight,
      },
      {
        field: 'alms',
        show: (value, data) => data?.productId == DeviceTypeEnum.LiquidEnergy232FireFight,
      },
    ],
  },
  {
    label: formatMessage({ id: 'siteMonitor.monomerInformation', defaultMessage: '单体极值信息' }),
    productTypeId: DeviceProductTypeEnum.BatteryCluster,
    position: { top: 452, left: 768 },
    icon: PeakImg,
    line: LiquidStackLineImg,
    linePosition: { top: 20, left: -165 },
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
    label: formatMessage({ id: 'device.energyStorageInverter', defaultMessage: '储能变流器' }),
    productTypeId: DeviceProductTypeEnum.Pcs,
    dataProductTypeIds: [DeviceProductTypeEnum.Ems],
    position: { top: 187, left: 768 },
    icon: PcsImg,
    line: LiquidPcsLineImg,
    linePosition: { top: -12, left: -222 },
    data: [
      {
        label: formatMessage({ id: 'siteMonitor.workingMode', defaultMessage: '工作模式' }),
        field: 'converterOperatingMode',
        show: (_, data) => data.productId != DeviceTypeEnum.LiquidEmsGrid,
      },
      {
        field: 'OffGridStatus',
        show: (_, data) => data.productId == DeviceTypeEnum.LiquidEmsGrid,
      },
      {
        label: formatMessage({ id: 'siteMonitor.workingCondition', defaultMessage: '工作状态' }),
        field: 'converterWorkingStatus',
      },
    ],
  },
  {
    label: formatMessage({ id: 'device.dehumidifier', defaultMessage: '除湿器' }),
    productTypeId: DeviceProductTypeEnum.Dehumidifier,
    position: { top: 377, left: 2 },
    icon: DehumidiferImg,
    line: DehumidiferLineImg,
    linePosition: { top: 20, left: 92 },
    data: [
      {
        label: formatMessage({ id: 'siteMonitor.workingMode', defaultMessage: '工作模式' }),
        field: 'WorkMode',
        show: (_, data) => data.productId != DeviceTypeEnum.DehumidifierTF,
      },
      {
        field: 'DehumidifierWorkStatus',
        show: (_, data) => data.productId == DeviceTypeEnum.DehumidifierTF,
      },
    ],
  },
];

const LiquidE: React.FC<EntityType> = (props) => {
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
          backgroundSize: '45%',
        }}
        configs={configs}
        {...props}
      />
    </>
  );
};

export default LiquidE;
