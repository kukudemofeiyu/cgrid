import * as AntdIcons from '@ant-design/icons';
import * as YTIcons from '@/components/YTIcons';
import type { CustomIconComponentProps } from '@ant-design/icons/lib/components/Icon';
import React from 'react';
import { DeviceProductTypeEnum } from './dictionary';
import {
  YTPVInverterOutlined,
  YTEnergyOutlined,
  YTEmsOutlined,
  YTBmsOutlined,
  YTAirOutlined,
  YTMeterOutlined,
  YTChargeOutlined,
  YTChargeStackOutlined,
  YTCabinetOutlined,
  YTSuperChargeOutlined,
  YTChargeGunOutlined,
  YTEmsTwoOutlined,
  YTPcsOutlined,
  YTBatteryPackOutlined,
  YTPvEnergyOutlined,
  YTDehumidifierOutlined,
  YTFireFightOutlined,
  YTGenerralDeviceOutlined,
  YTSlide,
  YTWorksheet,
  YTNotepad,
  YTLink,
  YTFolder,
  YTPicture,
  YTUnknown,
  YTWordile,
  YTPDFFile,
  YTCompress,
  YTAudio,
  YTFileVideo,
} from '@/components/YTIcons';

const allIcons: Record<string, any> = { ...AntdIcons, ...YTIcons };

export function getIcon(name: string): React.ReactNode | string {
  const icon = allIcons[name];
  return icon || '';
}

export function createIcon(
  icon: string | any,
  props: Partial<CustomIconComponentProps> = {},
): React.ReactNode | string {
  if (typeof icon === 'object') {
    return icon;
  }
  const ele = allIcons[icon];
  if (ele) {
    return React.createElement(allIcons[icon], props);
  }
  return '';
}

export const productTypeIconMap = new Map([
  [DeviceProductTypeEnum.ChargeStack, YTChargeStackOutlined],
  [DeviceProductTypeEnum.FastChargeTerminal, YTSuperChargeOutlined],
  [DeviceProductTypeEnum.DCChargePile, YTChargeOutlined],
  [DeviceProductTypeEnum.ACChargePile, YTChargeOutlined],
  [DeviceProductTypeEnum.Energy, YTEnergyOutlined],
  [DeviceProductTypeEnum.PV, YTPVInverterOutlined],
  [DeviceProductTypeEnum.ExchangeCabinet, YTCabinetOutlined],
  [DeviceProductTypeEnum.Battery, YTBmsOutlined],
  [DeviceProductTypeEnum.OverchargeTerminal, YTSuperChargeOutlined],
  [DeviceProductTypeEnum.ChargeGun, YTChargeGunOutlined],
  [DeviceProductTypeEnum.Ems, YTEmsTwoOutlined],
  [DeviceProductTypeEnum.BatteryStack, YTBmsOutlined],
  [DeviceProductTypeEnum.BatteryPack, YTEmsOutlined],
  [DeviceProductTypeEnum.Pcs, YTPcsOutlined],
  [DeviceProductTypeEnum.BatteryCluster, YTBatteryPackOutlined],
  [DeviceProductTypeEnum.Air, YTAirOutlined],
  [DeviceProductTypeEnum.ElectricMeter, YTMeterOutlined],
  [DeviceProductTypeEnum.LocalEms, YTEmsTwoOutlined],
  [DeviceProductTypeEnum.EnergyElectricMeter, YTMeterOutlined],
  [DeviceProductTypeEnum.PvEnergy, YTPvEnergyOutlined],
  [DeviceProductTypeEnum.Dehumidifier, YTDehumidifierOutlined],
  [DeviceProductTypeEnum.FireFight, YTFireFightOutlined],
  [DeviceProductTypeEnum.Default, YTGenerralDeviceOutlined],
]);

export const isImgFile = (fileName: string): boolean => {
  return /\.(jpg|jpeg|png|gif|bmp|webp|svg|tiff|tif)$/.test(fileName);
};
/*
 *@Author: aoshilin
 *@Date: 2024-11-01 09:09:23
 *@parms: fileName 文件名称
 *@parms: type 文件类型 0-文件 1-文件夹
 *@Description:
 */
export function getFileTypeIcon(fileName: string, type: number = 0) {
  const fileType: string = fileName.split('.').pop() || '';
  const linkRegex: RegExp = new RegExp('^(https?://)[^s/$.?#].[^s]*$');
  if (type === 1) {
    return YTFolder;
  }
  if (isImgFile(fileName)) {
    return YTPicture;
  }
  if (linkRegex.test(fileName)) {
    return YTLink;
  }
  if (['xlsx', 'xls', 'csv', 'ods', 'html'].includes(fileType)) {
    return YTWorksheet;
  }
  if (['mp3', 'wav', 'ogg'].includes(fileType)) {
    return YTAudio;
  }
  if (['mp4', 'avi', 'wmv', 'mov', 'flv', 'mkv'].includes(fileType)) {
    return YTFileVideo;
  }
  if (['pdf'].includes(fileType)) {
    return YTPDFFile;
  }
  if (['doc', 'docx', 'odt'].includes(fileType)) {
    return YTWordile;
  }
  if (['ppt', 'pptx', 'odp'].includes(fileType)) {
    return YTSlide;
  }
  if (['txt', 'rtf', 'Markdown', 'json'].includes(fileType)) {
    return YTNotepad;
  }
  if (['zip', 'tgz'].includes(fileType)) {
    return YTCompress;
  }
  return YTUnknown;
}
