/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-06-27 10:49:21
 * @LastEditTime: 2023-11-13 11:01:55
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\YtCharge\RealTime.tsx
 */
import React, { useState, useEffect } from 'react';
import { Skeleton, Row, Col } from 'antd';
import Meter, { MeterSkeleton } from '@/components/Meter';
import { RealTimeProps } from '@/components/ScreenDialog';
import Label from '@/components/Detail/DotLabel';
import useSubscribe from '@/pages/screen/useSubscribe';
import { getRelatedDevice, getGuns } from '@/services/equipment';
import { arrayToMap, formatMessage } from '@/utils';
import Detail from '@/components/Detail';
import type { DetailItem } from '@/components/Detail';
import { useFormat, powerHourFormat } from '@/utils/format';
import { LabelTypeEnum } from '@/components/ScreenDialog';

const RealTime: React.FC<RealTimeProps> = (props) => {
  const { id, loading, open = true, detailProps, labelType = LabelTypeEnum.DotLabel } = props;

  const [relatedIds, setRelatedIds] = useState<string[]>([]);
  const [aGunId, setAGunId] = useState('');
  const [bGunId, setBGunId] = useState('');
  const realTimeData = useSubscribe(id, open);
  const aGunData = useSubscribe(aGunId, open);
  const bGunData = useSubscribe(bGunId, open);
  const meterData = useSubscribe(relatedIds, open);

  useEffect(() => {
    if (open && id) {
      getRelatedDevice(id).then((res) => {
        setRelatedIds([...(res?.data?.associatedIds || []), id]);
      });
      getGuns(id).then(({ data = [] }) => {
        const gunMap: Record<string, any> = arrayToMap(data || [], 'key', 'deviceId');
        setAGunId(gunMap.AGun);
        setBGunId(gunMap.BGun);
      });
    }
  }, [id, open]);

  const aStatusItems: DetailItem[] = [
    {
      label: 'A' + formatMessage({ id: 'siteMonitor.gunCondition', defaultMessage: '枪状态' }),
      field: 'Status',
      format: useFormat,
    },
  ];
  const bStatusItems: DetailItem[] = [
    {
      label: 'B' + formatMessage({ id: 'siteMonitor.gunCondition', defaultMessage: '枪状态' }),
      field: 'Status',
      format: useFormat,
    },
  ];
  const runItems: DetailItem[] = [
    {
      label: formatMessage({ id: 'siteMonitor.chargingVolumeToday', defaultMessage: '今日充电量' }),
      field: 'dayChargeElec',
      format: powerHourFormat,
      showExtra: false,
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
          <MeterSkeleton />
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
          <Row>
            <Col span={12}>
              <Detail data={aGunData} items={aStatusItems} column={2} {...(detailProps || {})} />
            </Col>
            <Col span={12}>
              <Detail data={bGunData} items={bStatusItems} column={2} {...(detailProps || {})} />
            </Col>
          </Row>
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
          <Detail data={realTimeData} items={runItems} column={4} {...(detailProps || {})} />
          <Meter
            data={meterData}
            detailProps={{
              extral: detailProps?.extral || undefined,
              colon: false,
              valueStyle: { flex: 1, textAlign: 'right' },
            }}
          />
        </>
      )}
    </>
  );
};

export default RealTime;
