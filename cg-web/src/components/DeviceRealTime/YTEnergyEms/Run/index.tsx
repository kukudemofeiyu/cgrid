/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-13 23:36:42
 * @LastEditTime: 2023-12-07 15:36:34
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\YTEnergyEms\Run\index.tsx
 */
import React, { useCallback, useMemo, useState } from 'react';
import Detail, { DetailItem, GroupItem } from '@/components/Detail';
import { emsOperationItems } from './config';
import Button from '@/components/CollectionModal/Button';
import useDeviceModel from '../../useDeviceModel';
import { formatMessage } from '@/utils';
import { DeviceTypeEnum } from '@/utils/dictionary';
import AccessDeviceList from '@/components/Device/module/AccessDeviceList';

export type StackProps = {
  id?: string;
  productId?: DeviceTypeEnum;
  realTimeData?: Record<string, any>;
};

const Stack: React.FC<StackProps> = (props) => {
  const { realTimeData, id, productId } = props;

  const { modelMap } = useDeviceModel({ productId });
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
      deviceId={id}
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
            title={formatMessage({
              id: 'siteMonitor.operationalInformation',
              defaultMessage: '运行信息',
            })}
          />
        ),
        items: emsOperationItems,
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
      <AccessDeviceList deviceId={id} />
    </>
  );
};

export default Stack;
