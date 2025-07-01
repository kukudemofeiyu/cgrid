/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-21 13:46:37
 * @LastEditTime: 2025-02-21 13:46:56
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/financial/person/index.tsx
 */
import React, { memo } from 'react';
import Seperate from '../component/Seperate';
import { SeperateTypeEnum } from '@/utils/enum';

const Index: React.FC = () => {
  return <>
    <Seperate type={SeperateTypeEnum.Person} />
  </>;
};

export default memo(Index);