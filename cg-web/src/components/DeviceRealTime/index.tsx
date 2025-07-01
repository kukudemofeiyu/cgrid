/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-11 14:10:26
 * @LastEditTime: 2023-12-22 16:30:51
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\index.tsx
 */
import React, { Suspense, lazy, useEffect, useState } from 'react';
import { DeviceRealTimeMapType, deviceProductTypeMap, deviceRealTimeMap } from './config';
import { Spin } from 'antd';
import { DeviceDataType } from '@/services/equipment';

export type DeviceRealTimeType = {
  deviceData?: DeviceDataType;
  loading?: boolean;
  showRemoteControl?: boolean;
};

const DeviceRealTime: React.FC<DeviceRealTimeType> = (props) => {
  const { deviceData, showRemoteControl = true, ...restProps } = props;

  const [Component, setComponent] = useState<React.FC<DeviceRealTimeType>>();
  const [componentProps, setComponentProps] = useState<Record<string, any>>();

  useEffect(() => {
    if (deviceData?.productId && deviceData?.productTypeId) {
      const result: DeviceRealTimeMapType =
        deviceRealTimeMap?.[deviceData?.productId] ||
        deviceProductTypeMap?.[deviceData?.productTypeId] ||
        deviceRealTimeMap.default;
      setComponent(lazy(() => import('./' + result.component)));
      setComponentProps(result.props || {});
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
          <Component
            deviceData={deviceData}
            showRemoteControl={showRemoteControl}
            {...restProps}
            {...componentProps}
          />
        </Suspense>
      )}
    </>
  );
};

export default DeviceRealTime;
