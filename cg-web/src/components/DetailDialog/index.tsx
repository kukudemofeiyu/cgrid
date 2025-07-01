/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-30 15:12:51
 * @LastEditTime: 2024-04-16 16:39:27
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DetailDialog\index.tsx
 */
import React from 'react';
import { Modal, Button } from 'antd';
import type { ModalProps } from 'antd';
import Detail from '../Detail';
import type { DetailProps } from '../Detail';
import { formatMessage } from '@/utils';

export type DetailDialogProps = ModalProps & {
  detailProps: DetailProps;
  prepend?: React.ReactNode;
  append?: React.ReactNode;
};

const DetailDialog: React.FC<DetailDialogProps> = (props) => {
  const { detailProps, onCancel, prepend, append, confirmLoading, ...reseProps } = props;

  return (
    <>
      <Modal
        title={formatMessage({ id: 'common.view', defaultMessage: '详情' })}
        maskClosable={false}
        footer={[
          <Button key="confirm" type="primary" onClick={onCancel} loading={confirmLoading}>
            {formatMessage({ id: 'common.ok', defaultMessage: '确定' })}
          </Button>,
        ]}
        onCancel={onCancel}
        {...reseProps}
      >
        {prepend}
        <Detail {...detailProps} />
        {append}
      </Modal>
    </>
  );
};

export default DetailDialog;
