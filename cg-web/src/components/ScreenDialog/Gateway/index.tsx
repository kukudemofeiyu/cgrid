import React, { useState, useCallback } from 'react';
import { Tabs, Empty as AntEmpty } from 'antd';
import Dialog from '@/components/Dialog';
import type { BusinessDialogProps } from '@/components/ScreenDialog';
import EquipInfo from '@/components/EquipInfo';
import type { DeviceType } from '@/components/EquipInfo/type';
import AlarmTable from '@/components/AlarmTable';
import LogTable from '@/components/LogTable';
import { getAlarms, getLogs } from '@/services/equipment';
import Empty from '@/components/Empty';
import Community from '../Community';
import RealTime from '@/components/Meter/RealTime';
import { formatMessage } from '@/utils';

const Gateway: React.FC<BusinessDialogProps> = (props) => {
  const { id, open, onCancel, model } = props;
  const [loading, setLoading] = useState(false);
  const [deviceData, setDeviceData] = useState<DeviceType>();

  const onDataChange = useCallback((data) => {
    setDeviceData(data);
  }, []);

  const tabItems = [
    {
      label: formatMessage({ id: 'device.operationMonitoring', defaultMessage: '运行监测' }),
      key: 'item-0',
      children: <RealTime id={id} open={open} loading={loading} />,
    },
    {
      label: formatMessage({ id: 'device.remoteSettings', defaultMessage: '远程设置' }),
      key: 'item-1',
      children: model == 'screen' ? <Empty /> : <AntEmpty />,
    },
    {
      label: formatMessage({ id: 'device.alarm/fault', defaultMessage: '告警/故障' }),
      key: 'item-2',
      children: <AlarmTable params={{ id }} request={getAlarms} />,
    },
    {
      label: formatMessage({ id: 'device.log', defaultMessage: '日志' }),
      key: 'item-3',
      children: <LogTable params={{ id }} request={getLogs} />,
    },
  ];

  return (
    <>
      <Dialog
        model={model}
        title={formatMessage({ id: 'device.deviceDetails', defaultMessage: '设备详情' })}
        open={open}
        onCancel={onCancel}
        footer={null}
        destroyOnClose
      >
        <EquipInfo
          id={id}
          model={model}
          setLoading={setLoading}
          buttons={
            <Community
              id={id}
              model={model}
              siteId={deviceData?.siteId}
              type={deviceData?.paramConfigType}
              userLabel={`EMS mqtt${formatMessage({
                id: 'common.userName',
                defaultMessage: '用户名',
              })}`}
              passwordLabel={`EMS mqtt${formatMessage({
                id: 'common.password',
                defaultMessage: '密码',
              })}`}
            />
          }
          onChange={onDataChange}
        />
        <Tabs items={tabItems} />
      </Dialog>
    </>
  );
};

export default Gateway;
