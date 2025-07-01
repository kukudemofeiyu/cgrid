import { DeviceTypeEnum } from '@/utils/dictionary';
import CabinetImg from '@/assets/image/product/cabinet.png';
import CabinetIntroImg from '@/assets/image/product/cabinet-intro.jpg';
import BoxSubstationIntroImg from '@/assets/image/product/transfer-intro.jpg';
import EnergyIntroImg from '@/assets/image/product/energy-intro.jpg';
import HwChargeStackIntroImg from '@/assets/image/product/hw-charge-stack-intro.jpg';
import PvInverterIntroImg from '@/assets/image/product/pv-inverter-intro.jpg';
import PvInverterCabinetIntroImg from '@/assets/image/product/pvInverter-intro.jpg';
import YtChargeIntroImg from '@/assets/image/product/yt-charge-intro.jpg';

export const deviceProductDataMap: Record<number, any> = {
  [DeviceTypeEnum.ExchangePowerCabinet]: {
    img: CabinetImg,
    introImg: CabinetIntroImg,
  },
  [DeviceTypeEnum.BoxSubstation]: {
    introImg: BoxSubstationIntroImg,
  },
  [DeviceTypeEnum.Energy]: {
    introImg: EnergyIntroImg,
  },
  [DeviceTypeEnum.YtCharge7]: {
    introImg: YtChargeIntroImg,
  },
  [DeviceTypeEnum.YtCharge120]: {
    introImg: YtChargeIntroImg,
  },
  [DeviceTypeEnum.YtCharge120LocalEms]: {
    introImg: YtChargeIntroImg,
  },
  [DeviceTypeEnum.YtCharge160]: {
    introImg: YtChargeIntroImg,
  },
  [DeviceTypeEnum.YtCharge160LocalEms]: {
    introImg: YtChargeIntroImg,
  },
  [DeviceTypeEnum.HwCharge]: {
    introImg: HwChargeStackIntroImg,
  },
  [DeviceTypeEnum.HwChargeLocalEms]: {
    introImg: HwChargeStackIntroImg,
  },
  [DeviceTypeEnum.YtCharge360]: {
    introImg: HwChargeStackIntroImg,
  },
  [DeviceTypeEnum.YtCharge360LocalEms]: {
    introImg: HwChargeStackIntroImg,
  },
  [DeviceTypeEnum.HwChargeChild]: {
    introImg: HwChargeStackIntroImg,
  },
  [DeviceTypeEnum.HwChargeSuperChild]: {
    introImg: HwChargeStackIntroImg,
  },
  [DeviceTypeEnum.HwChargeYt]: {
    introImg: HwChargeStackIntroImg,
  },
  [DeviceTypeEnum.YunCharge120]: {
    introImg: HwChargeStackIntroImg,
  },
  [DeviceTypeEnum.PvInverter4]: {
    introImg: PvInverterIntroImg,
  },
  [DeviceTypeEnum.PvInverter36]: {
    introImg: PvInverterIntroImg,
  },
  [DeviceTypeEnum.PvInverter11]: {
    introImg: PvInverterIntroImg,
  },
  [DeviceTypeEnum.PvInverter4LocalEms]: {
    introImg: PvInverterIntroImg,
  },
  [DeviceTypeEnum.PvInverter11LocalEms]: {
    introImg: PvInverterIntroImg,
  },
  [DeviceTypeEnum.GRWTPvInverter]: {
    introImg: PvInverterIntroImg,
  },
  [DeviceTypeEnum.PvInverterCabinet]: {
    introImg: PvInverterCabinetIntroImg,
  },
};
