/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-05-23 09:18:31
 * @LastEditTime: 2025-02-19 17:54:02
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/models/siteTypes.ts
 */

import React, { useReducer, useState } from 'react';
import { getSiteType } from '@/services/station';
import { useRequest } from 'umi';
import { mergeWith } from 'lodash';
import { SiteMonitorEnum, SiteTypeStrEnum } from '@/utils/enum';

export type SiteTypeType = {
  label?: string;
  value: string;
};

export type UnitType = {
  hasPv?: boolean;
  hasEnergy?: boolean;
  hasCharge?: boolean;
  hasExchange?: boolean;
  hasFan?: boolean;
  hasDiesel?: boolean;
  hasElse?: boolean;
};

const reducer = (
  state: SiteTypeType[] | undefined,
  action: { payload: SiteTypeType[]; type?: string },
) => {
  if (action.payload) {
    return action.payload;
  } else {
    return state;
  }
};

export const getUnitBySiteMonitor = (siteMonitors: SiteMonitorEnum[] | string): UnitType => {
  let monitor = Array.isArray(siteMonitors) ? siteMonitors : siteMonitors?.split?.('')?.map?.((item) => Number(item)) || [];
  const result: UnitType = {
    hasPv: monitor?.includes?.(SiteMonitorEnum.PV) || false,
    hasEnergy: monitor?.includes?.(SiteMonitorEnum.ES) || false,
    hasCharge: monitor?.includes?.(SiteMonitorEnum.CS) || false,
    hasExchange: monitor?.includes?.(SiteMonitorEnum.Exchange) || false,
    hasFan: monitor?.includes?.(SiteMonitorEnum.Fan) || false,
    hasDiesel: monitor?.includes?.(SiteMonitorEnum.Diesel) || false,
    hasElse: monitor?.includes?.(SiteMonitorEnum.Else) || false,
  };
  return result;
};

export const getUnitBySiteType = (siteType: SiteTypeStrEnum | string): UnitType => {
  const type = (siteType ?? '') + '';
  const result: UnitType = {
    hasPv: type?.indexOf?.(SiteTypeStrEnum.PV) > -1 || false,
    hasEnergy: type?.indexOf?.(SiteTypeStrEnum.ES) > -1 || false,
    hasCharge: type?.indexOf?.(SiteTypeStrEnum.CS) > -1 || false,
    hasExchange: type?.indexOf?.(SiteTypeStrEnum.Exchange) > -1 || false,
    hasFan: type?.indexOf?.(SiteTypeStrEnum.FAN) > -1 || false,
    hasDiesel: type?.indexOf?.(SiteTypeStrEnum.DIESEL) > -1 || false,
    hasElse: type?.includes?.(SiteTypeStrEnum.Exchange) || false,
  };
  return result;
};

const useSiteTypeModel = () => {
  const [state, dispatch] = useReducer(reducer, undefined);
  const [unit, setUnit] = useState<UnitType>({});

  const { run } = useRequest(() => { }, {
    manual: true,
    formatResult: (res) => {
      const result =
        res?.data?.map?.((item) => {
          return {
            value: item.value || '',
            monitor: item.value ? item.collectDataTypes?.sort()?.join('') || '' : '',
            label: item.name,
          };
        }) || [];

      const unitResult: UnitType = {};
      result.forEach((item) => {
        const itemUnit = getUnitBySiteType(item.monitor);
        mergeWith(unitResult, itemUnit, (newValue: boolean, oldValue: boolean) => {
          if (!oldValue) {
            return newValue;
          }
          return oldValue;
        });
      });
      setUnit(unitResult);
      dispatch({ payload: result });
      return result;
    },
  });

  return {
    siteTypes: state,
    refresh: run,
    unit,
  };
};

export default useSiteTypeModel;
