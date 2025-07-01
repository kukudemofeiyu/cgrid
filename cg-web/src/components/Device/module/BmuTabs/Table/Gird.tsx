/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-07-10 11:14:21
 * @LastEditTime: 2025-01-15 10:51:20
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/Device/module/BmuTabs/Table/Gird.tsx
 */

import React, { memo, useCallback, useContext, useMemo } from 'react';
import styles from './index.less';
import { bumConfigMap, cellText, defaultLables, getFieldByLabel, poleTempText, tempText } from '../helper';
import DeviceContext from '@/components/Device/Context/DeviceContext';
import { useSubscribe } from '@/hooks';
import { DeviceModelType } from '@/types/device';
import { MaxDataType } from '../type';
import { merge } from 'lodash';
import { formatMessage, getPlaceholder } from '@/utils';
import Detail from '@/components/Detail';

type GridType = {
  bmuName?: string;
  sn?: string;
  deviceId?: string;
  modelMap?: Record<string, DeviceModelType>;
  onDataChange?: (bmuName?: string, data?: MaxDataType) => void;
  onOpenChart?: (
    deviceId: string,
    collectionInfo: {
      title: string;
      collection: string;
    },
  ) => void;
  voltageNum?: number;
  temperatureNum?: number;
  poleNum?: number;
};

const Grid: React.FC<GridType> = (props) => {
  const { bmuName, deviceId, sn, modelMap, onDataChange, onOpenChart, voltageNum, temperatureNum, poleNum } = props;

  const { data: deviceData } = useContext(DeviceContext);

  const realTimeData = useSubscribe(deviceId, true);

  const maxData = useMemo(() => {
    const result = {
      cell: {
        max: {
          index: 0,
          value: Number.MIN_SAFE_INTEGER,
        },
        min: {
          index: 0,
          value: Number.MAX_SAFE_INTEGER,
        },
      },
      temp: {
        max: {
          index: 0,
          value: Number.MIN_SAFE_INTEGER,
        },
        min: {
          index: 0,
          value: Number.MAX_SAFE_INTEGER,
        },
      },
      poleTemp: {
        max: {
          index: 0,
          value: Number.MIN_SAFE_INTEGER,
        },
        min: {
          index: 0,
          value: Number.MAX_SAFE_INTEGER,
        },
      },
    };
    const config = bumConfigMap.get(deviceData?.productId)?.labels || defaultLables;
    const voltage = (voltageNum || config.voltage || defaultLables.voltage) * 1;
    for (let i = voltage; i > 0; i--) {
      const field = 'Voltage' + i;
      if (realTimeData[field] >= result.cell.max.value) {
        result.cell.max.index = i;
        result.cell.max.value = realTimeData[field];
      }
      if (realTimeData[field] <= result.cell.min.value) {
        result.cell.min.index = i;
        result.cell.min.value = realTimeData[field];
      }
    }
    const temperature = (temperatureNum || config.temperature || defaultLables.temperature) * 1;
    for (let i = temperature; i > 0; i--) {
      const field = 'Temperature' + i;
      if (realTimeData[field] >= result.temp.max.value) {
        result.temp.max.index = i;
        result.temp.max.value = realTimeData[field];
      }
      if (realTimeData[field] <= result.temp.min.value) {
        result.temp.min.index = i;
        result.temp.min.value = realTimeData[field];
      }
    }
    const poleTemperature = (poleNum || config.temperature || defaultLables.temperature) * 1;
    for (let i = poleTemperature; i > 0; i--) {
      const field = 'PoleTemp' + i;
      if (realTimeData[field] >= result.poleTemp.max.value) {
        result.poleTemp.max.index = i;
        result.poleTemp.max.value = realTimeData[field];
      }
      if (realTimeData[field] <= result.poleTemp.min.value) {
        result.poleTemp.min.index = i;
        result.poleTemp.min.value = realTimeData[field];
      }
    }
    onDataChange?.(bmuName, result);
    return result;
  }, [realTimeData, deviceData]);

  const onClick = useCallback(
    (name: string, field: string) => {
      onOpenChart?.(deviceId || '', {
        title: `${bmuName}-${name}`,
        collection: field,
      });
    },
    [onOpenChart, bmuName, deviceId],
  );

  const items = useMemo(() => {
    const config = bumConfigMap.get(deviceData?.productId)?.labels || defaultLables;
    const voltage = (voltageNum || config.voltage || defaultLables.voltage) * 1;
    const voltageCom = Array.from({ length: voltage }).map((item, index) => {
      const num = index + 1;
      const field = 'Voltage' + num;
      let className = '';
      if (maxData.cell.min.index == num) {
        className = styles.min;
      }
      if (maxData.cell.max.index == num) {
        className = styles.max;
      }
      return (
        <div
          key={field}
          className={`${styles.box} ${className}`}
          onClick={() => onClick(cellText + num, field)}
        >
          <div className={styles.dot}>{num}</div>
          <div>{getPlaceholder(realTimeData[field])}</div>
          {/* <span>{modelMap?.[field]?.specs?.unit}</span> */}
        </div>
      );
    });
    const temperature = (temperatureNum || config.temperature || defaultLables.temperature) * 1;
    const temperatureCom = Array.from({ length: temperature }).map((item, index) => {
      const num = index + 1;
      const field = 'Temperature' + num;
      let className = '';
      if (maxData.temp.min.index == num) {
        className = styles.min;
      }
      if (maxData.temp.max.index == num) {
        className = styles.max;
      }
      return (
        <div
          key={field}
          className={`${styles.box} ${styles.temp} ${className}`}
          onClick={() => onClick(tempText + num, field)}
        >
          <div className={styles.dot}>{num}</div>
          <div>{getPlaceholder(realTimeData[field])}</div>
          {/* <span>{modelMap?.[field]?.specs?.unit}</span> */}
        </div>
      );
    });

    let poleCom = <></>;
    if (poleNum) {
      const poleChild = Array.from({ length: poleNum * 1 }).map((item, index) => {
        const num = index + 1;
        const field = 'PoleTemp' + num;
        let className = '';
        if (maxData.poleTemp.min.index == num) {
          className = styles.min;
        }
        if (maxData.poleTemp.max.index == num) {
          className = styles.max;
        }
        return (
          <div
            key={field}
            className={`${styles.box} ${styles.temp} ${className}`}
            onClick={() => onClick(poleTempText + num, field)}
          >
            <div className={styles.dot}>{num}</div>
            <div>{getPlaceholder(realTimeData[field])}</div>
            {/* <span>{modelMap?.[field]?.specs?.unit}</span> */}
          </div>
        );
      });
      poleCom = <>
        <Detail.Label title={formatMessage({ id: 'device.1105', defaultMessage: '极柱温度' }) + '(°C)'} />
        {poleChild}
      </>;
    }

    return <>
      <Detail.Label title={formatMessage({ id: 'device.cellVoltage', defaultMessage: '单体电压' }) + `(${modelMap?.['Voltage1']?.specs?.unit ?? ''})`} />
      {voltageCom}
      <Detail.Label title={formatMessage({ id: 'device.cellTemperature', defaultMessage: '单体温度' }) + '(°C)'} />
      {temperatureCom}
      {poleCom}
    </>;
  }, [realTimeData, deviceData, modelMap, maxData, onClick]);

  return (
    <>
      <div className={`${styles.card}`}>
        <Detail.Label title={`${bmuName}(${getPlaceholder(sn)})`} />
        <div>{items}</div>
      </div>
    </>
  );
};

export default memo(Grid);
