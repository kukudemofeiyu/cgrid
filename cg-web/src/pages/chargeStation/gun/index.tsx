/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-20 15:36:38
 * @LastEditTime: 2025-02-20 16:26:44
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/chargeStation/gun/index.tsx
 */
import { ActionType, ProFormInstance } from '@ant-design/pro-components';
import React, { memo, useCallback, useRef, useState } from 'react';
import { columns, formColumns } from './helper';
import SchemaForm, { FormTypeEnum } from '@/components/SchemaForm';
import ProTable from '@/components/ProTable';

const Gun: React.FC = () => {

  const formRef = useRef<ProFormInstance>();
  const actionRef = useRef<ActionType>(null);
  const [open, setOpen] = useState(false);
  const [formInfo, setFormInfo] = useState({
    type: FormTypeEnum.Add,
    id: '',
  });

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
      grid={true}
      colProps={{
        span: 12,
      }}
    />
  </>;
};

export default memo(Gun);