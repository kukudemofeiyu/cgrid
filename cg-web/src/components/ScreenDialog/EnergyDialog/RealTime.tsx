/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-13 17:07:13
 * @LastEditTime: 2023-07-13 17:07:13
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\EnergyDialog\RealTime.tsx
 */

import React from 'react';
import { Skeleton, Row, Col, Space } from 'antd';
import OperationMonitor from './operationMonitor';
import { RealTimeProps } from '@/components/ScreenDialog';

const RealTime: React.FC<
  RealTimeProps & {
    label?: string;
    setSettingData?: (value: any) => void;
    equipmentIds: Record<string, any>;
  }
> = (props) => {
  const { loading, open = true, setSettingData, equipmentIds } = props;

  return (
    <>
      {loading ? (
        <>
          <Row gutter={20}>
            <Col flex="100px">
              <Space direction="vertical">
                <Skeleton.Button size="small" active />
                <Skeleton.Button size="small" active />
                <Skeleton.Button size="small" active />
                <Skeleton.Button size="small" active />
                <Skeleton.Button size="small" active />
              </Space>
            </Col>
            <Col flex="1">
              <Space className="mb12" direction="vertical">
                <Skeleton.Button size="small" active />
                <Skeleton.Button size="small" active />
              </Space>
              <Skeleton paragraph={{ rows: 2 }} active />
              <Skeleton.Button className="mb12" size="small" active />
              <Skeleton paragraph={{ rows: 4 }} active />
            </Col>
          </Row>
        </>
      ) : (
        <OperationMonitor
          open={open}
          equipmentIds={equipmentIds}
          onEmsDataChange={setSettingData}
        />
      )}
    </>
  );
};

export default RealTime;
