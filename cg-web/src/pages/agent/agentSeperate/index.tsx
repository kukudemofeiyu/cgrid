/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-21 11:21:54
 * @LastEditTime: 2025-02-21 11:22:30
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/agent/agentSeperate/index.tsx
 */

import React, { memo } from 'react';
import Seperate from '../platformSeperate';
import { SeperateTypeEnum } from '@/utils/enum';

const Index: React.FC = () => {
  return <>
    <Seperate type={SeperateTypeEnum.Agent} />
  </>;
};

export default memo(Index);
