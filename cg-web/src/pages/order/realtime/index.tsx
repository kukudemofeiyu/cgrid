/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-20 14:55:11
 * @LastEditTime: 2025-02-20 14:55:11
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/order/refund/index.tsx
 */

import React, { memo } from 'react';
import Order, { } from '../component/Order';
import { orderType } from '../component/Order/helper';

const Realtime: React.FC = () => {

  return <>
    <Order type={orderType.Realtime} />
  </>;
};

export default memo(Realtime);
