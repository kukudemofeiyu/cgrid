/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-26 19:40:28
 * @LastEditTime: 2023-07-28 14:45:31
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceMonitor\index.tsx
 */

import React from 'react';
import DeviceProvider from '../Device/Context/DeviceProvider';
import Device from './Device';

export type DeviceMonitorType = {
  deviceId?: string;
};

const DeviceMonitor: React.FC<DeviceMonitorType> = (props) => {
  const { deviceId } = props;

  return (
    <>
      <DeviceProvider deviceId={deviceId}>
        <Device />
      </DeviceProvider>
    </>
  );
};

export default DeviceMonitor;
