import { formatMessage, getValue, strToArray } from '@/utils';
import dayjs from 'dayjs';
import { onlineStatus, deviceAlarmStatus, onlineStatus1, gunConditionStatus } from './dict';
import Field from '@/components/Field';
import { ProField } from '@ant-design/pro-components';

type MapType = {
  [k: number]: string;
};

type MapDataType = {
  [k: number]: {
    text: string;
    color: string;
  };
};

export const timeFormat = (value: string) => {
  return value ? dayjs(value).format('YYYY-MM-DD HH:mm:ss') : '';
};
export const onlineFormat = (value: any) => {
  return <span className={value == 0 ? 'cl-error' : ''}>{onlineStatus?.[value].text}</span>;
};
export const communicateFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.normal', defaultMessage: '正常' }),
    1: formatMessage({ id: 'things.disconnection', defaultMessage: '失联' }),
  };
  return <span className={value == 0 ? '' : 'cl-error'}>{map[value]}</span>;
};
export const runFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.standby', defaultMessage: '待机' }),
    1: formatMessage({ id: 'things.run', defaultMessage: '运行' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const systemRunFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.firing', defaultMessage: '启动' }),
    1: formatMessage({ id: 'things.standby', defaultMessage: '待机' }),
    2: formatMessage({ id: 'things.cease', defaultMessage: '停止' }),
    3: formatMessage({ id: 'things.emergencyStop', defaultMessage: '急停' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const workStatusFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.firing', defaultMessage: '启动' }),
    1: formatMessage({ id: 'things.cease', defaultMessage: '停止' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const enableFormat = (status: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.enable', defaultMessage: '使能' }),
    1: formatMessage({ id: 'things.disable', defaultMessage: '禁用' }),
  };
  return <span className="">{map[status]}</span>;
};
export const airRunFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.run', defaultMessage: '运行' }),
    1: formatMessage({ id: 'things.standby', defaultMessage: '待机' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const modelFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.handMovement', defaultMessage: '手动' }),
    1: formatMessage({ id: 'things.automatic', defaultMessage: '自动' }),
    2: formatMessage({ id: 'things.handMovement', defaultMessage: '手动' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const converterFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.offGridWork', defaultMessage: '离网工作' }),
    1: formatMessage({ id: 'things.gridConnectionWork', defaultMessage: '并网工作' }),
    2: formatMessage({ id: 'things.mainsCharging', defaultMessage: '市电充电' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const converterStauesFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.firing', defaultMessage: '启动' }),
    1: formatMessage({ id: 'things.standby', defaultMessage: '待机' }),
    2: formatMessage({ id: 'things.cease', defaultMessage: '停止' }),
    3: formatMessage({ id: 'things.reset', defaultMessage: '复位' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const runStateFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.normal', defaultMessage: '正常' }),
    1: formatMessage({ id: 'things.prohibitionOfCharging', defaultMessage: '禁充' }),
    2: formatMessage({ id: 'things.prohibitionOfRelease', defaultMessage: '禁放' }),
    3: formatMessage({ id: 'things.standby', defaultMessage: '待机' }),
    4: formatMessage({ id: 'things.halt', defaultMessage: '停机' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const batteryWorkFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.normal', defaultMessage: '正常' }),
    1: formatMessage({ id: 'things.nuclearCapacity', defaultMessage: '核容' }),
    2: formatMessage({ id: 'things.balanced', defaultMessage: '均衡' }),
    3: formatMessage({ id: 'things.charge', defaultMessage: '充电' }),
    4: formatMessage({ id: 'things.discharge', defaultMessage: '放电' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const batteryWorkingStatusFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.firing', defaultMessage: '启动' }),
    1: formatMessage({ id: 'things.standby', defaultMessage: '待机' }),
    2: formatMessage({ id: 'things.cease', defaultMessage: '停止' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const closeFormat = (value: string) => {
  const valueArr = strToArray(value);
  const map: MapDataType = {
    0: { text: formatMessage({ id: 'things.break', defaultMessage: '断开' }), color: '' },
    1: { text: formatMessage({ id: 'things.close', defaultMessage: '闭合' }), color: '' },
  };
  return (
    <span className={`${map[valueArr.includes(0) ? 0 : 1]?.color} mr8`}>
      {map[valueArr.includes(0) ? 0 : 1]?.text}
    </span>
  );
};
export const contactorFormat = (value: string) => {
  const valueArr = strToArray(value);
  const map: MapType = {
    0: formatMessage({ id: 'things.break', defaultMessage: '断开' }),
    1: formatMessage({ id: 'things.mainContactorClosed', defaultMessage: '主接触器闭合' }),
    2: formatMessage({ id: 'things.preChargeContactorClosed', defaultMessage: '预充接触器闭合' }),
    5: formatMessage({ id: 'things.DCCircuitBreakerClosed', defaultMessage: '直流断路器闭合' }),
    6: formatMessage({ id: 'things.ACCircuitBreakerClosed', defaultMessage: '交流断路器闭合' }),
    7: formatMessage({ id: 'things.accessControlClosed', defaultMessage: '门禁闭合' }),
  };
  const result = valueArr?.map?.((item) => map[item])?.join?.('，') || '';
  return result;
};
export const singleBFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.normal', defaultMessage: '正常' }),
    1: formatMessage({ id: 'things.break', defaultMessage: '断开' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const singleFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.break', defaultMessage: '断开' }),
    1: formatMessage({ id: 'things.normal', defaultMessage: '正常' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const singleEffectFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.effective', defaultMessage: '有效' }),
    1: formatMessage({ id: 'things.break', defaultMessage: '断开' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const mvFormat = (value: string | number) => {
  return getValue(value, 'mV');
};
export const clusterFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.break', defaultMessage: '断开' }),
    1: formatMessage({ id: 'things.close', defaultMessage: '闭合' }),
  };
  return map[value];
};
export const powerFormat = (value: string | number) => {
  return getValue(value, 'kW');
};
export const kWpFormat = (value: string | number) => {
  return getValue(value, 'kWp');
};
export const noPowerFormat = (value: string | number) => {
  return getValue(value, 'kvar');
};
export const powerHourFormat = (value: string | number) => {
  return getValue(value, 'kWh');
};
export const noPowerHourFormat = (value: string | number) => {
  return getValue(value, 'kvar·h');
};
export const voltageFormat = (value: string | number) => {
  return getValue(value, 'V');
};
export const kVoltageFormat = (value: string | number) => {
  return getValue(value, 'kV');
};
export const kVAFormat = (value: string | number) => {
  return getValue(value, 'kVA');
};
export const currentFormat = (value: string | number) => {
  return getValue(value, 'A');
};
export const frequencyFormat = (value: string | number) => {
  return getValue(value, 'HZ');
};
export const tempFormat = (value: string | number) => {
  return getValue(value, '℃');
};
export const wetFormat = (value: string | number) => {
  return getValue(value, '%rh');
};
export const percentageFormat = (value: string | number) => {
  return getValue(value, '%');
};
export const kohmFormat = (value: string | number) => {
  return getValue(value, 'kΩ');
};
export const mohmFormat = (value: string | number) => {
  return getValue(value, 'MΩ');
};
export const hydrogenFormat = (value: string | number) => {
  return getValue(value, 'ppm');
};
export const moneyFormat = (value: string | number) => {
  return getValue(value, formatMessage({ id: 'common.rmb', defaultMessage: '元' }));
};
export const moneyPowerFormat = (value: string | number) => {
  return getValue(value, formatMessage({ id: 'common.rmb', defaultMessage: '元' }) + '/kWh');
};
export const electricModelFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.batteryConstantVoltage', defaultMessage: '电池恒压' }),
    1: formatMessage({ id: 'things.gridConnectedConstantCurrent', defaultMessage: '并网恒流' }),
    2: formatMessage({ id: 'things.batteryConstantCurrent', defaultMessage: '电池恒流' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const workFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.halt', defaultMessage: '停机' }),
    1: formatMessage({ id: 'things.fault', defaultMessage: '故障' }),
    2: formatMessage({ id: 'things.run', defaultMessage: '运行' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const emsConnectMethodFormat = (value: number) => {
  const map: MapType = {
    0: 'RS485',
    1: formatMessage({ id: 'things.unknown', defaultMessage: '未知' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const liquidWorkFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.halt', defaultMessage: '停机' }),
    1: formatMessage({ id: 'things.fault', defaultMessage: '故障' }),
    2: formatMessage({ id: 'things.run', defaultMessage: '运行' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const doorFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.closeDoor', defaultMessage: '关门' }),
    1: formatMessage({ id: 'things.open', defaultMessage: '开门' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const chargeFormat = (status: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.stewing', defaultMessage: '静置' }),
    1: formatMessage({ id: 'things.discharge', defaultMessage: '放电' }),
    2: formatMessage({ id: 'things.charge', defaultMessage: '充电' }),
  };
  const result = <span className={''}>{map[status]}</span>;
  return result;
};
export const chargePutFormat = (status: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.charge', defaultMessage: '充电' }),
    1: formatMessage({ id: 'things.discharge', defaultMessage: '放电' }),
  };
  return <span className={''}>{map[status]}</span>;
};
export const openFormat = (status: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.closeDown', defaultMessage: '关闭' }),
    1: formatMessage({ id: 'things.open1', defaultMessage: '开启' }),
  };
  return <span className={''}>{map[status]}</span>;
};
export const faultFormat = (value: number) => {
  const valueArr = strToArray(value);
  const map: MapDataType = {
    0: { text: formatMessage({ id: 'things.normal', defaultMessage: '正常' }), color: '' },
    1: { text: formatMessage({ id: 'things.fault', defaultMessage: '故障' }), color: 'cl-error' },
  };
  return (
    <span className={`${map[valueArr.includes(0) ? 0 : 1]?.color} mr8`}>
      {map[valueArr.includes(0) ? 0 : 1]?.text}
    </span>
  );
};
export const alarmArrFormat = (value: string) => {
  const valueArr = strToArray(value);
  const map: MapDataType = {
    0: { text: formatMessage({ id: 'things.normal', defaultMessage: '正常' }), color: '' },
    1: { text: formatMessage({ id: 'things.alarm', defaultMessage: '告警' }), color: 'cl-error' },
  };
  return (
    <span className={`${map[valueArr.includes(0) || !valueArr.length ? 0 : 1]?.color} mr8`}>
      {map[valueArr.includes(0) || !valueArr.length ? 0 : 1]?.text}
    </span>
  );
};
export const outputFormat = (status: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.cease', defaultMessage: '停止' }),
    1: formatMessage({ id: 'things.output', defaultMessage: '输出' }),
  };
  return <span className="">{map[status]}</span>;
};
export const openCloseFormat = (status: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.closeDown', defaultMessage: '关闭' }),
    1: formatMessage({ id: 'things.open2', defaultMessage: '打开' }),
  };
  return <span className="">{map[status]}</span>;
};
export const liquidSensorFormat = (status: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.selfCheckingInProgress', defaultMessage: '正在自检' }),
    1: formatMessage({ id: 'things.normalOperation', defaultMessage: '工作正常' }),
    2: formatMessage({ id: 'things.equipmentFailure', defaultMessage: '设备故障' }),
    3: formatMessage({ id: 'things.detectedAlarmInformation', defaultMessage: '探测到报警信息' }),
  };
  return <span className="">{map[status]}</span>;
};
export const liquidSystemModeFormat = (status: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.cease', defaultMessage: '停止' }),
    1: formatMessage({ id: 'things.internalCirculation', defaultMessage: '内循环' }),
    2: formatMessage({ id: 'things.refrigeration', defaultMessage: '制冷' }),
    3: formatMessage({ id: 'things.heat', defaultMessage: '加热' }),
    4: formatMessage({ id: 'things.fullyAutomaticByTemp', defaultMessage: '全自动(根据水温)' }),
  };
  return <span className="">{map[status]}</span>;
};
export const dehumidifierWorkModeFormat = (status: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.heatingType', defaultMessage: '升温型' }),
    1: formatMessage({ id: 'things.coolingType', defaultMessage: '降温型' }),
  };
  return <span className="">{map[status]}</span>;
};
export const systemOperatingModeFormat = (status: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.peakLoadShifting', defaultMessage: '削峰填谷' }),
    1: formatMessage({ id: 'things.backuppower', defaultMessage: '备电' }),
    2: formatMessage({ id: 'things.manual', defaultMessage: '手动控制' }),
  };
  return <span className="">{map[status]}</span>;
};
export const booleanFormat = (status: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.no', defaultMessage: '否' }),
    1: formatMessage({ id: 'things.yes', defaultMessage: '是' }),
  };
  return <span className="">{map[status]}</span>;
};
export const airsetFormat = (status: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.unitShutdown', defaultMessage: '机组关闭' }),
    1: formatMessage({ id: 'things.unitStart', defaultMessage: '机组开启' }),
    2: formatMessage({ id: 'things.unitStandby', defaultMessage: '机组待机' }),
    3: formatMessage({ id: 'things.unitLock', defaultMessage: '机组锁定' }),
    4: formatMessage({ id: 'things.remoteShutdown', defaultMessage: '远程关闭' }),
  };
  return <span className={status == 3 ? 'cl-error' : ''}>{map[status]}</span>;
};
export const useFormat = (value: number) => {
  const map: MapType = {
    // 0：离网1：空闲2：占用（未充电）3：占用（充电中）4：占用（预约锁定）255：故障
    0: formatMessage({ id: 'things.offGrid', defaultMessage: '离网' }),
    1: formatMessage({ id: 'things.idle', defaultMessage: '空闲' }),
    2: formatMessage({ id: 'things.occupationNotCharged', defaultMessage: '占用（未充电）' }),
    3: formatMessage({ id: 'things.occupationCharging', defaultMessage: '占用（充电中）' }),
    4: formatMessage({
      id: 'things.occupationAppointmentLock',
      defaultMessage: '占用（预约锁定）',
    }),
    5: formatMessage({ id: 'things.chargeComplete', defaultMessage: '充电完成' }),
    255: formatMessage({ id: 'things.fault', defaultMessage: '故障' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const alarmFormat = (status: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.normal', defaultMessage: '正常' }),
    1: formatMessage({ id: 'things.alarm', defaultMessage: '告警' }),
  };
  return <span className="">{map[status]}</span>;
};
export const startFormat = (status: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'things.normal', defaultMessage: '正常' }),
    1: formatMessage({ id: 'things.firing', defaultMessage: '启动' }),
  };
  return <span className="">{map[status]}</span>;
};
export const pankFanFormat = (value: string) => {
  const valueArr = strToArray(value);
  const map: MapDataType = {
    0: { text: formatMessage({ id: 'things.close1', defaultMessage: '关' }), color: '' },
    1: {
      text: 'BMU1' + formatMessage({ id: 'things.fanOn', defaultMessage: '风扇开' }),
      color: '',
    },
    2: {
      text: 'BMU2' + formatMessage({ id: 'things.fanOn', defaultMessage: '风扇开' }),
      color: '',
    },
    3: {
      text: 'BMU3' + formatMessage({ id: 'things.fanOn', defaultMessage: '风扇开' }),
      color: '',
    },
    4: {
      text: 'BMU4' + formatMessage({ id: 'things.fanOn', defaultMessage: '风扇开' }),
      color: '',
    },
    5: {
      text: 'BMU5' + formatMessage({ id: 'things.fanOn', defaultMessage: '风扇开' }),
      color: '',
    },
    6: {
      text: 'BMU6' + formatMessage({ id: 'things.fanOn', defaultMessage: '风扇开' }),
      color: '',
    },
    7: {
      text: 'BMU7' + formatMessage({ id: 'things.fanOn', defaultMessage: '风扇开' }),
      color: '',
    },
    8: {
      text: 'BMU8' + formatMessage({ id: 'things.fanOn', defaultMessage: '风扇开' }),
      color: '',
    },
    9: {
      text: 'BMU9' + formatMessage({ id: 'things.fanOn', defaultMessage: '风扇开' }),
      color: '',
    },
    10: {
      text: 'BMU10' + formatMessage({ id: 'things.fanOn', defaultMessage: '风扇开' }),
      color: '',
    },
  };
  const result =
    valueArr?.map?.((item) => (
      <span className={`${map[item]?.color} mr8`}>{map[item]?.text}</span>
    )) || '';
  return result;
};
export const pankFanAlarmFormat = (value: string) => {
  const valueArr = strToArray(value);
  const map: MapDataType = {
    0: { text: formatMessage({ id: 'things.normal', defaultMessage: '正常' }), color: '' },
    1: {
      text: 'BMU1' + formatMessage({ id: 'things.fanFailure', defaultMessage: '风扇故障' }),
      color: 'cl-error',
    },
    2: {
      text: 'BMU2' + formatMessage({ id: 'things.fanFailure', defaultMessage: '风扇故障' }),
      color: 'cl-error',
    },
    3: {
      text: 'BMU3' + formatMessage({ id: 'things.fanFailure', defaultMessage: '风扇故障' }),
      color: 'cl-error',
    },
    4: {
      text: 'BMU4' + formatMessage({ id: 'things.fanFailure', defaultMessage: '风扇故障' }),
      color: 'cl-error',
    },
    5: {
      text: 'BMU5' + formatMessage({ id: 'things.fanFailure', defaultMessage: '风扇故障' }),
      color: 'cl-error',
    },
    6: {
      text: 'BMU6' + formatMessage({ id: 'things.fanFailure', defaultMessage: '风扇故障' }),
      color: 'cl-error',
    },
    7: {
      text: 'BMU7' + formatMessage({ id: 'things.fanFailure', defaultMessage: '风扇故障' }),
      color: 'cl-error',
    },
    8: {
      text: 'BMU8' + formatMessage({ id: 'things.fanFailure', defaultMessage: '风扇故障' }),
      color: 'cl-error',
    },
    9: {
      text: 'BMU9' + formatMessage({ id: 'things.fanFailure', defaultMessage: '风扇故障' }),
      color: 'cl-error',
    },
    10: {
      text: 'BMU10' + formatMessage({ id: 'things.fanFailure', defaultMessage: '风扇故障' }),
      color: 'cl-error',
    },
  };
  const result =
    valueArr?.map?.((item) => (
      <span className={`${map[item]?.color} mr8`}>{map[item]?.text}</span>
    )) || '';
  return result;
};
export const airWorkFormat = (value: string) => {
  const valueArr = strToArray(value);
  const map: MapDataType = {
    0: { text: formatMessage({ id: 'things.no', defaultMessage: '否' }), color: '' },
    1: { text: formatMessage({ id: 'things.refrigeration', defaultMessage: '制冷' }), color: '' },
    2: { text: formatMessage({ id: 'things.heat1', defaultMessage: '制热' }), color: '' },
    3: {
      text: formatMessage({ id: 'things.dehumidification', defaultMessage: '除湿' }),
      color: '',
    },
    4: { text: formatMessage({ id: 'things.airSupply', defaultMessage: '送风' }), color: '' },
    5: { text: formatMessage({ id: 'things.standby', defaultMessage: '待机' }), color: '' },
  };
  const result =
    valueArr?.map?.((item) => (
      <span className={`${map[item]?.color} mr8`}>{map[item]?.text}</span>
    )) || '';
  return result;
};
export const bwattAirWorkFormat = (value: number) => {
  const map: MapDataType = {
    0: {
      text: formatMessage({ id: 'things.unitShutdown', defaultMessage: '机组关闭' }),
      color: '',
    },
    1: { text: formatMessage({ id: 'things.unitStart', defaultMessage: '机组开启' }), color: '' },
    2: { text: formatMessage({ id: 'things.unitStandby', defaultMessage: '机组待机' }), color: '' },
    3: { text: formatMessage({ id: 'things.unitLock', defaultMessage: '机组锁定' }), color: '' },
    4: {
      text: formatMessage({ id: 'things.remoteShutdown', defaultMessage: '远程关闭' }),
      color: '',
    },
  };
  return <span>{map[value]?.text}</span>;
};
export const airSwitchFormat = (value: string) => {
  const valueArr = strToArray(value);
  const map: MapDataType = {
    0: { text: formatMessage({ id: 'things.cease', defaultMessage: '停止' }), color: '' },
    1: {
      text: formatMessage({
        id: 'things.indoorFanSwitchOutput',
        defaultMessage: '室内风机开关输出',
      }),
      color: 'cl-error',
    },
    2: {
      text: formatMessage({
        id: 'things.compressorSwitchOutput',
        defaultMessage: '压缩机开关输出',
      }),
      color: 'cl-error',
    },
    3: {
      text: formatMessage({
        id: 'things.electricHeatingSwitchOutput',
        defaultMessage: '电加热开关输出',
      }),
      color: 'cl-error',
    },
    4: {
      text: formatMessage({
        id: 'things.announcementAlarmSwitchOutput',
        defaultMessage: '公告告警开关输出',
      }),
      color: 'cl-error',
    },
  };
  const result =
    valueArr?.map?.((item) => (
      <span className={`${map[item]?.color} mr8`}>{map[item]?.text}</span>
    )) || '';
  return result;
};
export const airAlarmFormat = (value: string) => {
  const valueArr = strToArray(value);
  const isRight = valueArr.includes(0);
  return (
    <span className={`${isRight ? '' : 'cl-error'} mr8`}>
      {isRight
        ? formatMessage({ id: 'things.normal', defaultMessage: '正常' })
        : formatMessage({ id: 'things.fault', defaultMessage: '故障' })}
    </span>
  );
};
export const onlineStatusFormat = (value: string) => {
  return (
    <span className="pl4">
      <ProField mode="read" text={value} valueEnum={onlineStatus} />
    </span>
  );
};
export const onlineStatus1Format = (value: string) => {
  return (
    <span className="pl4">
      <ProField mode="read" text={value} valueEnum={onlineStatus1} />
    </span>
  );
};
export const deviceAlarmStatusFormat = (value: string) => {
  return (
    <span className="profield-alarm pl4">
      <ProField mode="read" text={value} valueEnum={deviceAlarmStatus} />
    </span>
  );
};

export const earlyWarningFormat = (value: number) => {
  const map: MapDataType = {
    0: {
      text: formatMessage({ id: 'things.theSystemIsNormal', defaultMessage: '系统正常' }),
      color: '',
    },
    3: {
      text: formatMessage({ id: 'things.level1Warning', defaultMessage: '一级预警' }),
      color: 'cl-error',
    },
    4: {
      text: formatMessage({ id: 'things.level2Warning', defaultMessage: '二级预警' }),
      color: 'cl-error',
    },
  };
  return <span className={`${map[value]?.color}`}>{map[value]?.text}</span>;
};

export const faultFireFightFormat = (value: number) => {
  const map: MapDataType = {
    0: { text: formatMessage({ id: 'things.noFault', defaultMessage: '无故障' }), color: '' },
    1: {
      text: formatMessage({
        id: 'things.BIESActivatedFireExtinguisherActivated',
        defaultMessage: 'BIES已启动（灭火器启动）',
      }),
      color: 'cl-error',
    },
    2: {
      text: formatMessage({
        id: 'things.CAN1CommunicationFailure',
        defaultMessage: 'CAN1通讯故障',
      }),
      color: 'cl-error',
    },
    3: {
      text: formatMessage({
        id: 'things.suppressionDeviceMalfunction',
        defaultMessage: '抑制装置故障',
      }),
      color: 'cl-error',
    },
    4: {
      text: formatMessage({
        id: 'things.detectorFailure',
        defaultMessage: '探测器故障（包括单箱离线故障和探测器本身故障）',
      }),
      color: 'cl-error',
    },
  };
  return <span className={`${map[value]?.color}`}>{map[value]?.text}</span>;
};
export const outputMethodFormat = (value: number) => {
  const map: MapType = {
    1: formatMessage({ id: 'things.threePhaseFourWireSystem', defaultMessage: '三相四线制' }),
  };
  return <span className="">{map[value]}</span>;
};

export const yesFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'debug.no', defaultMessage: '无' }),
    1: formatMessage({ id: 'debug.yes', defaultMessage: '有' }),
  };
  return <span className={''}>{map[value]}</span>;
};
export const startUpFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'charge.shutDown', defaultMessage: '关机' }),
    1: formatMessage({ id: 'charge.powerOn', defaultMessage: '开机' }),
  };
  return map[value];
};
export const parallelGridFormat = (value: number) => {
  const map: MapType = {
    0: formatMessage({ id: 'device.gridConnection', defaultMessage: '并网' }),
    1: formatMessage({ id: 'device.offGrid', defaultMessage: '离网' }),
  };
  return map[value];
};
