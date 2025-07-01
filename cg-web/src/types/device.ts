/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-08-04 10:34:17
 * @LastEditTime: 2024-01-11 15:43:20
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\types\device.ts
 */
import { DeviceModelDescribeTypeEnum, DeviceModelShowTypeEnum, DeviceModelTypeEnum } from '@/utils';
import { TreeNodeProps } from 'antd';

export type DeviceModelAuthorityType = {
  detail?: string;
  edit?: string;
};

export type DeviceModelType = {
  type?: DeviceModelTypeEnum;
  specs?: any;
  name?: string;
};

export type DevicePropsType = {
  id?: string;
  name?: string;
  groupName?: string;
  dataType?: DeviceModelType;
  properties?: DevicePropsType[];
  component?: string;
  showHistory?: boolean;
  sourceId?: string;
};

export type TipType = {
  title?: string;
  content?: string;
};

export type DeviceArrayType = {
  type?: DeviceModelTypeEnum;
  specs?: {
    size?: number;
    item?: DeviceTimeRangeType | DeviceStructType;
    originalItem?: DeviceTimeRangeType | DeviceStructType;
  };
};

export type DeviceTreeSelectType = {
  type?: DeviceModelTypeEnum;
  specs?: {
    id?: string;
    name?: string;
  }[];
};

export type DeviceTimeRangeType = {
  type?: DeviceModelTypeEnum;
  specs?: {
    length: number;
  };
};

export type DeviceEnumType = {
  type?: DeviceModelTypeEnum;
  specs?: Record<string, any>;
};

export type DeviceDoubleType = {
  type?: DeviceModelTypeEnum;
  specs?: {
    unit?: string;
    unitName?: string;
    min?: number;
    max?: number;
    enable: boolean;
  };
};

export type DeviceStructType = {
  type?: DeviceModelTypeEnum;
  specs?: DeviceServiceModelType[];
};

export type DeviceServiceModelType = {
  id?: string;
  deviceId?: string;
  parentId?: string;
  serviceId?: string;
  queryId?: string;
  name?: string;
  required?: boolean;
  span?: number;
  showType?: DeviceModelShowTypeEnum;
  disabled?: string;
  tip?: string;
  authority?: DeviceModelAuthorityType[];
  type?: DeviceModelDescribeTypeEnum;
  dataType?:
  | DeviceArrayType
  | DeviceDoubleType
  | DeviceStructType
  | DeviceTimeRangeType
  | DeviceEnumType
  | DeviceTreeSelectType
  | {
    type?: DeviceModelTypeEnum;
    specs?: DeviceServiceModelType[];
  };
  buttons?: 'refresh' | 'edit';
  defaultValue?: string;
  showHistory?: boolean;
  form?: {
    span?: number;
    showType?: DeviceModelShowTypeEnum;
  };
  promptRule?: TipType[];
  controlIntercept?: string;
};

export type DeviceServiceType = {
  id?: string;
  queryId?: string;
  deviceId?: string;
  disabled?: string;
  name?: string;
  groupName?: string;
  showType?: DeviceModelShowTypeEnum;
  type?: DeviceModelDescribeTypeEnum;
  tip?: string;
  outputData?: DeviceServiceModelType[];
  children?: DeviceServiceModelType[];
  authority?: DeviceModelAuthorityType[];
  buttons?: 'refresh' | 'edit';
  extraParams?: any;
  promptRule?: TipType[];
};

export type DeviceLocationType = {
  id?: string;
  name?: string;
};

export type DeviceServiceGroupType = {
  groupName?: string;
  location?: DeviceLocationType;
  services?: DeviceServiceType[];
  component?: string;
  authority?: DeviceModelAuthorityType[];
};

export type DevicePageModels = {
  location?: DeviceLocationType;
  serviceGroups?: DeviceServiceGroupType[];
};

export type DeviceModelDescribeType = {
  id?: string;
  queryId?: string;
  name?: string;
  type?: DeviceModelDescribeTypeEnum;
  showType?: DeviceModelShowTypeEnum;
  icon?: string;
  columns?: number;
  tip?: string;
  authority?: DeviceModelAuthorityType[];
  children?: DeviceModelDescribeType[] | DeviceServiceType[];
  dataInterceptor?: string;
  showHistory?: boolean;
};

export type DeviceModelDataType = {
  properties?: DevicePropsType[];
  services?: DeviceServiceGroupType[];
  data?: DeviceModelDescribeType[];
};

export type DeviceTreeDataType = TreeNodeProps & {
  id?: string;
  deviceName?: string;
  deviceSN?: string;
  parentId?: string;
  productId?: string;
  siteId?: string;
  siteName?: string;
  children?: DeviceTreeDataType[];
  component?: number;
  selectable?: boolean;
  loaded?: boolean;
};
