/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-12 09:39:40
 * @LastEditTime: 2023-09-27 09:23:08
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\RemoteSetting\Air\index.tsx
 */
import React, { useMemo } from 'react';
import { AirType } from './typing';
import Detail, { GroupItem } from '@/components/Detail';
import { runItems } from './helper';
import { useAuthority, useSubscribe } from '@/hooks';
import RunForm from './RunForm';
import { formatMessage } from '@/utils';

const Air: React.FC<AirType> = (props) => {
  const { deviceId, deviceData } = props;

  const { authorityMap } = useAuthority([
    'iot:device:config:runConstantSetting',
    'iot:device:config:runConstantSetting:distribute',
  ]);
  const realTimeData = useSubscribe(deviceId, true);

  const groupItems = useMemo<GroupItem[]>(() => {
    return [
      {
        label: (
          <Detail.Label
            title={formatMessage({
              id: 'device.runFixedValueSetting',
              defaultMessage: '运行定值设置',
            })}
          >
            {authorityMap.get('iot:device:config:runConstantSetting:distribute') && (
              <RunForm deviceId={deviceId} deviceData={deviceData} runData={realTimeData} />
            )}
          </Detail.Label>
        ),
        items: runItems,
      },
    ];
  }, [deviceId, realTimeData, authorityMap]);

  return (
    <>
      {authorityMap.get('iot:device:config:runConstantSetting') && (
        <Detail.Group items={groupItems} data={realTimeData} />
      )}
    </>
  );
};

export default Air;
