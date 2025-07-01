/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-26 15:32:02
 * @LastEditTime: 2023-07-26 15:58:27
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Field\index.tsx
 */
import React from 'react';
import CircleGreen from '@/assets/image/circle-green.png';
import CircleRed from '@/assets/image/circle-red.png';
import styles from './index.less';

export type valueEnum = {
  [key: string | number]: {
    text: string;
    icon?: string;
  };
};

export type FieldProps = {
  text?: string | number;
  valueEnum: valueEnum;
};

const iconMap = new Map([
  ['green', CircleGreen],
  ['red', CircleRed],
]);

const Field: React.FC<FieldProps> = (props) => {
  const { text, valueEnum } = props;

  return (
    <>
      <span className={styles.contain}>
        {valueEnum?.[text ?? '']?.icon && (
          <img className={styles.img} src={iconMap.get(valueEnum?.[text ?? '']?.icon || '')} />
        )}
        {valueEnum?.[text ?? '']?.text}
      </span>
    </>
  );
};

export default Field;
