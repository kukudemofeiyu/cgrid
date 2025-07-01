/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-05-29 15:49:29
 * @LastEditTime: 2024-05-30 11:49:04
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\module\TimeBatteryInfo\index.tsx
 */

import { DatePicker, Tabs, TabsProps } from 'antd';
import React, { memo, useCallback, useContext, useMemo, useState } from 'react';
import DeviceContext from '../../Context/DeviceContext';
import { formatMessage, getLocale } from '@/utils';
import TimeBattery from './TimeBattery';
import moment from 'moment';
import Detail from '@/components/Detail';

const TimeBatteryInfo: React.FC = () => {
  const { data: deviceData } = useContext(DeviceContext);
  const [date, setDate] = useState(moment().format(getLocale().dateFormat));

  const items: TabsProps['items'] = useMemo(() => {
    return [
      {
        key: 'energy',
        label: formatMessage({ id: 'device.1007', defaultMessage: '储能充放电量' }),
        children: (
          <TimeBattery deviceId={deviceData?.deviceId} date={date} params={{ chargeType: 4 }} />
        ),
      },
    ];
  }, [deviceData?.deviceId, date]);

  const onChange = useCallback((_, value) => {
    setDate(value);
  }, []);

  return (
    <>
      <Detail.Label
        title={formatMessage({ id: 'device.1006', defaultMessage: '储能分时计量数据' })}
      />
      <Tabs
        items={items}
        tabBarExtraContent={
          <DatePicker
            defaultValue={moment()}
            allowClear={false}
            format={getLocale().dateFormat}
            onChange={onChange}
          />
        }
      />
    </>
  );
};

export default memo(TimeBatteryInfo);
