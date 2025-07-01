/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-11-22 15:11:07
 * @LastEditTime: 2025-02-22 11:45:43
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/utils/dict.ts
 */
import { OptionType, ValueEnum } from '@/types';
import { MapTypeEnum, reportTypeEnum, timeDimensionEnum } from './dictionary';
import { formatMessage } from './index';

export const alarmSource = {
  0: {
    text: formatMessage({ id: 'device.deviceSide', defaultMessage: '设备端' }),
  },
  1: {
    text: formatMessage({ id: 'device.platformRules', defaultMessage: '平台规则' }),
  },
};
export const electricMoneyMap = new Map([
  [1, formatMessage({ id: 'siteManage.set.demandCharge', defaultMessage: '需量电费' })],
  [0, formatMessage({ id: 'siteManage.set.capacityCharge', defaultMessage: '容量电费' })],
]);

export const runningState = {
  1: {
    text: formatMessage({ id: 'common.normal', defaultMessage: '正常' }),
    status: 'Success',
  },
  0: {
    text: formatMessage({ id: 'common.abnormal', defaultMessage: '异常' }),
    status: 'Error',
  },
};

export const buildStatus = {
  1: {
    text: formatMessage({ id: 'siteManage.set.siteStatusSuccess', defaultMessage: '已投运' }),
    status: 'Success',
  },
  0: {
    text: formatMessage({ id: 'siteManage.set.siteStatusProcessing', defaultMessage: '建设中' }),
    status: 'Processing',
  },
};

export const enableStatus = {
  0: {
    text: formatMessage({ id: 'common.disable', defaultMessage: '禁用' }),
    status: 'Success',
  },
  1: {
    text: formatMessage({ id: 'common.enable', defaultMessage: '启用' }),
    status: 'Error',
  },
};

export const jumpMethodEnum = {
  0: {
    text: formatMessage({ id: 'common.1001', defaultMessage: '直接跳转' }),
    status: 'Success',
  },
  1: {
    text: formatMessage({ id: 'common.1002', defaultMessage: 'SSO跳转' }),
    status: 'Error',
  },
};

export const dataSaveTime = {
  0: formatMessage({ id: 'common.time.oneMonth', defaultMessage: '一个月' }),
  1: formatMessage({ id: 'common.time.threeMonth', defaultMessage: '三个月' }),
  2: formatMessage({ id: 'common.time.sixMonth', defaultMessage: '六个月' }),
  3: formatMessage({ id: 'common.time.oneYear', defaultMessage: '一年' }),
};

export const noticeMethod = {
  0: formatMessage({ id: 'common.notInform', defaultMessage: '不通知' }),
  1: formatMessage({ id: 'common.shortMessage', defaultMessage: '短信' }),
  2: formatMessage({ id: 'common.email', defaultMessage: '邮件' }),
  3: formatMessage({ id: 'common.SMSAndEmail', defaultMessage: '短信+邮件' }),
};

export const deviceAlarmStatus = {
  1: {
    text: formatMessage({ id: 'common.warning', defaultMessage: '告警' }),
    status: 'Processing',
    icon: 'red',
  },
  0: {
    text: formatMessage({ id: 'common.normal', defaultMessage: '正常' }),
    status: 'Success',
    icon: 'green',
  },
};

export enum OnlineStatusEnum {
  Offline,
  Online,
  None,
}

export const onlineStatus: ValueEnum = {
  [OnlineStatusEnum.None]: {
    text: formatMessage({ id: 'common.notConfigured', defaultMessage: '未配置' }),
    status: '',
  },
  [OnlineStatusEnum.Online]: {
    text: formatMessage({ id: 'common.onLine', defaultMessage: '在线' }),
    icon: 'green',
    status: 'Processing',
  },
  [OnlineStatusEnum.Offline]: {
    text: formatMessage({ id: 'common.offline', defaultMessage: '离线' }),
    icon: 'red',
    status: 'Default',
  },
};

export enum ChargingTypeEnum {
  Card,
  App,
  LocalManage,
  Vin,
  PassWord,
  Hand,
}

export enum GunTypeEnum {
  DC = 1,
  AC = 2,
}

export const GunType: ValueEnum = {
  [GunTypeEnum.DC]: {
    text: formatMessage({ id: 'device.DC', defaultMessage: '直流' }),
  },
  [GunTypeEnum.AC]: {
    text: formatMessage({ id: 'device.AC', defaultMessage: '交流' }),
  },
};

export enum SourceTypeEnum {
  Voltage12V,
  Voltage24V,
}

export const SourceType: ValueEnum = {
  [SourceTypeEnum.Voltage12V]: {
    text: '12V',
  },
  [SourceTypeEnum.Voltage24V]: {
    text: '24V',
  },
};

export enum ServerTypeEnum {
  Null = 0,
  SINOPEC = 1,
  XIAOJI = 2,
  JoinFastCharge = 3,
  None = 4,
  FastCharg = 5,
  StateGrid = 6,
  SANYUN = 7,
  ZHJN = 8,
  PT = 9,
  XXCD = 10,
  XX = 11,
  XYSPT = 12,
  YKCen = 13,
  SN = 14,
  ANC = 15,
  YTYW = 16,
  LX = 17,
  JTZX = 18,
}

export enum ChargingStrategyEnum {
  Auto,
  Time,
  Money,
  Quantity,
  SOC,
}

export const ChargingStrategy: ValueEnum = {
  [ChargingStrategyEnum.Auto]: {
    text: formatMessage({ id: 'device.auto', defaultMessage: '自动充满' }),
  },
  [ChargingStrategyEnum.Time]: {
    text: formatMessage({ id: 'device.time', defaultMessage: '按时间' }),
  },
  [ChargingStrategyEnum.Money]: {
    text: formatMessage({ id: 'device.money', defaultMessage: '按金额' }),
  },
  [ChargingStrategyEnum.Quantity]: {
    text: formatMessage({ id: 'device.quantity', defaultMessage: '按电量' }),
  },
  [ChargingStrategyEnum.SOC]: {
    text: formatMessage({ id: 'device.soc', defaultMessage: '按soc' }),
  },
};

export const ChargingStrategyParam: ValueEnum = {
  [ChargingStrategyEnum.Auto]: {
    text: '',
  },
  [ChargingStrategyEnum.Time]: {
    text: '(s)',
  },
  [ChargingStrategyEnum.Money]: {
    text: `(${formatMessage({ id: 'common.rmb', defaultMessage: '元' })})`,
  },
  [ChargingStrategyEnum.Quantity]: {
    text: '(kWh)',
  },
  [ChargingStrategyEnum.SOC]: {
    text: '',
  },
};

export const onInstallStatus = {
  0: {
    text: formatMessage({ id: 'equipmentList.unInstall', defaultMessage: '未安装' }),
  },
  1: {
    text: formatMessage({ id: 'equipmentList.installed', defaultMessage: '已安装' }),
  },
};

export const onlineStatus1 = {
  [OnlineStatusEnum.None]: {
    text: formatMessage({ id: 'common.notConfigured', defaultMessage: '未配置' }),
    status: '',
  },
  [OnlineStatusEnum.Offline]: {
    text: formatMessage({ id: 'common.onLine', defaultMessage: '在线' }),
    icon: 'green',
    status: 'Processing',
  },
  [OnlineStatusEnum.Online]: {
    text: formatMessage({ id: 'common.offline', defaultMessage: '离线' }),
    icon: 'red',
    status: 'Default',
  },
};

export const connectStatus = {
  1: {
    text: formatMessage({ id: 'common.onLine', defaultMessage: '在线' }),
    status: 'Success',
  },
  0: {
    text: formatMessage({ id: 'common.offline', defaultMessage: '离线' }),
    status: 'Error',
  },
};

export const alarmStatus = {
  0: {
    text: formatMessage({ id: 'common.alarming', defaultMessage: '告警中' }),
    status: 'Error',
  },
  1: {
    text: formatMessage({ id: 'common.eliminated', defaultMessage: '已消除' }),
    status: 'Default',
  },
};

export const alarmStatus1 = {
  0: {
    text: formatMessage({ id: 'common.normal', defaultMessage: '正常' }),
    status: 'success',
  },
  1: {
    text: formatMessage({ id: 'common.warning', defaultMessage: '告警' }),
    status: 'Error',
  },
};

export const chargingAndDischargingStatus = {
  0: {
    text: formatMessage({ id: 'common.stewing', defaultMessage: '静置' }),
    status: 'Default',
  },
  1: {
    text: formatMessage({ id: 'siteMonitor.discharge', defaultMessage: '放电' }),
    status: 'Warning',
  },
  2: {
    text: formatMessage({ id: 'siteMonitor.charge', defaultMessage: '充电' }),
    status: 'Processing',
  },
};

export const chargeAndDischargeStatus: ValueEnum = {
  0: {
    text: formatMessage({ id: 'device.discharge', defaultMessage: '放电' }),
  },
  1: {
    text: formatMessage({ id: 'device.charge', defaultMessage: '充电' }),
  },
};

export const systemMode = {
  0: {
    text: formatMessage({ id: 'siteMonitor.manualMode', defaultMessage: '手动模式' }),
  },
  1: {
    text: formatMessage({ id: 'siteMonitor.autoMode', defaultMessage: '自动模式' }),
  },
};

// 0是停机，1是故障，2是运行
export const workStatus = {
  2: {
    text: formatMessage({ id: 'siteMonitor.run', defaultMessage: '运行' }),
  },
  1: {
    text: formatMessage({ id: 'siteMonitor.fault', defaultMessage: '故障' }),
  },
  0: {
    text: formatMessage({ id: 'siteMonitor.halt', defaultMessage: '停机' }),
  },
};

export const logType = {
  0: { text: formatMessage({ id: 'siteMonitor.deviceDownlink', defaultMessage: '设备下行' }) },
  1: { text: formatMessage({ id: 'siteMonitor.deviceReport', defaultMessage: '设备上报' }) },
  2: { text: formatMessage({ id: 'siteMonitor.ruleTriggering', defaultMessage: '规则触发' }) },
};

export const reportType = new Map([
  [reportTypeEnum.Site, formatMessage({ id: 'dataManage.siteReport', defaultMessage: '站点报表' })],
  [
    reportTypeEnum.Electric,
    formatMessage({ id: 'dataManage.mainsReport', defaultMessage: '市电报表' }),
  ],
  [
    reportTypeEnum.PvInverter,
    formatMessage({ id: 'dataManage.pvReport', defaultMessage: '光伏报表' }),
  ],
  [
    reportTypeEnum.Energy,
    formatMessage({ id: 'dataManage.storageReport', defaultMessage: '储能报表' }),
  ],
  [
    reportTypeEnum.ChargeOrder,
    formatMessage({ id: 'dataManage.chargingOrderReport', defaultMessage: '充电桩订单报表' }),
  ],
  [
    reportTypeEnum.ChargeBase,
    formatMessage({ id: 'dataManage.chargingFoundationReport', defaultMessage: '充电桩基础报表' }),
  ],
  [reportTypeEnum.FanColumns, formatMessage({ id: 'dataManage.1091', defaultMessage: '风机报表' })],
  [
    reportTypeEnum.ChaiFaColumns,
    formatMessage({ id: 'dataManage.1092', defaultMessage: '柴发报表' }),
  ],
  [reportTypeEnum.Meter, formatMessage({ id: 'dataManage.1122', defaultMessage: '抄表报表' })],
  [
    reportTypeEnum.Else,
    formatMessage({ id: 'dataManage.otherLoadReport', defaultMessage: '其他负载报表' }),
  ],
]);

export const timeDimension = new Map([
  [
    timeDimensionEnum.Day,
    {
      text: formatMessage({ id: 'dataManage.dayStatistics', defaultMessage: '自定义日统计' }),
      format: 'YYYY-MM-DD',
    },
  ],
  [
    timeDimensionEnum.Month,
    {
      text: formatMessage({ id: 'dataManage.monthStatistics', defaultMessage: '月循环统计' }),
      format: 'YYYY-MM',
    },
  ],
  [
    timeDimensionEnum.Year,
    {
      text: formatMessage({ id: 'dataManage.yearStatistics', defaultMessage: '按年统计' }),
      format: 'YYYY',
    },
  ],
  [
    timeDimensionEnum.Cycle,
    {
      text: formatMessage({
        id: 'dataManage.lifeCycleStatistics',
        defaultMessage: '按生命周期统计',
      }),
    },
  ],
]);

export const alarmClearStatus = {
  0: {
    text: formatMessage({ id: 'dataManage.generate', defaultMessage: '产生' }),
  },
  1: {
    text: formatMessage({ id: 'dataManage.eliminate', defaultMessage: '消除' }),
  },
};

export const cleanUpType = {
  0: formatMessage({ id: 'dataManage.automaticRecovery', defaultMessage: '自动恢复' }),
  1: formatMessage({ id: 'dataManage.manualClear', defaultMessage: '手动消除' }),
};

export const effectStatus = {
  0: {
    text: formatMessage({ id: 'taskManage.valid', defaultMessage: '有效' }),
    status: 'Success',
  },
  1: {
    text: formatMessage({ id: 'taskManage.invalid', defaultMessage: '无效' }),
    status: 'Error',
  },
};

export const enum SiteTypeEnum {
  PV = 1,
  ES = 2,
  CS = 3,
  ES_CS = 23,
  PV_CS = 13,
  PV_ES = 12,
  PV_ES_CS = 123,
  Exchange = 4,
}

export type SiteTypeEnumType = `${(typeof SiteTypeEnum)[keyof typeof SiteTypeEnum]}`;

export const siteType = {
  0: { text: formatMessage({ id: 'common.all', defaultMessage: '全部' }) },
  [SiteTypeEnum.PV]: { text: formatMessage({ id: 'screen.pvSite', defaultMessage: '光伏站点' }) },
  [SiteTypeEnum.ES]: {
    text: formatMessage({ id: 'screen.storageSite', defaultMessage: '储能站点' }),
  },
  [SiteTypeEnum.CS]: {
    text: formatMessage({ id: 'screen.chargingStation', defaultMessage: '充电站点' }),
  },
  [SiteTypeEnum.ES_CS]: {
    text: formatMessage({ id: 'screen.storageChargeStation', defaultMessage: '储充站点' }),
  },
  [SiteTypeEnum.PV_CS]: {
    text: formatMessage({ id: 'screen.pvChargeStation', defaultMessage: '光充站点' }),
  },
  [SiteTypeEnum.PV_ES]: {
    text: formatMessage({ id: 'screen.opticalStorageSite', defaultMessage: '光储站点' }),
  },
  [SiteTypeEnum.PV_ES_CS]: {
    text: formatMessage({
      id: 'screen.opticalStorageChargingStation',
      defaultMessage: '光储充站点',
    }),
  },
};

export const platformTypes = {
  0: {
    text: formatMessage({ id: 'system.Version.android', defaultMessage: '安卓' }),
  },
  1: {
    text: formatMessage({ id: 'system.Version.ios', defaultMessage: '苹果' }),
  },
};

export const platform = {
  1: {
    text: formatMessage({ id: 'dataManage.1037', defaultMessage: '自研' }),
  },
  0: {
    text: formatMessage({ id: 'dataManage.1038', defaultMessage: '矩形' }),
  },
};

export const enableOptions = [
  {
    value: 1,
    label: formatMessage({ id: 'common.disable', defaultMessage: '禁用' }),
  },
  {
    value: 0,
    label: formatMessage({ id: 'common.enabled', defaultMessage: '使能' }),
  },
];

export const masterSlaveEnum: ValueEnum = {
  0: {
    text: formatMessage({ id: 'device.host', defaultMessage: '主机' }),
  },
  1: {
    text: formatMessage({ id: 'device.slave', defaultMessage: '从机' }),
  },
};
export const masterSlave1Enum: ValueEnum = {
  1: {
    text: formatMessage({ id: 'device.host', defaultMessage: '主机' }),
  },
  0: {
    text: formatMessage({ id: 'device.slave', defaultMessage: '从机' }),
  },
};
export const masterSlave2Enum: ValueEnum = {
  0: {
    text: formatMessage({ id: 'common.master', defaultMessage: '主' }),
  },
  1: {
    text: formatMessage({ id: 'common.slave', defaultMessage: '从' }),
  },
};
export const meterSerialNumberEnum: ValueEnum = {
  1: {
    text: formatMessage({ id: 'device.inverterMeter', defaultMessage: '计量电表' }),
  },
  2: {
    text: formatMessage({ id: 'device.gridMeter', defaultMessage: '变压器电表' }),
  },
};

export const connectEnum: ValueEnum = {
  1: {
    text: formatMessage({ id: 'common.connect', defaultMessage: '在线' }),
  },
  0: {
    text: formatMessage({ id: 'common.break', defaultMessage: '离线' }),
  },
};
export const weekInfo = [
  formatMessage({ id: 'date.sunday', defaultMessage: '周日' }),
  formatMessage({ id: 'date.monday', defaultMessage: '周一' }),
  formatMessage({ id: 'date.tuesday', defaultMessage: '周二' }),
  formatMessage({ id: 'date.wednesday', defaultMessage: '周三' }),
  formatMessage({ id: 'date.thursday', defaultMessage: '周四' }),
  formatMessage({ id: 'date.friday', defaultMessage: '周五' }),
  formatMessage({ id: 'date.saturday', defaultMessage: '周六' }),
  formatMessage({ id: 'date.sunday', defaultMessage: '周日' }),
];

export const mapTypeOptions: OptionType[] = [
  {
    label: formatMessage({ id: 'common.amap', defaultMessage: '高德' }),
    value: MapTypeEnum.AMap,
  },
  {
    label: formatMessage({ id: 'common.google', defaultMessage: '谷歌' }),
    value: MapTypeEnum.Google,
  },
];

export const timeZoneOptions: OptionType[] = [
  {
    label:
      '(UTC+08:00) ' + formatMessage({ id: 'system.chinaShanghai', defaultMessage: '中国，上海' }),
    value: 8,
    position: { lng: 116.407649, lat: 39.903439 },
  },
  {
    label:
      '(UTC+0) ' + formatMessage({ id: 'system.africaGuinea', defaultMessage: '非洲，几内亚' }),
    value: 0,
    position: { lng: -13.592248, lat: 9.643234 },
  },
];

export const upgradeForce = {
  0: {
    text: formatMessage({ id: 'common.no', defaultMessage: '否' }),
  },
  1: {
    text: formatMessage({ id: 'common.yes', defaultMessage: '是' }),
  },
};

export const aggregationTime = [
  {
    value: '',
    label: formatMessage({ id: 'dataManage.1095', defaultMessage: '不聚合' }),
  },
  {
    value: '1',
    label: formatMessage({ id: 'dataManage.1064', defaultMessage: '1分钟' }),
  },
  {
    value: '2',
    label: formatMessage({ id: 'dataManage.1065', defaultMessage: '2分钟' }),
  },
  {
    value: '5',
    label: formatMessage({ id: 'dataManage.1066', defaultMessage: '5分钟' }),
  },
  {
    value: '10',
    label: formatMessage({ id: 'dataManage.1067', defaultMessage: '10分钟' }),
  },
  {
    value: '15',
    label: formatMessage({ id: 'dataManage.1068', defaultMessage: '15分钟' }),
  },
  {
    value: '30',
    label: formatMessage({ id: 'dataManage.1069', defaultMessage: '30分钟' }),
  },
];

export const wayEnum = {
  0: {
    text: formatMessage({ id: 'siteManage.1063', defaultMessage: '最大值' }),
  },
  1: {
    text: formatMessage({ id: 'siteManage.1064', defaultMessage: '最小值' }),
  },
  2: {
    text: formatMessage({ id: 'siteManage.1065', defaultMessage: '平均值' }),
  },
  3: {
    text: formatMessage({ id: 'siteManage.1066', defaultMessage: '第一个值' }),
  },
  4: {
    text: formatMessage({ id: 'siteManage.1067', defaultMessage: '最后一个值' }),
  },
};

export const taskStatusEnum = {
  // 0正在执行 1执行完成 2执行错误  先这样定吧
  0: {
    text: formatMessage({ id: 'dataManage.1076', defaultMessage: '执行中' }),
  },
  1: {
    text: formatMessage({ id: 'dataManage.1078', defaultMessage: '执行完毕' }),
  },
  2: {
    text: formatMessage({ id: 'dataManage.1077', defaultMessage: '执行失败' }),
  },
};

export const logSelect = {
  0: {
    text: formatMessage({ id: 'common.fail', defaultMessage: '失败' }),
  },
  1: {
    text: formatMessage({ id: 'common.success', defaultMessage: '成功' }),
  },
};

export const alarmPushModesOptions = {
  '0': {
    text: formatMessage({ id: 'common.1009', defaultMessage: 'APP消息推送' }),
    status: '0',
  },
  '1': {
    text: formatMessage({ id: 'common.mailbox', defaultMessage: '邮箱' }),
    status: '1',
  },
};

export const exportStatusSelect = {
  0: {
    text: formatMessage({ id: 'dataManage.1111', defaultMessage: '待导出' }),
  },
  1: {
    text: formatMessage({ id: 'dataManage.1112', defaultMessage: '导出中' }),
  },
  2: {
    text: formatMessage({ id: 'dataManage.1113', defaultMessage: '导出失败' }),
  },
  3: {
    text: formatMessage({ id: 'dataManage.1114', defaultMessage: '导出完毕' }),
  },
};

export const fixesStatusSelect = {
  0: {
    text: formatMessage({ id: 'dataManage.1115', defaultMessage: '待修复' }),
  },
  1: {
    text: formatMessage({ id: 'dataManage.1116', defaultMessage: '修复中' }),
  },
  2: {
    text: formatMessage({ id: 'dataManage.1117', defaultMessage: '修复失败' }),
  },
  3: {
    text: formatMessage({ id: 'dataManage.1118', defaultMessage: '修复完毕' }),
  },
};
export const dataSourceType = {
  report: {
    text: formatMessage({ id: 'siteMonitor.deviceReport', defaultMessage: '设备上报' }),
  },
  alone: {
    text: formatMessage({ id: 'physicalModel.1068', defaultMessage: '云端配置' }),
  },
  share: {
    text: formatMessage({ id: 'physicalModel.1069', defaultMessage: '云端配置和设备上报' }),
  },
};
export const LanguageType: Record<string, any> = {
  'en-US': {
    text: formatMessage({ id: 'common.1017', defaultMessage: '英文' }),
  },
  'zh-CN': {
    text: formatMessage({ id: 'common.1016', defaultMessage: '简体中文' }),
  },
  'fa-IR': {
    text: formatMessage({ id: 'common.1018', defaultMessage: '波斯语' }),
  },
  'es-ES': {
    text: formatMessage({ id: 'common.1025', defaultMessage: '西班牙语' }),
  },
};

export const chargeTypeOption = {
  0: formatMessage({ id: '', defaultMessage: '快充' }),
  1: formatMessage({ id: '', defaultMessage: '慢充' }),
};

export const feeOption = {
  0: formatMessage({ id: '', defaultMessage: '收费' }),
  1: formatMessage({ id: '', defaultMessage: '免费' }),
};

export const gunStatus = {
  0: formatMessage({ id: '', defaultMessage: '离线' }),
  1: formatMessage({ id: '', defaultMessage: '故障' }),
  2: formatMessage({ id: '', defaultMessage: '空闲' }),
  3: formatMessage({ id: '', defaultMessage: '充电' }),
  4: formatMessage({ id: '', defaultMessage: '已插枪' }),
  5: formatMessage({ id: '', defaultMessage: '占用' }),
};

export const agreementOption = {
  0: formatMessage({ id: '', defaultMessage: '云快充' }),
  1: formatMessage({ id: '', defaultMessage: 'OCPP1.6J' }),
  2: formatMessage({ id: '', defaultMessage: '万高' }),
};

export const defaultOption = {
  0: formatMessage({ id: '', defaultMessage: '默认' }),
  1: formatMessage({ id: '', defaultMessage: '否' }),
};

export const timeTypeOption = {
  0: formatMessage({ id: '', defaultMessage: '尖时段' }),
  1: formatMessage({ id: '', defaultMessage: '峰时段' }),
  2: formatMessage({ id: '', defaultMessage: '平时段' }),
  3: formatMessage({ id: '', defaultMessage: '谷时段' }),
};

export const seperateTypeOption = {
  0: formatMessage({ id: '', defaultMessage: '电费' }),
  1: formatMessage({ id: '', defaultMessage: '服务费' }),
  2: formatMessage({ id: '', defaultMessage: '电费+服务费' }),
};

export const invoiceStatus = {
  0: formatMessage({ id: '', defaultMessage: '待审核' }),
  1: formatMessage({ id: '', defaultMessage: '驳回' }),
  2: formatMessage({ id: '', defaultMessage: '已开票' }),
  3: formatMessage({ id: '', defaultMessage: '开票成功签章中' }),
  4: formatMessage({ id: '', defaultMessage: '开票失败' }),
  5: formatMessage({ id: '', defaultMessage: '开票成功签章失败' }),
  6: formatMessage({ id: '', defaultMessage: '作废中' }),
  7: formatMessage({ id: '', defaultMessage: '已作废' }),
  8: formatMessage({ id: '', defaultMessage: '开票完成(最终状态)' }),
};

export const rechargeTypeOption = {
  0: formatMessage({ id: '', defaultMessage: '充值' }),
  1: formatMessage({ id: '', defaultMessage: '消费' }),
  2: formatMessage({ id: '', defaultMessage: '赠送' }),
  3: formatMessage({ id: '', defaultMessage: '系统充值' }),
  4: formatMessage({ id: '', defaultMessage: '退款' }),
  5: formatMessage({ id: '', defaultMessage: '退款失败' }),
  6: formatMessage({ id: '', defaultMessage: '转账' }),
  7: formatMessage({ id: '', defaultMessage: '订单退款' }),
  8: formatMessage({ id: '', defaultMessage: '提现' }),
  9: formatMessage({ id: '', defaultMessage: '扣除赠送' }),
};

export const rechargeChannel = {
  0: formatMessage({ id: '', defaultMessage: '前台充值' }),
  1: formatMessage({ id: '', defaultMessage: '后台充值' }),
};

export const withdrawStatus = {
  0: formatMessage({ id: '', defaultMessage: '待审核' }),
  1: formatMessage({ id: '', defaultMessage: '审核中' }),
  2: formatMessage({ id: '', defaultMessage: '已提现' }),
  3: formatMessage({ id: '', defaultMessage: '提现失败' }),
};

export const siteDimention = {
  0: formatMessage({ id: '', defaultMessage: '全部站点' }),
  1: formatMessage({ id: '', defaultMessage: '部分站点' }),
};

export const pushOption = {
  0: formatMessage({ id: '', defaultMessage: '不推送' }),
  1: formatMessage({ id: '', defaultMessage: '推送' }),
};

export const secretKeyOption = {
  0: formatMessage({ id: '', defaultMessage: '我方秘钥' }),
  1: formatMessage({ id: '', defaultMessage: '对方秘钥' }),
};

export const siteStatus = {
  0: formatMessage({ id: '', defaultMessage: '未知' }),
  1: formatMessage({ id: '', defaultMessage: '建设中' }),
  2: formatMessage({ id: '', defaultMessage: '关闭下线' }),
  3: formatMessage({ id: '', defaultMessage: '维护中' }),
  4: formatMessage({ id: '', defaultMessage: '正常使用' }),
  5: formatMessage({ id: '', defaultMessage: '歇业' }),
};

export const operateStatus = {
  0: formatMessage({ id: '', defaultMessage: '未知' }),
  1: formatMessage({ id: '', defaultMessage: '建设中' }),
  2: formatMessage({ id: '', defaultMessage: '关闭下线' }),
  3: formatMessage({ id: '', defaultMessage: '维护中' }),
  4: formatMessage({ id: '', defaultMessage: '正常使用' }),
};

export const supportOption = {
  0: formatMessage({ id: '', defaultMessage: '不支持' }),
  1: formatMessage({ id: '', defaultMessage: '支持' }),
};

export const deviceType = {
  0: formatMessage({ id: '', defaultMessage: '直流设备' }),
  1: formatMessage({ id: '', defaultMessage: '交流设备' }),
  2: formatMessage({ id: '', defaultMessage: '交直流一体设备' }),
  3: formatMessage({ id: '', defaultMessage: '其他' }),
};

export const bindStatus = {
  0: formatMessage({ id: '', defaultMessage: '待审核' }),
  1: formatMessage({ id: '', defaultMessage: '已绑定' }),
  2: formatMessage({ id: '', defaultMessage: '已解绑' }),
  3: formatMessage({ id: '', defaultMessage: '已拒绝' }),
};

export const orderStatus = {
  0: formatMessage({ id: '', defaultMessage: '下单' }),
  1: formatMessage({ id: '', defaultMessage: '已完成' }),
  2: formatMessage({ id: '', defaultMessage: '已取消' }),
  3: formatMessage({ id: '', defaultMessage: '退款' }),
};

export const alarm2Status = {
  0: formatMessage({ id: '', defaultMessage: '待处理' }),
  1: formatMessage({ id: '', defaultMessage: '已处理' }),
};

export const discountStatus = {
  0: formatMessage({ id: '', defaultMessage: '站点折扣' }),
  1: formatMessage({ id: '', defaultMessage: '服务费折扣' }),
  2: formatMessage({ id: '', defaultMessage: '站点一口价' }),
  3: formatMessage({ id: '', defaultMessage: '服务费一口价' }),
};

export const effectTime = {
  0: formatMessage({ id: '', defaultMessage: '全天' }),
  1: formatMessage({ id: '', defaultMessage: '时段' }),
};

export const couponType = {
  0: formatMessage({ id: '', defaultMessage: '现金卷' }),
  1: formatMessage({ id: '', defaultMessage: '折扣卷' }),
};

export const discountType = {
  0: formatMessage({ id: '', defaultMessage: '服务费' }),
  1: formatMessage({ id: '', defaultMessage: '总费用' }),
};

export const useTime = {
  0: formatMessage({ id: '', defaultMessage: '相对时间' }),
  1: formatMessage({ id: '', defaultMessage: '绝对时间' }),
};

export const activityType = {
  0: formatMessage({ id: '', defaultMessage: '注册活动' }),
  1: formatMessage({ id: '', defaultMessage: '充值活动' }),
};

export const couponUseType = {
  0: formatMessage({ id: '', defaultMessage: '未使用' }),
  1: formatMessage({ id: '', defaultMessage: '已使用' }),
  2: formatMessage({ id: '', defaultMessage: '已失效' }),
};

export const dealStatus = {
  0: formatMessage({ id: '', defaultMessage: '忽略' }),
  1: formatMessage({ id: '', defaultMessage: '处理完成' }),
};

export const articleType = {
  0: formatMessage({ id: '', defaultMessage: '用户指南' }),
  1: formatMessage({ id: '', defaultMessage: '关于我们' }),
  2: formatMessage({ id: '', defaultMessage: '其他' }),
};

export const wxInterfaceType = {
  0: formatMessage({ id: '', defaultMessage: 'V2' }),
  1: formatMessage({ id: '', defaultMessage: 'V3' }),
};

export const menuType = {
  0: formatMessage({ id: '', defaultMessage: '菜单' }),
  1: formatMessage({ id: '', defaultMessage: '按钮' }),
};
