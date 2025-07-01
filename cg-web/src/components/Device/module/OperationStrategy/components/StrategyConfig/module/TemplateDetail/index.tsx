import React, { useState } from 'react';
import { FileSearchOutlined } from '@ant-design/icons';
import { formatMessage } from '@/utils';
import { Modal } from 'antd';
import Detail from '@/components/Detail';
import { detailItem } from '../../../StrategyTemplate/config';

type TemplateDetailProps = {
  values?: any;
};
const TemplateDetail: React.FC<TemplateDetailProps> = (props) => {
  const { values } = props;
  const [visible, setVisible] = useState(false);
  return (
    <>
      <FileSearchOutlined style={{ color: '#697078' }} onClick={() => setVisible(true)} />
      <Modal
        width={450}
        title={`${values.name}-${formatMessage({
          id: 'common.detail',
          defaultMessage: '查看详情',
        })}`}
        open={visible}
        destroyOnClose
        footer={false}
        onCancel={() => setVisible(false)}
      >
        <Detail
          data={{
            ...values?.config,
            systemOperatingMode: values?.systemOperatingMode,
          }}
          items={detailItem(values?.systemOperatingMode)}
          column={2}
        />
      </Modal>
    </>
  );
};

export default TemplateDetail;
