/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-21 16:55:08
 * @LastEditTime: 2025-02-21 16:55:09
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/user/car/index.tsx
 */

import ProTable from '@/components/ProTable';
import React, { memo, useMemo, useRef } from 'react';
import { columns, } from './helper';
import { ActionType } from '@ant-design/pro-components';
import { Avatar, Card, Col, Row } from 'antd';

const Cash: React.FC = (props) => {

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

export default memo(Cash);
