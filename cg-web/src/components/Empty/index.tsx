/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-11 19:13:40
 * @LastEditTime: 2023-05-11 19:39:51
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Empty\index.tsx
 */
import React from 'react';
import { Empty as AntEmpty } from 'antd';
import EmptyIcon from '@/assets/image/empty.png';

const Empty: React.FC = () => {
  return <AntEmpty style={{ margin: '40px' }} image={EmptyIcon} />;
};

export default Empty;
