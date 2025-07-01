/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-11-22 14:40:55
 * @LastEditTime: 2023-11-22 14:40:55
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\types\index.ts
 */

import { ReactNode } from 'react';

export type LocationType<Params = Record<string, any>> = {
  query?: Params;
};

export type OptionType<T = any> = T & {
  label: string;
  value: string | number;
  [key: string]: any;
};

export type ValueEnum = {
  [key: string]: {
    text?: ReactNode;
    status?: 'Success' | 'Error' | 'Processing' | 'Warning' | 'Default' | '';
    [key: string]: any;
  };
};

export type SiteOptionType = {
  name?: string;
  value?: string;
  unitName?: string;
};

export type HkyfConfig = {
  SSOUrl?: string;
  userId?: string;
  appKey?: string;
  secretKey?: string;
  productCode?: string;
  path?: string;
};

export type AreaDataType = {
  areaName?: string;
  domainName?: string;
  language?: string;
  monetaryUnit?: number;
};

export type MoneyUnitDataType = {
  code?: number;
  unit?: string;
}
