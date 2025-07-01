/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-11 14:27:32
 * @LastEditTime: 2023-09-11 14:27:32
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\BoxSubstation\index.tsx
 */
import React, { useCallback, useState } from 'react';
import RealTime from '@/components/Meter/RealTime';
import { DeviceRealTimeType } from '../config';
import Button from '@/components/CollectionModal/Button';
import Detail, { DetailItem } from '@/components/Detail';
import useDeviceModel from '../useDeviceModel';
import { formatMessage } from '@/utils';

const BoxSubstation: React.FC<DeviceRealTimeType> = (props) => {
  const { deviceData, loading } = props;

  const { modelMap } = useDeviceModel({ productId: deviceData?.productId });
  const [collectionInfo, setCollectionInfo] = useState({
    title: '',
    collection: '',
  });

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
        label={
          <Detail.Label
            title={formatMessage({
              id: 'siteMonitor.operationalInformation',
              defaultMessage: '运行信息',
            })}
          />
        }
        detailProps={{
          extral,
          colon: false,
          valueStyle: { flex: 1, textAlign: 'right' },
        }}
      />
    </>
  );
};

export default BoxSubstation;
