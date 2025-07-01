import { useEffect } from 'react';
import { Form } from 'antd';
import YTModalForm from '@/components/YTModalForm';
import { FormOperations } from '@/components/YTModalForm/typing';
import { ProFormColumnsType, ProRequestData } from '@ant-design/pro-components';

export type FormReadBaseProps = {
  visible: boolean;
  onVisibleChange: (state: boolean) => void;
  operations: FormOperations;
  initialValues?: any;
  id?: number;
  onSuccess?: () => void;
};

export type FormReadProps<FormData, Param = Record<string, any>> = {
  titleRead: string;
  requestRead: () => ProRequestData<FormData, Param>;
  columns: ProFormColumnsType[];
};

export type FormReadComposeProps<FormData, Param> = FormReadProps<FormData, Param> &
  FormReadBaseProps;

const DEFAULT_PROPS = {
  layout: 'vertical' as 'vertical',
  labelCol: { flex: 'auto' },
  wrapperCol: { flex: 'auto' },
};

export const YTFormRead = <FormData = any, Param = Record<string, any>>(
  props: FormReadComposeProps<FormData, Param>,
) => {
  const { operations, titleRead, columns, requestRead, id, ...resetProps } = props;
  const [form] = Form.useForm();

  useEffect(() => {
    if (props.visible) {
      form?.resetFields();
      if (id) {
        requestRead().then(({ data }) => {
          form?.setFieldsValue(data);
        });
      }
    }
  }, [props.visible, form, id, requestRead]);

  return (
    <YTModalForm<FormData, Param>
      form={form}
      title={titleRead}
      {...DEFAULT_PROPS}
      layoutType={'ModalForm'}
      columns={columns}
      operations={operations}
      {...resetProps}
    />
  );
};
