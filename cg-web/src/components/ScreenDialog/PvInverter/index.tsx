/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-08 18:59:32
 * @LastEditTime: 2023-06-01 13:42:25
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\PvInverter\index.tsx
 */

import React, { useState, useCallback } from 'react';
import { Tabs, Empty as AntEmpty } from 'antd';
import Dialog from '@/components/Dialog';
import type { BusinessDialogProps } from '@/components/ScreenDialog';
import EquipInfo from '@/components/EquipInfo';
import type { DeviceType } from '@/components/EquipInfo/type';
import AlarmTable from '@/components/AlarmTable';
import LogTable from '@/components/LogTable';
import { getAlarms, getLogs } from '@/services/equipment';
import PvInverterImg from '@/assets/image/product/pvInverter.png';
import PvInverterIntroImg from '@/assets/image/product/pv-inverter-intro.jpg';
import Empty from '@/components/Empty';
import Community from '../Community';
import RealTime from './RealTime';
import { formatMessage } from '@/utils';

export type PvInverterProps = BusinessDialogProps & {
  loopNum: number;
};

const PvInverter: React.FC<PvInverterProps> = (props) => {
  const { id, open, onCancel, model, loopNum } = props;
  const [loading, setLoading] = useState(false);
  const [deviceData, setDeviceData] = useState<DeviceType>();

  const onDataChange = useCallback((data) => {
    setDeviceData(data);
  }, []);

  const tabItems = [
    {
      label: formatMessage({ id: 'device.operationMonitoring', defaultMessage: '运行监测' }),
      key: 'item-0',
      children: <RealTime id={id} open={open} loading={loading} loopNum={loopNum} />,
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
          equipmentImg={PvInverterImg}
          productImg={PvInverterIntroImg}
          setLoading={setLoading}
          buttons={
            <Community
              id={id}
              model={model}
              siteId={deviceData?.siteId}
              type={deviceData?.paramConfigType}
            />
          }
          onChange={onDataChange}
        />
        <Tabs items={tabItems} />
      </Dialog>
    </>
  );
};

export default PvInverter;
