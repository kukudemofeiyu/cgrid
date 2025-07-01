/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-05-13 10:03:12
 * @LastEditTime: 2024-05-27 18:09:08
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\module\ElectricitySetting\index.tsx
 */

import React, { memo, useContext, useEffect } from 'react';
import DeviceContext from '../../Context/DeviceContext';
import { Empty } from 'antd';
import { formatMessage } from '@/utils';
import ElectricityPrice from '@/pages/station/stationManage/setting/ElectricityPrice';
import { useRequest } from 'umi';
import { getStation } from '@/services/station';
import styles from './index.less';

const ElectricitySetting: React.FC = () => {
  const { data: deviceData } = useContext(DeviceContext);

  const { data: siteInfo, run } = useRequest(getStation, {
    manual: true,
  });

  useEffect(() => {
    if (deviceData?.siteId) {
      run(deviceData?.siteId);
    }
  }, [deviceData?.siteId]);

  return (
    <>
      {!!deviceData?.siteId ? (
        <div className={styles.price}>
          <ElectricityPrice
            siteType={siteInfo?.energyOptions}
            siteId={deviceData?.siteId}
            inDevice
          />
        </div>
      ) : (
        <Empty description={formatMessage({ id: 'device.1003', defaultMessage: '请先绑定站点' })} />
      )}
    </>
  );
};

export default memo(ElectricitySetting);
