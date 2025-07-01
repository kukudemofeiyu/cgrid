/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-03-08 11:05:59
 * @LastEditTime: 2024-04-19 16:20:28
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\components\Power\typing.ts
 */

export type PowerDataType = {
  list: {
    power: number;
    powerUseRate: number;
    time: string;
  }[];
};
