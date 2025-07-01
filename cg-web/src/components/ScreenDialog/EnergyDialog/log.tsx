/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-08 17:09:25
 * @LastEditTime: 2023-05-08 17:16:33
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\pages\screen\components\EnergyDialog\log.tsx
 */
import React from 'react';
import { Tabs } from 'antd';
import LogTable from '@/components/LogTable';
import { getLogs } from '@/services/equipment';
import { EnergyEquipmentEnum } from '@/utils/dictionary';
import { formatMessage } from '@/utils';

export type LogProps = {
  equipmentIds: Record<string, any>;
};

const Log: React.FC<LogProps> = (props) => {
  const { equipmentIds } = props;

  const items = [
    {
      label: 'EMS',
      key: 'item-0',
      children: (
        <LogTable params={{ id: equipmentIds[EnergyEquipmentEnum.EMS] }} request={getLogs} />
      ),
    },
    {
      label: 'PCS',
      key: 'item-1',
      children: (
        <LogTable params={{ id: equipmentIds[EnergyEquipmentEnum.PCS] }} request={getLogs} />
      ),
    },
    {
      label: 'BMS',
      key: 'item-2',
      children: (
        <LogTable params={{ id: equipmentIds[EnergyEquipmentEnum.BMS] }} request={getLogs} />
      ),
    },
    {
      label: formatMessage({ id: 'device.airConditioner', defaultMessage: '空调' }),
      key: 'item-3',
      children: (
        <LogTable params={{ id: equipmentIds[EnergyEquipmentEnum.AIR] }} request={getLogs} />
      ),
    },
    {
      label: formatMessage({ id: 'device.ammeter', defaultMessage: '空调' }),
      key: 'item-4',
      children: (
        <LogTable params={{ id: equipmentIds[EnergyEquipmentEnum.METER] }} request={getLogs} />
      ),
    },
  ];

  return (
    <>
      <Tabs className="child-tabs" items={items} tabPosition="left" />
    </>
  );
};

export default Log;
