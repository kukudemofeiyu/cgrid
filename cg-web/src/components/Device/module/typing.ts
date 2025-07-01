/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2024-12-26 15:20:48
 * @LastEditTime: 2024-12-26 15:20:48
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/Device/module/typing.ts
 */

import { DeviceDataType } from "@/services/equipment";

export type ControlInterceptType = {
  deviceData?: DeviceDataType;
  onChange?: (data?: Record<string, any>) => void;
};