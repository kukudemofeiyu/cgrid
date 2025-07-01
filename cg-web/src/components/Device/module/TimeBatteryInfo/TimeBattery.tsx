/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-05-29 15:59:08
 * @LastEditTime: 2024-05-31 16:30:07
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\module\TimeBatteryInfo\TimeBattery.tsx
 */

import YTProTable from '@/components/ProTable';
import React, { memo, useEffect, useState } from 'react';
import { useRequest } from 'umi';
import { getTimeBatteryData } from './service';
import { columns, detailItems } from './helper';
import Detail from '@/components/Detail';
import { isEmpty } from '@/utils';

type TimeBatteryType = {
  deviceId?: string;
  params?: Record<string, any>;
  date?: string;
};

const TimeBattery: React.FC<TimeBatteryType> = (props) => {
  const { deviceId, params, date } = props;

  const [totalData, setTotalData] = useState<Record<string, any>>({});

  const {
    data: dataSource,
    loading,
    run,
  } = useRequest(getTimeBatteryData, {
    manual: true,
    formatResult(res) {
      const result: Record<string, any>[] = [{}];
      let totalCharge: number | undefined,
        totalDischarge: number | undefined,
        hourTotalCharge: number | undefined,
        hourTotalDischarge: number | undefined;
      res?.data?.forEach?.((item, index) => {
        if (index % 4 == 0 && index) {
          if (!isEmpty(hourTotalCharge)) {
            const value = hourTotalCharge || 0;
            result[result.length - 1].totalCharge = Number(value.toFixed(2));
            totalCharge = (totalCharge ?? 0) + value;
          }
          if (!isEmpty(hourTotalDischarge)) {
            const value = hourTotalDischarge || 0;
            result[result.length - 1].totalDischarge = Number(value.toFixed(2));
            totalDischarge = (totalDischarge ?? 0) + value;
          }
          hourTotalCharge = undefined;
          hourTotalDischarge = undefined;
          result.push({});
        }
        result[result.length - 1]['time' + (index % 4)] = `${item?.startTime}-${item?.endTime}`;
        result[result.length - 1]['charge' + (index % 4)] = item.charge;
        result[result.length - 1]['discharge' + (index % 4)] = item.discharge;
        if (!isEmpty(item.charge)) {
          hourTotalCharge = (hourTotalCharge ?? 0) + (item.charge ?? 0) * 1;
        }
        if (!isEmpty(item.discharge)) {
          hourTotalDischarge = (hourTotalDischarge ?? 0) + (item.discharge ?? 0) * 1;
        }
      });
      setTotalData({
        charge: totalCharge,
        discharge: totalDischarge,
      });
      return result;
    },
  });

  useEffect(() => {
    if (deviceId) {
      run({
        ...params,
        deviceId,
        date,
      });
    }
  }, [deviceId, date]);

  return (
    <>
      <Detail
        className="detail-center detail-two-center"
        items={detailItems}
        data={totalData}
        unitInLabel
        column={2}
      />
      <YTProTable
        className="mb16"
        search={false}
        options={false}
        columns={columns}
        toolBarRender={false}
        dataSource={dataSource}
        scroll={{ y: 'auto' }}
        pagination={false}
        loading={loading}
        size="small"
      />
    </>
  );
};

export default memo(TimeBattery);
