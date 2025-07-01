/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-20 14:41:41
 * @LastEditTime: 2025-02-20 14:41:41
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/order/component/Order/index.tsx
 */

import ProTable from '@/components/ProTable';
import React, { memo, useCallback, useMemo, useRef, useState } from 'react';
import { getColumns, orderType } from './helper';
import { ActionType, ProFormInstance } from '@ant-design/pro-components';
import SchemaForm, { FormTypeEnum } from '@/components/SchemaForm';

type OrderType = {
  type: orderType;
};

const Order: React.FC<OrderType> = (props) => {
  const { type } = props;

  const formRef = useRef<ProFormInstance>();
  const actionRef = useRef<ActionType>(null);
  const [open, setOpen] = useState(false);
  const [formInfo, setFormInfo] = useState({
    type: FormTypeEnum.Add,
    id: '',
  });

  const columns = useMemo(() => {
    return getColumns(type);
  }, [type]);

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
      toolBarRender={() => []}
      option={{
        onDetailChange(_, entity) {
        },
        onEditChange(_, entity) {
          onEditClick(entity);
        }
      }}
    />
  </>;
};

export default memo(Order);