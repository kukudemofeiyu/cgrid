/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-11 15:04:41
 * @LastEditTime: 2023-09-11 15:04:41
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\HwChargeChild\index.tsx
 */
import React, { useCallback, useMemo, useState } from 'react';
import { DeviceRealTimeType } from '../config';
import { OnlineStatusEnum } from '@/utils/dict';
import { DetailItem } from '@/components/Detail';
import RealTime from '@/components/ScreenDialog/HwChargeChild/RealTime';
import Button from '@/components/CollectionModal/Button';
import { LabelTypeEnum } from '@/components/ScreenDialog';
import useDeviceModel from '../useDeviceModel';
import { isEmpty } from '@/utils';

const HwChargeChild: React.FC<DeviceRealTimeType> = (props) => {
  const { deviceData, loading } = props;

  const [collectionInfo, setCollectionInfo] = useState({
    title: '',
    collection: '',
  });
  const { modelMap } = useDeviceModel({ productId: deviceData?.productId });

  const onClick = useCallback((item: DetailItem) => {
    if (item.field) {
      setCollectionInfo({
        title: item.label as any,
        collection: item.field,
      });
    }
  }, []);

  const extral = (
    <Button
      title={collectionInfo.title}
      deviceId={deviceData?.deviceId}
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

export default HwChargeChild;
