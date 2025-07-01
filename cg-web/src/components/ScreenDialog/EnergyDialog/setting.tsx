/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-05-09 11:09:19
 * @LastEditTime: 2024-05-22 14:40:17
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\ScreenDialog\EnergyDialog\setting.tsx
 */

import React, { useCallback, useEffect, useState } from 'react';
import {
  Row,
  Col,
  Switch,
  Form,
  InputNumber,
  Button,
  TimePicker,
  DatePicker,
  message,
  Modal,
} from 'antd';
import type { FormInstance } from 'antd';
import Label from '@/components/Detail/DotLabel';
import { default as LineLabel } from '@/components/Detail/LineLabel';
import moment from 'moment';
import type { Moment } from 'moment';
import { useRequest } from 'umi';
import { DeviceDataType, editSetting } from '@/services/equipment';
import { formatMessage, isEmpty } from '@/utils';
import { closeFormat } from '@/utils/format';
import lodash from 'lodash';
import { useBoolean } from 'ahooks';
import { DeviceTypeEnum, OnlineStatusEnum } from '@/utils/dictionary';
import { useAuthority } from '@/hooks';

export type ControlType = {
  systemFiring: boolean;
  systemStop: boolean;
  flash: boolean;
  bmsConnect: boolean;
  bmsBreak: boolean;
};

const DatePick: any = DatePicker;
const timeFormat = 'HH:mm';

type SettingProps = {
  id?: string;
  deviceData?: DeviceDataType;
  settingData?: Record<string, any>;
  isLineLabel?: boolean;
  isDeviceChild?: boolean;
  type?: DeviceTypeEnum;
};

const powerMap = new Map([
  ['power1', 'PCSPeriodPowerOne'],
  ['power2', 'PCSPeriodPowerTwo'],
  ['power3', 'PCSPeriodPowerThree'],
  ['power4', 'PCSPeriodPowerFour'],
  ['power5', 'PCSPeriodPowerFive'],
]);

const timeMap = new Map([
  [
    'time1',
    [
      'AtTheBeginningOfThePeriodOne',
      'StartingPointOfTimePeriodOne',
      'AtTheEndOfThePeriodOne',
      'PeriodEndMinuteOne',
    ],
  ],
  [
    'time2',
    [
      'AtTheBeginningOfThePeriodTwo',
      'StartingPointOfTimePeriodTwo',
      'AtTheEndOfThePeriodTwo',
      'PeriodEndMinuteTwo',
    ],
  ],
  [
    'time3',
    [
      'AtTheBeginningOfThePeriodThree',
      'StartingPointOfTimePeriodThree',
      'AtTheEndOfThePeriodThree',
      'PeriodEndMinuteThree',
    ],
  ],
  [
    'time4',
    [
      'AtTheBeginningOfThePeriodFour',
      'StartingPointOfTimePeriodFour',
      'AtTheEndOfThePeriodFour',
      'PeriodEndMinuteFour',
    ],
  ],
  [
    'time5',
    [
      'AtTheBeginningOfThePeriodFive',
      'StartingPointOfTimePeriodFive',
      'AtTheEndOfThePeriodFive',
      'PeriodEndMinuteFive',
    ],
  ],
]);

const Setting: React.FC<SettingProps> = (props) => {
  const { id, deviceData, isLineLabel = false, isDeviceChild, type } = props;

  const settingData = props.settingData || {};
  const [controlForm] = Form.useForm();
  const [protectFrom] = Form.useForm();
  const [runForm] = Form.useForm();
  const [timeForm] = Form.useForm();
  const { authorityMap } = useAuthority([
    'device:detail:remoteControl:controlCommand',
    'device:detail:remoteControl:batterProtectSetting:detail',
    'device:detail:remoteControl:batterProtectSetting:edit',
    'device:detail:remoteControl:runParamSetting:detail',
    'device:detail:remoteControl:runParamSetting:edit',
    'device:detail:remoteControl:schoolTimeSetting:detail',
    'device:detail:remoteControl:schoolTimeSetting:edit',
  ]);
  const [disableProtect, { setTrue: setDisableProtectTrue, setFalse: setDisableProtectFlalse }] =
    useBoolean(true);
  const [disableRun, { setTrue: setDisableRunTrue, setFalse: setDisableRunFalse }] =
    useBoolean(true);
  const [disableTime, { setTrue: setDisableTimeTrue, setFalse: setDisableTimeFalse }] =
    useBoolean(true);
  const { loading, run } = useRequest(editSetting, {
    manual: true,
  });

  const onControlChange = useCallback(
    (fieldValue: ControlType) => {
      const field = Object.keys(fieldValue)[0];
      let content: React.ReactNode;
      switch (field) {
        case 'sysStart':
        case 'sysStop':
          const runStatus = settingData?.emsSysStatus
            ? formatMessage({ id: 'common.activate', defaultMessage: '启动' })
            : formatMessage({ id: 'common.stop', defaultMessage: '停止' });
          content = (
            <span>
              {formatMessage({ id: 'device.theCurrentSystemIs', defaultMessage: '当前系统为' })}
              <span className="cl-primary">
                {runStatus}
                {formatMessage({ id: 'common.status', defaultMessage: '状态' })}
              </span>
              ，
              {formatMessage({
                id: 'device.whetherToExecuteSystem',
                defaultMessage: '是否执行系统',
              })}
              {field === 'sysStart'
                ? formatMessage({ id: 'common.activate', defaultMessage: '启动' })
                : formatMessage({ id: 'common.stop', defaultMessage: '停止' })}
              {formatMessage({ id: 'device.order', defaultMessage: '指令' })}
            </span>
          );
          break;
        case 'bmsClose':
        case 'bmsBreak':
          const contactStatus = closeFormat(settingData?.MainContactorStatus);
          content = (
            <span>
              {formatMessage({
                id: 'device.currentBMSMainContactorIs',
                defaultMessage: '当前BMS主接触器为',
              })}
              <span className="cl-primary">
                {contactStatus}
                {formatMessage({ id: 'common.status', defaultMessage: '状态' })}
              </span>
              ，
              {formatMessage({
                id: 'device.whetherExecuteBMSTheMainContactorIs',
                defaultMessage: '是否执行BMS主接触器为',
              })}
              {field === 'bmsClose'
                ? formatMessage({ id: 'device.close', defaultMessage: '闭合' })
                : formatMessage({ id: 'device.break', defaultMessage: '断开' })}
              {formatMessage({ id: 'device.order', defaultMessage: '指令' })}
            </span>
          );
          break;
        case 'manualAutomaticSwitch':
          const systemStatus = settingData?.sysModel ? '自动' : '手动';
          content = (
            <span>
              {formatMessage({
                id: 'device.currentSystemModeIs',
                defaultMessage: '当前系统模式为',
              })}
              <span className="cl-primary">
                {systemStatus}
                {formatMessage({ id: 'common.status', defaultMessage: '状态' })}
              </span>
              {formatMessage({
                id: 'device.whetherExecuteManualAutomaticCommand',
                defaultMessage: '是否执行手/自动模式切换指令',
              })}
            </span>
          );
          break;
        case 'sysReset':
          content = (
            <span>
              {formatMessage({
                id: 'device.whetherExecuteSystemResetCommand',
                defaultMessage: '是否执行系统复位指令',
              })}
            </span>
          );
          break;
        case 'parallelOffGridModeSwitch':
          content = (
            <span>
              {formatMessage({
                id: 'device.whetherGridSwitch',
                defaultMessage: '是否执行并离网切换指令',
              })}
            </span>
          );
          break;
      }
      Modal.confirm({
        title: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
        content: content,
        okText: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
        cancelText: formatMessage({ id: 'common.cancel', defaultMessage: '取消' }),
        onOk: () => {
          return run({
            deviceId: id,
            input: { [field]: 1 },
            serviceId: field == 'manualAutomaticSwitch' ? 'operateModel' : field,
          })
            .then((data) => {
              if (data) {
                message.success(
                  formatMessage({ id: 'device.issueSuccess', defaultMessage: '下发成功' }),
                );
              }
            })
            .finally(() => {
              controlForm.setFieldValue(field, false);
            });
        },
        onCancel: () => {
          controlForm.setFieldValue(field, false);
        },
      });
    },
    [controlForm],
  );

  const onProtectClick = useCallback(() => {
    protectFrom.submit();
  }, [protectFrom]);
  const requestProtect = useCallback((formData) => {
    Modal.confirm({
      title: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
      content: formatMessage({
        id: 'device.whetherExecuteCurrentProtectionParameter',
        defaultMessage: '是否执行当前保护参数下发',
      }),
      okText: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
      cancelText: formatMessage({ id: 'common.cancel', defaultMessage: '取消' }),
      onOk: () =>
        run({ deviceId: id, input: { ...formData }, serviceId: 'setChargeReleaseProtect' }).then(
          (data) => {
            if (data) {
              message.success(
                formatMessage({ id: 'device.issueSuccess', defaultMessage: '下发成功' }),
              );
              setDisableProtectTrue();
            }
          },
        ),
    });
  }, []);

  const onRunClick = useCallback(() => {
    runForm.submit();
  }, [runForm]);
  const requestRun = useCallback(
    (formData) => {
      Modal.confirm({
        title: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
        content: formatMessage({
          id: 'device.whetherExecuteCurrentRunningParameter',
          defaultMessage: '是否执行当前运行参数下发',
        }),
        okText: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
        cancelText: formatMessage({ id: 'common.cancel', defaultMessage: '取消' }),
        onOk: () => {
          const inputData: any = { handOpePcsPower: formData.handOpePcsPower };
          let fields = [];
          for (const key in formData) {
            if (key.includes('power')) {
              inputData[powerMap.get(key) || ''] = formData[key];
            } else if (key.includes('time') && formData[key] && formData[key].length) {
              fields = timeMap.get(key) || [];
              inputData[fields[0]] = formData[key][0].hour();
              inputData[fields[1]] = formData[key][0].minute();
              inputData[fields[2]] = formData[key][1].hour();
              inputData[fields[3]] = formData[key][1].minute();
            }
          }
          run({
            deviceId: id,
            input: inputData,
            serviceId: 'pcsPowerSetting',
          }).then((data) => {
            if (data) {
              message.success(
                formatMessage({ id: 'device.issueSuccess', defaultMessage: '下发成功' }),
              );
              setDisableRunTrue();
            }
          });
        },
      });
    },
    [runForm],
  );

  const onTimeClick = useCallback(() => {
    timeForm.submit();
  }, [timeForm]);
  const onTimeFormFinish = useCallback((formData: any) => {
    Modal.confirm({
      title: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
      content: formatMessage({
        id: 'whetherDeliverParametersDuringCurrentSchool',
        defaultMessage: '是否执行当前校时参数下发',
      }),
      okText: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
      cancelText: formatMessage({ id: 'common.cancel', defaultMessage: '取消' }),
      onOk: () => {
        const time: Moment = formData.time;
        run({
          deviceId: id,
          input: {
            yearWait: time.year(),
            monthWait: time.month() + 1,
            dayWait: time.date(),
            hourWait: time.hour(),
            minuteWait: time.minute(),
            secondWait: time.second(),
            weekWait: time.day() || 7,
          },
          serviceId: 'correctionTime',
        }).then((data) => {
          if (data) {
            message.success(
              formatMessage({ id: 'device.issueSuccess', defaultMessage: '下发成功' }),
            );
            setDisableTimeTrue();
          }
        });
      },
    });
  }, []);

  const validatorTime = useCallback((getFieldValue: FormInstance['getFieldValue'], num: number) => {
    const prevValue: Moment[] = getFieldValue('time' + (num - 1));
    const nextValue: Moment[] = getFieldValue('time' + (num + 1));
    return {
      validator: (_: any, value: Moment[]) => {
        if (value && value.length) {
          if (nextValue && nextValue.length && value[1].isAfter(nextValue[0])) {
            return Promise.reject(
              `${formatMessage({
                id: 'siteMonitor.TimePeriod',
                defaultMessage: '时段',
              })}${num}${formatMessage({
                id: 'siteMonitor.lessThan',
                defaultMessage: '应小于',
              })}${formatMessage({ id: 'siteMonitor.TimePeriod', defaultMessage: '时段' })}${
                num + 1
              }`,
            );
          }
          if (num > 1 && prevValue && prevValue.length && value[0].isBefore(prevValue[1])) {
            return Promise.reject(
              `${formatMessage({
                id: 'siteMonitor.TimePeriod',
                defaultMessage: '时段',
              })}${num}${formatMessage({
                id: 'siteMonitor.greaterThan',
                defaultMessage: '应大于',
              })}${formatMessage({ id: 'siteMonitor.TimePeriod', defaultMessage: '时段' })}${
                num - 1
              }`,
            );
          }
        } else {
          if (nextValue && nextValue.length) {
            return Promise.reject(
              `${formatMessage({
                id: 'siteMonitor.TimePeriod',
                defaultMessage: '时段',
              })}${num}${formatMessage({ id: 'common.required', defaultMessage: '必填' })}`,
            );
          }
          if (getFieldValue('power' + num)) {
            return Promise.reject(
              `${formatMessage({
                id: 'siteMonitor.TimePeriod',
                defaultMessage: '时段',
              })}${num}${formatMessage({ id: 'common.required', defaultMessage: '必填' })}`,
            );
          }
        }
        return Promise.resolve();
      },
    };
  }, []);
  const validatorPower = useCallback(
    (getFieldValue: FormInstance['getFieldValue'], num: number) => {
      const timeValue: Moment[] = getFieldValue('time' + num);
      return {
        validator: (_: any, value: string) => {
          if (timeValue && timeValue.length) {
            if (isEmpty(value)) {
              return Promise.reject(
                formatMessage({ id: 'siteMonitor.powerNotEmpty', defaultMessage: '功率不能为空' }),
              );
            }
          }
          return Promise.resolve();
        },
      };
    },
    [],
  );

  useEffect(() => {
    if (!lodash.isEmpty(settingData)) {
      if (disableProtect && !isDeviceChild) {
        protectFrom.setFieldsValue({
          OverchargeProtection: settingData?.OverchargeProtection ?? '',
          OverchargeRelease: settingData?.OverchargeRelease ?? '',
          OverdischargeProtection: settingData?.OverdischargeProtection ?? '',
          Overrelease: settingData?.Overrelease ?? '',
        });
      }
      if (disableRun) {
        const runData: any = {};
        timeMap.forEach((item, key) => {
          runData[key] = !isEmpty(settingData?.[item[0]])
            ? [
                moment(
                  `${moment().format('YYYY-MM-DD')} ${settingData?.[item[0]]}:${
                    settingData?.[item[1]]
                  }`,
                ),
                moment(
                  `${moment().format('YYYY-MM-DD')} ${settingData?.[item[2]]}:${
                    settingData?.[item[3]]
                  }`,
                ),
              ]
            : [];
        });
        powerMap.forEach((item, key) => {
          runData[key] = settingData?.[item];
        });
        runForm.setFieldsValue({
          handOpePcsPower: settingData?.handOpePcsPower ?? '',
          ...runData,
        });
      }
      if (disableTime && !isDeviceChild) {
        timeForm.setFieldsValue({
          time: settingData?.sysTem ? moment(settingData?.sysTem) : null,
        });
      }
    }
  }, [settingData, protectFrom, runForm, timeForm]);

  return (
    <>
      {authorityMap.get('device:detail:remoteControl:controlCommand') && (
        <>
          {isLineLabel ? (
            <LineLabel
              title={formatMessage({
                id: 'siteMonitor.controlCommand',
                defaultMessage: '控制指令',
              })}
            />
          ) : (
            <Label
              title={formatMessage({
                id: 'siteMonitor.controlCommand',
                defaultMessage: '控制指令',
              })}
            />
          )}
          <Form
            form={controlForm}
            className="setting-form"
            layout="horizontal"
            onValuesChange={onControlChange}
            disabled={deviceData?.status === OnlineStatusEnum.Offline}
          >
            <Row>
              <Col flex="25%">
                <Form.Item
                  name="sysStart"
                  label={formatMessage({
                    id: 'siteMonitor.systemStartup',
                    defaultMessage: '系统启动',
                  })}
                  labelCol={{ flex: '116px' }}
                  valuePropName="checked"
                >
                  <Switch />
                </Form.Item>
              </Col>
              <Col flex="25%">
                <Form.Item
                  name="sysStop"
                  label={formatMessage({
                    id: 'siteMonitor.systemHalt',
                    defaultMessage: '系统停止',
                  })}
                  labelCol={{ flex: '116px' }}
                  valuePropName="checked"
                >
                  <Switch />
                </Form.Item>
              </Col>
              <Col flex="25%">
                <Form.Item
                  name="bmsClose"
                  label={
                    'BMS' +
                    formatMessage({
                      id: 'siteMonitor.mainContactorClosed',
                      defaultMessage: '主接触器闭合',
                    })
                  }
                  valuePropName="checked"
                >
                  <Switch />
                </Form.Item>
              </Col>
              <Col flex="25%">
                <Form.Item
                  name="bmsBreak"
                  label={
                    'BMS' +
                    formatMessage({
                      id: 'siteMonitor.mainContactorDisconnected',
                      defaultMessage: '主接触器断开',
                    })
                  }
                  valuePropName="checked"
                >
                  <Switch />
                </Form.Item>
              </Col>
              <Col flex="25%">
                <Form.Item
                  name="manualAutomaticSwitch"
                  label={formatMessage({
                    id: 'siteMonitor.manualAutomaticSwitch',
                    defaultMessage: '手/自动切换',
                  })}
                  labelCol={{ flex: '116px' }}
                  valuePropName="checked"
                >
                  <Switch />
                </Form.Item>
              </Col>
              {type === DeviceTypeEnum.BWattEms || type === DeviceTypeEnum.React100XEmsEnergy ? (
                <Col flex="25%">
                  <Form.Item
                    name="sysReset"
                    label={formatMessage({
                      id: 'siteMonitor.systemReset',
                      defaultMessage: '系统复位',
                    })}
                    labelCol={{ flex: '116px' }}
                    valuePropName="checked"
                  >
                    <Switch />
                  </Form.Item>
                </Col>
              ) : (
                <></>
              )}
              {type === DeviceTypeEnum.React100XEmsEnergy && (
                <Col flex="25%">
                  <Form.Item
                    name="parallelOffGridModeSwitch"
                    label={formatMessage({
                      id: 'device.andOffGridSwitching',
                      defaultMessage: '并离网切换',
                    })}
                    labelCol={{ flex: '116px' }}
                    valuePropName="checked"
                  >
                    <Switch />
                  </Form.Item>
                </Col>
              )}
            </Row>
          </Form>
        </>
      )}
      {authorityMap.get('device:detail:remoteControl:batterProtectSetting:detail') && (
        <>
          {!isDeviceChild ? (
            <>
              {isLineLabel ? (
                <LineLabel
                  title={formatMessage({
                    id: 'siteMonitor.batteryProtectionParameterSet',
                    defaultMessage: '电池保护参数设置',
                  })}
                >
                  {authorityMap.get('device:detail:remoteControl:batterProtectSetting:edit') && (
                    <Button
                      type="primary"
                      onClick={onProtectClick}
                      loading={loading}
                      disabled={disableProtect || deviceData?.status === OnlineStatusEnum.Offline}
                    >
                      {formatMessage({
                        id: 'siteMonitor.issueParameters',
                        defaultMessage: '下发参数',
                      })}
                    </Button>
                  )}
                </LineLabel>
              ) : (
                <Label
                  title={formatMessage({
                    id: 'siteMonitor.batteryProtectionParameterSet',
                    defaultMessage: '电池保护参数设置',
                  })}
                  operate={
                    authorityMap.get('device:detail:remoteControl:batterProtectSetting:edit') && (
                      <Button
                        type="primary"
                        onClick={onProtectClick}
                        loading={loading}
                        disabled={disableProtect || deviceData?.status === OnlineStatusEnum.Offline}
                      >
                        {formatMessage({
                          id: 'siteMonitor.issueParameters',
                          defaultMessage: '下发参数',
                        })}
                      </Button>
                    )
                  }
                />
              )}
              <Form
                form={protectFrom}
                className="setting-form"
                layout="horizontal"
                labelCol={{ flex: '116px' }}
                onFinish={requestProtect}
                onValuesChange={setDisableProtectFlalse}
                disabled={
                  deviceData?.status === OnlineStatusEnum.Offline ||
                  !authorityMap.get('device:detail:remoteControl:batterProtectSetting:edit')
                }
              >
                <Row>
                  <Col flex="25%">
                    <Form.Item
                      name="OverchargeProtection"
                      label={formatMessage({
                        id: 'siteMonitor.overchargeProtection',
                        defaultMessage: '过充保护',
                      })}
                      rules={[
                        {
                          required: true,
                          message:
                            formatMessage({
                              id: 'siteMonitor.overchargeProtection',
                              defaultMessage: '过充保护',
                            }) + formatMessage({ id: 'common.required', defaultMessage: '必填' }),
                        },
                      ]}
                    >
                      <InputNumber className="w-full" addonAfter="V" />
                    </Form.Item>
                  </Col>
                  <Col flex="25%">
                    <Form.Item
                      name="OverchargeRelease"
                      label={formatMessage({
                        id: 'siteMonitor.overchargeRelease',
                        defaultMessage: '过充释放',
                      })}
                      rules={[
                        {
                          required: true,
                          message:
                            formatMessage({
                              id: 'siteMonitor.overchargeRelease',
                              defaultMessage: '过充释放',
                            }) + formatMessage({ id: 'common.required', defaultMessage: '必填' }),
                        },
                      ]}
                    >
                      <InputNumber className="w-full" addonAfter="V" />
                    </Form.Item>
                  </Col>
                  <Col flex="25%">
                    <Form.Item
                      name="OverdischargeProtection"
                      label={formatMessage({
                        id: 'siteMonitor.overDischargeProtection',
                        defaultMessage: '过放保护',
                      })}
                      rules={[
                        {
                          required: true,
                          message:
                            formatMessage({
                              id: 'siteMonitor.overDischargeProtection',
                              defaultMessage: '过放保护',
                            }) + formatMessage({ id: 'common.required', defaultMessage: '必填' }),
                        },
                      ]}
                    >
                      <InputNumber className="w-full" addonAfter="V" />
                    </Form.Item>
                  </Col>
                  <Col flex="25%">
                    <Form.Item
                      name="Overrelease"
                      label={formatMessage({
                        id: 'siteMonitor.overrelease',
                        defaultMessage: '过放释放',
                      })}
                      rules={[
                        {
                          required: true,
                          message:
                            formatMessage({
                              id: 'siteMonitor.overrelease',
                              defaultMessage: '过放释放',
                            }) + formatMessage({ id: 'common.required', defaultMessage: '必填' }),
                        },
                      ]}
                    >
                      <InputNumber className="w-full" addonAfter="V" />
                    </Form.Item>
                  </Col>
                </Row>
              </Form>
            </>
          ) : (
            <></>
          )}
        </>
      )}
      {authorityMap.get('device:detail:remoteControl:runParamSetting:detail') && (
        <>
          {isLineLabel ? (
            <LineLabel
              title={formatMessage({
                id: 'siteMonitor.runningParameterSet',
                defaultMessage: '运行参数设置',
              })}
            >
              {authorityMap.get('device:detail:remoteControl:runParamSetting:edit') && (
                <Button
                  type="primary"
                  onClick={onRunClick}
                  loading={loading}
                  disabled={disableRun || deviceData?.status === OnlineStatusEnum.Offline}
                >
                  {formatMessage({ id: 'siteMonitor.issueParameters', defaultMessage: '下发参数' })}
                </Button>
              )}
            </LineLabel>
          ) : (
            <Label
              title={formatMessage({
                id: 'siteMonitor.runningParameterSet',
                defaultMessage: '运行参数设置',
              })}
              operate={
                authorityMap.get('device:detail:remoteControl:runParamSetting:edit') && (
                  <Button
                    type="primary"
                    onClick={onRunClick}
                    loading={loading}
                    disabled={disableRun || deviceData?.status === OnlineStatusEnum.Offline}
                  >
                    {formatMessage({
                      id: 'siteMonitor.issueParameters',
                      defaultMessage: '下发参数',
                    })}
                  </Button>
                )
              }
            />
          )}
          <Form
            form={runForm}
            className="setting-form"
            layout="horizontal"
            labelAlign="right"
            labelCol={{ flex: '116px' }}
            onFinish={requestRun}
            onValuesChange={setDisableRunFalse}
            disabled={
              deviceData?.status === OnlineStatusEnum.Offline ||
              !authorityMap.get('device:detail:remoteControl:runParamSetting:edit')
            }
          >
            <Row>
              <Col flex="25%">
                <Form.Item
                  name="handOpePcsPower"
                  label={formatMessage({
                    id: 'siteMonitor.manualPCSPower',
                    defaultMessage: '手动PCS功率',
                  })}
                >
                  <InputNumber className="w-full" addonAfter="kW" />
                </Form.Item>
              </Col>
            </Row>
            <Row>
              <Col flex="25%">
                <Form.Item
                  name="time1"
                  label={
                    formatMessage({ id: 'siteMonitor.TimePeriod', defaultMessage: '时段' }) + '1'
                  }
                  rules={[({ getFieldValue }) => validatorTime(getFieldValue, 1)]}
                >
                  <TimePicker.RangePicker
                    className="w-full"
                    format={timeFormat}
                    minuteStep={15}
                    placeholder={[
                      formatMessage({ id: 'common.start', defaultMessage: '开始' }),
                      formatMessage({ id: 'common.end', defaultMessage: '结束' }),
                    ]}
                    getPopupContainer={(triggerNode) => triggerNode.parentElement || document.body}
                  />
                </Form.Item>
              </Col>
              <Col flex="25%">
                <Form.Item
                  name="power1"
                  label={formatMessage({
                    id: 'siteMonitor.executionPower',
                    defaultMessage: '执行功率',
                  })}
                  rules={[({ getFieldValue }) => validatorPower(getFieldValue, 1)]}
                >
                  <InputNumber className="w-full" addonAfter="kW" />
                </Form.Item>
              </Col>
            </Row>
            <Row>
              <Col flex="25%">
                <Form.Item
                  name="time2"
                  label={
                    formatMessage({ id: 'siteMonitor.TimePeriod', defaultMessage: '时段' }) + '2'
                  }
                  rules={[({ getFieldValue }) => validatorTime(getFieldValue, 2)]}
                >
                  <TimePicker.RangePicker
                    className="w-full"
                    format={timeFormat}
                    minuteStep={15}
                    placeholder={[
                      formatMessage({ id: 'common.start', defaultMessage: '开始' }),
                      formatMessage({ id: 'common.end', defaultMessage: '结束' }),
                    ]}
                    getPopupContainer={(triggerNode) => triggerNode.parentElement || document.body}
                  />
                </Form.Item>
              </Col>
              <Col flex="25%">
                <Form.Item
                  name="power2"
                  label={formatMessage({
                    id: 'siteMonitor.executionPower',
                    defaultMessage: '执行功率',
                  })}
                  rules={[({ getFieldValue }) => validatorPower(getFieldValue, 2)]}
                >
                  <InputNumber className="w-full" addonAfter="kW" />
                </Form.Item>
              </Col>
            </Row>
            <Row>
              <Col flex="25%">
                <Form.Item
                  name="time3"
                  label={
                    formatMessage({ id: 'siteMonitor.TimePeriod', defaultMessage: '时段' }) + '3'
                  }
                  rules={[({ getFieldValue }) => validatorTime(getFieldValue, 3)]}
                >
                  <TimePicker.RangePicker
                    className="w-full"
                    format={timeFormat}
                    minuteStep={15}
                    placeholder={[
                      formatMessage({ id: 'common.start', defaultMessage: '开始' }),
                      formatMessage({ id: 'common.end', defaultMessage: '结束' }),
                    ]}
                    getPopupContainer={(triggerNode) => triggerNode.parentElement || document.body}
                  />
                </Form.Item>
              </Col>
              <Col flex="25%">
                <Form.Item
                  name="power3"
                  label={formatMessage({
                    id: 'siteMonitor.executionPower',
                    defaultMessage: '执行功率',
                  })}
                  rules={[({ getFieldValue }) => validatorPower(getFieldValue, 3)]}
                >
                  <InputNumber className="w-full" addonAfter="kW" />
                </Form.Item>
              </Col>
            </Row>
            <Row>
              <Col flex="25%">
                <Form.Item
                  name="time4"
                  label={
                    formatMessage({ id: 'siteMonitor.TimePeriod', defaultMessage: '时段' }) + '4'
                  }
                  rules={[({ getFieldValue }) => validatorTime(getFieldValue, 4)]}
                >
                  <TimePicker.RangePicker
                    className="w-full"
                    format={timeFormat}
                    minuteStep={15}
                    placeholder={[
                      formatMessage({ id: 'common.start', defaultMessage: '开始' }),
                      formatMessage({ id: 'common.end', defaultMessage: '结束' }),
                    ]}
                    getPopupContainer={(triggerNode) => triggerNode.parentElement || document.body}
                  />
                </Form.Item>
              </Col>
              <Col flex="25%">
                <Form.Item
                  name="power4"
                  label={formatMessage({
                    id: 'siteMonitor.executionPower',
                    defaultMessage: '执行功率',
                  })}
                  rules={[({ getFieldValue }) => validatorPower(getFieldValue, 4)]}
                >
                  <InputNumber className="w-full" addonAfter="kW" />
                </Form.Item>
              </Col>
            </Row>
            <Row>
              <Col flex="25%">
                <Form.Item
                  name="time5"
                  label={
                    formatMessage({ id: 'siteMonitor.TimePeriod', defaultMessage: '时段' }) + '5'
                  }
                  rules={[({ getFieldValue }) => validatorTime(getFieldValue, 5)]}
                >
                  <TimePicker.RangePicker
                    className="w-full"
                    format={timeFormat}
                    minuteStep={15}
                    placeholder={[
                      formatMessage({ id: 'common.start', defaultMessage: '开始' }),
                      formatMessage({ id: 'common.end', defaultMessage: '结束' }),
                    ]}
                    getPopupContainer={(triggerNode) => triggerNode.parentElement || document.body}
                  />
                </Form.Item>
              </Col>
              <Col flex="25%">
                <Form.Item
                  name="power5"
                  label={formatMessage({
                    id: 'siteMonitor.executionPower',
                    defaultMessage: '执行功率',
                  })}
                  rules={[({ getFieldValue }) => validatorPower(getFieldValue, 5)]}
                >
                  <InputNumber className="w-full" addonAfter="kW" />
                </Form.Item>
              </Col>
            </Row>
          </Form>
        </>
      )}
      {authorityMap.get('device:detail:remoteControl:schoolTimeSetting:detail') && (
        <>
          {!isDeviceChild ? (
            <>
              {isLineLabel ? (
                <LineLabel
                  title={formatMessage({
                    id: 'siteMonitor.timingSettings',
                    defaultMessage: '校时设置',
                  })}
                >
                  {authorityMap.get('device:detail:remoteControl:schoolTimeSetting:edit') && (
                    <Button
                      type="primary"
                      onClick={onTimeClick}
                      loading={loading}
                      disabled={disableTime || deviceData?.status === OnlineStatusEnum.Offline}
                    >
                      {formatMessage({
                        id: 'siteMonitor.issueParameters',
                        defaultMessage: '下发参数',
                      })}
                    </Button>
                  )}
                </LineLabel>
              ) : (
                <Label
                  title={formatMessage({
                    id: 'siteMonitor.timingSettings',
                    defaultMessage: '校时设置',
                  })}
                  operate={
                    authorityMap.get('device:detail:remoteControl:schoolTimeSetting:edit') && (
                      <Button
                        type="primary"
                        onClick={onTimeClick}
                        loading={loading}
                        disabled={disableTime || deviceData?.status === OnlineStatusEnum.Offline}
                      >
                        {formatMessage({
                          id: 'siteMonitor.issueParameters',
                          defaultMessage: '下发参数',
                        })}
                      </Button>
                    )
                  }
                />
              )}
              <Form
                form={timeForm}
                layout="horizontal"
                labelCol={{ flex: '116px' }}
                onFinish={onTimeFormFinish}
                onValuesChange={setDisableTimeFalse}
                disabled={
                  deviceData?.status === OnlineStatusEnum.Offline ||
                  !authorityMap.get('device:detail:remoteControl:schoolTimeSetting:edit')
                }
              >
                <Row>
                  <Col flex="25%">
                    <Form.Item
                      name="time"
                      label={formatMessage({
                        id: 'siteMonitor.systemTime',
                        defaultMessage: '系统时间',
                      })}
                      rules={[
                        {
                          required: true,
                          message:
                            formatMessage({
                              id: 'siteMonitor.systemTime',
                              defaultMessage: '系统时间',
                            }) + formatMessage({ id: 'common.required', defaultMessage: '必填' }),
                        },
                      ]}
                    >
                      <DatePick
                        className="w-full"
                        getPopupContainer={(triggerNode: any) => triggerNode.parentElement}
                        showTime
                      />
                    </Form.Item>
                  </Col>
                </Row>
              </Form>
            </>
          ) : (
            <></>
          )}
        </>
      )}
    </>
  );
};

export default Setting;
