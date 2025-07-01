/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-10 11:19:17
 * @LastEditTime: 2023-09-08 17:58:58
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EquipForm\index.tsx
 */
import React, { useEffect, useState, useCallback } from 'react';
import { Form, message, Modal } from 'antd';
import { useRequest, useModel } from 'umi';
import Dialog from '@/components/Dialog';
import { formatMessage } from '@/utils';
import SchemaForm, { FormTypeEnum } from '@/components/SchemaForm';
import { editDeviceInfo } from '@/services/equipment';
export type DeviceSnProps = {
  id?: string;
  open: boolean;
  onSuccess: any;
  initialValues?: Record<string, any>;
  onOpenChange: any;
  beforeSubmit: any;
};
export type EquipFormType = {
  name?: string;
  masterSlaveSystemName?: string;
};
const DeviceNameDialog: React.FC<DeviceSnProps> = (props) => {
  const { id, open, onOpenChange, initialValues, beforeSubmit, onSuccess } = props;
  const [form] = Form.useForm<EquipFormType>();

  const columns = [
    {
      title: formatMessage({ id: 'common.deviceName', defaultMessage: '设备名称' }),
      dataIndex: 'name',
      formItemProps: {
        rules: [{ required: true }],
      },
      fieldProps: {},
      colProps: {
        span: 24,
      },
    },
    {
      title: formatMessage({
        id: 'siteMonitor.masterSlaveSystemName',
        defaultMessage: '主从系统名称',
      }),
      dataIndex: 'masterSlaveSystemName',
      formItemProps: {
        rules: [{ required: true }],
      },
      fieldProps: {},
      colProps: {
        span: 24,
      },
    },
  ];

  return (
    <>
      <SchemaForm
        id={id}
        open={open}
        onOpenChange={onOpenChange}
        title={formatMessage({
          id: 'siteMonitor.editDeviceInformation',
          defaultMessage: '编辑设备信息',
        })}
        type={FormTypeEnum.Edit}
        width={552}
        columns={columns}
        initialValues={initialValues}
        editData={editDeviceInfo}
        beforeSubmit={beforeSubmit}
        onSuccess={onSuccess}
      />
    </>
  );
};

export default DeviceNameDialog;
