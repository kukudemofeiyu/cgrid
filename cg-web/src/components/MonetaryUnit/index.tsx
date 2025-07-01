import React from 'react';
import { useModel } from 'umi';
import { arrayToMap } from '@/utils';

const monetaryUnitEnum = JSON.parse(localStorage.getItem('monetaryUnitEnum') || '{}');

export const storedMonetaryUnitEnum = arrayToMap(monetaryUnitEnum, 'code', 'unit');

export type MonetaryUnitType = {
  unit?: any;
};

const MonetaryUnit: React.FC<MonetaryUnitType> = (props) => {
  const { unit } = props;
  const { state: siteData } = useModel('site');
  const monetaryUnitNow = unit ? unit : siteData?.monetaryUnit;

  return <>{storedMonetaryUnitEnum[monetaryUnitNow]}</>;
};

export default MonetaryUnit;
