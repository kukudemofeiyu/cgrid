/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-14 11:28:59
 * @LastEditTime: 2024-03-15 11:08:37
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\Cabinet\Entity\WindPvFirewood\index.tsx
 */

import React from 'react';
import Front from './Front';
import Detail from '@/components/Detail';
import { formatMessage } from '@/utils';
import Back from './Back';

const WindPvFirewood: React.FC = (props) => {
  return (
    <>
      <Detail.Label
        title={formatMessage({ id: 'device.frontView', defaultMessage: '前视图' })}
        showLine={false}
      />
      <Front {...props} />
      <Detail.Label
        title={formatMessage({ id: 'device.rearView', defaultMessage: '后视图' })}
        showLine={false}
      />
      <Back {...props} />
    </>
  );
};

export default WindPvFirewood;
