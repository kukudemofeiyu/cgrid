import React, { useCallback, useEffect, useState, useRef, useContext } from 'react';
import { message } from 'antd';
import { ProConfigProvider, BetaSchemaForm } from '@ant-design/pro-components';
import type { ProFormColumnsType, ProFormInstance } from '@ant-design/pro-components';
import type { MeterCommunityType } from './data';
import type { EquipFormType } from '@/components/EquipForm/data.d';
import { getEquipInfo, editEquipConfig } from '@/services/equipment';
import { getModalProps } from '@/components/Dialog';
import type { CommunityProps } from './index';
import { tableTreeSelectValueTypeMap } from '@/components/TableSelect';
import type { TABLETREESELECTVALUETYPE } from '@/components/TableSelect';
import { omit } from 'lodash';
import { formatMessage } from '@/utils';
import DeviceContext from '@/components/Device/Context/DeviceContext';

type DeviceDataType = {
  id: string;
  deviceName: string;
};

const Meter: React.FC<CommunityProps> = (props) => {
  const { id, type, open, onOpenChange, model } = props;
  const formRef = useRef<ProFormInstance>();
  const [equipData, setEquipData] = useState<EquipFormType>();
  const { updateData } = useContext(DeviceContext);

  const modalProps = getModalProps(model);

  const onFinish = useCallback(
    (formData: MeterCommunityType<DeviceDataType[]>) => {
      const formParams = omit(formData, ['associateDevices']);
      formParams.associateIds = formData?.associateDevices?.map?.((item) => item.id).join(',');
      return editEquipConfig({
        deviceId: id,
        name: equipData?.name,
        productId: equipData?.productId,
        paramConfigType: type,
        config: JSON.stringify(formParams),
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
        config.associateDevices = data?.bindList?.map((item: any) => {
          return {
            ...item,
            id: item.deviceId,
          };
        });
        formRef?.current?.setFieldsValue?.(config);
      });
    }
  }, [open]);

  const columns: ProFormColumnsType<
    MeterCommunityType<DeviceDataType[]>,
    TABLETREESELECTVALUETYPE
  >[] = [
    {
      title: formatMessage({ id: 'device.currentRatio', defaultMessage: '电流变比' }),
      dataIndex: 'currentRatio',
      valueType: 'digit',
      formItemProps: {
        rules: [
          {
            required: true,
            message:
              formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请填写' }) +
              formatMessage({ id: 'device.currentRatio', defaultMessage: '电流变比' }),
          },
        ],
      },
    },
    {
      title: formatMessage({ id: 'device.voltageRatio', defaultMessage: '电压变比' }),
      dataIndex: 'voltageRatio',
      valueType: 'digit',
      formItemProps: {
        rules: [
          {
            required: true,
            message:
              formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请填写' }) +
              formatMessage({ id: 'device.voltageRatio', defaultMessage: '电压变比' }),
          },
        ],
      },
    },
  ];

  return (
    <>
      <ProConfigProvider valueTypeMap={tableTreeSelectValueTypeMap}>
        <BetaSchemaForm
          formRef={formRef}
          layoutType="ModalForm"
          title={formatMessage({
            id: 'device.setCommunicationInformation',
            defaultMessage: '设置通信信息',
          })}
          width="600px"
          visible={open}
          onVisibleChange={onOpenChange}
          columns={columns}
          onFinish={onFinish}
          modalProps={{
            centered: true,
            destroyOnClose: true,
            ...modalProps,
          }}
          grid={true}
          colProps={{
            span: 12,
          }}
        />
      </ProConfigProvider>
    </>
  );
};

export default Meter;
