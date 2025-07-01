/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-05 09:11:33
 * @LastEditTime: 2024-05-20 15:14:55
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\Cabinet\Entity\Charge1Gun\index.tsx
 */

import React, { useMemo } from 'react';
import { EntityType } from '../../type';
import Model from '../../Model';
import { ConfigType } from '../../type';
import { formatMessage, parseToObj } from '@/utils';
import { DeviceProductTypeEnum } from '@/utils/dictionary';
import ChargeImg from '@/assets/image/station/charge-1gun/charge.png';
import DoorImg from '@/assets/image/station/energy/door.png';
import DoorLineImg from '@/assets/image/station/charge-2gun/door-line.png';
import GunImg from '@/assets/image/station/charge-2gun/gun.png';
import GunBLine from '@/assets/image/station/charge-2gun/gun-b-line.png';
import EnvironmentImg from '@/assets/image/station/charge-2gun/environment.png';
import EnvironmentLineImg from '@/assets/image/station/charge-2gun/environment-line.png';
import RunImg from '@/assets/image/station/charge-2gun/run.png';
import RunLineImg from '@/assets/image/station/charge-2gun/run-line.png';
import PowerImg from '@/assets/image/station/charge-2gun/power.png';
import PowerLineImg from '@/assets/image/station/charge-2gun/power-line.png';

const configs: ConfigType[] = [
  {
    label: '',
    productTypeId: DeviceProductTypeEnum.DCChargePile,
    dataProductTypeIds: [DeviceProductTypeEnum.ChargeGun],
    showLabel: false,
    position: { top: 16, left: 2 },
    icon: DoorImg,
    line: DoorLineImg,
    linePosition: { top: 22, left: 142 },
    data: [
      {
        field: 'glocstu',
      },
    ],
  },
  {
    label: formatMessage({ id: 'device.chargingGun', defaultMessage: '充电枪' }),
    productTypeId: DeviceProductTypeEnum.ChargeGun,
    position: { top: 79, left: 754 },
    icon: GunImg,
    line: GunBLine,
    linePosition: { top: 26, left: -156 },
    data: [
      { field: 'gworkmode' },
      { field: 'WorkStatus' },
      { field: 'galarmStatus' },
      { field: 'gp' },
      { field: 'gcu' },
      { field: 'gci' },
      { field: 'gcapacity' },
      { field: 'gxqu' },
      { field: 'gxqi' },
      { field: 'SOC' },
      { field: 'gst' },
    ],
  },
  {
    label: formatMessage({
      id: 'device.chargingModuleInformation',
      defaultMessage: '充电模块信息',
    }),
    productTypeId: DeviceProductTypeEnum.ChargeMaster,
    position: { top: 423, left: 2 },
    icon: EnvironmentImg,
    line: EnvironmentLineImg,
    linePosition: { top: 22, left: 120 },
    data: [{ field: 'mmodp' }, { field: 'mgmodnum' }],
  },
  {
    label: '',
    showLabel: false,
    productTypeId: DeviceProductTypeEnum.ChargeMaster,
    position: { top: 20, left: 754 },
    icon: RunImg,
    line: RunLineImg,
    linePosition: { top: 22, left: -222 },
    data: [{ field: 'ctlmd' }, { field: 'systemWorkingStatus' }],
  },
  {
    label: formatMessage({ id: 'device.hostStatusInformation', defaultMessage: '主机状态信息' }),
    productTypeId: DeviceProductTypeEnum.ChargeMaster,
    position: { top: 510, left: 754 },
    icon: PowerImg,
    line: PowerLineImg,
    linePosition: { top: 22, left: -222 },
    data: [
      { field: 'mworkMode' },
      { field: 'mWorkStatus' },
      { field: 'WorkStatus' },
      { field: 'malarmStatus' },
      { field: 'mt1' },
      { field: 'mt2' },
      { field: 'mh' },
      { field: 'Pa' },
      { field: 'Pb' },
      { field: 'Pc' },
      { field: 'Ptotal' },
    ],
  },
];

const Charge2Gun: React.FC<EntityType> = (props) => {
  const { deviceData } = props;

  const deviceImg = useMemo(() => {
    const config = parseToObj(deviceData?.productConfig);
    return config?.views?.frontView || ChargeImg;
  }, [deviceData]);

  return (
    <>
      <Model
        modelStyle={{
          backgroundImage: `url(${deviceImg})`,
          backgroundSize: '38%',
        }}
        configs={configs}
        detailProps={{
          labelStyle: {
            maxWidth: '120px',
          },
        }}
        {...props}
      ></Model>
    </>
  );
};

export default Charge2Gun;
