/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-16 11:43:44
 * @LastEditTime: 2024-12-26 16:31:17
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/services/equipment.ts
 */

import type { ResponseCommonData, ResponsePageData } from '@/utils/request';
import request from '@/utils/request';
import type {
  DeviceMasterMode,
  DeviceProductTypeEnum,
  DeviceTypeEnum,
  ListDataType,
} from '@/utils/dictionary';
import type { DeviceModelDataType } from '@/types/device';

export type DeviceDataType = {
  devices?: any;
  id?: string;
  forShort?: string;
  deviceId?: string;
  deviceName?: string;
  isBindMainServer?: string | number;
  siteId?: string;
  productImg?: string;
  parentId?: number;
  name?: string;
  sn?: string;
  model?: string;
  productId?: DeviceTypeEnum;
  productIntroduce?: string;
  productType?: DeviceProductTypeEnum;
  productTypeId?: DeviceProductTypeEnum;
  productTypeName?: string;
  subsystemName?: string;
  childSystem?: string;
  siteName?: string;
  createTime?: string;
  sessionStartTime?: string;
  connectStatus?: number;
  status?: number;
  alarmStatus?: number;
  alarmCount?: number;
  lastOnlineTime?: string;
  key?: string;
  aliasSn?: string;
  paramConfigType?: number;
  productConfig?: string;
  productConfigType?: number;
  config?: string;
  photos?: string;
  masterSlaveMode?: DeviceMasterMode;
  masterSlaveSystemName?: string;
  networkStatus?: number;
  canBeDeleted?: number;
  canUnbind?: number;
  children?: DeviceDataType[];
  values?: any[];
  root?: boolean;
  connectDevice?: boolean;
  guaranteeEndTime?: string;
  guaranteeStartTime?: string;
  guaranteeTime?: number;
};
export type EmsDevicesType = {
  deviceId: any;
  productId: any;
  deviceName: string;
  masterSlaveMode: any;
  groupId: any;
  groupName: any;
  networkStatus?: any;
  alarmStatus?: number;
  alarmCount?: number;
};

export type ClusterType = {
  key: string;
  deviceId: string;
  deviceName: string;
  connectStatus: number;
  alarmStatus: number;
  ratedCapacity: number;
  runState: number;
  soc: number;
};

export type ProductModelType = {
  id?: string;
  model?: string;
  name?: string;
};

export type FactoryDataType = {
  id?: string;
  name?: string;
};

export type SiteEmsDataType = {
  emsSn?: string;
  cabinetName?: string;
};

export const getDevicePage = (params: any) => {
  return request<ResponsePageData<DeviceDataType>>(`/iot/device/deviceList`, {
    method: 'GET',
    params,
  });
};

export const getDeviceInfo = (params: any) => {
  return request<ResponseCommonData<DeviceDataType>>(`/oss/site/monitor/device/getBasicInfo`, {
    method: 'GET',
    params,
  });
};

export const getFireDeviceInfo = (params: any) => {
  return request<ResponseCommonData<DeviceDataType>>(
    `/oss/site/monitor/device/fireProtection/getBasicInfo`,
    {
      method: 'GET',
      params,
    },
  );
};

export const editDeviceInfo = (data: DeviceDataType) => {
  return request(`/iot/device/update`, {
    method: 'POST',
    data,
  });
};

export const getChildEquipment = (params: any) => {
  return request<ResponseCommonData<DeviceDataType[]>>(`/oss/device/subDevice`, {
    method: 'GET',
    params,
  });
};

export const getChargeDayCurve = (params: any) => {
  return request<ResponseCommonData<DeviceDataType[]>>(`/iot/ytcharging/getChargeDayCurve`, {
    method: 'GET',
    params,
  });
};

export type OrderCurveRequestType = {
  endTime?: string;
  keyValue?: KeyValue[];
  /**
   * 聚合方式，0最大值  1最小值 2平均值 3-第一个值 4最后一个值
   */
  polymerizationType?: number;
  startTime?: string;
  /**
   * 聚合时间 ，分钟
   */
  timeBucket?: number;
  [property: string]: any;
};

export type KeyValue = {
  deviceId?: number | string;
  key?: string;
  name?: string;
  /**
   * key的数据类型
   */
  type?: string;
  [property: string]: any;
};

export const getytOrdercurve = (data: any) => {
  return request<ResponseCommonData<DeviceDataType[]>>(`/iot/deviceData/queryHistorical`, {
    method: 'POST',
    data,
  });
};

// export const getytOrdercurve = (params: any) => {
//   return request<ResponseCommonData<DeviceDataType[]>>(`/iot/ytcharging/ytOrder/curve`, {
//     method: 'GET',
//     params,
//   });
// };

export const getChargeTCurve = (params: any) => {
  return request<ResponseCommonData<DeviceDataType[]>>(`/iot/ytcharging/getChargeTCurve`, {
    method: 'GET',
    params,
  });
};

export const getWholeDeviceTree = (params: any) => {
  return request<ResponseCommonData<DeviceDataType>>(`/iot/es/deviceTree`, {
    method: 'GET',
    params,
  });
};

export const getFireDeviceTree = (params: any) => {
  return request<ResponseCommonData<DeviceDataType>>(`/iot/es/deviceTree`, {
    method: 'GET',
    params,
  });
};
export const getWeather = (code: string) => {
  return request(`/screen/weather/${code}`, {
    method: 'GET',
  });
};

export const getAlarms = (params: any) => {
  return request(`/oss/alarm/getAlarm`, {
    method: 'GET',
    params,
  });
};

export const getLogs = (params: any) => {
  return request(`/oss/deviceLog/page`, {
    method: 'GET',
    params,
  });
};

export const getRelatedDevice = (id: string) => {
  return request(`/oss/device/associatedDevice`, {
    method: 'GET',
    params: {
      deviceId: id,
    },
  });
};

export const getGuns = (id: string) => {
  return request(`/oss/device/getABGunDeviceEncode`, {
    method: 'GET',
    params: {
      deviceId: id,
    },
  });
};

export const getEquipInfo = (params: any) => {
  return request(`/oss/device/details`, {
    method: 'GET',
    params,
  });
};

export const editEquipConfig = (data: any) => {
  return request(`/iot/device/updateDeviceCommunicationConfig`, {
    method: 'put',
    data,
  });
};

export const getThirdStation = (params: any) => {
  return request(`/iot/thirdSite/getThirdSiteList`, {
    method: 'GET',
    params,
  });
};

export const getDeviceTree = (params: any) => {
  return request(`/iot/siteSystemConfiguration/condition/deviceTree`, {
    method: 'GET',
    params,
  });
};

export const getMultipleDeviceTree = (params?: any) => {
  return request(`/iot/siteSystemConfiguration/allSites/deviceTree`, {
    method: 'GET',
    params,
  });
};

export const getSiteDeviceTree = (params: any) => {
  return request(`/iot/siteSystemConfiguration/siteDeviceTree`, {
    method: 'GET',
    params,
  });
};

export const optionalDeviceList = (params: any) => {
  return request(`/iot/es/optionalDeviceList`, {
    method: 'GET',
    params,
  });
};

export const unitDeviceList = (params: any) => {
  return request(`/iot/es/unitDeviceList`, {
    method: 'GET',
    params,
  });
};

export const updataUnitDeviceList = (data: any) => {
  return request(`/iot/es/unitDeviceList`, {
    method: 'POST',
    data,
  });
};

export const getDeviceCollection = (params: any) => {
  return request('/iot/siteSystemConfiguration/dataSource/deviceParamList', {
    method: 'GET',
    params,
  });
};

export const getProductTypeList = (params: any) => {
  return request<ResponseCommonData<ListDataType[]>>('/oss/product/getProductTypeList', {
    method: 'GET',
    params,
  });
};

export const getProductTypeTree = (params?: any) => {
  return request<ResponseCommonData<ListDataType[]>>('/iot/product/getProductTypeTree', {
    method: 'GET',
    params,
  });
};

export const getClusterByStack = (params: any) => {
  return request<ResponseCommonData<ClusterType[]>>('/oss/site/monitor/device/getBatteryDevices', {
    method: 'GET',
    params,
  });
};

export const getEmsAssociationDevice = (params: any) => {
  return request<ResponseCommonData<DeviceDataType[]>>('/oss/site/monitor/device/getDescendants', {
    method: 'GET',
    params,
  });
};

export const getChargeTerm = (params: any) => {
  return request<ResponseCommonData<DeviceDataType[]>>('/iot/ytcharging/getChargeTerm', {
    method: 'GET',
    params,
  });
};

export const getDeviceLocalLog = (params: any) => {
  return request('/iot/deviceLocalLog/page', {
    method: 'GET',
    params,
  });
};

export const getDeviceReportLog = (params: any) => {
  return request('/iot/deviceReportLog/page', {
    method: 'GET',
    params,
  });
};

export const getLocalLogList = (params: any) => {
  return request('/iot/deviceLocalLog/getLocalLogList', {
    method: 'GET',
    params,
  });
};

export const deleteDeviceLocalLog = (data: any) => {
  return request<ResponseCommonData<DeviceDataType[]>>('/iot/deviceLocalLog', {
    method: 'DELETE',
    data,
  });
};

export const getLocalLog = (params: any) => {
  return request<ResponseCommonData<DeviceDataType[]>>('/iot/deviceLocalLog/getLocalLog', {
    method: 'GET',
    params,
  });
};

export const exportDeviceReportLog = (params: any) => {
  return request('/iot/deviceReportLog/export', {
    method: 'GET',
    responseType: 'blob',
    params,
  });
};

export const addTerminal = (data: any) => {
  return request<ResponseCommonData<DeviceDataType[]>>('/iot/ytcharging/addTerminal', {
    method: 'POST',
    data,
  });
};

export const termBindMainServer = (data: any) => {
  return request<ResponseCommonData<DeviceDataType[]>>('/iot/ytcharging/termBindMainServer', {
    method: 'PUT',
    data,
  });
};

export const updateTerm = (data: any) => {
  return request<ResponseCommonData<DeviceDataType[]>>('/iot/ytcharging/updateTerm', {
    method: 'PUT',
    data,
  });
};
export const delTerm = (params: any) => {
  return request<ResponseCommonData<DeviceDataType[]>>('/iot/ytcharging/delTerm', {
    method: 'DELETE',
    params,
  });
};

export const getytOrder = (params: any) => {
  return request<ResponseCommonData<any[]>>('/iot/ytcharging/ytOrder/page', {
    method: 'GET',
    params,
  });
};

export const getEnergeListBySite = (params: any) => {
  return request<ResponseCommonData<DeviceDataType[]>>('/oss/site/monitor/energyStorage/esList', {
    method: 'GET',
    params,
  });
};

export const getDeviceGroupModel = (params: any) => {
  return request<ResponseCommonData<DeviceModelDataType>>('/oss/product/getThingsModeByProductId', {
    method: 'GET',
    params,
  });
};

export const getDeviceModel = (params: any) => {
  return request<ResponseCommonData<DeviceModelDataType>>('/iot/model/getThingsModeByProductId', {
    method: 'GET',
    params,
  });
};

export const getChargeHost = (params: any) => {
  return request<ResponseCommonData<DeviceDataType[]>>('/iot/device/getChargingPileHost', {
    method: 'GET',
    params,
  });
};

export const editSetting = (data: any, showMessage = true) => {
  return request(`/oss/device/remote_setting`, {
    method: 'POST',
    data,
    showMessage,
  });
};

export const setPostDeviceLogTime = (data: any, showMessage = true) => {
  return request(`/iot/deviceLocalLog/setPostDeviceLogTime`, {
    method: 'PUT',
    data,
    showMessage,
  });
};

export const getProductModelByType = (params: any) => {
  return request<ResponseCommonData<ProductModelType[]>>(
    `/iot/product/getAllProductsByproductType`,
    {
      method: 'GET',
      params,
    },
  );
};

export const getFactoryList = () => {
  return request<ResponseCommonData<FactoryDataType[]>>(`/iot/product/getFactoryList`, {
    method: 'GET',
  });
};

export const getAssociateDevice = (params: any) => {
  return request<ResponseCommonData<DeviceDataType[]>>(
    `/iot/device/getAssociateDevicesByDeviceIdAndType`,
    {
      method: 'GET',
      params,
    },
  );
};

export const updateAssociateDevice = (data: any) => {
  return request(`/iot/device/updateDeviceProductConfig`, {
    method: 'PUT',
    data,
  });
};

export const getChargeStack = (params: any) => {
  return request<ResponseCommonData<DeviceDataType[]>>(`/iot/device/getChargingPileHost`, {
    method: 'GET',
    params,
  });
};

export const getUpgradeVersion = (params: any) => {
  return request(`/iot/otaPackage/getVersionByDeviceId`, {
    method: 'GET',
    params,
  });
};

export const upgradeDevice = (data: any) => {
  return request(`/iot/otaUpgrade/sendUpgrade`, {
    method: 'POST',
    data,
  });
};

export const getUpgradeRecord = (params: any) => {
  return request(`/iot/otaRecord/page`, {
    method: 'GET',
    params,
  });
};

export const getParallelDevice = (params: any) => {
  return request<ResponseCommonData<DeviceDataType[]>>(`/iot/device/getEsGroup`, {
    method: 'GET',
    params,
  });
};

export const editGroupName = (data: any) => {
  return request(`/iot/es/group_name`, {
    method: 'PUT',
    data,
  });
};

export const sendDebug = (data: any) => {
  return request(`/iot/deviceDebug/send`, {
    method: 'POST',
    data,
  });
};

export const openVpn = (data: any) => {
  return request(`/iot/es/vpnSwitch`, {
    method: 'POST',
    data,
  });
};

export const getVpn = (params: any) => {
  return request(`/iot/es/vpnStatus`, {
    method: 'GET',
    params,
  });
};

export const getFileUrl = (data: any) => {
  return request(`/uc/fileUrl?${new URLSearchParams(data).toString()}`, {
    method: 'GET',
  });
};

export const getSimInfo = (params: any) => {
  return request('/iot/sim/web', {
    method: 'GET',
    params,
  });
};

export const getSiteEmsDevices = (params: any) => {
  return request<ResponseCommonData<SiteEmsDataType[]>>('/iot/device/getSiteEmsSendDevices', {
    method: 'GET',
    params,
  });
};
