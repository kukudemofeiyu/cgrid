import { useEffect, useCallback } from 'react';
import { Form, message } from 'antd';
import YTModalForm from '@/components/YTModalForm';
import { FormOperations } from '@/components/YTModalForm/typing';
import type { ProFormColumnsType, ProRequestData } from '@ant-design/pro-components';

export type FormUpdateBaseProps = {
  visible: boolean;
  onVisibleChange: (state: boolean) => void;
  operations: FormOperations;
  initialValues?: any;
  id?: number;
  onSuccess?: () => void;
};

export type FormUpdateProps<T, U> = {
  titleUpdate: string;
  titleCreate: string;
  requestUpdate: ProRequestData<T, U>;
  requestCrate: ProRequestData<T, U>;
  requestRead: () => ProRequestData<T, U>;
  columns: ProFormColumnsType[];
};

export type FormUpdateComposeProps<T, U> = FormUpdateProps<T, U> & FormUpdateBaseProps;

const DEFAULT_PROPS = {
  layout: 'vertical' as 'vertical',
  labelCol: { flex: 'auto' },
  wrapperCol: { flex: 'auto' },
};

export const YTFormUpdate = <FormData = any, Param = Record<string, any>>(
  props: FormUpdateComposeProps<FormData, Param>,
) => {
  const {
    operations,
    titleCreate,
    titleUpdate,
    requestUpdate,
    requestCrate,
    columns,
    requestRead,
    onSuccess,
    ...resetProps
  } = props;
  const isCreate = operations === FormOperations.CREATE;
  const title = isCreate ? titleCreate : titleUpdate;
  const [form] = Form.useForm();

  const onFinish = useCallback(
    (formData) => {
      const run = isCreate ? requestCrate : requestUpdate;
      return run({ ...formData }, {}).then(({ data, code }) => {
        if (code === 200 || data) {
          message.success(formatMessage({ id: 'common.successSaved', defaultMessage: '保存成功' }));
          onSuccess?.();
          return true;
        }
      });
    },
    [isCreate, onSuccess, requestCrate, requestUpdate],
  );

  useEffect(() => {
    if (props.visible) {
      form?.resetFields();
      if (!isCreate) {
        requestRead()?.then(({ data }) => {
          form?.setFieldsValue(data);
        });
      }
    }
  }, [props.visible, form, requestRead, isCreate]);

  return (
    <YTModalForm<FormData>
      form={form}
      title={title}
      layoutType={'ModalForm'}
      columns={columns}
      operations={operations}
      onFinish={onFinish}
      {...DEFAULT_PROPS}
      {...resetProps}
    />
  );
};
