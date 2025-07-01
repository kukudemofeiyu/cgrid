/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-09-11 14:10:41
 * @LastEditTime: 2024-04-15 17:07:53
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\DeviceRealTime\config.ts
 */

import { DeviceDataType } from '@/services/equipment';
import { DeviceProductTypeEnum, DeviceTypeEnum } from '@/utils/dictionary';

export type DeviceRealTimeType = {
  deviceData?: DeviceDataType;
  loading?: boolean;
  showRemoteControl?: boolean;
};

export type DeviceRealTimeMapType = {
  component?: string;
  props?: any;
};

export const deviceProductTypeMap: Record<
  number,
  {
    component: string;
    props?: any;
  }
> = {
  [DeviceProductTypeEnum.Energy]: { component: 'Energy' },
  [DeviceProductTypeEnum.PvEnergy]: { component: 'Energy' },
  [DeviceProductTypeEnum.SmallEnergy]: { component: 'Energy' },
  [DeviceProductTypeEnum.WindPvFirewoodEnergy]: { component: 'Energy' },
  [DeviceProductTypeEnum.BEnergy]: { component: 'Energy' },
};

export const deviceRealTimeMap: any = {
  [DeviceTypeEnum.Gateway]: { component: 'Gateway' },
  [DeviceTypeEnum.ElectricMeter]: { component: 'ElectricMeter' },
  [DeviceTypeEnum.PvInverter11]: { component: 'PvInverter', props: { loopNum: 12 } },
  [DeviceTypeEnum.PvInverter11LocalEms]: { component: 'PvInverter', props: { loopNum: 12 } },
  [DeviceTypeEnum.PvInverter4]: { component: 'PvInverter', props: { loopNum: 4 } },
  [DeviceTypeEnum.PvInverter4LocalEms]: { component: 'PvInverter', props: { loopNum: 4 } },
  [DeviceTypeEnum.PvInverter36]: { component: 'PvInverter', props: { loopNum: 12 } },
  [DeviceTypeEnum.GRWTPvInverter]: { component: 'PvInverter', props: { loopNum: 12 } },
  [DeviceTypeEnum.YtCharge160]: { component: 'YTCharge' },
  [DeviceTypeEnum.YtCharge160LocalEms]: { component: 'YTCharge' },
  [DeviceTypeEnum.Energy]: { component: 'Energy' },
  [DeviceTypeEnum.EnergyLocalEms]: { component: 'Energy' },
  [DeviceTypeEnum.PvInverterCabinet]: { component: 'PvInverterCabinet' },
  [DeviceTypeEnum.HwCharge]: { component: 'HwCharge' },
  [DeviceTypeEnum.HwChargeLocalEms]: { component: 'HwCharge' },
  [DeviceTypeEnum.YtCharge360]: { component: 'HwCharge' },
  [DeviceTypeEnum.YtCharge360LocalEms]: { component: 'HwCharge' },
  [DeviceTypeEnum.YtCharge120]: { component: 'YTCharge' },
  [DeviceTypeEnum.YtCharge120LocalEms]: { component: 'YTCharge' },
  [DeviceTypeEnum.YunCharge120]: { component: 'HwChargeYt' },
  [DeviceTypeEnum.YtCharge7]: { component: 'YTCharge' },
  [DeviceTypeEnum.EnergyCabinet]: { component: 'EnergyCabinet' },
  [DeviceTypeEnum.BoxSubstation]: { component: 'BoxSubstation' },
  [DeviceTypeEnum.HwChargeChild]: { component: 'HwChargeChild' },
  [DeviceTypeEnum.HwChargeSuperChild]: { component: 'HwChargeChild' },
  [DeviceTypeEnum.HwChargeYt]: { component: 'HwChargeYt' },
  [DeviceTypeEnum.Device]: { component: 'Device' },
  [DeviceTypeEnum.BatteryStack]: { component: 'BatterryStack' },
  [DeviceTypeEnum.BatteryCluster]: { component: 'BatterryStack' },
  [DeviceTypeEnum.Pcs]: { component: 'Pcs' },
  [DeviceTypeEnum.Ems]: { component: 'Ems' },
  // [DeviceTypeEnum.Air]: { component: 'Air' },
  [DeviceTypeEnum.BWattBatteryStack]: { component: 'BatterryStack' },
  [DeviceTypeEnum.BWattBatteryCluster]: { component: 'BatterryStack' },
  [DeviceTypeEnum.BWattPcs]: { component: 'Pcs' },
  [DeviceTypeEnum.BWattEms]: { component: 'Ems', props: { type: DeviceTypeEnum.BWattEms } },
  [DeviceTypeEnum.BWattElectricMeter]: { component: 'ElectricMeter', props: { label: '实时数据' } },
  // [DeviceTypeEnum.BWattAir]: { component: 'Air' },
  [DeviceTypeEnum.BWattEnergy]: { component: 'Energy' },
  [DeviceTypeEnum.YTEnergy]: { component: 'Energy' },
  [DeviceTypeEnum.YTEnergyPcs]: { component: 'Pcs' },
  [DeviceTypeEnum.YTEnergyBatteryStack]: { component: 'BatterryStack' },
  [DeviceTypeEnum.YTEnergyEms]: { component: 'YTEnergyEms' },
  [DeviceTypeEnum.LiquidEnergyBatteryStack]: { component: 'BatterryStack' },
  [DeviceTypeEnum.React100XEmsEnergy]: {
    component: 'Ems',
    props: { type: DeviceTypeEnum.React100XEmsEnergy },
  },
  [DeviceTypeEnum.React100WEmsEnergy]: {
    component: 'Ems',
    props: { type: DeviceTypeEnum.React100WEmsEnergy },
  },
  [DeviceTypeEnum.PvInverter100k1]: { component: 'PvInverter', props: { loopNum: 4 } },
  [DeviceTypeEnum.PvInverter100k2]: { component: 'PvInverter', props: { loopNum: 4 } },
  [DeviceTypeEnum.PvInverter120k2]: { component: 'PvInverter', props: { loopNum: 4 } },
};
