/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-07-09 17:45:23
 * @LastEditTime: 2025-02-05 15:15:13
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/Device/module/BmuTabs/Table/index.tsx
 */

import React, { memo, useCallback, useContext, useMemo, useState } from 'react';
import { BmuType, MaxDataType } from '../type';
import Gird from './Gird';
import styles from './index.less';
import { formatMessage, getLocale } from '@/utils';
import Detail, { DetailItem } from '@/components/Detail';
import { initMaxData, items } from './helper';
import moment from 'moment';
import DeviceContext from '@/components/Device/Context/DeviceContext';
import { useSubscribe } from '@/hooks';

const Table: React.FC<BmuType> = (props) => {
  const { bmuData, onOpenChart, modelMap } = props;

  const { data: deviceData } = useContext(DeviceContext);

  const realTimeData = useSubscribe(deviceData?.deviceId, true);

  const [maxData, setMaxData] = useState<Record<string, any> & MaxDataType>(initMaxData);

  const detailItems = useMemo(() => {
    const result: DetailItem[] = [];
    items.forEach((item, index) => {
      result.push({
        ...item,
        unit:
          index > 1 ? modelMap?.['Temperature1']?.specs?.unit : modelMap?.['Voltage1']?.specs?.unit,
      });
    });
    if (!realTimeData?.b347) {
      result.splice(4);
    }
    return result;
  }, [modelMap, realTimeData]);

  const onDataChange = useCallback((bmuName?: string, data?: MaxDataType) => {
    setMaxData((prevData) => {
      const bmu = Number(bmuName?.replace?.(/[^0-9]/g, ''));
      if (data?.cell?.max?.index) {
        if (bmu == prevData.cell.max.bmu) {
          prevData.cell.max = { ...data.cell.max, bmu };
          prevData.maxCell = data.cell.max.value;
        } else if (data.cell.max.value >= prevData.cell.max.value) {
          if (
            data.cell.max.value != prevData.cell.max.value ||
            bmu <= (prevData.cell.max?.bmu as any)
          ) {
            prevData.cell.max = { ...data.cell.max, bmu };
            prevData.maxCell = data.cell.max.value;
          }
        }
      }
      if (data?.cell?.min?.index) {
        if (bmu == prevData.cell.min.bmu) {
          prevData.cell.min = { ...data.cell.min, bmu };
          prevData.minCell = data.cell.min.value;
        } else if (data.cell.min.value <= prevData.cell.min.value) {
          if (
            data.cell.min.value != prevData.cell.min.value ||
            bmu <= (prevData.cell.min?.bmu as any)
          ) {
            prevData.cell.min = { ...data.cell.min, bmu };
            prevData.minCell = data.cell.min.value;
          }
        }
      }
      if (data?.temp?.max?.index) {
        if (bmu == prevData.temp.max.bmu) {
          prevData.temp.max = { ...data.temp.max, bmu };
          prevData.maxTemp = data.temp.max.value;
        } else if (data.temp.max.value >= prevData.temp.max.value) {
          if (
            data.temp.max.value != prevData.temp.max.value ||
            bmu <= (prevData.temp.max?.bmu as any)
          ) {
            prevData.temp.max = { ...data.temp.max, bmu };
            prevData.maxTemp = data.temp.max.value;
          }
        }
      }
      if (data?.temp?.min?.index) {
        if (bmu == prevData.temp.min.bmu) {
          prevData.temp.min = { ...data.temp.min, bmu };
          prevData.minTemp = data.temp.min.value;
        } else if (data.temp.min.value <= prevData.temp.min.value) {
          if (
            data.temp.min.value != prevData.temp.min.value ||
            bmu <= (prevData.temp.min?.bmu as any)
          ) {
            prevData.temp.min = { ...data.temp.min, bmu };
            prevData.minTemp = data.temp.min.value;
          }
        }
      }
      if (data?.poleTemp?.max?.index) {
        if (bmu == prevData.poleTemp.max.bmu) {
          prevData.poleTemp.max = { ...data.poleTemp.max, bmu };
          prevData.maxPoleTemp = data.poleTemp.max.value;
        } else if (data.poleTemp.max.value >= prevData.poleTemp.max.value) {
          if (
            data.poleTemp.max.value != prevData.poleTemp.max.value ||
            bmu <= (prevData.poleTemp.max?.bmu as any)
          ) {
            prevData.poleTemp.max = { ...data.poleTemp.max, bmu };
            prevData.maxPoleTemp = data.poleTemp.max.value;
          }
        }
      }
      if (data?.poleTemp?.min?.index) {
        if (bmu == prevData.poleTemp.min.bmu) {
          prevData.poleTemp.min = { ...data.poleTemp.min, bmu };
          prevData.minPoleTemp = data.poleTemp.min.value;
        } else if (data.poleTemp.min.value <= prevData.poleTemp.min.value) {
          if (
            data.poleTemp.min.value != prevData.poleTemp.min.value ||
            bmu <= (prevData.poleTemp.min?.bmu as any)
          ) {
            prevData.poleTemp.min = { ...data.poleTemp.min, bmu };
            prevData.minPoleTemp = data.poleTemp.min.value;
          }
        }
      }
      return {
        ...prevData,
        time: moment().format(getLocale().dateTimeFormat),
      };
    });
  }, []);

  const girds = useMemo(() => {
    const result: React.ReactNode[] = [];
    bmuData?.forEach?.((item, index) => {
      result.push(
        <Gird
          key={item.deviceId}
          bmuName={item.aliasSn}
          sn={item.sn}
          deviceId={item.deviceId}
          modelMap={modelMap}
          onDataChange={onDataChange}
          onOpenChart={onOpenChart}
          voltageNum={realTimeData?.b345}
          temperatureNum={realTimeData?.b346}
          poleNum={realTimeData?.b347}
        />,
      );
    });
    return result;
  }, [bmuData, modelMap, onDataChange, onOpenChart, deviceData, realTimeData]);

  return (
    <>
      <div className="flex flex-start">
        <Detail
          items={detailItems}
          data={maxData}
          column={{
            xxl: 4,
            xl: 3,
            lg: 2,
            sm: 1,
            xs: 1,
          }}
          unitInLabel={true}
          labelStyle={{ width: 167, paddingRight: 14 }}
          ellipsis={false}
        />
        <div className={`flex1 tx-center ${styles.time}`}>{maxData?.time}</div>
      </div>
      <div className={`flex flex-center mb12 ${styles.legend}`}>
        <span className={`mr6 ${styles.icon}`} />
        <span>{formatMessage({ id: 'device.1012', defaultMessage: '最高值' })}</span>
        <span className={`ml24 mr6 ${styles.icon} ${styles.iconMin}`} />
        <span>{formatMessage({ id: 'device.1013', defaultMessage: '最低值' })}</span>
      </div>
      {girds}
    </>
  );
};

export default memo(Table);
