/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-16 14:03:18
 * @LastEditTime: 2024-01-11 15:40:01
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Meter\index.tsx
 */
import React, { useMemo } from 'react';
import { Card, Space, Skeleton } from 'antd';
import Detail, { DetailProps } from '../Detail';
import type { AnyMapType } from '@/utils/dictionary';
import styles from './index.less';
import { GridItemType, gridConfig } from './helper';

type MeterProps = {
  data: AnyMapType;
  detailProps?: Omit<DetailProps, 'items' | 'data'>;
  items?: GridItemType[];
};

const Meter: React.FC<MeterProps> = (props) => {
  const { data, detailProps, items } = props;

  const gridItem = useMemo(() => {
    return (items || gridConfig).map((item) => {
      return (
        <Card.Grid hoverable={false} key={item.title} style={{ width: item.width ?? '20%' }}>
          <div className="flex card-grid-title mb12">
            {item.icon ? <img src={item.icon} className={`mr12 ${styles.img}`} /> : ''}
            {item.title}
          </div>
          <Detail
            data={data || {}}
            items={item?.item || []}
            column={1}
            contentStyle={{ flex: 1, display: 'inline-block', textAlign: 'right' }}
            {...(detailProps || {})}
          />
        </Card.Grid>
      );
    });
  }, [data, detailProps]);

  return (
    <>
      <Card className="mb24">{gridItem}</Card>
    </>
  );
};

const MeterSkeleton: React.FC = () => {
  const grid = Array.from({ length: 9 }).map((_, index) => (
    <Card.Grid hoverable={false} style={{ width: '20%' }} key={index}>
      <Skeleton.Button className="mb8" size="small" active />
      <div>
        <Space direction="vertical">
          <Skeleton.Input size="small" active />
          <Skeleton.Input size="small" active />
          <Skeleton.Input size="small" active />
        </Space>
      </div>
    </Card.Grid>
  ));

  return (
    <>
      <Card>{grid}</Card>
    </>
  );
};

export { MeterSkeleton };

export default Meter;
