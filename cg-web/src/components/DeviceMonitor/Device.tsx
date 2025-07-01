/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-26 19:40:28
 * @LastEditTime: 2023-12-22 11:53:24
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceMonitor\Device.tsx
 */
import React, { useContext } from 'react';
import DeviceContext from '../Device/Context/DeviceContext';
import Page from '@/layouts/Page';
import { OnlineStatusEnum } from '@/utils/dictionary';
import Overview from '../DeviceInfo/Overview';
import DeviceRealTime from '../DeviceRealTime';
import { deviceProductDataMap } from './config';
import styles from './index.less';

const Device: React.FC = () => {
  const { data: deviceData, updateData, loading } = useContext(DeviceContext);

  return (
    <>
      <Page
        className={`${styles.page} ${
          deviceData?.networkStatus === OnlineStatusEnum.Offline ? 'device-offline' : ''
        }`}
        top={
          <Overview
            deviceData={deviceData}
            onChange={updateData}
            loading={loading}
            introImg={
              deviceData?.productId
                ? deviceProductDataMap[deviceData?.productId as any]?.introImg
                : ''
            }
          />
        }
      >
        <DeviceRealTime showRemoteControl={false} deviceData={deviceData} />
      </Page>
    </>
  );
};

export default Device;
