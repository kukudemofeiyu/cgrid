/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-07-09 14:32:35
 * @LastEditTime: 2024-07-11 09:26:19
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\module\BmuTabs\Tab\index.tsx
 */

import React, { memo, useCallback, useContext, useMemo, useState } from 'react';
import Chart from '@/components/Chart';
import { formatMessage, isEmpty } from '@/utils';
import { Tabs, TabsProps } from 'antd';
import styles from '../index.less';
import DeviceContext from '@/components/Device/Context/DeviceContext';
import { DeviceTypeEnum } from '@/utils/dictionary';
import { useSubscribe } from '@/hooks';
import { bumConfigMap, chartOptions, defaultLables, getFieldByLabel } from '../helper';
import { merge } from 'lodash';
import { defaultLineOption } from '@/components/Chart/config';
import { BmuType } from '../type';

const BmuTabs: React.FC<BmuType> = memo((props) => {
  const { bmuMap, onOpenChart, modelMap } = props;

  const { data: deviceData } = useContext(DeviceContext);
  const [activeKey, setActiveKey] = useState('0');
  const bmuData = useSubscribe(
    bmuMap?.get?.('BMU-' + (Number(activeKey) * 1 + 1) || '') || '',
    true,
  );

  const onTabChange = useCallback((key) => {
    setActiveKey(key);
  }, []);

  const onChartClick = useCallback(
    (params) => {
      let name = '';
      if (params?.componentType == 'xAxis') {
        name = params?.value;
      } else if (params?.componentType == 'series') {
        name = params?.name;
      }
      const field = getFieldByLabel(name);
      onOpenChart?.(bmuMap?.get?.('BMU-' + (Number(activeKey) + 1)) || '', {
        title: `BMU-${Number(activeKey) * 1 + 1}-${name}`,
        collection: field,
      });
    },
    [activeKey, onOpenChart, bmuMap],
  );

  const chartOption = useMemo(() => {
    const source = [
      [
        'product',
        formatMessage({ id: 'siteMonitor.voltage', defaultMessage: '电压' }),
        formatMessage({ id: 'siteMonitor.temperature', defaultMessage: '温度' }),
      ],
    ];
    const labels = bumConfigMap.get(deviceData?.productId)?.labels || defaultLables;
    labels.forEach((item) => {
      const field = getFieldByLabel(item);
      let value: any = '',
        resultValue;
      if (item.indexOf(formatMessage({ id: 'siteMonitor.cell', defaultMessage: '电芯' })) > -1) {
        value = bmuData?.[field] ?? '';
      } else {
        value = bmuData?.[field] ?? '';
      }
      resultValue = value;
      if (!isEmpty(resultValue)) {
        resultValue = resultValue * 1;
        if (isNaN(resultValue)) {
          resultValue = value;
        }
      }
      source.push([
        item,
        ...(item.indexOf(formatMessage({ id: 'siteMonitor.cell', defaultMessage: '电芯' })) > -1
          ? [resultValue, '']
          : ['', resultValue]),
      ]);
    });
    const result = merge({}, defaultLineOption, chartOptions, {
      dataset: {
        source,
      },
    });
    if (
      deviceData?.productId &&
      (deviceData?.productId as any) >= DeviceTypeEnum.Wind2BatteryStack
    ) {
      result.yAxis[0].name =
        formatMessage({ id: 'siteMonitor.voltage', defaultMessage: '电压' }) + '(mV)';
      result.tooltip.formatter = (params: any) => {
        const { value, name } = (params || {})[0];
        return (
          name +
          '：' +
          (value[1] === '' ? (value[2] === '' ? '' : value[2] + '℃') : value[1] + 'mV')
        );
      };
    }
    return result;
  }, [bmuData, deviceData, activeKey]);

  const tabItems = useMemo<TabsProps['items']>(() => {
    const bmuTabNum = bumConfigMap.get(deviceData?.productId)?.bmuNum || 10;
    return Array.from({
      length: bmuTabNum,
    }).map((_, index) => {
      return {
        key: index + '',
        label: 'BMU' + (index + 1),
      };
    });
  }, [deviceData?.productId]);

  return (
    <>
      <Tabs className={styles.tab} items={tabItems} onChange={onTabChange} />
      <Chart
        className="mb16"
        option={chartOption}
        style={{ height: 300 }}
        onEvents={{
          click: onChartClick,
        }}
      />
    </>
  );
});

export default BmuTabs;
