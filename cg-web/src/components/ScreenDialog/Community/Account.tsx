/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-01 15:52:41
 * @LastEditTime: 2023-06-02 08:57:20
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\Community\AccountCommunity.tsx
 */
import React, { useCallback, useEffect, useState, useRef, useContext } from 'react';
import { message } from 'antd';
import { BetaSchemaForm } from '@ant-design/pro-components';
import type { ProFormColumnsType, ProFormInstance } from '@ant-design/pro-components';
import type { MeterCommunityType } from './data';
import type { EquipFormType } from '@/components/EquipForm/data.d';
import { getEquipInfo, editEquipConfig } from '@/services/equipment';
import { getModalProps } from '@/components/Dialog';
import type { CommunityProps } from './index';
import { formatMessage } from '@/utils';
import DeviceContext from '@/components/Device/Context/DeviceContext';

type AccountType = CommunityProps & {
  userLabel?: string;
  passwordLabel?: string;
};

const Account: React.FC<AccountType> = (props) => {
  const {
    id,
    type,
    open,
    onOpenChange,
    model,
    userLabel = `mqtt${formatMessage({ id: 'common.userName', defaultMessage: '用户名' })}`,
    passwordLabel = `mqtt${formatMessage({ id: 'common.password', defaultMessage: '密码' })}`,
  } = props;
  const formRef = useRef<ProFormInstance>();
  const [equipData, setEquipData] = useState<EquipFormType>();
  const { updateData } = useContext(DeviceContext);

  const modalProps = getModalProps(model);

  useEffect(() => {
    if (open) {
      formRef?.current?.resetFields?.();
      getEquipInfo({ deviceId: id }).then(({ data }) => {
        setEquipData(data || {});
        let config = (data || {})?.config || '{}';
        try {
          config = JSON.parse(config);
        } catch (e) {
          config = {};
        }
        formRef?.current?.setFieldsValue?.(config);
      });
    }
  }, [open]);

  const onFinish = useCallback(
    (formData) => {
      return editEquipConfig({
        deviceId: id,
        productId: equipData?.productId,
        paramConfigType: type,
        config: JSON.stringify(formData),
      }).then(({ data }) => {
        if (data) {
          message.success(formatMessage({ id: 'common.successSaved', defaultMessage: '保存成功' }));
          updateData?.();
          return true;
        }
      });
    },
    [id, equipData, updateData],
  );

  const columns: ProFormColumnsType<MeterCommunityType>[] = [
    {
      title: userLabel,
      dataIndex: 'userName',
      formItemProps: {
        rules: [
          {
            required: true,
            message:
              formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请填写' }) + userLabel,
          },
        ],
      },
    },
    {
      title: passwordLabel,
      dataIndex: 'password',
      valueType: 'password',
      formItemProps: {
        rules: [
          {
            required: true,
            message:
              formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请填写' }) + passwordLabel,
          },
        ],
      },
    },
  ];

  return (
    <>
      <BetaSchemaForm<MeterCommunityType>
        formRef={formRef}
        layoutType="ModalForm"
        title={formatMessage({
          id: 'device.setCommunicationInformation',
          defaultMessage: '设置通信信息',
        })}
        width="460px"
        visible={open}
        onVisibleChange={onOpenChange}
        columns={columns}
        onFinish={onFinish}
        modalProps={{
          centered: true,
          destroyOnClose: true,
          ...modalProps,
        }}
      />
    </>
  );
};

export default Account;
