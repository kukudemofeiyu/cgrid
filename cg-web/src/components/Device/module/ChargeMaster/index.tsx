/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-05 09:11:33
 * @LastEditTime: 2024-04-18 17:27:45
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\module\ChargeStack\index.tsx
 */

import React, { memo, useContext, useEffect, useMemo } from 'react';
import Cabinet from '@/components/EnergyInfo/Cabinet';
import { Col, Row, Spin } from 'antd';
import DeviceContext from '../../Context/DeviceContext';
import { useRequest } from 'umi';
import { getWholeDeviceTree } from '@/services/equipment';
import { DeviceProductTypeEnum } from '@/utils/dictionary';
import Power from '../../components/Power';

const ChargeMaster: React.FC = () => {
  const { data: deviceData } = useContext(DeviceContext);

  const {
    loading: loading,
    data: energyData,
    run,
  } = useRequest(getWholeDeviceTree, {
    manual: true,
  });

  const masterDeviceData = useMemo(() => {
    const result = energyData?.children?.find?.(
      (item) => item.productTypeId == DeviceProductTypeEnum.ChargeMaster,
    );
    if (result) {
      result.deviceId = result.id;
    }
    return result;
  }, [energyData]);

  useEffect(() => {
    if (deviceData?.deviceId) {
      run({ deviceId: deviceData?.deviceId });
    }
  }, [deviceData?.deviceId]);

  return (
    <>
      {loading ? (
        <div className="tx-center mt24">
          <Spin className="ml12" />
        </div>
      ) : (
        <Row className="mb20" gutter={20}>
          <Col span={14}>
            <Cabinet deviceData={masterDeviceData} />
          </Col>
          <Col span={10}>
            <Power deviceData={deviceData} />
          </Col>
        </Row>
      )}
    </>
  );
};

export default memo(ChargeMaster);
