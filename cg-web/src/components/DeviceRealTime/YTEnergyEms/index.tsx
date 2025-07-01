/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-11 14:44:27
 * @LastEditTime: 2023-12-20 15:27:40
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\YTEnergyEms\index.tsx
 */
import React, { useMemo } from 'react';
import { DeviceRealTimeType } from '../config';
import { Tabs, TabsProps } from 'antd';
import Run from './Run';
import { DeviceTypeEnum } from '@/utils/dictionary';
import { useAuthority, useSubscribe } from '@/hooks';
import styles from './index.less';
import Setting from './Control';
import { formatMessage } from '@/utils';

export type EmsType = DeviceRealTimeType & {
  type?: DeviceTypeEnum;
};

const YTEnergyEms: React.FC<EmsType> = (props) => {
  const { deviceData, showRemoteControl, type } = props;

  const { authorityMap } = useAuthority([
    'iot:device:remoteControl',
    'iot:device:remoteControl:systemStatusControl',
  ]);
  const realTimeData = useSubscribe(deviceData?.deviceId, true);

  const tabItems = useMemo<TabsProps['items']>(() => {
    return deviceData?.masterSlaveMode == 1 || !authorityMap.get('iot:device:remoteControl')
      ? []
      : [
          {
            key: '1',
            label: formatMessage({ id: 'siteMonitor.operatingData', defaultMessage: '运行数据' }),
            children: (
              <Run
                id={deviceData?.deviceId}
                productId={deviceData?.productId}
                realTimeData={realTimeData}
              />
            ),
          },
          {
            key: '2',
            label: formatMessage({ id: 'siteMonitor.remoteControl', defaultMessage: '远程控制' }),
            children: authorityMap.get('iot:device:remoteControl:systemStatusControl') ? (
              <Setting
                id={deviceData?.deviceId}
                deviceData={deviceData}
                settingData={realTimeData}
                type={type}
                isLineLabel
                isDeviceChild
              />
            ) : (
              <></>
            ),
          },
        ];
  }, [realTimeData, deviceData, authorityMap]);

  return (
    <>
      {deviceData?.masterSlaveMode == 1 ||
      !authorityMap.get('iot:device:remoteControl') ||
      !showRemoteControl ? (
        <Run
          id={deviceData?.deviceId}
          productId={deviceData?.productId}
          realTimeData={realTimeData}
        />
      ) : (
        <Tabs className={styles.tabs} items={tabItems} />
      )}
    </>
  );
};

export default YTEnergyEms;
