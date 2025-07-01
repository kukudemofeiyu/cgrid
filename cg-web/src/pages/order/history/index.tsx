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

const History: React.FC = () => {

  return <>
    <Order type={orderType.History} />
  </>;
};

export default memo(History);
