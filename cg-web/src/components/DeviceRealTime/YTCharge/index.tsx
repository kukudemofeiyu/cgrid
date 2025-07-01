/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-11 15:25:00
 * @LastEditTime: 2023-12-01 10:04:38
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\YTCharge\index.tsx
 */
import React, { useCallback, useState } from 'react';
import { DeviceRealTimeType } from '../config';
import { DetailItem } from '@/components/Detail';
import Button from '@/components/CollectionModal/Button';
import RealTime from '@/components/ScreenDialog/YtCharge/RealTime';
import { LabelTypeEnum } from '@/components/ScreenDialog';
import useDeviceModel from '../useDeviceModel';

const YTCharge: React.FC<DeviceRealTimeType> = (props) => {
  const { deviceData, loading } = props;

  const [collectionInfo, setCollectionInfo] = useState({
    deviceId: '',
    title: '',
    collection: '',
  });
  const { modelMap } = useDeviceModel({ productId: deviceData?.productId });

  const onClick = useCallback(
    (item: DetailItem, _, data) => {
      if (item.field) {
        setCollectionInfo({
          deviceId: data?.ids?.[0] ?? deviceData?.deviceId,
          title: item.label as any,
          collection: item.field,
        });
      }
    },
    [deviceData],
  );

  const extral = (
    <Button
      title={collectionInfo.title}
      deviceId={collectionInfo.deviceId}
      collection={collectionInfo.collection}
      model={modelMap?.[collectionInfo.collection]}
      onClick={onClick}
    />
  );

  return (
    <>
      <RealTime
        id={deviceData?.deviceId}
        loading={loading}
        labelType={LabelTypeEnum.LineLabel}
        detailProps={{
          extral,
          colon: false,
          labelStyle: { width: 140 },
        }}
      />
    </>
  );
};

export default YTCharge;
