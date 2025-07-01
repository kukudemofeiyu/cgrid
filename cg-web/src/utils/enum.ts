/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-11-22 15:11:07
 * @LastEditTime: 2024-10-14 16:23:40
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\utils\enum.ts
 */

export const enum SiteTypeStrEnum {
  PV = '1', //光
  ES = '2', //储
  CS = '3', //充
  ES_CS = '23',
  PV_CS = '13',
  PV_ES = '12',
  PV_ES_CS = '123',
  Exchange = '4',
  FAN = '6',
  DIESEL = '7',
  OTHER = '100',
}

export const enum SiteMonitorEnum {
  //1-光伏 2-储能 3-充电桩 4-其他负载 5-市电 6-风机 7-柴发
  PV = 1,
  ES,
  CS,
  Else,
  Elec,
  Fan,
  Diesel,
  Exchange = 9999,
}

export const enum ModelSizeEnum {
  TwoCol = '552px',
  ThreeCol = '816px',
  FourCol = '1080px',
}

export const enum TaskExecuteStatus {
  // 0执行中 1成功 2失败
  Inprogress = 0,
  Success = 1,
  Failure = 2,
}

export const enum FixesExporStatus {
  // 导出状态：0-待导出 1-导出中 2-导出失败 3-导出完毕
  Await = 0,
  Exporting = 1,
  Failure = 2,
  Finish = 3,
}
export const enum FixesStatus {
  // 修复状态：0-待修复 1-修复 2-修复失败 3-修复完毕
  Await = 0,
  Exporting = 1,
  Failure = 2,
  Finish = 3,
}

export enum SeperateTypeEnum {
  Platform = 1,
  Agent,
  Person,
  Charge,
}
