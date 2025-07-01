/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-11 14:53:12
 * @LastEditTime: 2023-09-11 14:53:12
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\EnergyCabinet\index.tsx
 */
import React, { useCallback, useMemo, useState } from 'react';
import { DeviceRealTimeType } from '../config';
import RealTime from '@/components/Meter/RealTime';
import Detail, { DetailItem } from '@/components/Detail';
import Button from '@/components/CollectionModal/Button';
import { OnlineStatusEnum } from '@/utils/dict';
import useDeviceModel from '../useDeviceModel';
import { isEmpty, formatMessage } from '@/utils';

const EnergyCabinet: React.FC<DeviceRealTimeType> = (props) => {
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

export default EnergyCabinet;
