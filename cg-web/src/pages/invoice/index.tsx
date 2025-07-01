/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-21 14:51:14
 * @LastEditTime: 2025-02-21 15:34:35
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/invoice/index.tsx
 */

import ProTable from '@/components/ProTable';
import React, { memo, useRef } from 'react';
import { columns } from './helper';
import { ActionType } from '@ant-design/pro-components';


const Seperate: React.FC = (props) => {

  const actionRef = useRef<ActionType>(null);

  return <>
    <ProTable
      actionRef={actionRef}
      columns={columns}
      resizable={true}
      toolBarRender={() => []}
    />
  </>;
};

export default memo(Seperate);
