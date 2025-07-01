/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-12 10:27:00
 * @LastEditTime: 2023-12-18 11:16:42
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\RemoteSetting\Ems\ProtectForm\index.tsx
 */
import { Button } from 'antd';
import React, { useCallback, useEffect, useState } from 'react';
import { ProtectFormType } from '../typing';
import SchemaForm, { FormTypeEnum } from '@/components/SchemaForm';
import { columns } from './helper';
import { useBoolean } from 'ahooks';
import { editSetting } from '@/services/equipment';
import { RemoteSettingDataType } from '../../typing';
import { formatMessage } from '@/utils';
import { OnlineStatusEnum } from '@/utils/dictionary';

const ProtectForm: React.FC<ProtectFormType> = (props) => {
  const { deviceId, deviceData, protectData, onSuccess } = props;

  const [initialValues, setInitialValues] = useState<ProtectFormType['protectData']>();
  const [open, { set, setTrue }] = useBoolean(false);

  const beforeSubmit = useCallback(
    (formData: any) => {
      const result: RemoteSettingDataType<ProtectFormType['protectData']> = {
        deviceId,
        input: formData,
        serviceId: 'setChargeReleaseProtect',
      };
      return result;
    },
    [deviceId],
  );

  useEffect(() => {
    if (!open) {
      setInitialValues({ ...protectData });
    }
  }, [protectData, open]);

  return (
    <>
      <Button
        type="primary"
        onClick={setTrue}
        disabled={deviceData?.networkStatus === OnlineStatusEnum.Offline}
      >
        {formatMessage({ id: 'siteMonitor.issueParameters', defaultMessage: '下发参数' })}
      </Button>
      <SchemaForm
        open={open}
        onOpenChange={set}
        title={formatMessage({ id: 'siteMonitor.issueParameters', defaultMessage: '下发参数' })}
        width={552}
        type={FormTypeEnum.Edit}
        columns={columns}
        initialValues={initialValues}
        editData={editSetting}
        beforeSubmit={beforeSubmit}
        onSuccess={onSuccess}
      />
    </>
  );
};

export default ProtectForm;
