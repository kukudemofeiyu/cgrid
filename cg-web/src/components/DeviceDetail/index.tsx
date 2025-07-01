/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-20 16:17:35
 * @LastEditTime: 2025-01-16 14:07:58
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/DeviceDetail/index.tsx
 */
import React, { useCallback, useEffect, useMemo, useState } from 'react';
import { Skeleton, Space, Tree } from 'antd';
import { useRequest } from 'umi';
import { DeviceDataType, getWholeDeviceTree } from '@/services/equipment';
import styles from './index.less';
import { getPropsFromTree, isEmpty } from '@/utils';
import { TreeNode, netWorkStatusEnum, networkStatusShows } from './config';
import { productTypeIconMap } from '@/utils/IconUtil';
import DeviceProvider from '../Device/Context/DeviceProvider';
import Device from './Device';
import { useSubscribe } from '@/hooks';
import { MessageEventType } from '@/utils/connection';
import { DeviceProductTypeEnum } from '@/utils/dictionary';
import { ProField } from '@ant-design/pro-components';
import { useBoolean } from 'ahooks';
import { LeftOutlined, RightOutlined } from '@ant-design/icons';

const dealTreeData = (data: TreeNode[], realTimeData: Record<string, any>) => {
  const result: TreeNode[] = [];
  data?.forEach?.((item) => {
    const networkStatus = realTimeData?.[item?.id ?? '']?.networkStatus;
    const node: TreeNode = {
      key: item?.id + '',
      deviceId: item?.id,
      deviceName: item?.name,
      productTypeName: item?.productTypeName,
      title: (
        <>
          <span title={item?.name}>{item?.name}</span>
          {networkStatusShows.includes(networkStatus) && (
            <span className={styles.network}>
              <ProField mode="read" text={networkStatus} valueEnum={netWorkStatusEnum} />
            </span>
          )}
        </>
      ),
      networkStatus: networkStatus,
      productId: item?.productId,
      productTypeId: item?.productTypeId,
    };
    node.icon =
      productTypeIconMap.get(item?.productTypeId ?? DeviceProductTypeEnum.Default) ||
      productTypeIconMap.get(DeviceProductTypeEnum.Default);
    if (item?.children && item?.children?.length) {
      node.children = dealTreeData(item.children as any, realTimeData);
    }
    result.push(node);
  });
  return result;
};

export type DeviceDetailProps = {
  id: string;
  hideTree?: string;
  fire?: string;
};

const returnChild = (item: any) => {
  if (item.children && item.children.length) {
    item.children.forEach((child: any) => {
      child.name = child.productTypeName;
      item.children?.map((res: any) => returnChild(res));
    });
  }
  return item;
};

const DeviceDetail: React.FC<DeviceDetailProps> = (props) => {
  const { id, hideTree, fire } = props;

  const [isOpen, { toggle, setFalse }] = useBoolean(!hideTree);
  const [selectOrg, setSelectOrg] = useState<DeviceDataType>({
    deviceId: parseInt(id) as any,
    key: id,
  });
  const {
    data: treeData,
    loading,
    run: runGetDeviceTree,
  } = useRequest(getWholeDeviceTree, {
    manual: true,
    formatResult: (res) => {
      const getData: DeviceDataType = res.data;
      if (
        getData?.productTypeId &&
        [
          DeviceProductTypeEnum.Energy,
          DeviceProductTypeEnum.LocalEms,
          DeviceProductTypeEnum.PvEnergy,
          DeviceProductTypeEnum.SmallEnergy,
          DeviceProductTypeEnum.WindPvFirewoodEnergy,
          DeviceProductTypeEnum.BEnergy,
        ].includes(getData?.productTypeId)
      )
        returnChild(getData);
      return getData ? [getData] : [];
    },
  });
  const deviceIds = useMemo(() => {
    return getPropsFromTree(treeData || []);
  }, [treeData]);
  const realTimeData = useSubscribe(deviceIds, true, MessageEventType.NETWORKSTSTUS);

  const mergedTreeData = useMemo(() => {
    return dealTreeData(treeData as any, realTimeData);
  }, [treeData, realTimeData]);

  const selectedKeys = useMemo<string[]>(() => {
    return isEmpty(selectOrg?.deviceId) ? [] : [selectOrg?.deviceId as string];
  }, [selectOrg]);

  const onSelect = useCallback(
    (_, { selected, node }: { selected: boolean; node: DeviceDataType }) => {
      if (selected) {
        setSelectOrg(node);
      }
    },
    [],
  );

  const onProviderSelect = useCallback((data: DeviceDataType) => {
    if (data) {
      setSelectOrg(data);
    }
  }, []);

  const onChange = useCallback(() => {
    runGetDeviceTree({
      deviceId: id,
      component: 0,
      containTopParentDevice: 1,
    });
  }, [id]);

  useEffect(() => {
    runGetDeviceTree({
      deviceId: id,
      component: 0,
      containTopParentDevice: 1,
    }).then((res) => {
      if (!res?.[0]?.children?.length) {
        setFalse();
      }
    });
  }, [id]);

  return (
    <>
      <div className={`${styles.contain} ${isOpen ? styles.open : ''}`}>
        <div className={styles.tree}>
          {loading ? (
            <Space direction="vertical">
              <Skeleton.Input size="small" active />
              <Skeleton.Input size="small" active />
              <Skeleton.Input size="small" active />
            </Space>
          ) : (
            <Tree<TreeNode>
              treeData={mergedTreeData}
              defaultExpandAll={true}
              fieldNames={{
                key: 'deviceId',
                children: 'children',
              }}
              selectedKeys={selectedKeys}
              onSelect={onSelect}
              showIcon
            />
          )}
        </div>
        {!hideTree && (
          <div className={styles.switchWrap} onClick={toggle}>
            {isOpen ? <LeftOutlined /> : <RightOutlined />}
          </div>
        )}
        <div className={styles.content}>
          <DeviceProvider
            deviceId={selectOrg.deviceId}
            deviceTreeData={mergedTreeData}
            onChange={onChange}
            onSelect={onProviderSelect}
            fire={fire}
          >
            <Device deviceTreeData={mergedTreeData} />
          </DeviceProvider>
        </div>
      </div>
    </>
  );
};

export default DeviceDetail;
