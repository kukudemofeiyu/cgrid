/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-15 14:50:15
 * @LastEditTime: 2023-12-01 10:25:02
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\CollectionModal\Button.tsx
 */
import React, { useCallback } from 'react';
import LineChartImg from '@/assets/image/line-chart.png';
import styles from './index.less';
import CollectionModal from '.';
import { useBoolean } from 'ahooks';
import { DetailItem } from '@/components/Detail';
import { DeviceModelType } from '@/types/device';

export type ButtonProps = {
  title: string;
  className?: string;
  deviceId?: string;
  collection: string;
  model?: DeviceModelType;
  onClick?: (item: DetailItem, value?: any, data?: Record<string, any>) => void;
};

const Button: React.FC<ButtonProps> = (props) => {
  const { title, children, className, deviceId, collection, model, onClick, ...restProps } = props;

  const [open, { setTrue, setFalse }] = useBoolean(false);

  const onIconClick = useCallback(() => {
    setTrue();
    onClick?.({} as any);
  }, [onClick]);

  return (
    <>
      {children || (
        <img
          src={LineChartImg}
          className={`${styles.img} ${className}`}
          onClick={onIconClick}
          {...restProps}
        />
      )}
      <CollectionModal
        title={title}
        open={open}
        onCancel={setFalse}
        deviceId={deviceId}
        collection={collection}
        model={model}
      />
    </>
  );
};

export default Button;
