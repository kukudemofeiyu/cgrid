import { formatMessage } from '@/utils';

export type BtnParamType = {
  text: string;
  value: number;
};
export type ControlParamItem = {
  label: string;
  field: string;
  btnParam: any;
  disabled?: boolean;
};
export const controlItems: ControlParamItem[] = [
  {
    label: formatMessage({
      id: 'device.systemWorkingStateControl',
      defaultMessage: '系统工作状态控制',
    }),
    field: 'systemWorkingStatus',
    btnParam: [
      { text: '启动', value: 0 },
      { text: '待机', value: 1 },
      { text: '停止', value: 2 },
      { text: '急停', value: 3 },
    ],
  },
  {
    label: formatMessage({
      id: 'device.systemWorkingModeSwitch',
      defaultMessage: '系统工作模式切换',
    }),
    field: 'systemOperatingMode',
    btnParam: [
      {
        text: formatMessage({ id: 'device.peakFillingValley', defaultMessage: '削峰填谷' }),
        value: 0,
      },
      { text: formatMessage({ id: 'device.backupPower', defaultMessage: '备电' }), value: 1 },
      { text: formatMessage({ id: 'device.manualControl', defaultMessage: '手动控制' }), value: 2 },
    ],
  },
  {
    label: formatMessage({
      id: 'device.batteryPackWorkingModeSwitch',
      defaultMessage: '电池组工作模式切换',
    }),
    field: 'batteryPackOperatingMode',
    disabled: true,
    btnParam: [
      // { text: '正常', value: 0 },
      // { text: '核容', value: 1 },
      // { text: '均衡', value: 2 },
      { text: formatMessage({ id: 'siteMonitor.charge', defaultMessage: '充电' }), value: 3 },
      { text: formatMessage({ id: 'siteMonitor.discharge', defaultMessage: '放电' }), value: 4 },
      { text: formatMessage({ id: 'siteMonitor.stewing', defaultMessage: '静置' }), value: 5 },
    ],
  },
  // {
  //   label: '电池组工作状态控制',
  //   field: 'batteryPackWorkingStatus',
  //   disabled: true,
  //   btnParam: [
  //     { text: '启动', value: 0 },
  //     { text: '待机', value: 1 },
  //     { text: '停止', value: 2 },
  //   ],
  // },
  // {
  //   label: formatMessage(
  //     {
  //       id: 'device.mainContactorControlSentence',
  //       defaultMessage: 'BMS主接触器控制',
  //     },
  //     {
  //       name: 'BMS',
  //     },
  //   ),
  //   field: 'bmsOpenAndClose',
  //   btnParam: [
  //     { text: formatMessage({ id: 'device.close', defaultMessage: '闭合' }), value: 1 },
  //     { text: formatMessage({ id: 'device.break', defaultMessage: '断开' }), value: 0 },
  //   ],
  // },
  {
    label: formatMessage({ id: 'device.systemReset', defaultMessage: '系统复位' }),
    field: 'systemReset',
    btnParam: [{ text: formatMessage({ id: 'device.execute', defaultMessage: '执行' }), value: 1 }],
  },
];
