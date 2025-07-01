/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-23 10:58:19
 * @LastEditTime: 2024-03-25 08:54:28
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\module\ChargeModule\index.tsx
 */

import YTProTable from '@/components/ProTable';
import React, { memo } from 'react';
import { columns } from './helper';
import { startUpFormat } from '@/utils/format';
import { parseToArray } from '@/utils';

type ChargeModuleType = {
  realTimeData?: Record<string, any>;
};

const ChargeModule: React.FC<ChargeModuleType> = (props) => {
  const { realTimeData } = props;

  const dataSource = parseToArray(realTimeData?.mmoduarr || []);

  dataSource?.forEach?.((item: any) => {
    item.mnfs = startUpFormat(item.mnfs);
  });

  return (
    <>
      <YTProTable
        className="mb16"
        search={false}
        options={false}
        columns={columns}
        toolBarRender={false}
        dataSource={dataSource}
        scroll={{ y: 'auto' }}
      />
    </>
  );
};

export default memo(ChargeModule);
