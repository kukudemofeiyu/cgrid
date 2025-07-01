/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-01-29 11:30:32
 * @LastEditTime: 2024-01-29 16:49:20
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\Cabinet\Entity\Liquid\index.tsx
 */

import React, { useMemo } from 'react';
import { EntityType } from '../../type';
import Model from '../../Model';
import EnergyImg from '@/assets/image/station/liquid-energy/energy.png';
import { ConfigType } from '../../type';
import { formatMessage, parseToObj } from '@/utils';
import { DeviceProductTypeEnum } from '@/utils/dictionary';
import {
  chargeFormat,
  dehumidifierWorkModeFormat,
  doorFormat,
  liquidSensorFormat,
  liquidSystemModeFormat,
  liquidWorkFormat,
  percentageFormat,
  powerFormat,
  systemOperatingModeFormat,
  systemRunFormat,
  tempFormat,
  voltageFormat,
} from '@/utils/format';
import DoorImg from '@/assets/image/station/energy/door.png';
import LiquidDoorLineImg from '@/assets/image/station/liquid-energy/door-line.png';
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

const energyPowerFormat = (value: number, data: any) => {
  return (
    <span>
      {powerFormat(value)}({chargeFormat(data.CADI)})
    </span>
  );
};

const configs: ConfigType[] = [
  {
    label: formatMessage({ id: 'device.liquidCoolingUnit', defaultMessage: '液冷机组' }),
    productTypeId: DeviceProductTypeEnum.LiquidCooled,
    position: { top: 557, left: 2 },
    icon: LiquidAirImg,
    line: LiquidAirLineImg,
    linePosition: { top: 11, left: 100 },
    data: [
      {
        label: formatMessage({ id: 'device.systemMode', defaultMessage: '系统模式' }),
        field: 'SystemMode',
        customFormat: liquidSystemModeFormat,
      },
    ],
  },
  {
    label: formatMessage({ id: 'siteMonitor.storageDoor', defaultMessage: '储能仓门' }),
    productTypeId: DeviceProductTypeEnum.BatteryStack,
    showLabel: false,
    position: { top: 70, left: 2 },
    icon: DoorImg,
    line: LiquidDoorLineImg,
    linePosition: { top: 7, left: 135 },
    data: [
      {
        label: formatMessage({ id: 'siteMonitor.storageDoor', defaultMessage: '储能仓门' }),
        field: 'AccessControlStatus',
        customFormat: (value: number) => doorFormat(value),
      },
    ],
  },
  {
    label: 'EMS',
    productTypeId: DeviceProductTypeEnum.Ems,
    position: { top: 210, left: 2 },
    icon: EmsImg,
    line: LiquidEmsLineImg,
    linePosition: { top: 11, left: 77 },
    data: [
      {
        label: formatMessage({ id: 'siteMonitor.workingMode', defaultMessage: '工作模式' }),
        field: 'systemOperatingMode',
        customFormat: systemOperatingModeFormat,
      },
      {
        label: formatMessage({ id: 'siteMonitor.workingCondition', defaultMessage: '工作状态' }),
        field: 'systemWorkingStatus',
        customFormat: systemRunFormat,
      },
    ],
  },
  {
    label: formatMessage({ id: 'siteMonitor.batteryPile', defaultMessage: '电池堆' }),
    productTypeId: DeviceProductTypeEnum.BatteryStack,
    position: { top: 313, left: 768 },
    icon: StackImg,
    line: LiquidBmsLineImg,
    linePosition: { top: 7, left: -133 },
    data: [
      {
        label: 'SOC',
        field: 'SOC',
        customFormat: percentageFormat,
      },
      {
        label: formatMessage({
          id: 'siteMonitor.chargeDischargeIndication',
          defaultMessage: '充放电指示',
        }),
        field: 'CADI',
        customFormat: chargeFormat,
      },
    ],
  },
  {
    label: formatMessage({ id: 'device.fireFight', defaultMessage: '消防' }),
    productTypeId: DeviceProductTypeEnum.FireFight,
    position: { top: 79, left: 768 },
    icon: FireFightImg,
    line: LiquidFireFightLineImg,
    linePosition: { top: 12, left: -137 },
    data: [
      {
        label: formatMessage({ id: 'device.sensorStatus', defaultMessage: '传感器状态' }),
        field: 'SensorStatus',
        customFormat: liquidSensorFormat,
      },
      {
        label: formatMessage({ id: 'device.coConcentration', defaultMessage: 'CO浓度' }),
        field: 'DetectorCo',
      },
    ],
  },
  {
    label: formatMessage({ id: 'siteMonitor.monomerInformation', defaultMessage: '单体极值信息' }),
    productTypeId: DeviceProductTypeEnum.BatteryStack,
    position: { top: 465, left: 768 },
    icon: EmsImg,
    line: LiquidStackLineImg,
    linePosition: { top: 7, left: -165 },
    data: [
      {
        label: formatMessage({ id: 'siteMonitor.maxVoltage', defaultMessage: '最高电压' }),
        field: 'MVVOASU',
        customFormat: voltageFormat,
      },
      {
        label: formatMessage({ id: 'device.cellNumber', defaultMessage: '电芯编号' }),
        field: 'MaxNOIV',
      },
      {
        label: formatMessage({ id: 'siteMonitor.minVoltage', defaultMessage: '最低电压' }),
        field: 'MVVOSU',
        customFormat: voltageFormat,
      },
      {
        label: formatMessage({ id: 'device.cellNumber', defaultMessage: '电芯编号' }),
        field: 'MNOIV',
      },
      {
        label: formatMessage({ id: 'siteMonitor.maxTemperature', defaultMessage: '最高温度' }),
        field: 'MaximumIndividualTemperature',
        customFormat: tempFormat,
      },
      {
        label: formatMessage({ id: 'device.temperaturePoint', defaultMessage: '温度点' }),
        field: 'MITN',
      },
      {
        label: formatMessage({ id: 'siteMonitor.minTemperature', defaultMessage: '最低温度' }),
        field: 'LVOMT',
        customFormat: tempFormat,
      },
      {
        label: formatMessage({ id: 'device.temperaturePoint', defaultMessage: '温度点' }),
        field: 'MNOIT',
      },
    ],
  },
  {
    label: 'PCS',
    productTypeId: DeviceProductTypeEnum.Pcs,
    dataProductTypeIds: [DeviceProductTypeEnum.BatteryStack],
    position: { top: 200, left: 768 },
    icon: PcsImg,
    line: LiquidPcsLineImg,
    linePosition: { top: -29, left: -222 },
    data: [
      {
        label: formatMessage({ id: 'siteMonitor.workingCondition', defaultMessage: '工作状态' }),
        field: 'WorkStatus',
        customFormat: liquidWorkFormat,
      },
      {
        label: formatMessage({ id: 'device.power', defaultMessage: '功率' }),
        field: 'P',
        customFormat: energyPowerFormat,
      },
    ],
  },
  {
    label: formatMessage({ id: 'device.dehumidifier', defaultMessage: '除湿器' }),
    productTypeId: DeviceProductTypeEnum.Dehumidifier,
    position: { top: 377, left: 2 },
    icon: DehumidiferImg,
    line: DehumidiferLineImg,
    linePosition: { top: 11, left: 92 },
    data: [
      {
        label: formatMessage({ id: 'siteMonitor.workingMode', defaultMessage: '工作模式' }),
        field: 'WorkMode',
        customFormat: dehumidifierWorkModeFormat,
      },
    ],
  },
];

const Liquid: React.FC<EntityType> = (props) => {
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

export default Liquid;
