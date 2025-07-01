/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-05 09:11:33
 * @LastEditTime: 2024-08-05 10:30:16
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\Cabinet\Entity\ChargeTerminal2Gun\index.tsx
 */

import React, { useMemo } from 'react';
import { EntityType } from '../../type';
import Model from '../../Model';
import { ConfigType } from '../../type';
import { formatMessage, parseToObj } from '@/utils';
import { DeviceProductTypeEnum } from '@/utils/dictionary';
import ChargeImg from '@/assets/image/station/charge-terminal-2gun/charge.png';
import DoorImg from '@/assets/image/station/energy/door.png';
import DoorLineImg from '@/assets/image/station/charge-terminal-2gun/door-line.png';
import RunImg from '@/assets/image/station/charge-2gun/run.png';
import RunLineImg from '@/assets/image/station/charge-terminal-2gun/run-line.png';
import GunImg from '@/assets/image/station/charge-2gun/gun.png';
import GunALine from '@/assets/image/station/charge-terminal-2gun/gun-a-line.png';
import GunBLine from '@/assets/image/station/charge-terminal-2gun/gun-b-line.png';

const configs: ConfigType[] = [
  {
    label: '',
    productTypeId: DeviceProductTypeEnum.ChargeTerminal,
    dataProductTypeIds: [DeviceProductTypeEnum.ChargeGun],
    showLabel: false,
    position: { top: 48, left: 69 },
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
    label: '',
    showLabel: false,
    productTypeId: DeviceProductTypeEnum.ChargeTerminal,
    position: { top: 40, left: 706 },
    icon: RunImg,
    line: RunLineImg,
    linePosition: { top: 22, left: -195 },
    data: [{ field: 'WorkStatus' }, { field: 'talarmStatus' }],
  },
  {
    label: formatMessage(
      { id: 'device.gunSentence', defaultMessage: '枪' },
      {
        name: 'A',
      },
    ),
    productTypeId: DeviceProductTypeEnum.ChargeGun,
    fixValue: '1',
    position: { top: 112, left: 69 },
    icon: GunImg,
    line: GunALine,
    linePosition: { top: 22, left: 71 },
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
    label: formatMessage(
      { id: 'device.gunSentence', defaultMessage: '枪' },
      {
        name: 'B',
      },
    ),
    productTypeId: DeviceProductTypeEnum.ChargeGun,
    fixValue: '2',
    position: { top: 112, left: 706 },
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
];

const ChargeTerminal2Gun: React.FC<EntityType> = (props) => {
  const { deviceData } = props;

  const deviceImg = useMemo(() => {
    const config = parseToObj(deviceData?.productConfig);
    return config?.views?.frontView || ChargeImg;
  }, [deviceData]);

  return (
    <>
      <Model
        modelStyle={{
          height: '410px',
          backgroundImage: `url(${deviceImg})`,
          backgroundSize: '23%',
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

export default ChargeTerminal2Gun;
