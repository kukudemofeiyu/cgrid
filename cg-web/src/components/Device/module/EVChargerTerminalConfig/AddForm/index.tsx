import { Modal, Form } from 'antd';
import { ProFormText, ProFormSelect } from '@ant-design/pro-form';
import type { ConfigDataType } from '../data';
import { formatMessage } from '@/utils';
import { useEffect, useContext, useState } from 'react';
import { getProductModelByType } from '@/services/equipment';
import DeviceContext from '@/components/Device/Context/DeviceContext';

export type DetailProps = {
  onCancel: () => void;
  visible: boolean;
  onSubmit: (values: ConfigDataType) => Promise<void>;
};
const AddForm: React.FC<DetailProps> = (props) => {
  const { visible, onCancel, onSubmit } = props;
  const [form] = Form.useForm();
  const { data: deviceData } = useContext(DeviceContext);
  const [modeloptions, setModeloptions] = useState([]);

  const requestProductType = () => {
    getProductModelByType({ productTypeId: 547 }).then(({ data }) => {
      setModeloptions(data || []);
    });
  };
  useEffect(() => {
    if (visible) {
      form.resetFields();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
    requestProductType();
  }, [visible]);
  const handleCancel = () => {
    onCancel();
  };
  const handleFinish = async (value: Record<string, any>) => {
    onSubmit(value);
  };
  const handleOk = () => {
    form.submit();
  };
  return (
    <Modal
      width={800}
      title={formatMessage({ id: 'common.new', defaultMessage: '新建' })}
      visible={visible}
      destroyOnClose
      onOk={handleOk}
      onCancel={handleCancel}
    >
      <Form form={form} onFinish={handleFinish} layout="vertical">
        <ProFormSelect
          options={modeloptions}
          width="xl"
          name="productId"
          fieldProps={{
            fieldNames: {
              label: 'model',
              value: 'id',
            },
          }}
          label={formatMessage({
            id: 'device.model',
            defaultMessage: '型号',
          })}
          placeholder={formatMessage({
            id: 'common.pleaseSelect',
            defaultMessage: '请选择',
          })}
          rules={[
            {
              required: true,
              message: formatMessage({
                id: 'common.pleaseSelect',
                defaultMessage: '请选择',
              }),
            },
          ]}
        />
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
        <ProFormText
          name="sn"
          label={formatMessage({
            id: 'device.serialNumber',
            defaultMessage: '序列号',
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
