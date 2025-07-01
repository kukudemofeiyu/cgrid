import { DeviceTypeEnum } from '@/utils/dictionary';
import EnergyImg from '@/assets/image/station/energy/enery.png';
import LiquidEnergyImg from '@/assets/image/station/liquid-energy/energy.png';
import Energy261AImg from '@/assets/image/station/liquid-wind-energy/enery261A.png';
import Energy261BImg from '@/assets/image/station/liquid-wind-energy/enery261B.png';
import SmallEnergyImg from '@/assets/image/station/small-energy/energy.png';
import PvEnergyImg from '@/assets/image/station/pv-energy/pv-energy.png';
import ChargeImg from '@/assets/image/station/charge-1gun/charge.png';
import Charge2Img from '@/assets/image/station/charge-2gun/charge.png';
import ChargeMasterImg from '@/assets/image/station/charge-master/charge.png';
import ChargeTerminal1GunImg from '@/assets/image/station/charge-terminal-1gun/charge.png';
import ChargeTerminal2GunImg from '@/assets/image/station/charge-terminal-2gun/charge.png';
import EnergyBack from '@/assets/image/station/fgc-energy/energy-back.png';
import EnergyFront from '@/assets/image/station/fgc-energy/energy.png';
import LiquidWindEnergyImg from '@/assets/image/station/liquid-wind-energy/enery.png';
import DeviceImg from '@/assets/image/masterSlaveGroup/device.png';
type ProductView = {
  frontView?: string;
  rightSideView?: string;
  openDoorRearView?: string; //开门正视图
  openFrontView?: string; //开门后视图
};

const WindPvFirewood = {
  openDoorRearView: EnergyBack,
  openFrontView: EnergyFront,
  rightSideView: DeviceImg,
};

const Energy = {
  openFrontView: EnergyImg,
  rightSideView: DeviceImg,
};
const LiquidEnergy = {
  openFrontView: LiquidEnergyImg,
  rightSideView: DeviceImg,
};
const Energy261A = {
  openFrontView: Energy261AImg,
  rightSideView: DeviceImg,
};
const Energy261B = {
  openFrontView: Energy261BImg,
  rightSideView: DeviceImg,
};

const SmallEnergy = {
  openFrontView: SmallEnergyImg,
  rightSideView: DeviceImg,
};

const PvEnergy = {
  openFrontView: PvEnergyImg,
  rightSideView: DeviceImg,
};

const Charge = {
  frontView: ChargeImg,
};
const Charge2 = {
  frontView: Charge2Img,
};
const ChargeMaster = {
  frontView: ChargeMasterImg,
};
const ChargeTerminal1Gun = {
  frontView: ChargeTerminal1GunImg,
};
const ChargeTerminal2Gun = {
  frontView: ChargeTerminal2GunImg,
};
const LiquidWindEnergy = {
  openFrontView: LiquidWindEnergyImg,
  rightSideView: DeviceImg,
};

export const energyProductViewMap = new Map<DeviceTypeEnum, ProductView>([
  [DeviceTypeEnum.Energy, Energy],
  [DeviceTypeEnum.BWattEnergy, Energy],
  [DeviceTypeEnum.YTEnergy, Energy],
  [DeviceTypeEnum.LiquidEnergy, LiquidEnergy],
  [DeviceTypeEnum.LiquidEnergy261A, Energy261A],
  [DeviceTypeEnum.LiquidEnergy261B, Energy261B],
  [DeviceTypeEnum.Liquid2Energy, LiquidEnergy],
  [DeviceTypeEnum.Wind2Energy, Energy],
  [DeviceTypeEnum.SmallEnergy, SmallEnergy],
  [DeviceTypeEnum.PvEnergy, PvEnergy],
  [DeviceTypeEnum.ChargeY601, Charge],
  [DeviceTypeEnum.ChargeY801, Charge],
  [DeviceTypeEnum.Charge601, Charge],
  [DeviceTypeEnum.Charge801, Charge],
  [DeviceTypeEnum.Charge1201, Charge],
  [DeviceTypeEnum.Charge1601, Charge],
  [DeviceTypeEnum.Charge1801, Charge],
  [DeviceTypeEnum.Charge2401, Charge],
  [DeviceTypeEnum.Charge3201, Charge],
  [DeviceTypeEnum.Charge4001, Charge],
  [DeviceTypeEnum.Charge1201V1, Charge],
  [DeviceTypeEnum.Charge1601V1, Charge],
  [DeviceTypeEnum.Charge1801V1, Charge],
  [DeviceTypeEnum.Charge2401V1, Charge],
  [DeviceTypeEnum.Charge3201V1, Charge],
  [DeviceTypeEnum.Charge4001V1, Charge],
  [DeviceTypeEnum.ChargeY3001, Charge],
  [DeviceTypeEnum.ChargeY602, Charge2],
  [DeviceTypeEnum.Charge602, Charge2],
  [DeviceTypeEnum.Charge802, Charge2],
  [DeviceTypeEnum.ChargeY802, Charge2],
  [DeviceTypeEnum.Charge1202, Charge2],
  [DeviceTypeEnum.Charge1602, Charge2],
  [DeviceTypeEnum.Charge1802, Charge2],
  [DeviceTypeEnum.Charge2402, Charge2],
  [DeviceTypeEnum.Charge3202, Charge2],
  [DeviceTypeEnum.Charge4002, Charge2],
  [DeviceTypeEnum.Charge1202V1, Charge2],
  [DeviceTypeEnum.Charge1602V1, Charge2],
  [DeviceTypeEnum.Charge1802V1, Charge2],
  [DeviceTypeEnum.Charge2402V1, Charge2],
  [DeviceTypeEnum.Charge3202V1, Charge2],
  [DeviceTypeEnum.Charge4002V1, Charge2],
  [DeviceTypeEnum.ChargeY3002, Charge2],
  [DeviceTypeEnum.ChargeY240E12Gun, Charge2],
  [DeviceTypeEnum.ChargeMaster, ChargeMaster],
  [DeviceTypeEnum.ChargeY240E12Master, ChargeMaster],
  [DeviceTypeEnum.ChargeY120E12Master, ChargeMaster],
  [DeviceTypeEnum.Charge2501, ChargeTerminal1Gun],
  [DeviceTypeEnum.Charge6001, ChargeTerminal1Gun],
  [DeviceTypeEnum.Charge5001, ChargeTerminal1Gun],
  [DeviceTypeEnum.ChargeT600E1Terminal1Gun, ChargeTerminal1Gun],
  [DeviceTypeEnum.Charge2502, ChargeTerminal2Gun],
  [DeviceTypeEnum.Charge6002, ChargeTerminal2Gun],
  [DeviceTypeEnum.Charge5002, ChargeTerminal2Gun],
  [DeviceTypeEnum.ChargeEU3502, ChargeTerminal2Gun],
  [DeviceTypeEnum.ChargeEU5002, ChargeTerminal2Gun],
  [DeviceTypeEnum.ChargeT400E1Terminal2Gun, ChargeTerminal2Gun],
  [DeviceTypeEnum.ChargeT600E1Terminal2Gun, ChargeTerminal2Gun],
  [DeviceTypeEnum.FGCCEnergy, WindPvFirewood],
  [DeviceTypeEnum.React100XEnergy, Energy],
  [DeviceTypeEnum.React100WEnergy, Energy],
  [DeviceTypeEnum.LiquidEnergy232, LiquidEnergy],
  [DeviceTypeEnum.LiquidWindEnergy215, LiquidWindEnergy],
]);
