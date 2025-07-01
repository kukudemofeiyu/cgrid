/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-01-09 10:26:35
 * @LastEditTime: 2025-01-09 14:37:28
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/Device/Control/hooks/useRealtimeDataFormat.ts
 */

import { DeviceServiceModelType } from "@/types/device";
import { DeviceModelDescribeTypeEnum, formatModelValue, getPropsFromTree } from "@/utils";
import { useMemo } from "react";
import { getRealField } from "../helper";

const useRealtimeDataFormat = (
  props: {
    deviceId?: string;
    groupData?: DeviceServiceModelType[],
    realTimeData?: Record<string, any>,
    extraRealTimeData?: Record<string, any>,
  }
) => {

  const { deviceId, groupData, realTimeData, extraRealTimeData } = props;

  const realtimeDataFormat = useMemo(() => {
    const collectionModelMap = getPropsFromTree<DeviceServiceModelType, DeviceServiceModelType>(groupData, ['id', 'deviceId', 'dataType'], 'children', (item) => item.type == DeviceModelDescribeTypeEnum.Property);

    const result: Record<string, any> = {
      ...realTimeData?.[deviceId || ''],
      ...extraRealTimeData,
    };
    collectionModelMap.forEach(field => {
      const realFieldId = getRealField(field?.id);
      if (field?.deviceId) {
        result[field?.deviceId] = result[field?.deviceId] || {};
        result[field?.deviceId][realFieldId + 'Format'] = formatModelValue(result[field?.deviceId][realFieldId], field?.dataType || {});
      } else {
        result[realFieldId + 'Format'] = formatModelValue(result[realFieldId], field?.dataType || {});
      }
    });
    return result;
  }, [deviceId, groupData, realTimeData, extraRealTimeData]);

  return {
    realtimeDataFormat,
  }
};

export default useRealtimeDataFormat;