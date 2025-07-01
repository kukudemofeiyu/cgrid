/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-25 13:46:40
 * @LastEditTime: 2023-09-25 14:18:24
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\RemoteSetting\Air\RunForm\index.tsx
 */
import { Button } from 'antd';
import React, { useCallback, useEffect, useState } from 'react';
import SchemaForm, { FormTypeEnum } from '@/components/SchemaForm';
import { columns } from './helper';
import { useBoolean } from 'ahooks';
import { editSetting } from '@/services/equipment';
import { RemoteSettingDataType } from '../../typing';
import { RunFormType } from '../typing';
import { formatMessage } from '@/utils';
import { OnlineStatusEnum } from '@/utils/dictionary';

const RunForm: React.FC<RunFormType> = (props) => {
  const { deviceId, deviceData, runData, onSuccess } = props;

  const [initialValues, setInitialValues] = useState<RunFormType['runData']>();
  const [open, { set, setTrue }] = useBoolean(false);

  const beforeSubmit = useCallback(
    (formData: any) => {
      const result: RemoteSettingDataType<RunFormType['runData']> = {
        deviceId,
        input: formData,
        serviceId: 'SettingOfOperatingConstantValue',
      };
      return result;
    },
    [deviceId],
  );

  useEffect(() => {
    if (!open) {
      setInitialValues({ ...runData });
    }
  }, [runData, open]);

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
        grid={true}
        colProps={{
          span: 12,
        }}
      />
    </>
  );
};

export default RunForm;
