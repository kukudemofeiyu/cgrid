/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-26 13:55:43
 * @LastEditTime: 2023-07-28 15:08:19
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\index.ts
 */
import type React from 'react';
import { DetailProps } from '@/components/Detail';

export enum LabelTypeEnum {
  DotLabel,
  LineLabel,
}

export type RealTimeProps = {
  id?: string;
  loading?: boolean;
  open?: boolean;
  label?: React.ReactNode;
  labelType?: LabelTypeEnum;
  detailProps?: Omit<DetailProps, 'items' | 'data'>;
};

export type BusinessDialogProps = {
  id: string;
  open: boolean;
  onCancel: () => void;
  model?: string;
  deviceImg?: string;
};

export type DeviceDialogMapType = {
  component: string;
  props?: Record<string, any>;
};

export type PvInverterDeviceDialogMapType = {
  component: string;
  props?: Record<string, any>;
};
