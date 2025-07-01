/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-05 09:11:33
 * @LastEditTime: 2024-10-25 10:46:25
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\Cabinet\Entity\ChargeMaster\index.tsx
 */

import React, { useMemo } from 'react';
import { EntityType } from '../../type';
import Model from '../../Model';
import { ConfigType } from '../../type';
import { formatMessage, parseToObj } from '@/utils';
import { DeviceProductTypeEnum } from '@/utils/dictionary';
import ChargeImg from '@/assets/image/station/charge-master/charge.png';
import DoorImg from '@/assets/image/station/energy/door.png';
import DoorLineImg from '@/assets/image/station/charge-master/door-line.png';
import EnvironmentImg from '@/assets/image/station/charge-2gun/environment.png';
import EnvironmentLineImg from '@/assets/image/station/charge-master/environment-line.png';
import PowerImg from '@/assets/image/station/charge-2gun/power.png';
import PowerLineImg from '@/assets/image/station/charge-master/power-line.png';
import StatusLine from '@/assets/image/station/charge-terminal-2gun/gun-b-line.png';

const configs: ConfigType[] = [
  {
    label: '',
    productTypeId: DeviceProductTypeEnum.ChargeMaster,
    showLabel: false,
    position: { top: 16, left: 71 },
    icon: DoorImg,
    line: DoorLineImg,
    linePosition: { top: 22, left: 142 },
    data: [
      {
        field: 'mlocstu',
      },
    ],
  },
  {
    label: formatMessage({
      id: 'device.chargingModuleInformation',
      defaultMessage: '充电模块信息',
    }),
    productTypeId: DeviceProductTypeEnum.ChargeMaster,
    position: { top: 105, left: 71 },
    icon: EnvironmentImg,
    line: EnvironmentLineImg,
    linePosition: { top: 22, left: 110 },
    data: [{ field: 'mmodp' }, { field: 'mmodcount' }],
  },
  // {
  //   label: '',
  //   productTypeId: DeviceProductTypeEnum.DCChargePile,
  //   showLabel: false,
  //   position: { top: -20, left: 712 },
  //   icon: EnvironmentImg,
  //   line: StatusLine,
  //   linePosition: { top: 22, left: -150 },
  //   data: [{ field: 'ctlmd' }, { field: 'systemWorkingStatus' }],
  // },
  {
    label: formatMessage({ id: 'device.hostStatusInformation', defaultMessage: '主机状态信息' }),
    productTypeId: DeviceProductTypeEnum.ChargeMaster,
    position: { top: 60, left: 712 },
    icon: PowerImg,
    line: PowerLineImg,
    linePosition: { top: 22, left: -149 },
    data: [
      { field: 'mworkMode' },
      { field: 'mWorkStatus' },
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

const ChargeMaster: React.FC<EntityType> = (props) => {
  const { deviceData } = props;

  const deviceImg = useMemo(() => {
    const config = parseToObj(deviceData?.productConfig);
    return config?.views?.leftSideView || ChargeImg;
  }, [deviceData]);

  return (
    <>
      <Model
        modelStyle={{
          height: '300px',
          backgroundImage: `url(${deviceImg})`,
          backgroundSize: '22%',
        }}
        configs={configs}
        detailProps={{
          labelStyle: {
            maxWidth: '150px',
          },
        }}
        containTopParentDevice={1}
        {...props}
      ></Model>
    </>
  );
};

export default ChargeMaster;
