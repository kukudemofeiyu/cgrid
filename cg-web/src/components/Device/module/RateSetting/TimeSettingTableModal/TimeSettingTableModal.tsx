import { Button, Col, Form, message, Modal, Row } from 'antd';
import { useEffect, useState } from 'react';
import {
  ProForm,
  ProFormDatePicker,
  ProFormGroup,
  ProFormList,
  ProFormSelect,
  ProFormText,
  ProFormTimePicker,
} from '@ant-design/pro-components';
import styles from '../index.less';
import {
  contrastDate,
  contrastYear,
  dateTableOption,
  timePeriodSelectConfig,
  timeTableOption,
  timeTableSelectConfig,
} from '../config';
import { TimeTypeEnum } from '../type';
import type { RateSettingType, DateRangeType, DateRangeListType } from '../type';
import Detail from '@/components/Detail';
import { useRequest } from 'umi';
import { setMeterRateData } from '../service';
import type { DeviceDataType } from '@/services/equipment';
import moment from 'moment';
import type { Moment } from 'moment';
import { isEmpty } from 'lodash';
import { formatMessage } from '@/utils';

type TimeSettingModalType = {
  onSuccess?: (value: boolean) => void;
  deviceData?: DeviceDataType;
  data: RateSettingType;
  disabled: boolean;
};
const TimeSettingModal: React.FC<TimeSettingModalType> = (props) => {
  const { data, deviceData, onSuccess, disabled } = props;
  const [formData, setFormData] = useState<RateSettingType>(data);
  const [open, setOpen] = useState(false);
  const [form] = Form.useForm();

  const { loading, run } = useRequest(setMeterRateData, {
    manual: true,
  });

  useEffect(() => {
    setFormData(data);
  }, [data]);

  const closeForm = () => {
    form.resetFields();
    setOpen(false);
  };
  const formateDateTime = (type: TimeTypeEnum, value: string | Moment): Moment => {
    if (moment.isMoment(value)) {
      if (type === TimeTypeEnum.Date) {
        return moment(value.format('YYYY-MM-DD'));
      } else {
        return moment(value.format('YYYY-MM-DD HH:mm'));
      }
    }
    if (type === TimeTypeEnum.Date) {
      return moment(contrastYear + value);
    } else {
      return moment(contrastDate + value);
    }
  };

  const validatorTime = (
    rule: any,
    value: [],
    selectField: string,
    timeField: string,
    type: TimeTypeEnum,
  ) => {
    for (let i = 0; i < value.length - 1; i++) {
      const prevValue = value[i][timeField],
        nextValue = value[i + 1][timeField];

      if (
        (isEmpty(prevValue) && !isEmpty(nextValue)) ||
        (!isEmpty(prevValue) &&
          !isEmpty(nextValue) &&
          !formateDateTime(type, prevValue).isBefore(formateDateTime(type, nextValue)))
      ) {
        message.error(
          formatMessage(
            type == TimeTypeEnum.Date
              ? {
                  id: 'common.1010',
                  defaultMessage: `日期${i + 2}应大于等于日期${i + 1}`,
                }
              : {
                  id: 'common.1011',
                  defaultMessage: `时段${i + 2}应大于等于时段${i + 1}`,
                },
            {
              start: i + 2,
              end: i + 1,
            },
          ),
        );

        return Promise.reject(new Error());
      }
    }
    return Promise.resolve();
  };

  const saveData = async () => {
    if (!(await form.validateFields())) {
      const errors = form.getFieldsError();
      if (errors.length > 1 && errors[0].errors.length > 1) {
        message.error(errors[0].errors[0]);
      }
      return;
    }

    const curData = form.getFieldsValue();
    const dateList: DateRangeType[] = curData.m34.filter(
      (item: DateRangeType) => item.Md && item.Tn,
    );
    const input = {
      m34: dateList,
      m35: curData.m35.reduce((acc: DateRangeListType[], cur: DateRangeListType) => {
        if (dateList.some((obj: DateRangeType) => obj.Tn === cur.Tn)) {
          const list = cur.m36.filter((item2) => item2.m37 && item2.Hm);
          acc.push({
            ...cur,
            m36: list,
          });
        }
        return acc;
      }, []),
    };
    run({
      deviceId: deviceData?.deviceId,
      serviceId: 'm33',
      input: JSON.stringify(input),
    })?.then((res) => {
      onSuccess && onSuccess(true);
      message.success(formatMessage({ id: 'device.issueSuccess', defaultMessage: '下发成功' }));
      closeForm();
    });
  };

  return (
    <>
      <Detail.Label title={formatMessage({ id: 'device.1049', defaultMessage: '费率设置' })}>
        <Button type="primary" disabled={disabled} onClick={() => setOpen(true)}>
          {formatMessage({ id: 'common.configParam', defaultMessage: '配置参数' })}
        </Button>
      </Detail.Label>

      <Modal
        confirmLoading={loading}
        title={formatMessage({ id: 'device.1049', defaultMessage: '费率设置' })}
        centered
        open={open}
        onOk={() => saveData()}
        onCancel={() => closeForm()}
        width={1280}
      >
        <ProForm<RateSettingType>
          className={styles.form}
          initialValues={formData}
          form={form}
          submitter={{
            render: () => {
              return [];
            },
          }}
        >
          <Row wrap={false}>
            <Col flex="none" className={styles.timeForm}>
              <ProFormList
                name="m34"
                label={formatMessage({ id: 'device.1050', defaultMessage: '时区表' })}
                actionRender={() => []}
                creatorButtonProps={false}
              >
                {(_, index) => (
                  <ProFormGroup>
                    <ProFormSelect
                      name={timeTableSelectConfig.dataIndex}
                      label={timeTableSelectConfig.title}
                      fieldProps={{ options: dateTableOption }}
                    />
                    <ProFormDatePicker
                      name="Md"
                      disabled={index === 0}
                      label={formatMessage({ id: 'screen.date', defaultMessage: '日期' })}
                      fieldProps={{
                        format: 'MM-DD',
                        onChange: (value, valueString) => {
                          if (!isEmpty(valueString)) {
                            const currentvalue = form.getFieldValue('m34');
                            currentvalue[index].Md = valueString;
                            form.setFieldValue('m34', currentvalue);
                          }
                        },
                      }}
                      rules={[
                        {
                          validator: (rules) =>
                            validatorTime(
                              rules,
                              form.getFieldValue('m34'),
                              'Tn',
                              'Md',
                              TimeTypeEnum.Date,
                            ),
                        },
                      ]}
                    />
                  </ProFormGroup>
                )}
              </ProFormList>
            </Col>
            <Col flex="auto">
              <ProFormList
                name="m35"
                className={styles.timeFormList}
                creatorButtonProps={false}
                itemRender={({ listDom }) => (
                  <div
                    style={{
                      display: 'inline-flex',
                    }}
                    className={`${styles.timeForm}`}
                  >
                    {listDom}
                  </div>
                )}
              >
                {(__, index) => (
                  <ProFormGroup>
                    <ProFormText name="Tn" hidden />
                    <ProFormList
                      name="m36"
                      className={styles.form}
                      label={formatMessage(
                        { id: 'device.1051', defaultMessage: '时段表' },
                        { index: index + 1 },
                      )}
                      actionRender={() => []}
                      creatorButtonProps={false}
                    >
                      {(___, idx) => (
                        <ProFormGroup>
                          <ProFormSelect
                            name={timePeriodSelectConfig.dataIndex}
                            label={timePeriodSelectConfig.title}
                            fieldProps={{ options: timeTableOption }}
                          />
                          <ProFormTimePicker
                            name="Hm"
                            disabled={idx === 0}
                            label={formatMessage({
                              id: 'device.timePeriod',
                              defaultMessage: '时间段',
                            })}
                            fieldProps={{
                              format: 'HH:mm',
                              onChange: (value, valueString) => {
                                if (!isEmpty(valueString)) {
                                  const currentvalue = form.getFieldValue('m35');
                                  currentvalue[index].m36[idx].Hm = valueString;
                                  form.setFieldValue('m35', currentvalue);
                                }
                              },
                            }}
                            rules={[
                              {
                                validator: (rules) =>
                                  validatorTime(
                                    rules,
                                    form.getFieldValue('m35')[index]['m36'],
                                    'm37',
                                    'Hm',
                                    TimeTypeEnum.Time,
                                  ),
                              },
                            ]}
                          />
                        </ProFormGroup>
                      )}
                    </ProFormList>
                  </ProFormGroup>
                )}
              </ProFormList>
            </Col>
          </Row>
        </ProForm>
      </Modal>
    </>
  );
};

export default TimeSettingModal;
