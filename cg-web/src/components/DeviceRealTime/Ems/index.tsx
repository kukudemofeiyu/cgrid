/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-11 14:44:27
 * @LastEditTime: 2024-03-26 16:21:52
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\Ems\index.tsx
 */
import React, { useCallback, useMemo, useState } from 'react';
import { DeviceRealTimeType } from '../config';
import { Tabs, TabsProps } from 'antd';
import Run from './Run';
import Setting from '@/components/ScreenDialog/EnergyDialog/setting';
import { DeviceTypeEnum } from '@/utils/dictionary';
import { useSubscribe } from '@/hooks';
import styles from './index.less';
import { isEmpty, formatMessage } from '@/utils';

export type EmsType = DeviceRealTimeType & {
  type?: DeviceTypeEnum;
};

const Ems: React.FC<EmsType> = (props) => {
  const { deviceData, showRemoteControl, type } = props;

  const [activeKey, setActiveKey] = useState('detail');
  const realTimeData = useSubscribe(deviceData?.deviceId, true);

  const onChange = useCallback((key) => {
    setActiveKey(key);
  }, []);

  const tabItems = useMemo<TabsProps['items']>(() => {
    return [
      {
        key: 'detail',
        label: formatMessage({ id: 'siteMonitor.operatingData', defaultMessage: '运行数据' }),
      },
      {
        key: 'control',
        label: formatMessage({ id: 'siteMonitor.remoteControl', defaultMessage: '远程控制' }),
      },
    ];
  }, [realTimeData]);

  return (
    <>
      {showRemoteControl && <Tabs className={styles.tabs} items={tabItems} onChange={onChange} />}
      {activeKey == 'detail' ? (
        <Run
          id={deviceData?.deviceId}
          productId={deviceData?.productId}
          realTimeData={realTimeData}
        />
      ) : (
        <Setting
          id={deviceData?.deviceId}
          deviceData={deviceData}
          settingData={realTimeData}
          type={type}
          isLineLabel
          isDeviceChild
        />
      )}
    </>
  );
};

export default Ems;
