/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-02-21 14:31:10
 * @LastEditTime: 2024-05-16 10:12:24
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\index.tsx
 */
import React from 'react';
import { Row, Col } from 'antd';
import Cabinet from './Cabinet';
import Power from './Power';
import Electric from './Electric';
import { DeviceDataType } from '@/services/equipment';
import ElectricDiagram from './ElectricDiagram';
import { DeviceProductTypeEnum, DeviceTypeEnum } from '@/utils/dictionary';
import Address from './DeviceMap';

export enum EnergySourceEnum {
  SiteMonitor,
  DeviceManage,
}

export type EnergyInfoType = {
  deviceData?: DeviceDataType;
  showLabel?: boolean;
  loading?: boolean;
  source?: EnergySourceEnum;
};

const newWindLiquidEnergy: (DeviceTypeEnum | undefined)[] = [
  DeviceTypeEnum.Liquid2Energy,
  DeviceTypeEnum.Wind2Energy,
  // DeviceTypeEnum.PvEnergy,
  // DeviceTypeEnum.SmallEnergy,
];

const energy: (DeviceProductTypeEnum | undefined)[] = [
  DeviceProductTypeEnum.Energy,
  DeviceProductTypeEnum.PvEnergy,
  DeviceProductTypeEnum.SmallEnergy,
  DeviceProductTypeEnum.WindPvFirewoodEnergy,
];

const EnergyInfo: React.FC<EnergyInfoType> = (props) => {
  const { deviceData, showLabel, loading, source } = props;

  return (
    <>
      <Row className="mb20" gutter={20}>
        <Col span={14}>
          <Cabinet
            deviceData={deviceData}
            showLabel={showLabel}
            loading={loading}
            source={source}
          />
        </Col>
        <Col span={10}>
          <Power deviceData={deviceData} loading={loading} source={source} />
          <Electric deviceData={deviceData} loading={loading} source={source} />
        </Col>
      </Row>
      {newWindLiquidEnergy.includes(deviceData?.productId) && (
        <ElectricDiagram deviceData={deviceData} />
      )}
      {energy.includes(deviceData?.productTypeId) && <Address deviceData={deviceData} />}
    </>
  );
};

export default EnergyInfo;
