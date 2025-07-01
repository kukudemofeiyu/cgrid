/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-11-15 17:05:04
 * @LastEditTime: 2023-11-15 17:05:17
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\PvEnergyMachine\typing.ts
 */

import React from 'react';
import { DetailItem } from '@/components/Detail';

export type OverviewItemType = {
  icon: string;
  style: React.CSSProperties;
  items?: DetailItem[];
};

export type FlowType = {
  pathId: string;
  field: string;
  keyPoints: number[];
};

export type searchType = {
  deviceId: string;
  date?: string;
  type?: number;
  visitType?: number;
};

export type energyType = {
  id?: string;
  name: string;
  productId: number;
  children?: energyType[];
};

export type ChartDataType = {
  eventTs: string;
  doubleVal: number;
};

export type PowerDataType = {
  elec?: ChartDataType[];
  pv?: ChartDataType[];
};

export type ElectricDataType = {
  elec?: ChartDataType[];
  pv?: ChartDataType[];
};
