/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-14 11:31:33
 * @LastEditTime: 2024-05-24 14:36:54
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\Cabinet\Entity\WindPvFirewood\Front\index.tsx
 */

import React, { useMemo } from 'react';
import { EntityType } from '../../../type';
import Model from '../../../Model';
import { ConfigType } from '../../../type';
import { formatMessage, parseToObj } from '@/utils';
import { DeviceProductTypeEnum, DeviceTypeEnum } from '@/utils/dictionary';
import Energy from '@/assets/image/station/fgc-energy/energy.png';
import PcsImg from '@/assets/image/station/energy/pcs.png';
import PcsLineImg from '@/assets/image/station/fgc-energy/pcs-line.png';
import FireFightImg from '@/assets/image/station/energy/fire-fight.png';
import FireFightLineImg from '@/assets/image/station/fgc-energy/firefight-line.png';
import MeterImg from '@/assets/image/station/fgc-energy/meter.png';
import MeterLineImg from '@/assets/image/station/fgc-energy/meter-line.png';
import DoorImg from '@/assets/image/station/energy/door.png';
import DoorLineImg from '@/assets/image/station/fgc-energy/door-line.png';
import EmsImg from '@/assets/image/station/energy/ems.png';
import EmsLineImg from '@/assets/image/station/fgc-energy/ems-line.png';
import StackImg from '@/assets/image/station/energy/stack.png';
import StackLineImg from '@/assets/image/station/fgc-energy/bms-line.png';

const configs: ConfigType[] = [
  {
    label: formatMessage({ id: 'device.1002', defaultMessage: '逆变器' }),
    productTypeId: DeviceProductTypeEnum.Pcs,
    dataProductTypeIds: [DeviceProductTypeEnum.Ems],
    dataProductIds: [DeviceTypeEnum.Liquid2InverterMeter],
    position: { top: 6, left: 66 },
    icon: PcsImg,
    line: PcsLineImg,
    linePosition: { top: 22, left: 92 },
    data: [{ field: 'converterWorkingStatus' }, { field: 'alms' }],
  },
  {
    label: formatMessage({ id: 'device.fireFight', defaultMessage: '消防' }),
    productTypeId: DeviceProductTypeEnum.FireFight,
    position: { top: 138, left: 66 },
    icon: FireFightImg,
    line: FireFightLineImg,
    linePosition: { top: 22, left: 76 },
    data: [{ field: 'alms' }, { field: 'x15' }],
  },
  {
    label: '',
    productTypeId: DeviceProductTypeEnum.BatteryStack,
    showLabel: false,
    position: { top: 6, left: 734 },
    icon: DoorImg,
    line: DoorLineImg,
    linePosition: { top: 22, left: -160 },
    data: [{ field: 'd13' }],
  },
  {
    label: 'EMS',
    productTypeId: DeviceProductTypeEnum.Ems,
    position: { top: 70, left: 734 },
    icon: EmsImg,
    line: EmsLineImg,
    linePosition: { top: 22, left: -187 },
    data: [{ field: 'systemOperatingMode' }, { field: 'systemWorkingStatus' }],
  },
  {
    label: formatMessage({ id: 'device.batteryStack', defaultMessage: '电池堆' }),
    productTypeId: DeviceProductTypeEnum.BatteryStack,
    dataProductTypeIds: [DeviceProductTypeEnum.Ems],
    position: { top: 190, left: 734 },
    icon: StackImg,
    line: StackLineImg,
    linePosition: { top: 25, left: -190 },
    data: [
      { field: 'batteryPackWorkingStatus' },
      { field: 'alms' },
      { field: 'd5' },
      { field: 'SOC' },
    ],
  },
];

const Front: React.FC<EntityType> = (props) => {
  const { deviceData } = props;

  const deviceImg = useMemo(() => {
    const config = parseToObj(deviceData?.productConfig);
    return config?.views?.openFrontView || Energy;
  }, [deviceData]);

  return (
    <>
      <Model
        modelStyle={{
          height: '370px',
          backgroundImage: `url(${deviceImg})`,
          backgroundSize: '29%',
        }}
        configs={configs}
        detailProps={{
          labelStyle: {
            maxWidth: '100px',
          },
        }}
        {...props}
      ></Model>
    </>
  );
};

export default Front;
