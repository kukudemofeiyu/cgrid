/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-01 14:56:51
 * @LastEditTime: 2024-03-26 18:58:54
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\Community\index.tsx
 */

import React, { Suspense, lazy, useEffect, useState } from 'react';
import { Button } from 'antd';
import { useBoolean } from 'ahooks';
import { DeviceDataType } from '@/services/equipment';
import { OnlineStatusEnum } from '@/utils/dictionary';
import { formatMessage } from '@/utils';

export enum CommunityTypeEnum {
  Account = 1,
  Meter,
  StationGun,
  Station,
  BWatt,
  SelfEnergyMeter = 7,
  React100W,
}

export const communityTypeMap = new Map([
  [CommunityTypeEnum.Account, 'Account'],
  [CommunityTypeEnum.Meter, 'Meter'],
  [CommunityTypeEnum.StationGun, 'StationGun'],
  [CommunityTypeEnum.Station, 'Station'],
  [CommunityTypeEnum.BWatt, 'BWatt'],
  [CommunityTypeEnum.React100W, 'BWatt'],
  [CommunityTypeEnum.SelfEnergyMeter, 'SelfEnergyMeter'],
]);

export type CommunityProps = {
  id: string;
  deviceData?: DeviceDataType;
  siteId?: string;
  model?: string;
  open: boolean;
  onOpenChange: (value: boolean) => void;
  userLabel?: string;
  passwordLabel?: string;
  type?: CommunityTypeEnum;
  productConfigType?: number;
  onSuccess?: () => void;
};

const Community: React.FC<Omit<CommunityProps, 'open' | 'onOpenChange'>> = (props) => {
  const { type, ...restProps } = props;

  const [open, { set, setTrue }] = useBoolean(false);
  const [Component, setComponent] = useState<React.FC<CommunityProps>>();

  useEffect(() => {
    if (type) {
      const result = communityTypeMap.get(type);
      if (result) {
        setComponent(lazy(() => import('./' + result)));
      }
    }
  }, [type]);

  return (
    <>
      {type ? (
        <Button type="primary" onClick={setTrue}>
          {formatMessage({ id: 'common.modify', defaultMessage: '修改' })}
        </Button>
      ) : (
        <></>
      )}
      <Suspense fallback={<></>}>
        {Component && <Component type={type} open={open} onOpenChange={set} {...restProps} />}
      </Suspense>
    </>
  );
};

export default Community;
