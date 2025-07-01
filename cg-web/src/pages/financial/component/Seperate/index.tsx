/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-21 11:40:31
 * @LastEditTime: 2025-02-21 11:40:31
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/financial/agent/index.tsx
 */

import ProTable from '@/components/ProTable';
import React, { memo, useMemo, useRef } from 'react';
import { getColumns, } from './helper';
import { ActionType } from '@ant-design/pro-components';
import { SeperateTypeEnum } from '@/utils/enum';

type SeperateType = {
  type: SeperateTypeEnum;
};

const Seperate: React.FC<SeperateType> = (props) => {
  const { type } = props;

  const actionRef = useRef<ActionType>(null);

  const columns = useMemo(() => {
    return getColumns(type);
  }, []);

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