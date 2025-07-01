/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-04-19 17:09:52
 * @LastEditTime: 2024-04-25 15:17:35
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Chart\ChartDate\index.tsx
 */

import { DatePicker, DatePickerProps, Radio, Select, Spin } from 'antd';
import React, { memo, useEffect, useState } from 'react';
import { chartTypeEnum } from '../config';
import moment, { Moment } from 'moment';
import { typeMap } from './helper';

type ChartDateType = {
  defaultType?: chartTypeEnum;
  loading?: boolean;
  onTypeChange?: (value: chartTypeEnum) => void;
  onChange?: (value: Moment | null) => void;
  switchType?: 'select' | 'button';
};

const ChartDate: React.FC<ChartDateType> = (props) => {
  const {
    defaultType = chartTypeEnum.Day,
    loading,
    onTypeChange,
    onChange,
    switchType = 'select',
  } = props;

  const [chartType, setChartType] = useState(defaultType);

  const onSelect = (value: chartTypeEnum) => {
    setChartType(value);
    onTypeChange?.(value);
  };

  useEffect(() => {
    onTypeChange?.(defaultType);
    onChange?.(moment());
  }, []);

  return (
    <>
      {switchType == 'select' && (
        <Select className="ml8" defaultValue={defaultType} options={typeMap} onSelect={onSelect} />
      )}
      {chartType != chartTypeEnum.Label && (
        <DatePicker
          className="ml8"
          picker={
            typeMap.find((item) => chartType == item.value)?.dateType as DatePickerProps['picker']
          }
          defaultValue={moment()}
          format={typeMap.find((item) => chartType == item.value)?.format}
          onChange={onChange}
          allowClear={false}
        />
      )}
      {switchType == 'button' && (
        <Radio.Group
          className="ml8"
          defaultValue={defaultType}
          options={typeMap}
          optionType="button"
          onChange={(e) => onSelect(e.target.value)}
          buttonStyle="solid"
        ></Radio.Group>
      )}
      {loading && <Spin className="ml8" />}
    </>
  );
};

export default memo(ChartDate);
