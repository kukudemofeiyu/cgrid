/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-01-06 10:17:50
 * @LastEditTime: 2024-01-30 09:08:24
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\ElectricDiagram\index.tsx
 */

import type { DeviceDataType } from '@/services/equipment';
import React from 'react';
import styles from '../index.less';
import WindDiagram from '@/assets/image/device/风冷-bms.png';
import LiquidDiagram from '@/assets/image/device/液冷-bms.png';
import WindDiagramUS from '@/assets/image/device/风冷-bms_us.png';
import LiquidDiagramUS from '@/assets/image/device/液冷-bms_us.png';
import { DeviceTypeEnum } from '@/utils/dictionary';
import { formatMessage, getLocale } from '@/utils';
const isUs = getLocale().isEnUS;

type ElectricDiagramType = {
  deviceData?: DeviceDataType;
};

const ElectricDiagram: React.FC<ElectricDiagramType> = (props) => {
  const { deviceData } = props;

  return (
    <>
      <div className="card-wrap shadow p20 mb20">
        <label className={`${styles.label} mb12`}>
          {formatMessage({
            id: 'siteMonitor.electricalPrimaryDiagram',
            defaultMessage: '电气一次图',
          })}
        </label>
        <div className="tx-center">
          <img
            className={styles.diagram}
            src={
              deviceData?.productId == DeviceTypeEnum.Liquid2Energy
                ? isUs
                  ? LiquidDiagramUS
                  : LiquidDiagram
                : isUs
                ? WindDiagramUS
                : WindDiagram
            }
          />
        </div>
      </div>
    </>
  );
};

export default ElectricDiagram;
