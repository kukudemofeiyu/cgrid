/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-11 15:10:11
 * @LastEditTime: 2023-09-11 15:10:15
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\Pcs\index.tsx
 */
import React, { useCallback, useMemo, useState } from 'react';
import { DeviceRealTimeType } from '../config';
import useDeviceModel from '../useDeviceModel';
import { useSubscribe } from '@/hooks';
import Detail, { DetailItem, GroupItem } from '@/components/Detail';
import Button from '@/components/CollectionModal/Button';
import { directCurrentItems, exchargeItems, runItems, tempItems, versionItems } from './config';
import { formatMessage } from '@/utils';

const Pcs: React.FC<DeviceRealTimeType> = (props) => {
  const { deviceData } = props;

  const { modelMap } = useDeviceModel({ productId: deviceData?.productId });
  const realTimeData = useSubscribe(deviceData?.deviceId, true);
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

  const detailGroup = useMemo<GroupItem[]>(() => {
    return [
      {
        label: (
          <Detail.Label
            title={formatMessage({ id: 'siteMonitor.runningState', defaultMessage: '运行状态' })}
          />
        ),
        items: runItems,
      },
      {
        label: (
          <Detail.Label
            title={formatMessage({
              id: 'siteMonitor.acSideInformation',
              defaultMessage: '交流侧信息',
            })}
          />
        ),
        items: exchargeItems,
      },
      {
        label: (
          <Detail.Label
            title={formatMessage({
              id: 'siteMonitor.dcSideInformation',
              defaultMessage: '直流侧信息',
            })}
          />
        ),
        items: directCurrentItems,
      },
      {
        label: (
          <Detail.Label
            title={formatMessage({
              id: 'siteMonitor.temperatureInformation',
              defaultMessage: '温度信息',
            })}
          />
        ),
        items: tempItems,
      },
      {
        label: (
          <Detail.Label
            title={formatMessage({
              id: 'siteMonitor.versionInformation',
              defaultMessage: '版本信息',
            })}
          />
        ),
        items: versionItems,
      },
    ];
  }, []);

  return (
    <>
      <Detail.Group
        data={realTimeData}
        items={detailGroup}
        detailProps={{
          extral,
          colon: false,
          labelStyle: { width: 140 },
        }}
      />
    </>
  );
};

export default Pcs;
