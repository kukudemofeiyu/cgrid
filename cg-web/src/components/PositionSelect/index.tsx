/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-04 19:25:45
 * @LastEditTime: 2024-02-28 16:49:13
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\PositionSelect\index.tsx
 */

import React, { memo } from 'react';
import { MapTypeEnum } from '@/utils/dictionary';
import AMapPositionSelect from './AMap';
import GooglePositionSelect from './Google';
import GoogleMapContain from '@/components/MapContain/Google';

export type PositionSelectValueType = {
  address?: React.ReactNode;
  point?: google.maps.LatLngLiteral;
  countryCode?: string;
  provinceCode?: string;
  cityCode?: string;
  adcode?: string;
  countryName?: string;
  provinceName?: string;
  cityName?: string;
  high?: number;
};

export type PositionSelectType = {
  value?: PositionSelectValueType;
  onChange?: (value: PositionSelectValueType) => void;
  disabled?: boolean;
  readonly?: boolean;
  className?: string;
  type?: MapTypeEnum;
  initCenter?: google.maps.LatLngLiteral;
  showHigh?: boolean;
};

const Index: React.FC<PositionSelectType> = (props) => {
  const { type, initCenter, ...restProps } = props;

  return (
    <>
      {type == MapTypeEnum.Google ? (
        <GoogleMapContain>
          <GooglePositionSelect initCenter={initCenter} {...restProps} />
        </GoogleMapContain>
      ) : (
        <AMapPositionSelect {...restProps} />
      )}
    </>
  );
};

export default memo(Index);
