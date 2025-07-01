/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-14 11:30:35
 * @LastEditTime: 2024-05-24 14:33:39
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\Cabinet\Entity\WindPvFirewood\Back\index.tsx
 */

import React, { useMemo } from 'react';
import { EntityType } from '../../../type';
import Model from '../../../Model';
import { ConfigType } from '../../../type';
import { formatMessage, parseToObj } from '@/utils';
import { DeviceProductTypeEnum } from '@/utils/dictionary';
import Energy from '@/assets/image/station/fgc-energy/energy-back.png';
import AirImg from '@/assets/image/station/energy/air.png';
import AirLineImg from '@/assets/image/station/fgc-energy/air-line.png';
import Cluster2LineImg from '@/assets/image/station/fgc-energy/stack2-line.png';
import DoorImg from '@/assets/image/station/energy/door.png';
import DoorLineImg from '@/assets/image/station/fgc-energy/door-back-line.png';
import EmsImg from '@/assets/image/station/energy/ems.png';
import PeakImg from '@/assets/image/station/energy/peak.png';
import PeakLineImg from '@/assets/image/station/fgc-energy/peak-line.png';
import ClusterLineImg from '@/assets/image/station/fgc-energy/stack-line.png';

const configs: ConfigType[] = [
  {
    label: formatMessage({
      id: 'device.monomerExtremeValueInformation',
      defaultMessage: '单体极值信息',
    }),
    productTypeId: DeviceProductTypeEnum.BatteryStack,
    position: { top: 20, left: 90 },
    icon: PeakImg,
    line: PeakLineImg,
    linePosition: { top: 22, left: 140 },
    data: [
      { field: 'MVVOASU' },
      { field: 'MaxNOIV' },
      { field: 'MVVOSU' },
      { field: 'MNOIV' },
      { field: 'MaximumIndividualTemperature' },
      { field: 'MITN' },
      { field: 'LVOMT' },
      { field: 'MNOIT' },
    ],
  },
  {
    label: formatMessage({ id: 'device.batteryCluster', defaultMessage: '电池簇' }) + 1,
    productTypeId: DeviceProductTypeEnum.BatteryCluster,
    fixValue: 'BatteryPack-1',
    position: { top: 260, left: 90 },
    icon: EmsImg,
    line: ClusterLineImg,
    linePosition: { top: 22, left: 76 },
    data: [{ field: 'WorkStatus' }, { field: 'alms' }, { field: 'SOC' }],
  },
  {
    label: '',
    productTypeId: DeviceProductTypeEnum.BatteryStack,
    showLabel: false,
    position: { top: 34, left: 738 },
    icon: DoorImg,
    line: DoorLineImg,
    linePosition: { top: 22, left: -160 },
    data: [{ field: 'd14' }],
  },
  {
    label: formatMessage({ id: 'device.airConditioner', defaultMessage: '空调' }),
    productTypeId: DeviceProductTypeEnum.Air,
    position: { top: 102, left: 738 },
    icon: AirImg,
    line: AirLineImg,
    linePosition: { top: 22, left: -187 },
    data: [{ field: 'alms' }],
  },
  {
    label: formatMessage({ id: 'device.batteryCluster', defaultMessage: '电池簇' }) + 2,
    productTypeId: DeviceProductTypeEnum.BatteryCluster,
    fixValue: 'BatteryPack-2',
    position: { top: 248, left: 738 },
    icon: EmsImg,
    line: Cluster2LineImg,
    linePosition: { top: 25, left: -238 },
    data: [{ field: 'WorkStatus' }, { field: 'alms' }, { field: 'SOC' }],
  },
];

const Back: React.FC<EntityType> = (props) => {
  const { deviceData } = props;

  const deviceImg = useMemo(() => {
    const config = parseToObj(deviceData?.productConfig);
    return config?.views?.openDoorRearView || Energy;
  }, [deviceData]);

  return (
    <>
      <Model
        modelStyle={{
          height: '390px',
          backgroundImage: `url(${deviceImg})`,
          backgroundSize: '28%',
        }}
        configs={configs}
        detailProps={{
          labelStyle: {
            maxWidth: '150px',
          },
        }}
        {...props}
      ></Model>
    </>
  );
};

export default Back;
