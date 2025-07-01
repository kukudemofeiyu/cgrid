/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-13 23:37:01
 * @LastEditTime: 2024-01-06 16:03:28
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\BatterryStack\Cluster\index.tsx
 */
import React, { useEffect, useCallback, useState, useMemo } from 'react';
import { Tree, Space, Skeleton } from 'antd';
import { useRequest } from 'umi';
import { getClusterByStack, DeviceDataType } from '@/services/equipment';
import Detail, { DetailItem } from '@/components/Detail';
import Label from '@/components/Detail/LineLabel';
import { formatMessage, isEmpty } from '@/utils';
import { runItems, statusItems } from './config';
import styles from './index.less';
import Button from '@/components/CollectionModal/Button';
import { useSubscribe } from '@/hooks';
import { MessageEventType } from '@/utils/connection';
import { DeviceTypeEnum } from '@/utils/dictionary';
import BmuTabs from '@/components/Device/module/BmuTabs';

export type ClusterProps = {
  deviceData?: DeviceDataType;
};

const Cluster: React.FC<ClusterProps> = (props) => {
  const { deviceData } = props;

  const [collectionInfo, setCollectionInfo] = useState({
    title: '',
    collection: '',
  });
  const [selectOrg, setSelectOrg] = useState<DeviceDataType>({ deviceId: 0 as any, key: '0' });
  const realTimeData = useSubscribe(selectOrg?.deviceId || '', true);
  const networkData = useSubscribe(selectOrg?.deviceId || '', true, MessageEventType.NETWORKSTSTUS);

  const {
    data: clusterData,
    loading,
    run,
  } = useRequest(getClusterByStack, {
    manual: true,
    formatResult: ({ data }) => {
      const result = [
        { ...deviceData, deviceName: deviceData?.name, children: data || [], selectable: false },
      ];
      setSelectOrg(data[0]);
      return result;
    },
  });

  const selectedKeys = useMemo<string[]>(() => {
    return isEmpty(selectOrg?.deviceId) ? [] : [selectOrg?.deviceId ?? ''];
  }, [selectOrg]);

  const onSelect = useCallback(
    (_, { selected, node }: { selected: boolean; node: DeviceDataType }) => {
      if (selected) {
        setSelectOrg(node);
      }
    },
    [],
  );

  const onClick = useCallback((item: DetailItem) => {
    if (item.field) {
      setCollectionInfo({
        title: item.label as any,
        collection: item.field,
      });
    }
  }, []);

  useEffect(() => {
    if (deviceData?.deviceId) {
      run({ deviceId: deviceData?.deviceId });
    }
  }, [deviceData]);

  const extral = (
    <Button
      title={collectionInfo.title}
      deviceId={deviceData?.deviceId}
      collection={collectionInfo.collection}
      onClick={onClick}
    />
  );

  return (
    <>
      <div className={styles.contain}>
        <div className={styles.tree}>
          {loading ? (
            <Space direction="vertical">
              <Skeleton.Input size="small" active />
              <Skeleton.Input size="small" active />
              <Skeleton.Input size="small" active />
            </Space>
          ) : (
            <Tree
              treeData={clusterData}
              defaultExpandAll={true}
              fieldNames={{
                title: 'deviceName',
                key: 'deviceId',
                children: 'children',
              }}
              selectedKeys={selectedKeys}
              onSelect={onSelect}
            />
          )}
        </div>
        <div className={styles.content}>
          <Label className="mb26" title={selectOrg?.deviceName} showLine={false} />
          <Detail
            items={runItems}
            data={{ ...selectOrg, ...networkData }}
            colon={false}
            labelStyle={{ width: 170 }}
          />
          {deviceData?.productId != DeviceTypeEnum.LiquidEnergyBatteryStack ? (
            <>
              <Label
                title={formatMessage({
                  id: 'siteMonitor.statusInformation',
                  defaultMessage: '状态信息',
                })}
                className="mt16"
              />
              <Detail
                items={statusItems}
                data={realTimeData}
                extral={extral}
                colon={false}
                labelStyle={{ width: 170 }}
              />
            </>
          ) : (
            <></>
          )}
          <BmuTabs isStackChild={false} clusterDeviceId={selectOrg?.deviceId} />
        </div>
      </div>
    </>
  );
};

export default Cluster;
