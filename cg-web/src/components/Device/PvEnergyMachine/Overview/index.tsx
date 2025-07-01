/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-11-15 15:04:50
 * @LastEditTime: 2023-11-17 10:12:37
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\PvEnergyMachine\Overview\index.tsx
 */

import React, { useMemo, useRef } from 'react';
import styles from '../index.less';
import Detail from '@/components/Detail';
import { useSize } from 'ahooks';
import { config, flowItems } from './helper';
import EnergyFlow from './EnergyFlow';
import { formatMessage } from '@/utils';

export type OverviewType = {
  realTimeData?: Record<string, any>;
};

const Overview: React.FC<OverviewType> = (props) => {
  const { realTimeData } = props;

  const containRef = useRef(null);
  const containSize = useSize(containRef);

  const scaleNum = useMemo(() => {
    const scaleWidth = (containSize?.width || 944) / 944;
    const scaleHeight = (containSize?.height || 431) / 431;
    return Math.min(scaleWidth, scaleHeight);
  }, [containSize]);

  const items = useMemo(() => {
    return config.map((item) => {
      const { left, top, width, height } = item.style;
      return (
        <>
          <div className={styles.node} style={{ left, top }}>
            <img src={item.icon} style={{ width, height }} />
            {item?.items?.length ? (
              <Detail data={realTimeData} items={item.items} column={1} />
            ) : (
              <></>
            )}
          </div>
        </>
      );
    });
  }, [realTimeData]);

  const points = useMemo(() => {
    return flowItems.map((item) => {
      return (
        <circle r="5" fill="#FF974A" key={item.pathId}>
          <animateMotion
            dur="2s"
            repeatCount="indefinite"
            keyPoints={[...item.keyPoints].join(';')}
            keyTimes="0;1"
          >
            <mpath xlinkHref={item.pathId} />
          </animateMotion>
        </circle>
      );
    });
  }, []);

  return (
    <>
      <div className="card-wrap shadow p20 mb20">
        <Detail.Label
          title={formatMessage({ id: 'device.overview', defaultMessage: '概览' })}
          size="small"
          showLine={false}
        ></Detail.Label>
        <div ref={containRef} className="tx-center">
          <div className={styles.overview} style={{ transform: `scale(${scaleNum})` }}>
            {items}
            <EnergyFlow className={styles.flow}>{points}</EnergyFlow>
          </div>
        </div>
      </div>
    </>
  );
};

export default Overview;
