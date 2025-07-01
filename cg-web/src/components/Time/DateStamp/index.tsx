/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-11-30 11:18:34
 * @LastEditTime: 2024-05-13 15:14:12
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Time\DateStamp\index.tsx
 */
import React, { memo, useCallback, useMemo } from 'react';
import { DatePicker, DatePickerProps } from 'antd';
import moment, { Moment } from 'moment';
import { getLocale } from '@/utils';

export type DateStampType = Omit<DatePickerProps, 'value' | 'onChange'> & {
  value?: number;
  onChange?: (value?: number) => void;
  showTime?: boolean;
};

const DateStamp: React.FC<DateStampType> = memo((props) => {
  const { value, onChange, showTime = true, ...restProps } = props;

  const mergedValue = useMemo(() => {
    return value ? moment(value) : undefined;
  }, [value]);

  const mergedOnChange = useCallback(
    (param: Moment | null) => {
      onChange?.(param?.valueOf?.());
    },
    [onChange],
  );

  const onOpenChange = useCallback(
    (open: boolean) => {
      if (open) {
        onChange?.(value);
      }
    },
    [value],
  );

  return (
    <>
      <DatePicker
        className="w-full"
        value={mergedValue}
        onChange={mergedOnChange}
        showTime={showTime}
        onOpenChange={onOpenChange}
        format={getLocale().dateTimeFormat}
        {...restProps}
      />
    </>
  );
});

export default DateStamp;
