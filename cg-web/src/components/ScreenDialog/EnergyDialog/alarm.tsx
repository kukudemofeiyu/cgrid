import React from 'react';
import { Tabs } from 'antd';
import AlarmTable from '@/components/AlarmTable';
import { getAlarms } from '@/services/equipment';
import { EnergyEquipmentEnum } from '@/utils/dictionary';
import { formatMessage } from '@/utils';

export type AlarmProps = {
  equipmentIds: Record<string, any>;
};

const Alarm: React.FC<AlarmProps> = (props) => {
  const { equipmentIds } = props;

  const items = [
    {
      label: 'EMS',
      key: 'item-0',
      children: (
        <AlarmTable params={{ id: equipmentIds?.[EnergyEquipmentEnum.EMS] }} request={getAlarms} />
      ),
    },
    {
      label: 'PCS',
      key: 'item-1',
      children: (
        <AlarmTable params={{ id: equipmentIds?.[EnergyEquipmentEnum.PCS] }} request={getAlarms} />
      ),
    },
    {
      label: 'BMS',
      key: 'item-2',
      children: (
        <AlarmTable params={{ id: equipmentIds?.[EnergyEquipmentEnum.BMS] }} request={getAlarms} />
      ),
    },
    {
      label: formatMessage({ id: 'device.airConditioner', defaultMessage: '空调' }),
      key: 'item-3',
      children: (
        <AlarmTable params={{ id: equipmentIds?.[EnergyEquipmentEnum.AIR] }} request={getAlarms} />
      ),
    },
    {
      label: formatMessage({ id: 'device.ammeter', defaultMessage: '电表' }),
      key: 'item-4',
      children: (
        <AlarmTable
          params={{ id: equipmentIds?.[EnergyEquipmentEnum.METER] }}
          request={getAlarms}
        />
      ),
    },
  ];

  return (
    <>
      <Tabs className="child-tabs" items={items} tabPosition="left" />
    </>
  );
};

export default Alarm;
