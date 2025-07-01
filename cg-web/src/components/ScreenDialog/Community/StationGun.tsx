/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-20 10:40:19
 * @LastEditTime: 2024-03-26 17:37:57
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\Community\StationGun.tsx
 */

import React, { useCallback, useState, useRef, useEffect, useMemo, useContext } from 'react';
import { message } from 'antd';
import { BetaSchemaForm } from '@ant-design/pro-components';
import type { ProFormColumnsType, ProFormInstance } from '@ant-design/pro-components';
import type { CommunityType } from './data.d';
import type { EquipFormType } from '@/components/EquipForm/data.d';
import { getEquipInfo, editEquipConfig, getThirdStation } from '@/services/equipment';
import { getModalProps } from '@/components/Dialog';
import { CommunityProps } from './';
import { OptionType } from '@/types';
import { formatMessage } from '@/utils';
import DeviceContext from '@/components/Device/Context/DeviceContext';

const StationGun: React.FC<CommunityProps> = (props) => {
  const { id, type, productConfigType, open, onOpenChange, model } = props;
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
              label: item?.siteName,
              value: item?.id,
            };
          });
        });
      } else {
        return Promise.resolve([]);
      }
    },
    [equipData?.productId],
  );

  const onFinish = useCallback(
    (formData) => {
      return editEquipConfig({
        deviceId: id,
        productId: equipData?.productId,
        paramConfigType: type,
        config: JSON.stringify(formData),
        productConfigType,
      }).then(({ data }) => {
        if (data) {
          message.success(formatMessage({ id: 'common.successSaved', defaultMessage: '保存成功' }));
          updateData?.();
          return true;
        }
      });
    },
    [id, equipData, productConfigType, updateData],
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

  const columns = useMemo<ProFormColumnsType<CommunityType>[]>(() => {
    const result: ProFormColumnsType<CommunityType>[] = [
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
      {
        title: formatMessage({
          id: 'device.anyChargingGunSequenceCode',
          defaultMessage: '任一充电枪序列码',
        }),
        dataIndex: 'anyGnSn',
        formItemProps: {
          rules: [
            {
              required: true,
              message:
                formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请填写' }) +
                formatMessage({
                  id: 'device.anyChargingGunSequenceCode',
                  defaultMessage: '任一充电枪序列码',
                }) +
                ' ID',
            },
          ],
        },
      },
    ];
    return result;
  }, [requestStation, productConfigType]);

  return (
    <>
      <BetaSchemaForm<CommunityType>
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

export default StationGun;
