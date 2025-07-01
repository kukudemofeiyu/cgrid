/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-21 10:11:13
 * @LastEditTime: 2025-02-21 14:39:35
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/divider/divider/index.tsx
 */
import { ActionType, ProFormInstance } from '@ant-design/pro-components';
import React, { memo, useCallback, useMemo, useRef, useState } from 'react';
import { columns, formColumns } from './helper';
import SchemaForm, { FormTypeEnum } from '@/components/SchemaForm';
import ProTable from '@/components/ProTable';

const Agent: React.FC = () => {

  const formRef = useRef<ProFormInstance>();
  const actionRef = useRef<ActionType>(null);
  const [open, setOpen] = useState(false);
  const [formInfo, setFormInfo] = useState({
    type: FormTypeEnum.Add,
    id: '',
  });

  const initialValues = useMemo(() => {
    return {
      remain: '',
    }
  }, []);

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
      initialValues={initialValues}
    />
  </>;
};

export default memo(Agent);