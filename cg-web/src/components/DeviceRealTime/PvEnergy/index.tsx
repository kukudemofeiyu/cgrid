/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-11-16 11:14:24
 * @LastEditTime: 2024-01-26 10:47:55
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\PvEnergy\index.tsx
 */

import React from 'react';
import { DeviceRealTimeType } from '../config';
import PvEnergyMachine from '@/components/Device/PvEnergyMachine';

const PvEnergy: React.FC<Omit<DeviceRealTimeType, 'id' | 'productId'>> = (props) => {
  const { deviceData } = props;

  return (
    <>
      <PvEnergyMachine deviceData={deviceData} />
    </>
  );
};

export default PvEnergy;
