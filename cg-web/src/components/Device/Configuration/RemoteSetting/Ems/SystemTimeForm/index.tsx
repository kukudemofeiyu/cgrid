/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-12 10:27:09
 * @LastEditTime: 2023-12-18 11:17:05
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\RemoteSetting\Ems\SystemTimeForm\index.tsx
 */

import { Button } from 'antd';
import React, { useCallback, useEffect, useState } from 'react';
import { SystemTimeFormType } from '../typing';
import SchemaForm, { FormTypeEnum } from '@/components/SchemaForm';
import { columns } from './helper';
import { useBoolean } from 'ahooks';
import { editSetting } from '@/services/equipment';
import { RemoteSettingDataType } from '../../typing';
import moment, { Moment } from 'moment';
import { formatMessage } from '@/utils';
import { OnlineStatusEnum } from '@/utils/dictionary';

const SystemTimeForm: React.FC<SystemTimeFormType> = (props) => {
  const { deviceId, deviceData, systemTimeData, onSuccess } = props;

  const [open, { set, setTrue }] = useBoolean(false);
  const [initialValues, setInitialValues] = useState<SystemTimeFormType['systemTimeData']>();

  const beforeSubmit = useCallback(
    (formData: any) => {
      const time: Moment = moment(formData.sysTem);
      const result: RemoteSettingDataType<SystemTimeFormType['systemTimeData']> = {
        deviceId,
        input: {
          yearWait: time.year(),
          monthWait: time.month() + 1,
          dayWait: time.date(),
          hourWait: time.hour(),
          minuteWait: time.minute(),
          secondWait: time.second(),
          weekWait: time.day() || 7,
        },
        serviceId: 'correctionTime',
      };
      return result;
    },
    [deviceId],
  );

  useEffect(() => {
    if (!open) {
      setInitialValues(systemTimeData?.sysTem ? { sysTem: moment(systemTimeData?.sysTem) } : {});
    }
  }, [systemTimeData, open]);

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

export default SystemTimeForm;
