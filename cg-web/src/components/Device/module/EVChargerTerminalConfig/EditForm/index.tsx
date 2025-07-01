import { Modal, Form } from 'antd';
import { ProFormText } from '@ant-design/pro-form';
import type { ConfigDataType } from '../data';
import { formatMessage } from '@/utils';
import { useEffect } from 'react';

export type DetailProps = {
  onCancel: () => void;
  visible: boolean;
  values: any;
  onSubmit: (values: ConfigDataType) => Promise<void>;
};
const AddForm: React.FC<DetailProps> = (props) => {
  const { visible, onCancel, onSubmit, values } = props;
  const [form] = Form.useForm();

  useEffect(() => {
    if (visible) {
      form.resetFields();
      form.setFieldsValue({
        name: values.deviceName,
      });
    }
  }, [form, values.deviceName, visible]);
  const handleCancel = () => {
    onCancel();
  };
  const handleFinish = async (value: Record<string, any>) => {
    value.deviceId = values.id;
    onSubmit(value);
  };
  const handleOk = () => {
    form.submit();
  };
  return (
    <Modal
      width={800}
      title={formatMessage({ id: 'common.edit', defaultMessage: '编辑' })}
      visible={visible}
      destroyOnClose
      onOk={handleOk}
      onCancel={handleCancel}
    >
      <Form form={form} onFinish={handleFinish} layout="vertical">
        <ProFormText
          name="name"
          label={formatMessage({
            id: 'device.ConfigName',
            defaultMessage: '名称',
          })}
          width="xl"
          placeholder={formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' })}
          rules={[
            {
              required: true,
              message: formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }),
            },
          ]}
        />
      </Form>
    </Modal>
  );
};
export default AddForm;
