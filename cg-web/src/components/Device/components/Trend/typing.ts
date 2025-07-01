/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-08 11:05:59
 * @LastEditTime: 2024-03-08 11:05:59
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\components\Power\typing.ts
 */

export type PowerDataType = {
  chargeElectricity: number;
  chargeCount: number;
  chargeMoney: number;
  chargeDuration: number;
  time: string;
};

export type TrendDataType = {
  chargeElectricityTotal?: number;
  chargeCountTotal?: number;
  todayChargeTime?: number;
  todayMoney?: number;
  list?: PowerDataType[];
};
