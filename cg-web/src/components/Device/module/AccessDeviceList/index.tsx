/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-12-25 14:02:46
 * @LastEditTime: 2024-01-09 16:15:16
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\module\AccessDeviceList\index.tsx
 */

import YTProTable from '@/components/ProTable';
import { useSubscribe } from '@/hooks';
import Label from '@/components/Detail/LineLabel';
import { DeviceDataType, getEmsAssociationDevice } from '@/services/equipment';
import { formatMessage } from '@/utils';
import { MessageEventType } from '@/utils/connection';
import { onlineStatus } from '@/utils/dict';
import { ProColumns, ProField } from '@ant-design/pro-components';
import React, { memo, useCallback, useEffect, useMemo } from 'react';
import { useHistory, useRequest } from 'umi';

export type AccessDeviceListType = {
  deviceId?: string;
};

const AccessDeviceList: React.FC<AccessDeviceListType> = memo((props) => {
  const { deviceId } = props;

  const history = useHistory();

  const {
    data: associationData,
    run,
    loading,
  } = useRequest(getEmsAssociationDevice, {
    manual: true,
  });

  const associationDeviceIds = useMemo(() => {
    return associationData?.map?.((item) => item?.deviceId || '');
  }, [associationData]);

  const associationRealtimeData = useSubscribe(
    associationDeviceIds,
    true,
    MessageEventType.NETWORKSTSTUS,
  );

  const onDeviceClick = useCallback((record) => {
    history.push({
      pathname: '/site-monitor/device-detail',
      search: `?id=${record.deviceId}`,
    });
  }, []);

  useEffect(() => {
    if (deviceId) {
      run({ deviceId });
    }
  }, [deviceId]);

  const columns = useMemo<ProColumns<DeviceDataType>[]>(() => {
    return [
      {
        title: formatMessage({
          id: 'siteMonitor.deviceCommunicationStatus',
          defaultMessage: '设备通信状态',
        }),
        dataIndex: 'connectStatus',
        width: 150,
        ellipsis: true,
        hideInSearch: true,
        render: (_, { deviceId: rowDeviceId, connectStatus }) => {
          return (
            <ProField
              text={associationRealtimeData?.[rowDeviceId ?? '']?.status ?? connectStatus}
              mode="read"
              valueEnum={onlineStatus}
            />
          );
        },
      },
      {
        title: formatMessage({ id: 'common.productType', defaultMessage: '产品类型' }),
        dataIndex: 'productTypeName',
        width: 150,
        ellipsis: true,
        hideInSearch: true,
      },
      {
        title: formatMessage({ id: 'common.model', defaultMessage: '产品型号' }),
        dataIndex: 'model',
        width: 150,
        ellipsis: true,
        hideInSearch: true,
      },
      {
        title: formatMessage({ id: 'common.deviceName', defaultMessage: '设备名称' }),
        dataIndex: 'name',
        width: 150,
        ellipsis: true,
        hideInSearch: true,
        render: (_, record) => {
          return <a onClick={() => onDeviceClick(record)}>{record.name}</a>;
        },
      },
      {
        title: formatMessage({ id: 'common.deviceSn', defaultMessage: '设备序列号' }),
        dataIndex: 'sn',
        width: 150,
        ellipsis: true,
        hideInSearch: true,
      },
    ];
  }, [associationRealtimeData]);

  return (
    <>
      <Label
        title={formatMessage({
          id: 'siteMonitor.accessDeviceList',
          defaultMessage: '接入设备列表',
        })}
        className="mt16"
      />
      <YTProTable<DeviceDataType>
        className="mb16"
        loading={loading}
        search={false}
        options={false}
        columns={columns}
        toolBarRender={false}
        dataSource={associationData}
        scroll={{ y: 'auto' }}
      />
    </>
  );
});

export default AccessDeviceList;
