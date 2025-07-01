/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-01-06 11:15:56
 * @LastEditTime: 2024-08-09 14:21:17
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\module\ParallelMachine\index.tsx
 */

import React, { memo, useCallback, useContext, useEffect, useMemo } from 'react';
import YTProTable from '@/components/ProTable';
import { useDeviceModel, useSubscribe } from '@/hooks';
import Label from '@/components/Detail/LineLabel';
import { DeviceDataType, getParallelDevice } from '@/services/equipment';
import { formatMessage, formatModelValue, getPlaceholder, isEmpty } from '@/utils';
import { MessageEventType } from '@/utils/connection';
import { onlineStatus, masterSlaveEnum } from '@/utils/dict';
import { ProColumns, ProField } from '@ant-design/pro-components';
import { useHistory, useRequest } from 'umi';
import DeviceContext from '@/components/Device/Context/DeviceContext';

export type AccessDeviceListType = {
  deviceId?: string;
};

type ParallelDeviceType = DeviceDataType & {
  emsId?: string;
  bmsId?: string;
  systemWorkModelId?: string;
  systemWorkStatusId?: string;
  socId?: string;
  isSelf?: boolean;
};

const AccessDeviceList: React.FC<AccessDeviceListType> = memo((props) => {
  const { deviceId } = props;

  const { data: deviceData } = useContext(DeviceContext);
  const history = useHistory();
  const { modelMap } = useDeviceModel({
    productId: deviceData?.productId,
    isGroup: true,
  });
  const {
    data: associationDeviceDataList,
    run,
    loading,
  } = useRequest(getParallelDevice, {
    manual: true,
  });

  const associationDeviceIds = useMemo(() => {
    return associationDeviceDataList?.map?.((item) => item?.deviceId || '');
  }, [associationDeviceDataList]);
  const associationNetWorkRealtimeData = useSubscribe(
    associationDeviceIds,
    true,
    MessageEventType.NETWORKSTSTUS,
  );

  const associationChildDeviceIds = useMemo(() => {
    const result: string[] = [];
    associationDeviceDataList?.forEach?.((item: ParallelDeviceType) => {
      item?.emsId && result.push(item?.emsId || '');
      item?.bmsId && result.push(item?.bmsId || '');
    });
    return result;
  }, [associationDeviceDataList]);
  const associationRealtimeData = useSubscribe(associationChildDeviceIds, true);

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

  const columns = useMemo<ProColumns<ParallelDeviceType>[]>(() => {
    return [
      {
        title: formatMessage({ id: 'device.parallelMachineNum', defaultMessage: '并机编号' }),
        valueType: 'index',
        width: 100,
        ellipsis: true,
      },
      {
        title: formatMessage({ id: 'common.deviceName', defaultMessage: '设备名称' }),
        dataIndex: 'name',
        width: 150,
        ellipsis: true,
        render: (_, record) => {
          return record?.isSelf ? (
            `${record.name}(${formatMessage({ id: 'device.self', defaultMessage: '本机' })})`
          ) : (
            <a onClick={() => onDeviceClick(record)}>{record.name}</a>
          );
        },
      },
      {
        title: formatMessage({
          id: 'device.masterSlaveIdentification',
          defaultMessage: '主从标识',
        }),
        dataIndex: 'masterSlaveMode',
        width: 100,
        ellipsis: true,
        valueEnum: masterSlaveEnum,
      },
      {
        title: formatMessage({
          id: 'siteMonitor.deviceCommunicationStatus',
          defaultMessage: '通信状态',
        }),
        dataIndex: 'connectStatus',
        width: 150,
        ellipsis: true,
        render: (_, { deviceId: rowDeviceId, connectStatus }) => {
          return (
            <ProField
              text={associationNetWorkRealtimeData?.[rowDeviceId ?? '']?.status ?? connectStatus}
              mode="read"
              valueEnum={onlineStatus}
            />
          );
        },
      },
      {
        title: formatMessage({ id: 'common.ipAddress', defaultMessage: 'IP地址' }),
        dataIndex: 'ipAddress',
        width: 150,
        ellipsis: true,
      },
      {
        title: formatMessage({ id: 'common.systemWorkMode', defaultMessage: '系统工作模式' }),
        dataIndex: 'systemWorkModelId',
        width: 150,
        ellipsis: true,
        render: (_, { emsId, systemWorkModelId }: any) => {
          return isEmpty(systemWorkModelId)
            ? getPlaceholder(null, '-')
            : formatModelValue(
              associationRealtimeData?.[emsId]?.[systemWorkModelId],
              modelMap?.[systemWorkModelId],
            );
        },
      },
      {
        title: formatMessage({ id: 'common.systemWorkStatus', defaultMessage: '系统工作状态' }),
        dataIndex: 'systemWorkStatusId',
        width: 150,
        ellipsis: true,
        render: (_, { emsId, systemWorkStatusId }: any) => {
          return isEmpty(systemWorkStatusId)
            ? getPlaceholder(null, '-')
            : formatModelValue(
              associationRealtimeData?.[emsId]?.[systemWorkStatusId],
              modelMap?.[systemWorkStatusId],
            );
        },
      },
      {
        title: 'SOC(%)',
        dataIndex: 'socId',
        width: 100,
        ellipsis: true,
        render: (_, { bmsId, socId }: any) => {
          return isEmpty(socId)
            ? getPlaceholder(null, '-')
            : associationRealtimeData?.[bmsId]?.[socId];
        },
      },
    ];
  }, [deviceId, associationNetWorkRealtimeData, modelMap, associationRealtimeData]);

  return (
    <>
      <Label
        title={formatMessage({
          id: 'device.parallelMachineInfomation',
          defaultMessage: '并机信息',
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
        dataSource={associationDeviceDataList}
        scroll={{ y: 'auto' }}
        pagination={false}
      />
    </>
  );
});

export default AccessDeviceList;
