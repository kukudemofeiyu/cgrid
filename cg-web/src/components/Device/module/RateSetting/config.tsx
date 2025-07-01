import { ProColumns, ProFormColumnsType } from "@ant-design/pro-components";
import { DateRangeType, TimeRangeType } from "./type";
import { formatMessage } from "@/utils";


export const contrastYear = '2024-';
export const contrastDate = '2024-01-01 ';

export const dateTableOption = [
  { label: formatMessage({ id: 'device.1051', defaultMessage: '时段表' }, { index:  1 }), value: 1 },
  { label: formatMessage({ id: 'device.1051', defaultMessage: '时段表' }, { index: 2 }), value: 2 },
  { label: formatMessage({ id: 'device.1051', defaultMessage: '时段表' }, { index:  3}), value: 3 },
  { label: formatMessage({ id: 'device.1051', defaultMessage: '时段表' }, { index:  4}), value: 4 },
  { label: formatMessage({ id: 'device.1051', defaultMessage: '时段表' }, { index: 5 }), value: 5 },
  { label: formatMessage({ id: 'device.1051', defaultMessage: '时段表' }, { index: 6 }), value: 6 },
  { label: formatMessage({ id: 'device.1051', defaultMessage: '时段表' }, { index:  7}), value: 7 },
  { label: formatMessage({ id: 'device.1051', defaultMessage: '时段表' }, { index: 8 }), value: 8 }
];
export const timeTableOption = [
  { label: 'T1', value: 1 },
  { label: 'T2', value: 2 },
  { label: 'T3', value: 3 },
  { label: 'T4', value: 4 },
  { label: 'T5', value: 5 },
  { label: 'T6', value: 6 },
  { label: 'T7', value: 7 },
  { label: 'T8', value: 8 }
];
export const timeTableSelectConfig: ProColumns | ProFormColumnsType = {
  title: formatMessage({ id: 'device.1051', defaultMessage: '时段表' }, {index: ''} ),
  dataIndex: 'Tn',
  valueType: 'select',
  fieldProps: {
    options: dateTableOption

  }
}

export const timePeriodSelectConfig: ProColumns | ProFormColumnsType = {
  title: formatMessage({ id: 'device.1052', defaultMessage: '费率' }),
  dataIndex: 'm37',
  valueType: 'select',
  fieldProps: {
    options: timeTableOption

  }
};

export const timeZoneTable = [
  timeTableSelectConfig,
  {
    title: formatMessage({ id: 'screen.date', defaultMessage: '日期' }),
    key: 'Md',
    dataIndex: 'Md',
  }
  
];

export const timeRangeTable = [
  timePeriodSelectConfig,
  {
    title: formatMessage({ id: 'device.timePeriod', defaultMessage: '时间段' }),
    key: 'Hm',
    dataIndex: 'Hm',
  }
];




