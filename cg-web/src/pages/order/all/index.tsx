/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-20 13:51:46
 * @LastEditTime: 2025-02-20 14:19:27
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/order/all/index.tsx
 */

import ProTable from '@/components/ProTable';
import React, { memo, useCallback, useRef, useState } from 'react';
import Order, { } from '../component/Order';
import { orderType } from '../component/Order/helper';

const All: React.FC = () => {

  return <>
    <Order type={orderType.All} />
  </>;
};

export default memo(All);
