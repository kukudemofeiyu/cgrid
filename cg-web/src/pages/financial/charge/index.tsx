/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-21 13:46:26
 * @LastEditTime: 2025-02-21 13:46:26
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/financial/charge/index.tsx
 */
import React, { memo } from 'react';
import Seperate from '../component/Seperate';
import { SeperateTypeEnum } from '@/utils/enum';

const Index: React.FC = () => {
  return <>
    <Seperate type={SeperateTypeEnum.Charge} />
  </>;
};

export default memo(Index);