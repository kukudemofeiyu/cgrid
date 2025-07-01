/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-12-22 11:29:52
 * @LastEditTime: 2024-12-25 17:13:39
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/Device/Context/DeviceProvider.tsx
 */
import {
  DeviceDataType,
  editSetting,
  getDeviceInfo,
  getFireDeviceInfo,
} from '@/services/equipment';
import React, { memo, useCallback, useEffect, useMemo } from 'react';
import { useRequest } from 'umi';
import DeviceContext, { RefreshRequestParams } from './DeviceContext';
import { useSubscribe } from '@/hooks';
import { MessageEventType } from '@/utils/connection';
import { merge } from 'lodash';
import { flatTree } from './helper';

export type DeviceProviderType = {
  deviceId?: string;
  onChange?: () => void;
  deviceTreeData?: DeviceDataType[];
  onSelect?: (data: DeviceDataType) => void;
  fire?: string;
};

const DeviceProvider: React.FC<DeviceProviderType> = memo((props) => {
  const { deviceId, deviceTreeData, onChange, onSelect, children, fire } = props;

  const realTimeNetwork = useSubscribe(deviceId, true, MessageEventType.NETWORKSTSTUS);
  const realTimeAlarmData = useSubscribe(deviceId, true, MessageEventType.DeviceAlarm);

  const {
    data: deviceData,
    loading,
    run: runGetDevice,
  } = useRequest(getDeviceInfo, {
    manual: true,
  });
  const {
    data: fireDeviceData,
    loading: fireLoading,
    run: runGetFireDevice,
  } = useRequest(getFireDeviceInfo, {
    manual: true,
  });
  const realTImeDeviceData = useMemo(() => {
    return merge(
      {},
      deviceData || fireDeviceData,
      {
        networkStatus: deviceData?.status || fireDeviceData?.status,
      },
      realTimeNetwork,
    );
  }, [deviceData, fireDeviceData, realTimeNetwork]);

  const updateData = useCallback(() => {
    onChange?.();
    if (fire === '1') {
      runGetFireDevice({ deviceId });
    } else {
      runGetDevice({ deviceId });
    }
  }, [deviceId, onChange, fire]);

  const onTreeSelect = useCallback(
    (value: string) => {
      const treeData = flatTree(deviceTreeData);
      const result = treeData.find((item) => item.deviceId == value);
      if (result) {
        onSelect?.(result);
      }
    },
    [deviceTreeData, onSelect],
  );

  const refreshDataByRequest = useCallback((params: RefreshRequestParams, showMessage) => {
    return editSetting(
      {
        serviceId: 'queryParam',
        createBy: 1,
        ...params,
      },
      showMessage,
    );
  }, []);

  useEffect(() => {
    if (deviceId) {
      if (fire === '1') {
        runGetFireDevice({ deviceId });
      } else {
        runGetDevice({ deviceId });
      }
    }
  }, [deviceId, fire]);

  return (
    <>
      <DeviceContext.Provider
        value={{
          data: realTImeDeviceData,
          loading: loading || fireLoading,
          updateData: updateData,
          onSelect: onTreeSelect,
          refreshDataByRequest: refreshDataByRequest,
          treeData: deviceTreeData,
        }}
      >
        {children}
      </DeviceContext.Provider>
    </>
  );
});

export default DeviceProvider;
