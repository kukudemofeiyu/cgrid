/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-24 14:03:19
 * @LastEditTime: 2025-02-24 14:05:13
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/points/detail/index.tsx
 */
import ProTable from '@/components/ProTable';
import React, { memo, useCallback, useRef } from 'react';
import { columns, } from './helper';
import { ActionType } from '@ant-design/pro-components';

const Index: React.FC = (props) => {

  const actionRef = useRef<ActionType>(null);

  const onDetailChange = useCallback(() => { }, []);

  return <>
    <ProTable
      actionRef={actionRef}
      columns={columns}
      resizable={true}
      toolBarRender={() => []}
    />
  </>;
};

export default memo(Index);