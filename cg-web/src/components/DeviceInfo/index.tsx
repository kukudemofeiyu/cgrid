/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-06 16:19:01
 * @LastEditTime: 2023-07-24 11:41:50
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceInfo\index.tsx
 */

import React, { useState, useEffect, useMemo } from 'react';
import { Button, Skeleton } from 'antd';
import { useRequest } from 'umi';
import Detail from '../Detail';
import type { DetailItem } from '../Detail';
import Label from '../Detail/LineLabel';
import EquipForm from '../EquipForm';
import { FormTypeEnum } from '@/components/SchemaForm';
import { getDeviceInfo } from '@/services/equipment';
import './index.less';
import { formatMessage } from '@/utils';

export type DeviceInfoProps = {
  id: string;
  model?: string;
  buttons?: React.ReactNode;
  equipmentImg?: string;
  productImg?: string;
  setLoading?: (loading: boolean) => void;
  onChange?: (value: Record<string, any>) => void;
  className?: string;
};

const DeviceInfo: React.FC<DeviceInfoProps> = (props) => {
  const { id, model, buttons, setLoading, onChange } = props;
  const [openEditModal, setOpenEditModal] = useState(false);
  const {
    loading,
    data: equipData = {},
    run,
  } = useRequest(getDeviceInfo, {
    manual: true,
  });

  useEffect(() => {
    setLoading?.(true);
    run({ deviceId: id })
      .then((data) => {
        onChange?.(data);
      })
      .finally(() => {
        setLoading?.(false);
      });
  }, [id, onChange]);

  const onEditClick = () => {
    setOpenEditModal((openValue) => !openValue);
  };

  const equipInfoItems = useMemo<DetailItem[]>(() => {
    return [
      {
        label: formatMessage({ id: 'common.deviceName', defaultMessage: '设备名称' }),
        field: 'name',
      },
      {
        label: formatMessage({ id: 'common.equipmentSerial', defaultMessage: '设备序列号' }),
        field: 'sn',
      },
      { label: formatMessage({ id: 'common.model', defaultMessage: '产品型号' }), field: 'model' },
      {
        label: formatMessage({ id: 'common.productType', defaultMessage: '产品类型' }),
        field: 'productTypeName',
      },
      {
        label: formatMessage({ id: 'siteMonitor.packageName', defaultMessage: '软件包名称' }),
        field: 'b',
      },
      {
        label: formatMessage({
          id: 'siteMonitor.softwareVersionNumber',
          defaultMessage: '软件版本号',
        }),
        field: 'a',
      },
      {
        label: formatMessage({ id: 'siteMonitor.activationTime', defaultMessage: '激活时间' }),
        field: 'activeTime',
      },
      {
        label: formatMessage({ id: 'siteMonitor.entryTime', defaultMessage: '录入时间' }),
        field: 'createTime',
      },
      {
        label: formatMessage({ id: 'siteMonitor.enteredBy', defaultMessage: '录入人' }),
        field: 'updateUserName',
      },
    ];
  }, []);

  return (
    <>
      {loading ? (
        <>
          <Skeleton active paragraph={{ rows: 4 }} />
        </>
      ) : (
        <>
          <Label title={formatMessage({ id: 'common.baseInfo', defaultMessage: '基础信息' })}>
            <Button className="pr0" type="link" onClick={onEditClick}>
              {formatMessage({ id: 'common.modify', defaultMessage: '修改' })}
            </Button>
            {buttons}
          </Label>
          <Detail items={equipInfoItems} data={equipData} bordered size="small" />
        </>
      )}
      <EquipForm
        id={id}
        open={openEditModal}
        onCancel={onEditClick}
        model={model}
        type={FormTypeEnum.Edit}
        onSuccess={() => run({ deviceId: id })}
      />
    </>
  );
};

export default DeviceInfo;
