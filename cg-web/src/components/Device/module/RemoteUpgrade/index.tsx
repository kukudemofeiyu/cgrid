/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-25 14:42:09
 * @LastEditTime: 2024-01-09 16:18:32
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\module\RemoteUpgrade\index.tsx
 */

import React, { memo, useEffect, useMemo } from 'react';
import { RemoteUpgradeType } from './typing';
import Detail, { GroupItem } from '@/components/Detail';
import UpgradeForm from './UpgradeForm';
import { upgradeItems } from './helper';
import { useRequest } from 'umi';
import { getUpgradeVersion } from '@/services/equipment';
import UpgradeRecord from './UpgradeRecord';
import { formatMessage } from '@/utils';
import { useAuthority } from '@/hooks';

const RemoteUpgrade: React.FC<RemoteUpgradeType> = memo((props) => {
  const { deviceId, passEditAuth } = props;

  const { data: versionData, run } = useRequest(getUpgradeVersion, {
    manual: true,
  });

  useEffect(() => {
    if (deviceId) {
      run({ deviceId });
    }
  }, [deviceId]);

  const groupItems = useMemo<GroupItem[]>(() => {
    return [
      {
        label: (
          <Detail.Label
            title={formatMessage({ id: 'device.remoteUpgrade', defaultMessage: '远程升级' })}
          >
            {passEditAuth && (
              <UpgradeForm
                deviceId={deviceId}
                versionItems={versionData?.upgradeableVersionVOList}
              />
            )}
            <UpgradeRecord deviceId={deviceId} />
          </Detail.Label>
        ),
        items: upgradeItems,
      },
    ];
  }, [deviceId, versionData, passEditAuth]);

  return (
    <>
      <Detail.Group items={groupItems} data={versionData} />
    </>
  );
});

export default RemoteUpgrade;
