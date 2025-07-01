/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-27 14:35:32
 * @LastEditTime: 2023-10-16 09:48:06
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\HwCharge\RealTime.tsx
 */

import React, { useState, useEffect } from 'react';
import { Skeleton, Row, Col } from 'antd';
import { RealTimeProps } from '@/components/ScreenDialog';
import Label from '@/components/Detail/DotLabel';
import useSubscribe from '@/pages/screen/useSubscribe';
import { getRelatedDevice } from '@/services/equipment';
import Detail from '@/components/Detail';
import type { DetailItem } from '@/components/Detail';
import { powerHourFormat } from '@/utils/format';
import { formatMessage } from '@/utils';

const RealTime: React.FC<RealTimeProps> = (props) => {
  const {
    id,
    loading,
    open = true,
    label = formatMessage({ id: 'siteMonitor.operationalInformation', defaultMessage: '运行信息' }),
    detailProps,
  } = props;

  const [relatedIds, setRelatedIds] = useState([]);
  const equipmentData = useSubscribe(id, open);

  // useEffect(() => {
  //   if (open && id) {
  //     getRelatedDevice(id).then((res) => {
  //       if (res?.data?.associatedIds) {
  //         setRelatedIds(res.data.associatedIds);
  //       }
  //     });
  //   }
  // }, [id, open]);

  const runItems: DetailItem[] = [
    {
      label: formatMessage({ id: 'siteMonitor.chargingVolumeToday', defaultMessage: '今日充电量' }),
      field: 'dayChargeElec',
      format: powerHourFormat,
    },
    {
      label: formatMessage({ id: 'siteMonitor.totalCharge', defaultMessage: '累计充电量' }),
      field: 'totalChargeElec',
      format: powerHourFormat,
    },
  ];

  return (
    <>
      {loading ? (
        <>
          <Skeleton.Button className="mb12" size="small" active />
          <Row>
            <Col span={12}>
              <Skeleton.Button className="mb12" size="small" active />
            </Col>
            <Col span={12}>
              <Skeleton.Button className="mb12" size="small" active />
            </Col>
          </Row>
        </>
      ) : (
        <>
          {typeof label == 'string' ? <Label title={label} /> : label}
          <Detail data={equipmentData || {}} items={runItems} column={4} {...(detailProps || {})} />
        </>
      )}
    </>
  );
};

export default RealTime;
