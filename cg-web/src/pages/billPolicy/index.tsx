/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-20 17:15:57
 * @LastEditTime: 2025-02-20 18:58:58
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/billPolicy/index.tsx
 */
import { ActionType, ProFormInstance } from '@ant-design/pro-components';
import React, { memo, useCallback, useRef, useState } from 'react';
import { columns, formColumns } from './helper';
import SchemaForm, { FormTypeEnum } from '@/components/SchemaForm';
import ProTable from '@/components/ProTable';
import { timeTypeOption } from '@/utils/dict';


const initialValues = {
  fee: [
    { timeType: '', electricBill: 0, serverBill: 0, },
    { timeType: '', electricBill: 0, serverBill: 0, },
    { timeType: '', electricBill: 0, serverBill: 0, },
    { timeType: '', electricBill: 0, serverBill: 0, },
  ],
};

Object.entries(timeTypeOption).forEach(([key, value], index) => {
  initialValues.fee[index].timeType = value;
});

const Charge: React.FC = () => {

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

export default memo(Charge);
