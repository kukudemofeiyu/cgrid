/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-01-03 09:26:59
 * @LastEditTime: 2025-02-19 17:51:53
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/data/areaConfig.ts
 */

import md5 from "md5";
import { getAreaConfig, getMonetaryUnit } from "@/services";
import { AreaDataType } from "@/types";
import { MonetaryUnitType } from "@/components/MonetaryUnit";
import { arrayToMap, formatMessage } from "@/utils";
import defaultSettings from "../../config/defaultSettings";

class AreaConfig {
  static _instance: AreaConfig | undefined;

  moneyUnit?: string;
  locale?: string;

  areaPromise?: Promise<AreaDataType[]>;
  moneyPromise?: Promise<MonetaryUnitType[]>;

  static getInstance() {
    if (!this._instance) {
      this._instance = new AreaConfig();
    }
    return this._instance;
  }

  constructor() {
    // this.init();
  }

  init() {
    this.getArea();
    this.getMoneyUnit();
  }

  getArea() {
    const ts = new Date().getTime();
    this.areaPromise = getAreaConfig({
      signature: md5(`Yotai${ts}`),
      ts,
    }).then(({ data }) => {
      return data;
    }).catch(() => {
      return [];
    });
    return this.areaPromise;
  }

  getMoneyUnit() {
    if (!defaultSettings.authorityWhiteList?.includes(window.location.pathname)) {
      this.moneyPromise = getMonetaryUnit().then(({ data }) => {
        if (Array.isArray(data)) {
          localStorage.setItem('monetaryUnitEnum', JSON.stringify(data));
        }
        return data;
      }).catch(() => {
        return [];
      });
    } else {
      this.moneyPromise = Promise.resolve([]);
    }
    return this.moneyPromise;
  }

  loadData() {
    const promiseAll = Promise.all([this.areaPromise, this.moneyPromise]);
    promiseAll.then(([areaData, moneyUnits]) => {
      const origin = window.location.origin;
      const codeUnitMay = arrayToMap(moneyUnits || [], 'code', 'unit');
      const currentAreaConfig = areaData?.find?.(item => item.domainName == origin);
      this.moneyUnit = codeUnitMay[currentAreaConfig?.monetaryUnit || 1] ?? formatMessage({ id: 'common.rmb', defaultMessage: '元' });
      this.locale = currentAreaConfig?.language;
    });
    return promiseAll;
  }

};

export default AreaConfig;
