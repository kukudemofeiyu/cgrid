/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-27 16:31:19
 * @LastEditTime: 2024-01-05 21:04:33
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\SelfEmsIndex\index.tsx
 */
import React, { useMemo } from 'react';
import type { TabsProps } from 'antd';
import { Tabs, Empty } from 'antd';
import EnergyManageTab from './EnergyManageTab';
import SystemSetting from './SystemSetting';
import ConverterSetting from './ConverterSetting';
import BatterySetting from './BatterySetting';
import styles from './index.less';
import { useAuthority } from '@/hooks';
import type { DeviceDataType } from '@/services/equipment';
import { AuthorityModeEnum } from '@/hooks/useAuthority';
import { formatMessage } from '@/utils';
export type ConfigProps = {
  deviceId: string;
  deviceData: DeviceDataType;
  realTimeData: Record<string, any>;
};

const SelfEmsIndex: React.FC<ConfigProps> = (props) => {
  const { deviceId, deviceData, realTimeData } = props;
  const { passAuthority, authorityMap } = useAuthority(
    [
      'iot:device:config:systemSetting',
      'iot:device:config:converterSetting',
      'iot:device:config:batterySetting',
      'iot:device:config:energyManage',
    ],
    {
      mode: AuthorityModeEnum.Within,
    },
  );

  const items: TabsProps['items'] = [];

  if (authorityMap.get('iot:device:config:systemSetting')) {
    items.push({
      key: '1',
      label: formatMessage({ id: 'device.system', defaultMessage: '系统' }),
      children: (
        <SystemSetting deviceId={deviceId} realTimeData={realTimeData} deviceData={deviceData} />
      ),
    });
  }
  if (authorityMap.get('iot:device:config:energyManage') && deviceData?.masterSlaveMode != 1) {
    items?.push({
      key: '4',
      label: formatMessage({ id: 'device.energyManagement', defaultMessage: '能量管理' }),
      children: (
        <EnergyManageTab deviceId={deviceId} deviceData={deviceData} realTimeData={realTimeData} />
      ),
    });
  }

  return (
    <>
      {passAuthority ? <Tabs className={styles.tabs} tabBarGutter={34} items={items} /> : <Empty />}
    </>
  );
};

export default SelfEmsIndex;
