/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-21 11:40:31
 * @LastEditTime: 2025-02-21 11:40:31
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/financial/agent/index.tsx
 */
import React, { memo } from 'react';
import Seperate from '../component/Seperate';
import { SeperateTypeEnum } from '@/utils/enum';

const Index: React.FC = () => {
  return <>
    <Seperate type={SeperateTypeEnum.Agent} />
  </>;
};

export default memo(Index);