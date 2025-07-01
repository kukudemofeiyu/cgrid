/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-29 17:30:30
 * @LastEditTime: 2023-06-29 17:35:38
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Steps\index.tsx
 */
import React from 'react';
import { Steps as AntSteps, StepsProps } from 'antd';
import styles from './index.less';

const Steps: React.FC<StepsProps> = (props) => {
  const { className, ...restProps } = props;

  return (
    <>
      <AntSteps className={`${styles.steps} ${className}`} progressDot {...restProps} />
    </>
  );
};

export default Steps;
