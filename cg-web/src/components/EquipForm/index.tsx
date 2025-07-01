/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-10 11:19:17
 * @LastEditTime: 2024-01-03 09:20:26
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EquipForm\index.tsx
 */
import React, { useEffect, useState, useCallback } from 'react';
import { Form, message } from 'antd';
import { useRequest, useModel } from 'umi';
import Dialog from '@/components/Dialog';
import { PlusOutlined } from '@ant-design/icons';
import { ProForm, ProFormText, ProFormSelect, ProFormUploadButton } from '@ant-design/pro-form';
import type { EquipFormType } from './data.d';
import { editData, getData, addData, getProductTypes } from './service';
import type { OptionType } from '@/types';
import { FormTypeEnum } from '@/components/SchemaForm';
import { api } from '@/services';
import { getProductModelByType } from '@/services/equipment';
import { arrayToMap, formatMessage, parseToObj } from '@/utils';
import { FormattedMessage } from 'umi';
import { ProFormDependency } from '@ant-design/pro-components';
import { DeviceTypeEnum } from '@/utils/dictionary';

export type EquipFormProps = {
  id?: string;
  model?: string;
  open: boolean;
  onCancel: () => void;
  type: FormTypeEnum;
  onSuccess?: () => void;
  isStationChild?: boolean;
  initialValues?: Record<string, any>;
};

const EquipForm: React.FC<EquipFormProps> = (props) => {
  const { id, model, open, onCancel, type, onSuccess, isStationChild, initialValues } = props;
  const [modelOption, setModelOption] = useState<OptionType[]>();
  const { stationId } = useModel('station', (stateData) => ({ stationId: stateData?.state.id }));

  const { loading: getLoading, run: runGet } = useRequest(getData, {
    manual: true,
  });
  const { loading: addLoading, run: runAdd } = useRequest(addData, {
    manual: true,
  });
  const { loading: editLoading, run: runEdit } = useRequest(editData, {
    manual: true,
  });
  const [form] = Form.useForm<EquipFormType>();

  const triggerSubmit = () => {
    form.submit();
  };

  const beforeUpload = useCallback((file, field) => {
    const formData = new FormData();
    formData.append('file', file);
    api.uploadFile(formData).then(({ data }) => {
      if (data.url) {
        form.setFieldValue(field, [{ url: data.url }]);
      }
    });
    return false;
  }, []);

  const onFinish = useCallback((formData: EquipFormType) => {
    const request = type == FormTypeEnum.Add ? runAdd : runEdit;
    return request({
      ...formData,
      ...(isStationChild ? { siteId: stationId } : {}),
      deviceId: id,
      photos: formData?.photosList ? formData.photosList.map((item) => item.url).join(',') : '',
    }).then((data) => {
      if (data) {
        message.success(formatMessage({ id: 'common.successSaved', defaultMessage: '保存成功' }));
        onSuccess?.();
        onCancel?.();
      }
    });
  }, []);

  const requestProductType = useCallback(() => {
    return getProductTypes().then(({ data = {} }) => {
      const result: OptionType[] = [];
      data?.forEach?.((item: any) => {
        let config: Record<string, any> = {};
        try {
          config = JSON.parse(item.config);
        } catch {
          config = {};
        }
        if (config?.selectDisplay != 0 || FormTypeEnum.Edit == type) {
          result.push({
            label: item.name,
            value: item.id,
          });
        }
      });
      return result;
    });
  }, [type]);

  const requestProductModel = useCallback((productTypeId) => {
    if (productTypeId) {
      getProductModelByType({ productTypeId }).then(({ data }) => {
        setModelOption(
          data?.map?.((item: any) => {
            return {
              label: item.model,
              value: item.id,
              deviceConfig: parseToObj(item.config)?.deviceConfig,
            };
          }),
        );
      });
    }
  }, []);

  const onValuesChange = useCallback(({ productTypeId, subsystemId, productId }) => {
    if (productTypeId) {
      requestProductModel(productTypeId); //获取产品型号
      form.setFieldValue('productId', undefined);
    }
  }, []);

  const getValueFromEvent = useCallback((e) => {
    if (Array.isArray(e)) {
      return e;
    }
    return e?.fileList;
  }, []);

  useEffect(() => {
    if (open) {
      form.resetFields();
      if (type === FormTypeEnum.Edit || type === FormTypeEnum.Detail) {
        runGet(id).then((data) => {
          form.setFieldsValue({ ...data, photosList: data?.photos ? [{ url: data.photos }] : [] });
          requestProductModel(data?.productTypeId);
        });
      }
    }
  }, [open]);

  return (
    <>
      <Dialog
        model={model}
        open={open}
        title={
          type === FormTypeEnum.Add
            ? formatMessage({ id: 'common.new', defaultMessage: '新建' })
            : formatMessage({ id: 'common.edit', defaultMessage: '编辑' })
        }
        width="600px"
        onCancel={onCancel}
        onOk={triggerSubmit}
        confirmLoading={getLoading || editLoading || addLoading}
      >
        <ProForm<EquipFormType>
          form={form}
          autoFocusFirstInput
          onFinish={onFinish}
          onValuesChange={onValuesChange}
          submitter={false}
          grid={true}
          colProps={{
            span: 12,
          }}
          initialValues={initialValues}
        >
          {/* {isStationChild ? (
            <></>
          ) : (
            <ProFormSelect
              label={<FormattedMessage id="equipmentList.affSite" defaultMessage="所属站点" />}
              name="siteId"
              //placeholder="请选择"
              request={requestStations}
              fieldProps={{
                getPopupContainer: (triggerNode) => triggerNode.parentElement,
              }}
            />
          )} */}
          <ProFormSelect
            label={formatMessage({ id: 'common.productType', defaultMessage: '产品类型' })}
            name="productTypeId"
            request={requestProductType}
            fieldProps={{
              getPopupContainer: (triggerNode) => triggerNode.parentElement,
            }}
            rules={[
              {
                required: true,
                message:
                  formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }) +
                  formatMessage({ id: 'common.productType', defaultMessage: '产品类型' }),
              },
            ]}
            disabled={type == FormTypeEnum.Edit}
          />
          <ProFormSelect
            label={formatMessage({ id: 'common.model', defaultMessage: '产品型号' })}
            name="productId"
            //placeholder="请选择"
            options={modelOption}
            fieldProps={{
              getPopupContainer: (triggerNode) => triggerNode.parentElement,
            }}
            rules={[
              {
                required: true,
                message:
                  formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }) +
                  formatMessage({ id: 'common.model', defaultMessage: '产品型号' }),
              },
            ]}
            disabled={type == FormTypeEnum.Edit}
          />
          <ProFormText
            label={<FormattedMessage id="common.deviceName" defaultMessage="设备名称" />}
            name="name"
            //placeholder="请输入"
            rules={[
              {
                required: true,
                message: formatMessage({
                  id: 'common.pleaseEnterDevice',
                  defaultMessage: '请输入设备名称',
                }),
              },
            ]}
          />
          <ProFormDependency name={['productId']}>
            {({ productId }) => {
              const option = modelOption?.find((item) => item.value == productId);
              return (
                <ProFormText
                  label={
                    option?.deviceConfig?.snName ? (
                      option?.deviceConfig?.snName
                    ) : (
                      <FormattedMessage id="common.equipmentSerial" defaultMessage="设备序列号" />
                    )
                  }
                  name="sn"
                  rules={[
                    {
                      required: true,
                      message: formatMessage({
                        id: 'common.pleaseEnterSn',
                        defaultMessage: '请输入设备序列号',
                      }),
                    },
                  ]}
                />
              );
            }}
          </ProFormDependency>
          <ProFormUploadButton
            label={<FormattedMessage id="equipmentList.devicePhoto" defaultMessage="设备照片" />}
            name="photosList"
            valuePropName="fileList"
            getValueFromEvent={getValueFromEvent}
            title={<FormattedMessage id="common.uploadPhoto" defaultMessage="上传图片" />}
            max={1}
            accept="image/*"
            fieldProps={{
              name: 'file',
              listType: 'picture-card',
              beforeUpload: (file) => beforeUpload(file, 'photosList'),
            }}
            icon={
              <div>
                <PlusOutlined />
              </div>
            }
            colProps={{
              span: 24,
            }}
          />
        </ProForm>
      </Dialog>
    </>
  );
};

export default EquipForm;
