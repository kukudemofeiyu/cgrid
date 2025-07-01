/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-08 19:40:01
 * @LastEditTime: 2023-05-11 11:42:18
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\pages\screen\components\Cabinet\index.tsx
 */

import React, { useCallback, useState } from 'react';
import { Tabs, Empty as AntEmpty } from 'antd';
import Dialog from '@/components/Dialog';
import type { BusinessDialogProps } from '@/components/ScreenDialog';
import EquipInfo from '@/components/EquipInfo';
import type { DeviceType } from '@/components/EquipInfo/type';
import ChargeImg from '@/assets/image/product/cabinet.png';
import ChargeIntroImg from '@/assets/image/product/cabinet-intro.jpg';
import Empty from '@/components/Empty';
import AlarmTable from '@/components/AlarmTable';
import LogTable from '@/components/LogTable';
import { getAlarms, getLogs } from '@/services/equipment';
import Community from '../Community';
import { formatMessage } from '@/utils';

const Cabinet: React.FC<BusinessDialogProps> = (props) => {
  const { id, open, onCancel, model } = props;
  const [deviceData, setDeviceData] = useState<DeviceType>();

  const onDataChange = useCallback((data) => {
    setDeviceData(data);
  }, []);

  const tabItems = [
    {
      label: formatMessage({ id: 'device.operationMonitoring', defaultMessage: '运行监测' }),
      key: 'item-0',
      children: model == 'screen' ? <Empty /> : <AntEmpty />,
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
          equipmentImg={ChargeImg}
          productImg={ChargeIntroImg}
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

export default Cabinet;
