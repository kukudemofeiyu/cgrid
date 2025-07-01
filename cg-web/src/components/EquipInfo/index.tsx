/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-06 16:19:01
 * @LastEditTime: 2024-02-23 09:58:46
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EquipInfo\index.tsx
 */

import React, { useState, useEffect } from 'react';
import { Row, Col, Button, Skeleton } from 'antd';
import { useRequest } from 'umi';
import Detail from '../Detail';
import type { DetailItem } from '../Detail';
import Dialog from '../Dialog';
import Label from '../Detail/DotLabel';
import EquipForm from '../EquipForm';
import { FormTypeEnum } from '@/components/SchemaForm';
import { getEquipInfo } from '@/services/equipment';
import { onlineFormat } from '@/utils/format';
import './index.less';
import { formatMessage } from '@/utils';

export type EquipInfoProps = {
  id: string;
  model?: string;
  buttons?: React.ReactNode;
  equipmentImg?: string;
  productImg?: string;
  setLoading?: (loading: boolean) => void;
  onChange?: (value: Record<string, any>) => void;
  className?: string;
};

const EquipInfo: React.FC<EquipInfoProps> = (props) => {
  const {
    id,
    model,
    buttons,
    equipmentImg,
    productImg,
    setLoading,
    onChange,
    className = '',
  } = props;
  const [openDialog, setOpenDialog] = useState(false);
  const [openEditModal, setOpenEditModal] = useState(false);
  const {
    loading,
    data: equipData = {},
    run,
  } = useRequest(getEquipInfo, {
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

  const onCancel = () => {
    setOpenDialog(false);
  };

  const onClick = () => {
    setOpenDialog(true);
  };

  const onEditClick = () => {
    setOpenEditModal((openValue) => !openValue);
  };

  const equipInfoItems: DetailItem[] = [
    {
      label: formatMessage({ id: 'device.onlineStatus', defaultMessage: '在线状态' }),
      field: 'status',
      span: 3,
      format: onlineFormat,
    },
    { label: formatMessage({ id: 'common.model', defaultMessage: '产品型号' }), field: 'model' },
    { label: formatMessage({ id: 'common.deviceSn', defaultMessage: '设备序列号' }), field: 'sn' },
    {
      label: formatMessage({ id: 'common.deviceName', defaultMessage: '设备名称' }),
      field: 'name',
    },
    {
      label: formatMessage({ id: 'common.productType', defaultMessage: '产品类型' }),
      field: 'productTypeName',
    },
    {
      label: formatMessage({ id: 'device.owningSubsystem', defaultMessage: '所属子系统' }),
      field: 'subsystemName',
    },
    {
      label: formatMessage({ id: 'siteMonitor.entryTime', defaultMessage: '录入时间' }),
      field: 'createTime',
    },
    {
      label: formatMessage({ id: 'siteMonitor.activationTime', defaultMessage: '激活时间' }),
      field: 'activeTime',
    },
    {
      label: formatMessage({ id: 'siteMonitor.owningSite', defaultMessage: '所属站点' }),
      field: 'siteName',
    },
    {
      label: formatMessage({ id: 'siteMonitor.enteredBy', defaultMessage: '录入人' }),
      field: 'updateUserName',
    },
    {
      label: formatMessage({ id: 'device.onlineTime', defaultMessage: '在线时长' }),
      field: 'onlineTime',
    },
    {
      label: formatMessage({ id: 'device.lastOnlineTime', defaultMessage: '最近上线时间' }),
      field: 'sessionStartTime',
      show: equipData?.status !== 0,
    },
    {
      label: formatMessage({ id: 'device.lastOfflineTime', defaultMessage: '最近上线时间' }),
      field: 'offlineTime',
      show: equipData?.status === 0,
    },
  ];

  return (
    <>
      <Row className={className}>
        <Col flex={model === 'screen' ? '0 0 9.41vw' : '0 0 180px'}>
          <div className="dialog-product-logo-wrap">
            {loading ? (
              <Skeleton.Image active />
            ) : (
              <div
                className="dialog-product-logo"
                style={{ backgroundImage: `url(${equipData?.photos || equipmentImg})` }}
              />
            )}
          </div>
        </Col>
        <Col className="productInfo" flex="1">
          <Label
            title={formatMessage({ id: 'device.basicInformation', defaultMessage: '基本信息' })}
          />
          {loading ? (
            <>
              <Skeleton active paragraph={{ rows: 4 }} />
              <div className="flex">
                <div className="flex1">
                  <Skeleton.Button active />
                </div>
                <Skeleton.Button active />
              </div>
            </>
          ) : (
            <>
              <Detail items={equipInfoItems} data={equipData} />
              <div className="flex">
                <div className="flex1">
                  {productImg && (
                    <Button className="ant-btn-primary" type="primary" onClick={onClick}>
                      {formatMessage({
                        id: 'siteMonitor.productIntroduction',
                        defaultMessage: '产品介绍',
                      })}
                    </Button>
                  )}
                </div>
                <Button type="link" onClick={onEditClick}>
                  {formatMessage({
                    id: 'common.modifyBasicInformation',
                    defaultMessage: '修改基本信息',
                  })}
                </Button>
                {buttons}
              </div>
            </>
          )}
        </Col>
      </Row>
      <Dialog
        model={model}
        title={formatMessage({ id: 'siteMonitor.productIntroduction', defaultMessage: '产品介绍' })}
        open={openDialog}
        onCancel={onCancel}
        footer={null}
      >
        <img className="w-full" src={productImg} />
      </Dialog>
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

export default EquipInfo;
