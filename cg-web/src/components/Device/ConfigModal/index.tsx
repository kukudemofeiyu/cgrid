/*
 *@Author: aoshilin
 *@Date: 2023-11-03 15:25:48
 *@parms: 自研ems详情-配置--系统化设置
 *@Description:
 */
import React, { useState, useEffect, useCallback } from 'react';
import type { ProtectFormType, RemoteSettingDataType } from './config';
import Button from 'antd/lib/button';
import SchemaForm, { FormTypeEnum, SchemaFormProps } from '@/components/SchemaForm';
import { useBoolean } from 'ahooks';
import { editSetting, editEquipConfig } from '@/services/equipment';
import moment from 'moment';
import { DeviceTypeEnum, OnlineStatusEnum } from '@/utils/dictionary';
import { formatMessage } from '@/utils';
import { useAuthority } from '@/hooks';

export type ConfigModalType<T = any> = Omit<SchemaFormProps, 'beforeSubmit'> & {
  deviceId?: string;
  realTimeData?: Record<string, any>;
  columns: any;
  serviceId: string;
  title: React.ReactNode;
  beforeSubmit?: (data: RemoteSettingDataType<T>) => void | boolean | any;
  showClickButton?: boolean;
  deviceData?: Record<string, any>;
  authority?: string;
};

const ConfigModal: React.FC<ConfigModalType> = (props) => {
  const {
    realTimeData,
    deviceId,
    columns,
    serviceId,
    title,
    open,
    showClickButton = true,
    beforeSubmit,
    onOpenChange,
    deviceData,
    authority,
    ...restProps
  } = props;
  const [openSchemaForm, { set, setTrue }] = useBoolean(false);
  const [isEditing, { setFalse: setIsEditingFalse, setTrue: setIsEditingTrue }] = useBoolean(false);
  const [initialValues, setInitialValues] = useState<ProtectFormType['realTimeData']>();
  const { passAuthority } = useAuthority(authority ? [authority] : []);

  const onBeforeSubmit = useCallback(
    (formData: any) => {
      let result: RemoteSettingDataType<ProtectFormType['realTimeData']> = {
        deviceId,
        input: formData,
        serviceId,
      };
      if (deviceData?.productId == DeviceTypeEnum.YTEnergyEms && serviceId === 'correctionTime') {
        result.input = { correctionTime: moment(formData.correctionTime).valueOf() };
      }
      //尖峰平谷时段设置
      if (
        deviceData?.productId == DeviceTypeEnum.YTEnergyEms &&
        serviceId === 'PeakAndValleyTimeSettings'
      ) {
        if (formData.ElectrovalenceTimeFrame.length > 0) {
          const timeFormData = formData.ElectrovalenceTimeFrame.map((item: any) => {
            return {
              ...item,
              ElectrovalenceType: item.ElectrovalenceType,
              TimeFrame:
                moment('2023-01-01 ' + item.TimeFrame[0]).format('HH:mm') +
                '-' +
                moment('2023-01-01 ' + item.TimeFrame[1]).format('HH:mm'),
            };
          });
          result = {
            deviceId,
            input: { ...formData, ElectrovalenceTimeFrame: timeFormData },
            serviceId,
          };
        }
      }
      const submitResult = beforeSubmit?.(result);
      return submitResult ?? result;
    },
    [deviceId, deviceData, realTimeData, serviceId],
  );

  const onClick = useCallback(() => {
    setTrue();
  }, []);

  const mergedOnOpenChange = useCallback(
    (opened: boolean) => {
      if (showClickButton) {
        set(opened);
      } else {
        onOpenChange?.(opened);
      }
    },
    [showClickButton],
  );

  useEffect(() => {
    if (openSchemaForm || open) {
      setIsEditingFalse();
    }
  }, [openSchemaForm, open]);

  useEffect(() => {
    if (!isEditing || (!openSchemaForm && !open)) {
      setInitialValues({ ...realTimeData });
    }
  }, [realTimeData]);

  return (
    <>
      <div>
        {showClickButton && (!authority || passAuthority) ? (
          <Button
            type="primary"
            onClick={onClick}
            disabled={deviceData?.networkStatus === OnlineStatusEnum.Offline}
          >
            {formatMessage({ id: 'device.configurationParameter', defaultMessage: '配置参数' })}
          </Button>
        ) : (
          <></>
        )}
        <SchemaForm
          onValuesChange={setIsEditingTrue}
          open={open || openSchemaForm}
          onOpenChange={mergedOnOpenChange}
          title={title}
          width={'552px'}
          type={FormTypeEnum.Edit}
          columns={columns}
          className={'distributeParameters'}
          initialValues={initialValues}
          editData={serviceId === 'report' ? editEquipConfig : editSetting}
          beforeSubmit={onBeforeSubmit}
          grid={true}
          colProps={{
            span: 12,
          }}
          {...restProps}
        />
      </div>
    </>
  );
};

export default ConfigModal;
