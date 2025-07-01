import type { DetailItem } from '@/components/Detail';
import { moneyPowerFormat, percentageFormat, powerFormat } from '@/utils/format';
import type { FormInstance, ProFormColumnsType } from '@ant-design/pro-components';
import { MinusCircleOutlined, PlusCircleOutlined } from '@ant-design/icons';
import styles from '../index.less';
import { Col, Row, TimePicker } from 'antd';
import { formatMessage } from '@/utils';
import moment from 'moment';
import type { Moment } from 'moment';
import { chargeAndDischargeStatus } from '@/utils/dict';
import type { FormAndDetailType } from '@/components/Detail/Detail';
import { storedMonetaryUnitEnum } from '@/components/MonetaryUnit';
import { useContext } from 'react';
import UnitContext from '@/pages/screen/Scene/Unit/UnitContext';
const hourFormat = 'HH:mm';
const contrastDate = '2023-01-01 ';

export const validateAllTime = (value: any, field: string) => {
  if (
    value?.[0]?.[field]?.[0]?.format?.(hourFormat) != '00:00' ||
    value?.[value?.length - 1]?.[field]?.[1]?.format?.(hourFormat) != '23:59'
  ) {
    return Promise.reject(
      formatMessage({ id: 'device.timePeriod', defaultMessage: '时间段' }) +
      formatMessage({ id: 'common.should24Hour', defaultMessage: '应满24小时' }),
    );
  } else {
    return Promise.resolve();
  }
};

const validatorTime = (
  rule: any,
  value: Moment[],
  field: string,
  getFieldValue: FormInstance['getFieldValue'],
) => {
  const periodOfTime = getFieldValue(field);
  const ruleField = rule?.field?.split?.('.');
  const index = ruleField?.[1] * 1;
  const prevValue: Moment[] = periodOfTime[index - 1]?.[ruleField?.[2]];
  const nextValue: Moment[] = periodOfTime[index + 1]?.[ruleField?.[2]];
  if (value && value.length) {
    if (
      prevValue &&
      prevValue.length &&
      moment(contrastDate + value[0].format(hourFormat)).isBefore(
        moment(contrastDate + prevValue[1].format(hourFormat)),
      )
    ) {
      return Promise.reject(
        new Error(
          formatMessage(
            {
              id: 'common.timeStartShouldGreaterEnd',
              defaultMessage: `时段${index + 1}开始时间应大于等于时段${index}结束时间`,
            },
            {
              start: index + 1,
              end: index,
            },
          ),
        ),
      );
    }
    if (
      nextValue &&
      nextValue.length &&
      moment(contrastDate + value[1].format(hourFormat)).isAfter(
        moment(contrastDate + nextValue[0].format(hourFormat)),
      )
    ) {
      return Promise.reject(
        new Error(
          formatMessage(
            {
              id: 'common.timeStartShouldGreaterEnd',
              defaultMessage: `时段${index + 1}结束时间应小于等于时段${index + 2}开始时间`,
            },
            {
              end: index + 1,
              start: index + 2,
            },
          ),
        ),
      );
    }
  }
  return Promise.resolve();
};

export const validateTime = (field: string, getFieldValue: FormInstance['getFieldValue']) => {
  return {
    validator: (_: any, value: Moment[]) => {
      return validatorTime(_, value, field, getFieldValue);
    },
  };
};

export const manaulParamsItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'device.chargingPower', defaultMessage: '充电功率' }),
    field: 'ChargingPower',
    format: powerFormat,
  },
  {
    label: formatMessage({ id: 'device.dischargePower', defaultMessage: '放电功率' }),
    field: 'DischargePower',
    format: powerFormat,
    span: 2,
  },
];

export const manulSetColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: 'device.chargingPower', defaultMessage: '充电功率' }),
    dataIndex: 'ChargingPower',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.chargingPower', defaultMessage: '充电功率' }),
        },
      ],
    },
  },
  {
    title: formatMessage({ id: 'device.dischargePower', defaultMessage: '放电功率' }),
    dataIndex: 'DischargePower',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.dischargePower', defaultMessage: '放电功率' }),
        },
      ],
    },
  },
];

export const peakLoadShiftItems: FormAndDetailType[] = [
  {
    title: formatMessage({ id: 'device.maxSoc', defaultMessage: '最高SOC' }),
    dataIndex: 'peakShavingAndValleyFillingModeMaximumSOC',
    valueType: 'digit',
    format: percentageFormat,
    fieldProps: {
      addonAfter: '%',
    },
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.maxSoc', defaultMessage: '最高SOC' }),
        },
      ],
    },
    colProps: {
      span: 8,
    },
  },
  {
    title: formatMessage({ id: 'device.minSoc', defaultMessage: '最低SOC' }),
    dataIndex: 'peakShavingAndValleyFillingModeLowestSOC',
    valueType: 'digit',
    span: 2,
    format: percentageFormat,
    fieldProps: {
      addonAfter: '%',
    },
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.minSoc', defaultMessage: '最低SOC' }),
        },
      ],
    },
    colProps: {
      span: 8,
    },
  },
  {
    valueType: 'formList',
    dataIndex: 'PeriodOfTime',
    initialValue: [],
    fieldProps: {
      copyIconProps: false,
      creatorButtonProps: {
        className: 'pl0',
        creatorButtonText:
          formatMessage({ id: 'common.new', defaultMessage: '新建' }) +
          formatMessage({ id: 'device.timePeriod', defaultMessage: '时段' }),
        icon: <PlusCircleOutlined />,
        type: 'link',
        style: { width: 'unset' },
      },
      min: 1,
      deleteIconProps: {
        Icon: (prop: any) => {
          return <MinusCircleOutlined {...prop} style={{ color: '#165dff' }} />;
        },
        tooltipText: formatMessage({ id: 'common.delete', defaultMessage: '删除' }),
      },
      itemRender: ({ listDom, action }: any) => {
        return (
          <div>
            <Row>
              <Col style={{ display: 'inline-flex', alignItems: 'flex-end' }} span={24}>
                {listDom}
                {action}
              </Col>
            </Row>
          </div>
        );
      },
    },
    colProps: {
      span: 24,
    },
    columns: [
      {
        valueType: 'group',
        columns: [
          {
            title: formatMessage({ id: 'device.timePeriod', defaultMessage: '时段' }),
            dataIndex: 'pcsRunningTimeFrame',
            valueType: 'timeRange',
            colProps: {
              span: 8,
            },
            formItemProps: ({ getFieldValue }) => {
              return {
                rules: [
                  {
                    required: true,
                    message:
                      formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }) +
                      formatMessage({ id: 'device.timePeriod', defaultMessage: '时段' }),
                  },
                  {
                    validator: (rule, value) => {
                      return validatorTime(rule, value, 'PeriodOfTime', getFieldValue);
                    },
                  },
                ],
              };
            },
            renderFormItem: () => (
              <TimePicker.RangePicker
                order={true}
                format={'HH:mm'}
                popupClassName={styles.timePicker}
              />
            ),
          },
          {
            title: formatMessage({ id: 'device.workMode', defaultMessage: '工作模式' }),
            dataIndex: 'CorD',
            valueType: 'select',
            colProps: {
              span: 8,
            },
            valueEnum: chargeAndDischargeStatus,
            formItemProps: {
              rules: [
                {
                  required: true,
                  message:
                    formatMessage({
                      id: 'common.pleaseEnter',
                      defaultMessage: '请选择',
                    }) +
                    formatMessage({
                      id: 'device.workMode',
                      defaultMessage: '工作模式',
                    }),
                },
              ],
            },
          },
          {
            title: formatMessage({ id: 'siteMonitor.executionPower', defaultMessage: '执行功率' }),
            dataIndex: 'executionPower',
            valueType: 'digit',
            colProps: {
              span: 8,
            },
            fieldProps: {
              addonAfter: 'kW',
            },
            formItemProps: {
              rules: [
                {
                  required: true,
                  message:
                    formatMessage({
                      id: 'common.pleaseEnter',
                      defaultMessage: '请输入',
                    }) +
                    formatMessage({
                      id: 'siteMonitor.executionPower',
                      defaultMessage: '执行功率',
                    }),
                },
              ],
            },
          },
        ],
        colProps: {
          span: 24,
        },
      },
    ],
  },
];

export const backupModeItems: DetailItem[] = [
  {
    label: formatMessage({ id: 'device.chargingPower', defaultMessage: '充电功率' }),
    field: 'BackupChargingPower',
    format: powerFormat,
  },
  {
    label: formatMessage({ id: 'device.dischargePower', defaultMessage: '放电功率' }),
    field: 'BackupDischargePower',
    format: powerFormat,
  },
  {
    label: formatMessage({ id: 'device.maxSoc', defaultMessage: '最高SOC' }),
    field: 'BackupHighestSOC',
    format: percentageFormat,
  },
  {
    label: formatMessage({ id: 'device.minSoc', defaultMessage: '最低SOC' }),
    field: 'BackupMinimumSOC',
    format: percentageFormat,
  },
];

export const BackupPowerSetColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: 'device.chargingPower', defaultMessage: '充电功率' }),
    dataIndex: 'BackupChargingPower',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.chargingPower', defaultMessage: '充电功率' }),
        },
      ],
    },
  },
  {
    title: formatMessage({ id: 'device.dischargePower', defaultMessage: '放电功率' }),
    dataIndex: 'BackupDischargePower',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.dischargePower', defaultMessage: '放电功率' }),
        },
      ],
    },
  },
  {
    title: formatMessage({ id: 'device.maxSoc', defaultMessage: '最高SOC' }),
    dataIndex: 'BackupHighestSOC',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.maxSoc', defaultMessage: '放最高SOC电功率' }),
        },
      ],
    },
  },
  {
    title: formatMessage({ id: 'device.minSoc', defaultMessage: '最低SOC' }),
    dataIndex: 'BackupMinimumSOC',
    valueType: 'digit',
    formItemProps: {
      rules: [
        {
          required: true,
          message:
            formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }) +
            formatMessage({ id: 'device.minSoc', defaultMessage: '最低SOC' }),
        },
      ],
    },
  },
];

export const peakTimeItems: DetailItem[] = [
  {
    label:
      formatMessage({ id: 'dataManage.theTip', defaultMessage: '尖' }) +
      formatMessage({ id: 'device.electrovalence', defaultMessage: '电价' }),
    field: 'SharpElectrovalence',
    format: moneyPowerFormat,
  },
  {
    label:
      formatMessage({ id: 'dataManage.peak', defaultMessage: '峰' }) +
      formatMessage({ id: 'device.electrovalence', defaultMessage: '电价' }),
    field: 'PeakElectrovalence',
    format: moneyPowerFormat,
  },
  {
    label:
      formatMessage({ id: 'dataManage.flat', defaultMessage: '平' }) +
      formatMessage({ id: 'device.electrovalence', defaultMessage: '电价' }),
    field: 'FlatElectrovalence',
    format: moneyPowerFormat,
  },
  {
    label:
      formatMessage({ id: 'dataManage.valley', defaultMessage: '谷' }) +
      formatMessage({ id: 'device.electrovalence', defaultMessage: '电价' }),
    field: 'ValleyElectrovalence',
    format: moneyPowerFormat,
    span: 3,
  },
  {
    label:
      formatMessage({ id: 'dataManage.theTip', defaultMessage: '尖' }) +
      formatMessage({ id: 'device.timePeriod', defaultMessage: '时段' }),
    field: 'sharpTime',
    span: 3,
  },
  {
    label:
      formatMessage({ id: 'dataManage.peak', defaultMessage: '峰' }) +
      formatMessage({ id: 'device.timePeriod', defaultMessage: '时段' }),
    field: 'peakTime',
    span: 3,
  },
  {
    label:
      formatMessage({ id: 'dataManage.flat', defaultMessage: '平' }) +
      formatMessage({ id: 'device.timePeriod', defaultMessage: '时段' }),
    field: 'flatTime',
    span: 3,
  },
  {
    label:
      formatMessage({ id: 'dataManage.valley', defaultMessage: '谷' }) +
      formatMessage({ id: 'device.timePeriod', defaultMessage: '时段' }),
    field: 'valleyTime',
    span: 3,
  },
];

export const PeakSetColumns: () => ProFormColumnsType[] = () => {
  const nowUnit = useContext(UnitContext).unit;
  return [
    {
      title:
        formatMessage({ id: 'dataManage.theTip', defaultMessage: '尖' }) +
        formatMessage({ id: 'device.electrovalence', defaultMessage: '电价' }),
      valueType: 'digit',
      dataIndex: 'SharpElectrovalence',
      fieldProps: {
        placeholder: formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }),
        // addonAfter: formatMessage({ id: 'common.rmb', defaultMessage: '元' }) + '/kWh',
        addonAfter: storedMonetaryUnitEnum[nowUnit] + '/kWh',
      },
      formItemProps: {
        rules: [{ required: true, message: '请输入尖电价' }],
      },
    },
    {
      title:
        formatMessage({ id: 'dataManage.peak', defaultMessage: '峰' }) +
        formatMessage({ id: 'device.electrovalence', defaultMessage: '电价' }),
      valueType: 'digit',
      dataIndex: 'PeakElectrovalence',
      fieldProps: {
        placeholder: formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }),
        addonAfter: storedMonetaryUnitEnum[nowUnit] + '/kWh',
      },
      formItemProps: {
        rules: [{ required: true, message: '请输入峰电价' }],
      },
    },
    {
      title:
        formatMessage({ id: 'dataManage.flat', defaultMessage: '平' }) +
        formatMessage({ id: 'device.electrovalence', defaultMessage: '电价' }),
      valueType: 'digit',
      dataIndex: 'FlatElectrovalence',
      fieldProps: {
        placeholder: formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }),
        addonAfter: storedMonetaryUnitEnum[nowUnit] + '/kWh',
      },
      formItemProps: {
        rules: [{ required: true, message: '请输入平电价' }],
      },
    },
    {
      title:
        formatMessage({ id: 'dataManage.valley', defaultMessage: '谷' }) +
        formatMessage({ id: 'device.electrovalence', defaultMessage: '电价' }),
      valueType: 'digit',
      dataIndex: 'ValleyElectrovalence',
      fieldProps: {
        placeholder: formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }),
        addonAfter: storedMonetaryUnitEnum[nowUnit] + '/kWh',
      },
      formItemProps: {
        rules: [{ required: true, message: '请输入谷电价' }],
      },
    },
    {
      valueType: 'formList',
      dataIndex: 'ElectrovalenceTimeFrame',
      // initialValue: [{ effectiveTime: [] }],
      initialValue: [],
      fieldProps: {
        copyIconProps: false,
        creatorButtonProps: {
          className: 'pl0',
          creatorButtonText:
            formatMessage({ id: 'common.new', defaultMessage: '新建' }) +
            formatMessage({ id: 'device.timePeriod', defaultMessage: '时间段' }),
          icon: <PlusCircleOutlined />,
          type: 'link',
          style: { width: 'unset' },
        },
        min: 1,
        deleteIconProps: {
          Icon: (prop: any) => {
            return <MinusCircleOutlined {...prop} style={{ color: '#165dff' }} />;
          },
          tooltipText: formatMessage({ id: 'common.delete', defaultMessage: '删除' }),
        },
        itemRender: ({ listDom, action }: any) => {
          return (
            <div>
              <Row>
                <Col style={{ display: 'inline-flex', alignItems: 'flex-end' }} span={24}>
                  {listDom}
                  {action}
                </Col>
              </Row>
            </div>
          );
        },
      },
      colProps: {
        span: 18,
      },
      columns: [
        {
          valueType: 'group',
          columns: [
            {
              title: formatMessage({ id: 'device.timePeriod', defaultMessage: '时间段' }),
              dataIndex: 'TimeFrame',
              valueType: 'timeRange',
              colProps: {
                span: 12,
              },
              formItemProps: ({ getFieldValue }) => {
                return {
                  rules: [
                    {
                      required: true,
                      message:
                        formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }) +
                        formatMessage({ id: 'device.timePeriod', defaultMessage: '时间段' }),
                    },
                    {
                      validator: (rule, value) => {
                        return validatorTime(rule, value, 'ElectrovalenceTimeFrame', getFieldValue);
                      },
                    },
                  ],
                };
              },
              renderFormItem: () => (
                <TimePicker.RangePicker
                  order={true}
                  format={'HH:mm'}
                  popupClassName={styles.timePicker}
                />
              ),
            },
            {
              title: formatMessage({ id: 'device.electricityPriceType', defaultMessage: '电价类型' }),
              formItemProps: {
                rules: [
                  {
                    required: true,
                    message:
                      formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }) +
                      formatMessage({
                        id: 'device.electricityPriceType',
                        defaultMessage: '电价类型',
                      }),
                  },
                ],
              },
              dataIndex: 'ElectrovalenceType',
              valueType: 'select',
              colProps: {
                span: 12,
              },
              valueEnum: new Map([
                [0, formatMessage({ id: 'dataManage.theTip', defaultMessage: '尖' })],
                [1, formatMessage({ id: 'dataManage.peak', defaultMessage: '峰' })],
                [2, formatMessage({ id: 'dataManage.flat', defaultMessage: '平' })],
                [3, formatMessage({ id: 'dataManage.valley', defaultMessage: '谷' })],
              ]),
            },
          ],
          colProps: {
            span: 24,
          },
        },
      ],
    },
  ];
}
