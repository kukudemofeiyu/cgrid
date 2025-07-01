/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-02-21 14:31:10
 * @LastEditTime: 2024-03-05 15:30:16
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\module\Charge\index.tsx
 */

import React, { memo, useContext } from 'react';
import Cabinet from '@/components/EnergyInfo/Cabinet';
import { Col, Row } from 'antd';
import DeviceContext from '../../Context/DeviceContext';
import Power from '../../components/Power';
import Trend from '../../components/Trend';

const Charge: React.FC = () => {
  const { data: deviceData } = useContext(DeviceContext);

  return (
    <>
      <Row className="mb20" gutter={20}>
        <Col span={14}>
          <Cabinet deviceData={deviceData} />
        </Col>
        <Col span={10}>
          <Power deviceData={deviceData} />
          <Trend deviceData={deviceData} />
        </Col>
      </Row>
    </>
  );
};

export default memo(Charge);
