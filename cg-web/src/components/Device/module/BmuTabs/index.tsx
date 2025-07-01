/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-01-06 15:21:47
 * @LastEditTime: 2025-01-15 14:13:09
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/Device/module/BmuTabs/index.tsx
 */

import React, { memo, useCallback, useContext, useEffect, useState } from 'react';
import Detail from '@/components/Detail';
import { formatMessage } from '@/utils';
import { Radio, RadioChangeEvent } from 'antd';
import CollectionModal from '@/components/CollectionModal';
import DeviceContext from '@/components/Device/Context/DeviceContext';
import { DeviceTypeEnum } from '@/utils/dictionary';
import { useDeviceModel, useSubscribe } from '@/hooks';
import { DeviceDataType, getChildEquipment } from '@/services/equipment';
import { useBoolean } from 'ahooks';
import { LineChartOutlined, TableOutlined } from '@ant-design/icons';
import BmuTabs from './Tab';
import Table from './Table';

type BmuInfoType = {
  isStackChild?: boolean;
  clusterDeviceId?: string;
};

const BmuInfo: React.FC<BmuInfoType> = memo((props) => {
  const { isStackChild = false, clusterDeviceId } = props;

  const { data: deviceData } = useContext(DeviceContext);
  const [tableType, setTableType] = useState(0);
  const [bmuData, setBmuData] = useState<DeviceDataType[]>();
  const [bmuProductId, setBmuProductId] = useState<DeviceTypeEnum>();
  const [openBmuChart, { setFalse, setTrue }] = useBoolean(false);
  const { modelMap } = useDeviceModel({ productId: bmuProductId });
  const [deviceId, setDeviceId] = useState('');
  const [collectionInfo, setCollectionInfo] = useState({
    title: '',
    collection: '',
  });

  const onTypeChange = (e: RadioChangeEvent) => {
    setTableType(e.target.value);
  };

  const onOpenChart = useCallback((id, collection) => {
    setCollectionInfo(collection);
    setDeviceId(id);
    setTrue();
  }, []);

  useEffect(() => {
    if (clusterDeviceId || deviceData?.deviceId) {
      getChildEquipment({ parentId: isStackChild ? clusterDeviceId : deviceData?.deviceId }).then(
        ({ data: childData }) => {
          setBmuData(childData);
          setBmuProductId(childData?.[0]?.productId);
        },
      );
    }
  }, [deviceData, clusterDeviceId]);

  return (
    <>
      <Detail.Label
        title={
          isStackChild
            ? formatMessage({ id: 'siteMonitor.monomerInfo', defaultMessage: '单体信息' })
            : formatMessage({
              id: 'device.batteryModuleIndividualInformation',
              defaultMessage: '电池模块单体信息',
            })
        }
        className="mt16"
      >
        {/* <Radio.Group
          optionType="button"
          value={tableType}
          onChange={onTypeChange}
          buttonStyle="solid"
        >
          <Radio.Button value={0}>
            <LineChartOutlined />
          </Radio.Button>
          <Radio.Button value={1}>
            <TableOutlined />
          </Radio.Button>
        </Radio.Group> */}
      </Detail.Label>
      <Table bmuData={bmuData} onOpenChart={onOpenChart} modelMap={modelMap} />
      <CollectionModal
        title={collectionInfo.title}
        open={openBmuChart}
        onCancel={setFalse}
        deviceId={deviceId}
        collection={collectionInfo.collection}
        model={modelMap?.[collectionInfo.collection]}
      />
    </>
  );
});

export default BmuInfo;
