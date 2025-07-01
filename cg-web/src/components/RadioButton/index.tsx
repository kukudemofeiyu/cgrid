/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-12-27 13:58:30
 * @LastEditTime: 2024-01-09 16:37:24
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\RadioButton\index.tsx
 */

import React from 'react';
import { SelectProps, Space, Button, Radio } from 'antd';

export type RadioButtonType = {
  options?: SelectProps['options'];
  value?: any;
  onChange?: (value: any) => void;
  disabled?: boolean;
  loading?: boolean;
  type?: 'radio' | 'button';
};

const RadioButton: React.FC<RadioButtonType> = (props) => {
  const { options, value, onChange, disabled, loading, type = 'radio' } = props;

  return (
    <>
      <Space wrap>
        {options?.map?.((item) => {
          return (
            <Button
              type={type == 'radio' && value === item.value && !disabled ? 'primary' : 'default'}
              ghost={type == 'radio' && value === item.value}
              loading={loading}
              onClick={() => !disabled && onChange?.(item.value)}
              disabled={item.disabled || disabled}
            >
              {type == 'radio' && (
                <Radio
                  className="mr8"
                  checked={value === item.value}
                  disabled={disabled}
                  onClick={(e) => {
                    !disabled && onChange?.(item.value);
                    e.stopPropagation();
                  }}
                />
              )}
              {item.label}
            </Button>
          );
        })}
      </Space>
    </>
  );
};

export default RadioButton;
