/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-05 09:11:33
 * @LastEditTime: 2024-04-19 13:51:24
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\module\ChargeTerminal\index.tsx
 */

import React, { memo, useCallback, useContext, useEffect, useMemo, useRef, useState } from 'react';
import Cabinet from '@/components/EnergyInfo/Cabinet';
import { Col, Row, Spin, Divider, Button, Carousel } from 'antd';
import DeviceContext from '../../Context/DeviceContext';
import { useRequest } from 'umi';
import { getWholeDeviceTree } from '@/services/equipment';
import { DeviceProductTypeEnum } from '@/utils/dictionary';
import { CarouselRef } from 'antd/lib/carousel';
import styles from './index.less';
import { ArrowLeftOutlined, ArrowRightOutlined } from '@ant-design/icons';
import Trend from '../../components/Trend';
import { formatMessage } from '@/utils';

const ChargeTerminal: React.FC = () => {
  const { data: deviceData } = useContext(DeviceContext);
  const [activeIndex, setActiveIndex] = useState(0);
  const carouselRef = useRef<CarouselRef>(null);

  const {
    loading: loading,
    data: energyData,
    run,
  } = useRequest(getWholeDeviceTree, {
    manual: true,
  });

  const terminalsDeviceData = useMemo(() => {
    const result =
      energyData?.children?.filter?.(
        (item) => item.productTypeId == DeviceProductTypeEnum.ChargeTerminal,
      ) || [];
    result.forEach((item) => {
      item.deviceId = item.id;
    });
    return result;
  }, [energyData]);

  const carouselItems = useMemo(() => {
    return terminalsDeviceData?.map((item) => {
      return (
        <>
          <div className="tx-center">{item.name}</div>
          <Cabinet key={item.deviceId} deviceData={item} />
        </>
      );
    });
  }, [terminalsDeviceData]);

  const onCarouselChange = useCallback((index) => {
    setActiveIndex(index);
  }, []);

  useEffect(() => {
    if (deviceData?.deviceId) {
      run({ deviceId: deviceData?.deviceId });
    }
  }, [deviceData?.deviceId]);

  return (
    <>
      {loading ? (
        <div className="tx-center mt24">
          <Spin className="ml12" />
        </div>
      ) : (
        <Row className="mb20" gutter={20}>
          <Col span={24}>
            <Divider />
          </Col>
          <Col span={14}>
            {activeIndex !== 0 && (
              <Button
                className={styles.leftArrowBox}
                shape="circle"
                icon={<ArrowLeftOutlined />}
                onClick={() => carouselRef?.current?.prev?.()}
              />
            )}
            {activeIndex != terminalsDeviceData.length - 1 && (
              <Button
                className={styles.rightArrowBox}
                shape="circle"
                icon={<ArrowRightOutlined />}
                onClick={() => carouselRef?.current?.next?.()}
              />
            )}
            <Carousel ref={carouselRef} afterChange={onCarouselChange}>
              {carouselItems}
            </Carousel>
          </Col>
          <Col span={10}>
            <Trend
              label={formatMessage({
                id: 'device.terminalChargingTrends',
                defaultMessage: '终端充电趋势',
              })}
              className="mt20"
              deviceData={terminalsDeviceData[activeIndex]}
            />
          </Col>
        </Row>
      )}
    </>
  );
};

export default memo(ChargeTerminal);
