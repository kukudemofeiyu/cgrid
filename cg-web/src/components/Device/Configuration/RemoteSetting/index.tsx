/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-12 09:16:39
 * @LastEditTime: 2023-09-12 09:16:39
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\RemoteSetting\index.tsx
 */
import React, { Suspense, lazy, useEffect, useState } from 'react';
import { RemoteSettingProductType, RemoteSettingType } from './typing';
import { remoteSettingProductMap } from './helper';
import { DeviceTypeEnum } from '@/utils/dictionary';
import { Spin } from 'antd';

const RemoteSetting: React.FC<RemoteSettingType> = (props) => {
  const { deviceData } = props;

  const [Component, setComponent] = useState<React.FC<Record<string, any>>>();
  const [componentProps, setComponentProps] = useState<Record<string, any>>({});

  useEffect(() => {
    const result: RemoteSettingProductType | undefined = remoteSettingProductMap.get(
      deviceData?.productId as DeviceTypeEnum,
    );
    if (result) {
      setComponent(lazy(() => import('./' + result.component)));
      setComponentProps(result.props || {});
    }
  }, [deviceData]);

  return (
    <>
      {Component ? (
        <Suspense
          fallback={
            <div className="tx-center">
              <Spin />
            </div>
          }
        >
          <Component
            deviceId={deviceData?.deviceId}
            deviceData={deviceData}
            productId={deviceData?.productId}
            {...componentProps}
          />
        </Suspense>
      ) : (
        <></>
      )}
    </>
  );
};

export default RemoteSetting;
