/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-20 14:55:11
 * @LastEditTime: 2025-02-20 15:03:38
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/order/seat/index.tsx
 */

import React, { memo } from 'react';
import Order, { } from '../component/Order';
import { orderType } from '../component/Order/helper';

const Seat: React.FC = () => {

  return <>
    <Order type={orderType.Seat} />
  </>;
};

export default memo(Seat);
