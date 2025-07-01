/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-11 14:22:35
 * @LastEditTime: 2023-09-11 14:22:35
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\BatterryStack\index.tsx
 */
import React, { useMemo } from 'react';
import { DeviceRealTimeType } from '../config';
import { Tabs, TabsProps } from 'antd';
import Stack from './Stack';
import Cluster from './Cluster';
import { useSubscribe } from '@/hooks';
import { formatMessage } from '@/utils';

const BatterryStack: React.FC<DeviceRealTimeType> = (props) => {
  const { deviceData } = props;

  const realTimeData = useSubscribe(deviceData?.deviceId, true);

  const tabItems = useMemo<TabsProps['items']>(() => {
    return [
      {
        key: '1',
        label: formatMessage({
          id: 'siteMonitor.batteryStackInformation',
          defaultMessage: '电池堆信息',
        }),
        children: <Stack deviceData={deviceData} realTimeData={realTimeData} />,
      },
      {
        key: '2',
        label: formatMessage({
          id: 'siteMonitor.batteryClusterInformation',
          defaultMessage: '电池簇信息',
        }),
        children: <Cluster deviceData={deviceData} />,
      },
    ];
  }, [deviceData, realTimeData]);

  return (
    <>
      <Tabs items={tabItems} />
    </>
  );
};

export default BatterryStack;
