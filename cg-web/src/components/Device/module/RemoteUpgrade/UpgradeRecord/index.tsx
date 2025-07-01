/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-25 15:22:57
 * @LastEditTime: 2023-11-24 09:26:23
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\RemoteUpgrade\UpgradeRecord\index.tsx
 */
import YTProTable from '@/components/ProTable';
import React, { useCallback, useContext, useEffect, useRef } from 'react';
import { columns } from './helper';
import { RemoteUpgradeType } from '../typing';
import { getUpgradeRecord } from '@/services/equipment';
import { Button, Modal } from 'antd';
import { useBoolean } from 'ahooks';
import { ActionType } from '@ant-design/pro-components';
import { OnlineStatusEnum } from '@/utils/dictionary';
import { formatMessage } from '@/utils';
import DeviceContext from '@/components/Device/Context/DeviceContext';

const UpgradeRecord: React.FC<RemoteUpgradeType> = (props) => {
  const { deviceId } = props;

  const { data: deviceData } = useContext(DeviceContext);
  const actionRef = useRef<ActionType>();
  const [open, { setTrue, setFalse }] = useBoolean(false);

  const requestList = useCallback(
    (params) => {
      return getUpgradeRecord({ ...params, deviceId });
    },
    [deviceId],
  );

  useEffect(() => {
    if (open && deviceId) {
      actionRef?.current?.reloadAndRest?.();
    }
  }, [open, deviceId]);

  return (
    <>
      <Button
        type="primary"
        onClick={setTrue}
        disabled={deviceData?.networkStatus === OnlineStatusEnum.Offline}
      >
        {formatMessage({ id: 'device.upgradeRecord', defaultMessage: '升级记录' })}
      </Button>
      <Modal
        title={formatMessage({ id: 'device.upgradeRecord', defaultMessage: '升级记录' })}
        width={900}
        open={open}
        onCancel={setFalse}
        onOk={setFalse}
      >
        <YTProTable
          actionRef={actionRef}
          columns={columns}
          request={requestList}
          toolBarRender={false}
          search={false}
        />
      </Modal>
    </>
  );
};

export default UpgradeRecord;
