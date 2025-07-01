/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-04-25 14:26:38
 * @LastEditTime: 2023-06-15 10:36:39
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Dialog\index.tsx
 */
import React, { ReactNode, useState, useMemo } from 'react';
import { Modal, Spin } from 'antd';
import type { ModalProps } from 'antd';
import IconClose from '@/assets/image/screen/dialog/close.png';
import './index.less';
import DialogContext from './DialogContext';
import { formatMessage } from '@/utils';

export type DialogProps = ModalProps & {
  model?: string;
  loading?: boolean;
};

export const getModalProps = (model: string | undefined, wrapClassName = '') => {
  return model === 'screen'
    ? {
        closeIcon: <img className="close" src={IconClose} />,
        wrapClassName: `dialog-screen ${wrapClassName}`,
      }
    : {
        wrapClassName: `dialog-equipment ${wrapClassName}`,
      };
};

const Dialog: React.FC<DialogProps> = (props) => {
  const {
    model,
    title = formatMessage({ id: 'siteMonitor.deviceDetails', defaultMessage: '设备详情' }),
    width = '1200px',
    loading,
    wrapClassName,
    bodyStyle = {},
    ...restProps
  } = props;

  const dialogContext = useMemo(() => {
    return { model };
  }, [model]);

  const ModalBodyStyle = useMemo(() => {
    return width === '1200px'
      ? {
          height: '780px',
          overflow: 'auto',
          ...bodyStyle,
        }
      : bodyStyle;
  }, [width, bodyStyle]);

  const modalProps = getModalProps(model, wrapClassName);

  return (
    <Modal
      title={title}
      width={width}
      {...modalProps}
      centered
      bodyStyle={ModalBodyStyle}
      {...restProps}
      maskClosable={false}
    >
      <DialogContext.Provider value={dialogContext}>
        {loading ? <Spin /> : props.children}
      </DialogContext.Provider>
    </Modal>
  );
};

export default Dialog;

export { DialogContext };
