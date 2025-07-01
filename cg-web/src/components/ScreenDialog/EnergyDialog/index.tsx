/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-04-25 19:17:46
 * @LastEditTime: 2023-06-26 11:02:39
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\EnergyDialog\index.tsx
 */

import React, { useCallback, useEffect, useState } from 'react';
import { Tabs } from 'antd';
import Dialog from '@/components/Dialog';
import type { BusinessDialogProps } from '@/components/ScreenDialog';
import EquipInfo from '@/components/EquipInfo';
import type { DeviceType } from '@/components/EquipInfo/type';
import Alarm from './alarm';
import Log from './log';
import Setting from './setting';
import { getChildEquipment } from '@/services/equipment';
import EnergyImg from '@/assets/image/product/energy.png';
import EnergyIntroImg from '@/assets/image/product/energy-intro.jpg';
import Community from '../Community';
import RealTime from './RealTime';
import { formatMessage } from '@/utils';

const EnergyDialog: React.FC<BusinessDialogProps> = (props) => {
  const { id, open, onCancel, model } = props;
  const [equipmentIds, setEquipmentIds] = useState({});
  const [loading, setLoading] = useState(true);
  const [settingData, setSettingData] = useState<Record<string, any>>();
  const [deviceData, setDeviceData] = useState<DeviceType>();

  const onDataChange = useCallback((data) => {
    setDeviceData(data);
  }, []);

  useEffect(() => {
    if (open) {
      getChildEquipment({ parentId: id }).then((res) => {
        const obj = {};
        res?.data?.forEach?.((item: any) => {
          obj[item.productId] = item.deviceId;
        });
        setEquipmentIds(obj);
      });
    }
  }, [open]);

  const tabItems = [
    {
      label: formatMessage({ id: 'device.operationMonitoring', defaultMessage: '运行监测' }),
      key: 'item-0',
      children: (
        <RealTime
          id={id}
          open={open}
          loading={loading}
          setSettingData={setSettingData}
          equipmentIds={equipmentIds}
        />
      ),
    },
    {
      label: formatMessage({ id: 'device.remoteSettings', defaultMessage: '远程设置' }),
      key: 'item-1',
      children: <Setting id={id} settingData={settingData} />,
    },
    {
      label: formatMessage({ id: 'device.alarm/fault', defaultMessage: '告警/故障' }),
      key: 'item-2',
      children: <Alarm equipmentIds={equipmentIds} />,
    },
    {
      label: formatMessage({ id: 'device.log', defaultMessage: '日志' }),
      key: 'item-3',
      children: <Log equipmentIds={equipmentIds} />,
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
          equipmentImg={EnergyImg}
          productImg={EnergyIntroImg}
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

export default EnergyDialog;
