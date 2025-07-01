/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-27 14:41:09
 * @LastEditTime: 2023-06-27 14:41:09
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\HwChargeChild\RealTime.tsx
 */

import React from 'react';
import { Skeleton, Row, Col } from 'antd';
import { RealTimeProps } from '@/components/ScreenDialog';
import Label from '@/components/Detail/DotLabel';
import useSubscribe from '@/pages/screen/useSubscribe';
import Detail from '@/components/Detail';
import type { DetailItem } from '@/components/Detail';
import { powerHourFormat, useFormat } from '@/utils/format';
import { LabelTypeEnum } from '@/components/ScreenDialog';
import { formatMessage } from '@/utils';

const RealTime: React.FC<RealTimeProps> = (props) => {
  const { id, loading, open = true, detailProps, labelType = LabelTypeEnum.DotLabel } = props;

  const equipmentData = useSubscribe(id, open);

  const statusItems: DetailItem[] = [
    {
      label: 'A' + formatMessage({ id: 'siteMonitor.gunCondition', defaultMessage: '枪状态' }),
      field: 'a',
      format: useFormat,
    },
    {
      label: 'B' + formatMessage({ id: 'siteMonitor.gunCondition', defaultMessage: '枪状态' }),
      field: 'b',
      format: useFormat,
    },
  ];
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
          {labelType == LabelTypeEnum.DotLabel ? (
            <Label
              title={formatMessage({
                id: 'siteMonitor.statusInformation',
                defaultMessage: '状态信息',
              })}
            />
          ) : (
            <Detail.Label
              title={formatMessage({
                id: 'siteMonitor.statusInformation',
                defaultMessage: '状态信息',
              })}
            />
          )}
          <Detail
            data={equipmentData || {}}
            items={statusItems}
            column={4}
            {...(detailProps || {})}
          />
          {labelType == LabelTypeEnum.DotLabel ? (
            <Label
              title={formatMessage({
                id: 'siteMonitor.operationalInformation',
                defaultMessage: '运行信息',
              })}
            />
          ) : (
            <Detail.Label
              title={formatMessage({
                id: 'siteMonitor.operationalInformation',
                defaultMessage: '运行信息',
              })}
            />
          )}
          <Detail data={equipmentData || {}} items={runItems} column={4} {...(detailProps || {})} />
        </>
      )}
    </>
  );
};

export default RealTime;
