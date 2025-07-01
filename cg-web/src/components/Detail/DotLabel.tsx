/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-08 10:42:14
 * @LastEditTime: 2023-07-24 17:07:38
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Detail\label.tsx
 */

import React from 'react';
import './index.less';

export type DotLabelProps = {
  title: React.ReactNode;
  className?: string;
  operate?: React.ReactNode;
};

const DotLabel: React.FC<DotLabelProps> = (props) => {
  return (
    <>
      <div className={`flex label-wrap ${props.className}`}>
        <span className="label-line" />
        <span className="flex1 label-title">{props.title}</span>
        {props.operate}
      </div>
    </>
  );
};

export default DotLabel;
