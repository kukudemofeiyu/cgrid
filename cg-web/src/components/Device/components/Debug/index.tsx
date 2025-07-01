/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-21 14:25:32
 * @LastEditTime: 2024-07-26 10:51:08
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\components\Debug\index.tsx
 */

import React, { memo } from 'react';
import { Tabs } from 'antd';
import type { TabsProps } from 'antd/es/tabs';
import { formatMessage } from '@/utils';
import Adjust from '../../Adjust';
import type { DeviceDataType } from '@/services/equipment';
import VPN from './VPN';
import { useAuthority } from '@/hooks';
import { DeviceProductTypeEnum } from '@/utils/dictionary';
import DeviceLog from '@/components/Device/module/DeviceLog';
import DeviceLogReport from '@/components/Device/module/DeviceLogReport';

type DebugType = {
  deviceData: DeviceDataType;
};

const Debug: React.FC<DebugType> = (props) => {
  const { deviceData } = props;

  const { authorityMap } = useAuthority([
    'device:detail:communicationMessage',
    'deviceManage:detail:debug:vpn',
    'deviceManage:detail:debug:log',
    'deviceManage:detail:debug:report',
  ]);

  const items: TabsProps['items'] = [];
  // 换电柜没有通讯报文，其他都有
  if (
    deviceData?.productTypeId &&
    ![DeviceProductTypeEnum.ExchangeCabinet].includes(deviceData?.productTypeId) &&
    authorityMap.get('device:detail:communicationMessage')
  ) {
    items.push({
      label: formatMessage({ id: 'debug.communicationMessage', defaultMessage: '通信报文' }),
      key: 'community',
      children: (
        <Adjust
          deviceId={deviceData?.deviceId || ''}
          productTypeId={deviceData?.productTypeId as any}
        />
      ),
    });
  }
  //  只有ems有调试隧道
  if (
    deviceData?.productTypeId &&
    [DeviceProductTypeEnum.Ems, DeviceProductTypeEnum.LocalEms].includes(
      deviceData?.productTypeId,
    ) &&
    authorityMap.get('deviceManage:detail:debug:vpn')
  ) {
    items.push({
      label: formatMessage({ id: 'debug.debugTunnel', defaultMessage: '调试隧道' }),
      key: 'vpn',
      children: <VPN />,
    });
  }

  if (authorityMap.get('deviceManage:detail:debug:log')) {
    items.push({
      label: formatMessage({ id: 'debug.1000', defaultMessage: '设备日志服务' }),
      key: 'sevice',
      children: <DeviceLog />,
    });
  }
  //  只有ems有设备日志上报
  if (
    deviceData?.productTypeId &&
    [DeviceProductTypeEnum.Ems, DeviceProductTypeEnum.LocalEms].includes(
      deviceData?.productTypeId,
    ) &&
    authorityMap.get('deviceManage:detail:debug:report')
  ) {
    items.push({
      label: formatMessage({ id: 'debug.1001', defaultMessage: '设备日志上报' }),
      key: 'report',
      children: <DeviceLogReport />,
    });
  }

  return (
    <>
      <Tabs className="px24" items={items} />
    </>
  );
};

export default memo(Debug);
