/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-08-31 16:22:51
 * @LastEditTime: 2024-01-11 10:41:10
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\index.tsx
 */
import React, { useCallback, useRef, useState } from 'react';
import Community from './Community';
import PointTableConfig from './PointTableConfig';

import DeviceConfig from './DeviceConfig';
import { isEmpty } from '@/utils';
import RemoteSetting from './RemoteSetting';
import {
  DeviceMasterMode,
  DeviceServicePageEnum,
  DeviceTypeEnum,
  OnlineStatusEnum,
} from '@/utils/dictionary';
import RemoteUpgrade from '../module/RemoteUpgrade';
import SelfEmsIndex from './SelfEmsIndex';
import Control from '../Control';
import { useDeviceModel, useSubscribe } from '@/hooks';
import { Empty } from 'antd';

export type ConfigProps = {
  deviceData: any;
  productId?: DeviceTypeEnum;
  deviceId: string;
};

const remoteSettingType = [
  DeviceTypeEnum.Ems,
  DeviceTypeEnum.BWattAir,
  DeviceTypeEnum.YTEnergyBatteryStack,
  DeviceTypeEnum.LiquidEnergyBatteryStack,
];

const oldRemoteUpgradeProductIds = [
  DeviceTypeEnum.ExchangePowerCabinet,
  DeviceTypeEnum.Ems,
  DeviceTypeEnum.BWattEms,
];

const ConfigurationTab: React.FC<ConfigProps> = (props) => {
  const { deviceData, productId, deviceId } = props;

  const containRef = useRef<HTMLDivElement>(null);
  const [emptyNum, setEmptyNum] = useState(0);
  const { serviceGruop } = useDeviceModel({
    deviceId,
    isGroup: true,
    page: DeviceServicePageEnum.Config,
  });
  const realTimeData = useSubscribe(deviceId, true);

  const onLoadChange = useCallback(() => {
    setTimeout(() => {
      setEmptyNum(containRef?.current?.innerText?.trim?.() ? Math.random() + 1 : 0);
    }, 300);
  }, []);

  return (
    <>
      <div
        className={deviceData?.networkStatus === OnlineStatusEnum.Offline ? 'device-offline' : ''}
      >
        {productId == (DeviceTypeEnum.YTEnergyEms as any) ? (
          <SelfEmsIndex deviceData={deviceData} deviceId={deviceId} realTimeData={realTimeData} />
        ) : (
          <>
            <div ref={containRef} className="px24">
              {deviceData?.masterSlaveMode != DeviceMasterMode.Slave && (
                <>
                  {deviceData?.paramConfigType ? (
                    <>
                      <Community deviceData={deviceData} />
                      <PointTableConfig deviceData={deviceData} />
                    </>
                  ) : (
                    <></>
                  )}
                  {!isEmpty(deviceData?.productConfigType) ? (
                    <DeviceConfig deviceData={deviceData} />
                  ) : (
                    <></>
                  )}
                  {remoteSettingType.includes(deviceData?.productId) ? (
                    <RemoteSetting deviceData={deviceData} />
                  ) : (
                    <></>
                  )}
                </>
              )}
              <Control
                deviceId={deviceData?.deviceId}
                deviceData={deviceData}
                groupData={serviceGruop}
                realTimeData={realTimeData}
                onLoadChange={onLoadChange}
              />
              {oldRemoteUpgradeProductIds.includes(deviceData?.productId) && (
                <RemoteUpgrade deviceId={deviceData?.deviceId} />
              )}
            </div>
            {(!containRef?.current?.innerText || !emptyNum) && <Empty className="mt20" />}
          </>
        )}
      </div>
    </>
  );
};

export default ConfigurationTab;
