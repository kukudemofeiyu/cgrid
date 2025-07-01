/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-24 14:00:15
 * @LastEditTime: 2025-02-24 14:00:15
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/points/user/index.tsx
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
      option={{
        onDetailChange: onDetailChange
      }}
    />
  </>;
};

export default memo(Index);
