/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-27 14:07:10
 * @LastEditTime: 2023-11-13 17:53:29
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Meter\RealTime.tsx
 */

import React from 'react';
import { Skeleton } from 'antd';
import Meter, { MeterSkeleton } from '@/components/Meter';
import { RealTimeProps } from '@/components/ScreenDialog';
import Label from '@/components/Detail/DotLabel';
import useSubscribe from '@/pages/screen/useSubscribe';
import { DetailProps } from '@/components/Detail';
import { formatMessage } from '@/utils';

const RealTime: React.FC<
  RealTimeProps & {
    label?: React.ReactNode;
    detailProps?: Omit<DetailProps, 'items' | 'data'>;
  }
> = (props) => {
  const {
    id,
    loading,
    open = true,
    label = formatMessage({ id: 'siteMonitor.operationalInformation', defaultMessage: '运行信息' }),
    detailProps,
  } = props;

  const equipmentData = useSubscribe(id, open);

  return (
    <>
      {loading ? (
        <>
          <Skeleton.Button className="mb12" size="small" active />
          <MeterSkeleton />
        </>
      ) : (
        <>
          {typeof label == 'string' ? <Label title={label} /> : label}
          <Meter data={equipmentData || {}} detailProps={detailProps} />
        </>
      )}
    </>
  );
};

export default RealTime;
