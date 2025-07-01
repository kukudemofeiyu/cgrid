/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-05 09:11:33
 * @LastEditTime: 2024-08-05 11:27:34
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\Cabinet\Entity\ChargeTerminal1Gun\index.tsx
 */

import React, { useMemo } from 'react';
import { EntityType } from '../../type';
import Model from '../../Model';
import { ConfigType } from '../../type';
import { formatMessage, parseToObj } from '@/utils';
import { DeviceProductTypeEnum } from '@/utils/dictionary';
import ChargeImg from '@/assets/image/station/charge-terminal-1gun/charge.png';
import DoorImg from '@/assets/image/station/energy/door.png';
import DoorLineImg from '@/assets/image/station/charge-terminal-1gun/door-line.png';
import RunImg from '@/assets/image/station/charge-2gun/run.png';
import RunLineImg from '@/assets/image/station/charge-terminal-1gun/run-line.png';
import GunImg from '@/assets/image/station/charge-2gun/gun.png';
import GunBLine from '@/assets/image/station/charge-terminal-1gun/gun-line.png';

const configs: ConfigType[] = [
  {
    label: '',
    productTypeId: DeviceProductTypeEnum.ChargeTerminal,
    dataProductTypeIds: [DeviceProductTypeEnum.ChargeGun],
    showLabel: false,
    position: { top: 55, left: 69 },
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
    position: { top: 40, left: 687 },
    icon: RunImg,
    line: RunLineImg,
    linePosition: { top: 22, left: -183 },
    data: [{ field: 'WorkStatus' }, { field: 'talarmStatus' }],
  },
  {
    label: formatMessage({ id: 'device.chargingGun', defaultMessage: '充电枪' }),
    productTypeId: DeviceProductTypeEnum.ChargeGun,
    position: { top: 106, left: 687 },
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

const ChargeTerminal1Gun: React.FC<EntityType> = (props) => {
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
          backgroundSize: '20%',
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

export default ChargeTerminal1Gun;
