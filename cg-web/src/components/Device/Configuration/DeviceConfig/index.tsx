/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-01 08:56:38
 * @LastEditTime: 2023-09-01 13:46:55
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceDetail\Configuration\Device\index.tsx
 */
import React, { useEffect, useMemo, useCallback } from 'react';
import { useRequest } from 'umi';
import { configTypeItemMap, ConfigTypeEnum } from './config';
import { DeviceDataType, getAssociateDevice } from '@/services/equipment';
import Detail, { GroupItem } from '@/components/Detail';
import { formatMessage, isEmpty } from '@/utils';
import { Button } from 'antd';
import { useBoolean } from 'ahooks';
import ConfigForm from './ConfigForm';

export type DeviceProps = {
  deviceData: DeviceDataType;
};

const DeviceConfig: React.FC<DeviceProps> = (props) => {
  const { deviceData } = props;

  const { data: associateDevice, run } = useRequest(getAssociateDevice, {
    manual: true,
  });

  const runGetAssociateDevice = useCallback(() => {
    if (deviceData?.deviceId && !isEmpty(deviceData?.productConfigType)) {
      run({ deviceId: deviceData.deviceId });
    }
  }, [deviceData]);

  const configData = useMemo(() => {
    const value = associateDevice?.map?.((item) => item.name)?.join?.('，');
    const result: any = {};
    switch (deviceData?.productConfigType) {
      case ConfigTypeEnum.Device:
        result.associateDevices = value;
        break;
      case ConfigTypeEnum.ChangeStack:
        result.associateStack = value;
        break;
      default:
    }
    return result;
  }, [associateDevice, deviceData]);

  const configItems = useMemo<GroupItem[]>(() => {
    const result: GroupItem[] = [];
    if (!isEmpty(deviceData?.productConfigType)) {
      const field = configTypeItemMap.get(deviceData?.productConfigType as any);
      result.push({
        label: (
          <Detail.Label
            title={formatMessage({
              id: 'common.configurationInformation',
              defaultMessage: '配置信息',
            })}
          >
            <ConfigForm deviceData={deviceData} onSuccess={runGetAssociateDevice} />
          </Detail.Label>
        ),
        items: field ? [field] : [],
      });
    }
    return result;
  }, [deviceData]);

  useEffect(() => {
    runGetAssociateDevice();
  }, [deviceData]);

  return (
    <>
      {!isEmpty(deviceData?.productConfigType) ? (
        <Detail.Group data={configData} items={configItems} />
      ) : (
        <></>
      )}
    </>
  );
};

export default DeviceConfig;
