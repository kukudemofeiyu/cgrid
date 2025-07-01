/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-13 23:29:03
 * @LastEditTime: 2024-01-10 14:01:22
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Detail\LineLabel.tsx
 */
import React from 'react';
import { Divider, DividerProps } from 'antd';
import styles from './LineLabel.less';
import { Size } from '@/utils/dictionary';

export type LineLabelProps = {
  title?: React.ReactNode;
  className?: string;
  labelClassName?: string;
  showLine?: boolean;
  size?: keyof typeof Size;
  bold?: boolean;
  dividerProps?: DividerProps;
};

export const LineLabel: React.FC<LineLabelProps> = (props) => {
  const {
    title,
    className = '',
    labelClassName = '',
    showLine = true,
    children,
    size,
    bold = true,
    dividerProps,
  } = props;

  return (
    <>
      <div className={`flex ${className}`}>
        <label className={`flex1 ${styles.label} ${labelClassName} ${size} ${bold ? 'bold' : ''}`}>
          {title}
        </label>
        {children}
      </div>
      {showLine && (
        <Divider {...dividerProps} className={`${dividerProps?.className || ''} mt12`} />
      )}
    </>
  );
};

export default LineLabel;
