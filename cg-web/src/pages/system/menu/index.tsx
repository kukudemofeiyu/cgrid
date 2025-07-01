/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-24 14:25:02
 * @LastEditTime: 2025-02-24 14:59:21
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/system/menu/index.tsx
 */
import { ActionType, ProFormInstance } from '@ant-design/pro-components';
import React, { memo, useCallback, useRef, useState } from 'react';
import { columns, getFormColumn } from './helper';
import SchemaForm, { FormTypeEnum } from '@/components/SchemaForm';
import ProTable from '@/components/ProTable';
import { Modal } from 'antd';
import { getLocale, IntlProvider } from 'umi';
import IconSelector from '@/components/IconSelector';

const Agent: React.FC = () => {

  const formRef = useRef<ProFormInstance>();
  const actionRef = useRef<ActionType>(null);
  const [open, setOpen] = useState(false);
  const [menuIconName, setMenuIconName] = useState<any>();
  const [previewModalVisible, setPreviewModalVisible] = useState<boolean>(false);
  const [formInfo, setFormInfo] = useState({
    type: FormTypeEnum.Add,
    id: '',
  });

  const formColumns = getFormColumn(setPreviewModalVisible);

  const onAddClick = useCallback(() => {
    setFormInfo({
      type: FormTypeEnum.Add,
      id: '',
    });
    setOpen(true);
  }, []);

  const onEditClick = useCallback((record) => {
    setFormInfo({
      type: FormTypeEnum.Add,
      id: '',
    });
    setOpen(true);
  }, []);

  const onEvent = useCallback(() => {
    // show qrcode modal
  }, []);

  return <>
    <ProTable
      actionRef={actionRef}
      columns={columns}
      resizable={true}
      toolBarRenderOptions={{
        add: {
          onClick: onAddClick
        },
      }}
      option={{
        onDeleteChange(_, entity) {
        },
        onEditChange(_, entity) {
          onEditClick(entity);
        }
      }}
      onEvent={onEvent}
    />
    <SchemaForm
      formRef={formRef}
      width="552px"
      type={formInfo.type}
      columns={formColumns}
      open={open}
      onOpenChange={setOpen}
      shouldUpdate={false}
      id={formInfo.id}
      idKey="userId"
    // editData={editData}
    // addData={addData}
    // getData={getData}
    // beforeSubmit={beforeSubmit}
    // afterRequest={afterRequest}
    // onSuccess={onSuccess}
    />
    <Modal
      width={1200}
      visible={previewModalVisible}
      maskClosable={false}
      onCancel={() => {
        setPreviewModalVisible(false);
      }}
      footer={null}
    >
      <IntlProvider locale={getLocale()}>
        <IconSelector
          onSelect={(name: string) => {
            formRef.current?.setFieldsValue?.({ icon: name });
            setPreviewModalVisible(false);
          }}
        />
      </IntlProvider>
    </Modal>
  </>;
};

export default memo(Agent);