/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-12 09:39:40
 * @LastEditTime: 2023-09-12 11:38:12
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\RemoteSetting\Ems\index.tsx
 */
import React, { useCallback, useMemo } from 'react';
import { EmsType } from './typing';
import Detail, { GroupItem } from '@/components/Detail';
import { protectItems, systemTimeItems } from './helper';
import { useSubscribe } from '@/hooks';
import { message } from 'antd';
import ProtectForm from './ProtectForm';
import SystemTimeForm from './SystemTimeForm';
import { formatMessage } from '@/utils';

const EMS: React.FC<EmsType> = (props) => {
  const { deviceId, deviceData } = props;

  const realTimeData = useSubscribe(deviceId, true);

  const onSuccess = useCallback(() => {
    message.success(formatMessage({ id: 'device.issueSuccess', defaultMessage: '下发成功' }));
  }, []);

  const groupItems = useMemo<GroupItem[]>(() => {
    return [
      {
        label: (
          <Detail.Label
            title={formatMessage({
              id: 'siteMonitor.batteryProtectionParameterSet',
              defaultMessage: '电池保护参数设置',
            })}
          >
            <ProtectForm
              deviceId={deviceId}
              deviceData={deviceData}
              protectData={realTimeData}
              onSuccess={onSuccess}
            />
          </Detail.Label>
        ),
        items: protectItems,
      },
      {
        label: (
          <Detail.Label
            title={formatMessage({ id: 'siteMonitor.timingSettings', defaultMessage: '校时设置' })}
          >
            <SystemTimeForm
              deviceId={deviceId}
              deviceData={deviceData}
              systemTimeData={realTimeData}
              onSuccess={onSuccess}
            />
          </Detail.Label>
        ),
        items: systemTimeItems,
      },
    ];
  }, [deviceId, realTimeData]);

  return (
    <>
      <Detail.Group items={groupItems} data={realTimeData} />
    </>
  );
};

export default EMS;
