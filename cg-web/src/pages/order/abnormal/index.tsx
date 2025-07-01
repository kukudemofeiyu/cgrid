/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-20 13:51:46
 * @LastEditTime: 2025-02-20 14:28:48
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/order/abnormal/index.tsx
 */

import React, { memo } from 'react';
import Order, { } from '../component/Order';
import { orderType } from '../component/Order/helper';

const Abnormal: React.FC = () => {

  return <>
    <Order type={orderType.Abnormal} />
  </>;
};

export default memo(Abnormal);
