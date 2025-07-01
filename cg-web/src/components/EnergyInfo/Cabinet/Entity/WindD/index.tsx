/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-01-29 11:30:32
 * @LastEditTime: 2024-05-31 10:14:20
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\Cabinet\Entity\WindD\index.tsx
 */

import React, { useMemo } from 'react';
import { EntityType } from '../../type';
import Model from '../../Model';
import EnergyImg from '@/assets/image/station/energy/enery.png';
import { ConfigType } from '../../type';
import { formatMessage, parseToObj } from '@/utils';
import { DeviceProductTypeEnum, DeviceTypeEnum } from '@/utils/dictionary';
import AirImg from '@/assets/image/station/energy/air.png';
import AirLineImg from '@/assets/image/station/energy/air-line.png';
import DoorImg from '@/assets/image/station/energy/door.png';
import DoorLineImg from '@/assets/image/station/energy/door-line.png';
import EmsImg from '@/assets/image/station/energy/ems.png';
import EmsLineImg from '@/assets/image/station/energy/ems-line.png';
import StackImg from '@/assets/image/station/energy/stack.png';
import StackLineImg from '@/assets/image/station/energy/stack-line.png';
import PcsImg from '@/assets/image/station/energy/pcs.png';
import PcsLineImg from '@/assets/image/station/energy/pcs-line.png';
import FireFightImg from '@/assets/image/station/energy/fire-fight.png';
import FireFightLineImg from '@/assets/image/station/energy/fire-fight-line.png';
import PackLineImg from '@/assets/image/station/energy/pack-line.png';
import { getPackItems } from '../../helper';

const configs: ConfigType[] = [
  {
    label: formatMessage({ id: 'device.airConditioner', defaultMessage: '空调' }),
    productTypeId: DeviceProductTypeEnum.Air,
    position: { top: 51, left: 2 },
    icon: AirImg,
    line: AirLineImg,
    linePosition: { top: 11, left: 94 },
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
    productTypeId: DeviceProductTypeEnum.BatteryCluster,
    showLabel: false,
    position: { top: 203, left: 2 },
    icon: DoorImg,
    line: DoorLineImg,
    linePosition: { top: 11, left: 152 },
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
    position: { top: 302, left: 2 },
    icon: EmsImg,
    line: EmsLineImg,
    linePosition: { top: 11, left: 87 },
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
    position: { top: 450, left: 2 },
    icon: StackImg,
    line: StackLineImg,
    linePosition: { top: -74, left: 97 },
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
    position: { top: 40, left: 802 },
    icon: FireFightImg,
    line: FireFightLineImg,
    linePosition: { top: 10, left: -110 },
    data: [
      {
        label: formatMessage({ id: 'device.warningLevel', defaultMessage: '预警等级' }),
        field: 'lev',
      },
      {
        label: formatMessage({ id: 'device.subValveStatus', defaultMessage: '子阀门状态' }),
        field: 'svs',
      },
    ],
  },
  {
    label: formatMessage({ id: 'siteMonitor.monomerInformation', defaultMessage: '单体极值信息' }),
    productTypeId: DeviceProductTypeEnum.BatteryCluster,
    position: { top: 185, left: 802 },
    icon: EmsImg,
    line: PackLineImg,
    linePosition: { top: 11, left: -60 },
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
    position: { top: 487, left: 802 },
    icon: PcsImg,
    line: PcsLineImg,
    linePosition: { top: 11, left: -233 },
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
];

const WindD: React.FC<EntityType> = (props) => {
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
        }}
        configs={configs}
        {...props}
      >
        {getPackItems()}
      </Model>
    </>
  );
};

export default WindD;
