/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-12 13:53:34
 * @LastEditTime: 2024-03-01 14:55:22
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\Cabinet\index.tsx
 */
import React, { Suspense, lazy, useEffect, useState } from 'react';
import { Spin } from 'antd';
import { ComProps } from '../type';
import { EnergySourceEnum } from '..';
import { energyProductIdMap } from './helper';
import { DeviceTypeEnum } from '@/utils/dictionary';

export type CabinetProps = ComProps & {
  showLabel?: boolean;
  source?: EnergySourceEnum;
};

const DeviceRealTime: React.FC<CabinetProps> = (props) => {
  const { deviceData } = props;

  const [Component, setComponent] = useState<React.FC<CabinetProps>>();

  useEffect(() => {
    if (deviceData?.productId) {
      const result =
        energyProductIdMap.get(deviceData?.productId) ||
        energyProductIdMap.get(DeviceTypeEnum.Energy);
      setComponent(lazy(() => import('./Entity/' + result)));
    } else {
      setComponent(lazy(() => import('./Entity/' + energyProductIdMap.get(DeviceTypeEnum.Energy))));
    }
  }, [deviceData?.productId]);

  return (
    <>
      {Component && (
        <Suspense
          fallback={
            <div className="tx-center">
              <Spin />
            </div>
          }
        >
          <Component {...props} />
        </Suspense>
      )}
    </>
  );
};

export default DeviceRealTime;
