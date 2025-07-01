/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-11-15 15:24:24
 * @LastEditTime: 2023-11-17 09:58:19
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\PvEnergyMachine\Run\index.tsx
 */

import React from 'react';
import Detail from '@/components/Detail';
import { runItems } from './helper';
import styles from '../index.less';
import { formatMessage } from '@/utils';

export type RunType = {
  realTimeData?: Record<string, any>;
};

const Run: React.FC<RunType> = (props) => {
  const { realTimeData } = props;

  return (
    <>
      <div className={`card-wrap shadow p20 ${styles.run}`}>
        <Detail.Label
          className="mb16"
          title={formatMessage({ id: 'siteMonitor.runningState', defaultMessage: '运行状态' })}
          size="small"
          showLine={false}
        ></Detail.Label>
        <Detail data={realTimeData} items={runItems} column={3} />
      </div>
    </>
  );
};

export default Run;
