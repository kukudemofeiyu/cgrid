/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-01 15:14:53
 * @LastEditTime: 2023-09-01 11:43:20
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\Community\Station.tsx
 */

import React, { useCallback, useState, useRef, useEffect, useContext } from 'react';
import { message } from 'antd';
import { BetaSchemaForm } from '@ant-design/pro-components';
import type { ProFormColumnsType, ProFormInstance } from '@ant-design/pro-components';
import type { StationCommunityType } from './data';
import type { EquipFormType } from '@/components/EquipForm/data.d';
import { getEquipInfo, editEquipConfig, getThirdStation } from '@/services/equipment';
import { getModalProps } from '@/components/Dialog';
import type { CommunityProps } from './index';
import { OptionType } from '@/types';
import { formatMessage } from '@/utils';
import DeviceContext from '@/components/Device/Context/DeviceContext';

const Station: React.FC<CommunityProps> = (props) => {
  const { id, type, open, onOpenChange, model } = props;
  const formRef = useRef<ProFormInstance>();
  const [equipData, setEquipData] = useState<EquipFormType>();
  const { updateData } = useContext(DeviceContext);

  const modalProps = getModalProps(model);

  const requestStation = useCallback(
    (params) => {
      if (params?.loadDevice) {
        return getThirdStation({
          current: 1,
          pageSize: 10000,
          productId: equipData?.productId,
        }).then(({ data }) => {
          return data?.list?.map?.((item: any) => {
            return {
              label: item.siteName,
              value: item.id,
            };
          });
        });
      } else {
        return Promise.resolve([]);
      }
    },
    [equipData?.productId],
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
        formRef?.current?.setFieldsValue?.({ ...config, loadDevice: true });
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

  const columns: ProFormColumnsType<StationCommunityType>[] = [
    {
      dataIndex: 'loadDevice',
      hideInForm: true,
    },
    {
      title: formatMessage({ id: 'device.thirdPartySite', defaultMessage: '第三方站点' }),
      dataIndex: 'thirdSiteId',
      valueType: 'select',
      dependencies: ['loadDevice'],
      request: requestStation,
      formItemProps: {
        rules: [
          {
            required: true,
            message:
              formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }) +
              formatMessage({ id: 'device.thirdPartySite', defaultMessage: '第三方站点' }) +
              ' ID',
          },
        ],
      },
      fieldProps: (form) => {
        return {
          onChange: (_: any, option: OptionType) => {
            form?.setFieldValue('thirdSiteName', option?.label);
          },
          getPopupContainer: (triggerNode: any) => triggerNode?.parentElement,
        };
      },
    },
    {
      dataIndex: 'thirdSiteName',
      formItemProps: {
        hidden: true,
      },
    },
  ];

  return (
    <>
      <BetaSchemaForm<StationCommunityType>
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

export default Station;
