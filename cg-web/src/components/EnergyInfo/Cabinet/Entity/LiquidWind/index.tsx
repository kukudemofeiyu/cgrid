/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-01-29 15:03:45
 * @LastEditTime: 2024-11-06 13:38:27
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\Cabinet\Entity\LiquidWind\index.tsx
 */

import React, { useMemo } from 'react';
import { EntityType } from '../../type';
import Model from '../../Model';
import EnergyImg from '@/assets/image/station/liquid-wind-energy/enery.png';
import Energy261AImg from '@/assets/image/station/liquid-wind-energy/enery261A.png';
import Energy261BImg from '@/assets/image/station/liquid-wind-energy/enery261B.png';
import { ConfigType } from '../../type';
import { formatMessage, parseToObj } from '@/utils';
import { DeviceProductTypeEnum, DeviceTypeEnum } from '@/utils/dictionary';
import DoorImg from '@/assets/image/station/energy/door.png';
import LiquidDoorLineImg from '@/assets/image/station/liquid-wind-energy/door-line.png';
import PeakImg from '@/assets/image/station/energy/peak.png';
import EmsImg from '@/assets/image/station/energy/ems.png';
import StackImg from '@/assets/image/station/energy/stack.png';
import PcsImg from '@/assets/image/station/energy/pcs.png';
import LiquidAirImg from '@/assets/image/station/liquid-energy/air.png';
import LiquidAirLineImg from '@/assets/image/station/liquid-wind-energy/air-line.png';
import LiquidEmsLineImg from '@/assets/image/station/liquid-wind-energy/ems-line.png';
import LiquidBmsLineImg from '@/assets/image/station/liquid-wind-energy/bms-line.png';
import FireFightImg from '@/assets/image/station/energy/fire-fight.png';
import LiquidFireFightLineImg from '@/assets/image/station/liquid-wind-energy/firefight-line.png';
import LiquidStackLineImg from '@/assets/image/station/liquid-wind-energy/stack-line.png';
import LiquidPcsLineImg from '@/assets/image/station/liquid-wind-energy/pcs-line.png';
import DehumidiferImg from '@/assets/image/station/liquid-energy/dehumidifier.png';
import DehumidiferLineImg from '@/assets/image/station/liquid-wind-energy/dehumidifier-line.png';

const configs: ConfigType[] = [
  {
    label: formatMessage({ id: 'siteMonitor.storageDoor', defaultMessage: '储能仓门' }),
    productTypeId: DeviceProductTypeEnum.BatteryCluster,
    showLabel: false,
    position: { top: 77, left: 2 },
    icon: DoorImg,
    line: LiquidDoorLineImg,
    linePosition: { top: 16, left: 135, width: 188 },
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
    position: { top: 198, left: 2 },
    icon: EmsImg,
    line: LiquidEmsLineImg,
    linePosition: { top: 16, left: 77, width: 338 },
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
    label: formatMessage({ id: 'device.dehumidifier', defaultMessage: '除湿器' }),
    productTypeId: DeviceProductTypeEnum.Dehumidifier,
    position: { top: 375, left: 2 },
    icon: DehumidiferImg,
    line: DehumidiferLineImg,
    linePosition: { top: -32, left: 92, width: 325 },
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
  {
    label: formatMessage({ id: 'device.energyStorageInverter', defaultMessage: '储能变流器' }),
    productTypeId: DeviceProductTypeEnum.Pcs,
    dataProductTypeIds: [DeviceProductTypeEnum.Ems],
    position: { top: 550, left: 2 },
    icon: PcsImg,
    line: LiquidPcsLineImg,
    linePosition: { top: 16, left: 116, width: 380 },
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
    label: formatMessage({ id: 'device.batteryPack', defaultMessage: '电池组' }),
    productTypeId: DeviceProductTypeEnum.BatteryCluster,
    dataProductTypeIds: [DeviceProductTypeEnum.Ems],
    position: { top: 58, left: 768 },
    icon: StackImg,
    line: LiquidBmsLineImg,
    linePosition: { top: 13, left: -183, width: 178 },
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
    label: formatMessage({ id: 'siteMonitor.monomerInformation', defaultMessage: '单体极值信息' }),
    productTypeId: DeviceProductTypeEnum.BatteryCluster,
    position: { top: 191, left: 768 },
    icon: PeakImg,
    line: LiquidStackLineImg,
    linePosition: { top: 7, left: -165, width: 156 },
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
    label: formatMessage({ id: 'device.liquidCoolingUnit', defaultMessage: '液冷机组' }),
    productTypeId: DeviceProductTypeEnum.LiquidCooled,
    position: { top: 454, left: 768 },
    icon: LiquidAirImg,
    line: LiquidAirLineImg,
    linePosition: { top: 11, left: -220, width: 213 },
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
    label: formatMessage({ id: 'device.fireFight', defaultMessage: '消防' }),
    productTypeId: DeviceProductTypeEnum.FireFight,
    position: { top: 584, left: 768 },
    icon: FireFightImg,
    line: LiquidFireFightLineImg,
    linePosition: { top: 8, left: -166, width: 158 },
    data: [
      {
        field: 'alms',
      },
    ],
  },
];

const productIdImgMap = new Map([
  [DeviceTypeEnum.LiquidEnergy261A, Energy261AImg],
  [DeviceTypeEnum.LiquidEnergy261B, Energy261BImg],
]);

const LiquidE: React.FC<EntityType> = (props) => {
  const { deviceData } = props;

  const deviceImg = useMemo(() => {
    const config = parseToObj(deviceData?.productConfig);
    return (
      config?.views?.openFrontView ||
      (deviceData?.productId ? productIdImgMap.get(deviceData?.productId) || EnergyImg : EnergyImg)
    );
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
