/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-01-06 16:45:44
 * @LastEditTime: 2024-11-06 10:54:38
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\EnergyInfo\Cabinet\helper.tsx
 */

import { DeviceDataType } from '@/services/equipment';
import { DeviceTypeEnum } from '@/utils/dictionary';
import styles from '../index.less';
import { ProductIdMapType } from './type';
import PackImg from '@/assets/image/station/energy/pack.png';

export const getProductTypeIdMap = (data: DeviceDataType[]) => {
  let productTypeIdMap: ProductIdMapType = {};
  data?.forEach?.((item) => {
    if (item.productTypeId) {
      productTypeIdMap[item.productTypeId] = {
        deviceId: item.id,
        productId: item.productId,
      };
      if (item.aliasSn) {
        productTypeIdMap[item.productTypeId + '' + item.aliasSn] = {
          deviceId: item.id,
          productId: item.productId,
        };
      }
    }
    if (item.children && item.children?.length) {
      const result = getProductTypeIdMap(item.children);
      productTypeIdMap = { ...productTypeIdMap, ...result };
    }
  });
  return productTypeIdMap;
};

export const getPackItems = () => {
  return (
    <div className={styles.parckContain}>
      {Array.from({ length: 10 }).map((_, index) => {
        return (
          <>
            <div
              key={index}
              className={styles.parck}
              style={{ order: index < 5 ? 4 - index : index }}
            >
              <div className="flex flex-center">
                <img className="mr4" src={PackImg} />
                PACK-{10 - index}
              </div>
            </div>
          </>
        );
      })}
    </div>
  );
};

export const energyProductIdMap = new Map([
  [DeviceTypeEnum.Energy, 'Rect'],
  [DeviceTypeEnum.BWattEnergy, 'Bwt'],
  [DeviceTypeEnum.YTEnergy, 'Wind'],
  [DeviceTypeEnum.LiquidEnergy, 'Liquid'],
  [DeviceTypeEnum.Liquid2Energy, 'LiquidE'],
  [DeviceTypeEnum.Wind2Energy, 'WindD'],
  [DeviceTypeEnum.SmallEnergy, 'SmallEnergy'],
  [DeviceTypeEnum.PvEnergy, 'PvEnergy'],
  [DeviceTypeEnum.ChargeY601, 'Charge1Gun'],
  [DeviceTypeEnum.ChargeY801, 'Charge1Gun'],
  [DeviceTypeEnum.Charge601, 'Charge1Gun'],
  [DeviceTypeEnum.Charge801, 'Charge1Gun'],
  [DeviceTypeEnum.Charge1201, 'Charge1Gun'],
  [DeviceTypeEnum.Charge1601, 'Charge1Gun'],
  [DeviceTypeEnum.Charge1801, 'Charge1Gun'],
  [DeviceTypeEnum.Charge2401, 'Charge1Gun'],
  [DeviceTypeEnum.Charge3201, 'Charge1Gun'],
  [DeviceTypeEnum.Charge4001, 'Charge1Gun'],
  [DeviceTypeEnum.Charge1201V1, 'Charge1Gun'],
  [DeviceTypeEnum.Charge1601V1, 'Charge1Gun'],
  [DeviceTypeEnum.Charge1801V1, 'Charge1Gun'],
  [DeviceTypeEnum.Charge2401V1, 'Charge1Gun'],
  [DeviceTypeEnum.Charge3201V1, 'Charge1Gun'],
  [DeviceTypeEnum.Charge4001V1, 'Charge1Gun'],
  [DeviceTypeEnum.ChargeY3001, 'Charge1Gun'],
  [DeviceTypeEnum.ChargeY602, 'Charge2Gun'],
  [DeviceTypeEnum.Charge602, 'Charge2Gun'],
  [DeviceTypeEnum.Charge802, 'Charge2Gun'],
  [DeviceTypeEnum.ChargeY802, 'Charge2Gun'],
  [DeviceTypeEnum.Charge1202, 'Charge2Gun'],
  [DeviceTypeEnum.Charge1602, 'Charge2Gun'],
  [DeviceTypeEnum.Charge1802, 'Charge2Gun'],
  [DeviceTypeEnum.Charge2402, 'Charge2Gun'],
  [DeviceTypeEnum.Charge3202, 'Charge2Gun'],
  [DeviceTypeEnum.Charge4002, 'Charge2Gun'],
  [DeviceTypeEnum.Charge1202V1, 'Charge2Gun'],
  [DeviceTypeEnum.Charge1602V1, 'Charge2Gun'],
  [DeviceTypeEnum.Charge1802V1, 'Charge2Gun'],
  [DeviceTypeEnum.Charge2402V1, 'Charge2Gun'],
  [DeviceTypeEnum.Charge3202V1, 'Charge2Gun'],
  [DeviceTypeEnum.Charge4002V1, 'Charge2Gun'],
  [DeviceTypeEnum.ChargeY3002, 'Charge2Gun'],
  [DeviceTypeEnum.ChargeY240E12Gun, 'Charge2Gun'],
  [DeviceTypeEnum.ChargeMaster, 'ChargeMaster'],
  [DeviceTypeEnum.ChargeY240E12Master, 'ChargeMaster'],
  [DeviceTypeEnum.ChargeY120E12Master, 'ChargeMaster'],
  [DeviceTypeEnum.Charge2501, 'ChargeTerminal1Gun'],
  [DeviceTypeEnum.Charge6001, 'ChargeTerminal1Gun'],
  [DeviceTypeEnum.Charge5001, 'ChargeTerminal1Gun'],
  [DeviceTypeEnum.ChargeT600E1Terminal1Gun, 'ChargeTerminal1Gun'],
  [DeviceTypeEnum.Charge2502, 'ChargeTerminal2Gun'],
  [DeviceTypeEnum.Charge6002, 'ChargeTerminal2Gun'],
  [DeviceTypeEnum.Charge5002, 'ChargeTerminal2Gun'],
  [DeviceTypeEnum.ChargeEU3502, 'ChargeTerminal2Gun'],
  [DeviceTypeEnum.ChargeEU5002, 'ChargeTerminal2Gun'],
  [DeviceTypeEnum.ChargeT400E1Terminal2Gun, 'ChargeTerminal2Gun'],
  [DeviceTypeEnum.ChargeT600E1Terminal2Gun, 'ChargeTerminal2Gun'],
  [DeviceTypeEnum.FGCCEnergy, 'WindPvFirewood'],
  [DeviceTypeEnum.React100XEnergy, 'Bwt'],
  [DeviceTypeEnum.React100WEnergy, 'Bwt'],
  [DeviceTypeEnum.LiquidEnergy232, 'LiquidE'],
  [DeviceTypeEnum.LiquidEnergy261A, 'Liquid261A'],
  [DeviceTypeEnum.LiquidEnergy261B, 'LiquidWind'],
  [DeviceTypeEnum.LiquidWindEnergy215, 'LiquidWind'],
]);
