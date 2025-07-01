import React, { useEffect, useCallback, useMemo } from 'react';
import { Form, message } from 'antd';
import { useRequest, useModel } from 'umi';
import Dialog from '@/components/Dialog';
import { PlusOutlined } from '@ant-design/icons';
import { ProForm, ProFormUploadButton } from '@ant-design/pro-form';
import type { EquipFormType } from './data';
import { editData, getData, addData, bindDevice } from './service';
import { FormTypeEnum } from '@/components/SchemaForm';
import { api } from '@/services';
import { formatMessage } from '@/utils';
import { FormattedMessage } from 'umi';
import { Descriptions } from 'antd';
import Detail from '../Detail';

export type EquipFormProps = {
  id?: string;
  model?: string;
  open: boolean;
  onCancel: () => void;
  type: string;
  onSuccess?: () => void;
  isStationChild?: boolean;
  initialValues?: Record<string, any>;
  descValues: any;
};

const SiteConfigNewDevice: React.FC<EquipFormProps> = (props) => {
  const { id, model, open, onCancel, type, onSuccess, isStationChild, initialValues, descValues } =
    props;
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
  const { run: runBindDevice } = useRequest(bindDevice, {
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

  const onFinish = useCallback(
    (formData: EquipFormType) => {
      const request = runBindDevice;
      return request({
        ...formData,
        ...(isStationChild ? { siteId: stationId } : {}),
        deviceId: descValues?.deviceId,
        photos: formData?.photosList ? formData.photosList.map((item) => item.url).join(',') : '',
      }).then((data) => {
        if (data) {
          message.success(formatMessage({ id: 'common.successSaved', defaultMessage: '保存成功' }));
          onSuccess?.();
          onCancel?.();
        }
      });
    },
    [descValues],
  );

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
          form.setFieldsValue({ ...data, photosList: data?.url ? [{ url: data.url }] : [] });
        });
      }
    }
  }, [open]);

  const items = [
    {
      label: formatMessage({ id: 'equipmentList.affTeam', defaultMessage: '所属单元' }),
      field: 'subsystemName',
    },
    {
      label: formatMessage({ id: 'common.productType', defaultMessage: '产品类型' }),
      field: 'productTypeName',
    },
    {
      label: formatMessage({ id: 'common.model', defaultMessage: '产品型号' }),
      field: 'model',
    },
    {
      label: formatMessage({ id: 'common.deviceSn', defaultMessage: '设备序列号' }),
      field: 'sn',
    },
    {
      label: formatMessage({ id: 'common.deviceName', defaultMessage: '设备名称' }),
      field: 'name',
    },
  ];

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
        width="552px"
        onCancel={onCancel}
        onOk={triggerSubmit}
        confirmLoading={getLoading || editLoading || addLoading}
      >
        <ProForm<EquipFormType>
          form={form}
          autoFocusFirstInput
          onFinish={onFinish}
          //onValuesChange={onValuesChange}
          submitter={false}
          grid={true}
          colProps={{
            span: 12,
          }}
          initialValues={initialValues}
        >
          <Detail items={items} data={descValues} column={2} />
          <ProFormUploadButton
            label={<FormattedMessage id="equipmentList.devicePhoto" defaultMessage="设备照片" />}
            name="photosList"
            valuePropName="fileList"
            getValueFromEvent={getValueFromEvent}
            title={<FormattedMessage id="common.uploadPhoto" defaultMessage="上传图片" />}
            max={3}
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

export default SiteConfigNewDevice;
