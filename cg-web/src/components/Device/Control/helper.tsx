/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-11-27 16:00:49
 * @LastEditTime: 2024-12-26 16:09:30
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/Device/Control/helper.tsx
 */
import {
  EditOutlined,
  MinusCircleOutlined,
  PlusCircleOutlined,
  RedoOutlined,
} from '@ant-design/icons';
import { ProFormColumnsType } from '@ant-design/pro-components';
import { Col, FormInstance, message, Modal, Row, Spin } from 'antd';
import {
  DeviceModelShowTypeEnum,
  DeviceModelTypeEnum,
  formatMessage,
  formatModelValue,
  isEmpty,
} from '@/utils';
import moment from 'moment';
import { DeviceDoubleType, DeviceServiceModelType, DeviceServiceType, TipType } from '@/types/device';
import { FieldModeEnum } from './type';
import TreeSelect from './components/TreeSelect';
import { DetailItem } from '@/components/Detail';
import { DeviceDataType } from '@/services/equipment';
import styles from './index.less';
import { template } from 'lodash';
import { ControlInterceptType } from '../module/typing';
import { Suspense } from 'react';

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

export const validatorTime = (
  rule: any,
  value: string,
  field: string,
  getFieldValue: FormInstance['getFieldValue'],
) => {
  const valueArr = value?.split?.('-');
  const periodOfTime = getFieldValue(field);
  const ruleField = rule?.field?.split?.('.');
  const index = ruleField?.[1] * 1;
  const prevValue: string[] = periodOfTime?.[index - 1]?.[ruleField?.[2]]?.split?.('-');
  const nextValue: string[] = periodOfTime?.[index + 1]?.[ruleField?.[2]]?.split?.('-');
  if (valueArr && valueArr.length) {
    if (
      prevValue &&
      prevValue.length &&
      moment(contrastDate + valueArr[0]).isBefore(moment(contrastDate + prevValue[1]))
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
      moment(contrastDate + valueArr[1]).isAfter(moment(contrastDate + nextValue[0]))
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

export const timeRangeColumn: ProFormColumnsType = {
  valueType: 'formList',
  // dataIndex: 'ElectrovalenceTimeFrame',
  // initialValue: [{ effectiveTime: [] }],
  initialValue: [],
  fieldProps: {
    copyIconProps: false,
    creatorButtonProps: {
      className: 'pl0',
      creatorButtonText: formatMessage({
        id: 'siteManage.set.addTimeSlot',
        defaultMessage: '新增时间段',
      }),
      icon: <PlusCircleOutlined />,
      type: 'link',
      style: { width: 'unset' },
    },
    min: 1,
    deleteIconProps: {
      Icon: (prop: any) => {
        return <MinusCircleOutlined {...prop} style={{ color: '#165dff' }} />;
      },
      tooltipText: '删除',
    },
    itemRender: ({ listDom, action }: any, { index }: any) => {
      //label.replace(/^\d|\d$/, index + 1);
      return (
        <div className={styles.action}>
          <Row gutter={0}>
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
  rowProps: {
    gutter: 0,
  },
  columns: [
    {
      valueType: 'group',
      columns: [],
      rowProps: {
        gutter: [24, 0],
      },
      colProps: {
        span: 24,
      },
    },
  ],
};

export const getColumnsLength = (columns?: ProFormColumnsType[]): number => {
  const columnGruop: ProFormColumnsType[][] = [[]];
  let maxColumnLength = 0;
  columns?.forEach?.((item) => {
    if (item.valueType == 'formList') {
      const length = (item?.columns as any)?.[0]?.columns?.length || 0;
      maxColumnLength = Math.max(length, maxColumnLength);
      columnGruop.push([]);
    } else {
      columnGruop[columnGruop.length - 1].push(item);
    }
  });
  columnGruop.forEach((item) => {
    maxColumnLength = Math.max(maxColumnLength, item.length);
  });
  return maxColumnLength;
};

export const getRealField = (fieldId?: string) => {
  const result = (fieldId ?? '')?.split('.') || [];
  return result[result.length - 1];
};

const selectFieldType: (DeviceModelTypeEnum | undefined)[] = [DeviceModelTypeEnum.TreeSelect];

export const getFieldItems = (
  field: DeviceServiceModelType,
  {
    deviceData,
    onRefresh,
    onClick,
    passAuthority,
  }: {
    deviceData?: DeviceDataType;
    onRefresh: (...params: any) => void;
    onClick: (...params: any) => void;
    passAuthority: (...params: any) => boolean;
  },
) => {
  const columns: ProFormColumnsType[] = [];
  const details: DetailItem[] = [];
  if (passAuthority(field?.authority, 'edit')) {
    columns.push({
      title: field?.name,
      dataIndex: getRealField(field?.id),
      formItemProps: {
        validateTrigger: 'submit',
        rules:
          field?.required === false
            ? []
            : [
              {
                required: true,
                message: formatMessage(
                  {
                    id: selectFieldType.includes(field.dataType?.type)
                      ? 'common.pleaseSelectSentence'
                      : 'common.pleaseEnterSentence',
                    defaultMessage: '请选择',
                  },
                  {
                    content: field?.name,
                  },
                ),
              },
            ],
      },
      renderFormItem: () => {
        switch (field.dataType?.type) {
          case DeviceModelTypeEnum.TreeSelect:
            return <TreeSelect field={field} type={FieldModeEnum.Edit} />;
          default:
            return <></>;
        }
      },
      initialValue: isEmpty(field?.defaultValue) ? undefined : field?.defaultValue + '',
    });
    if (field?.form?.span == 24) {
      columns.push({
        formItemProps: {
          noStyle: true,
        },
        renderFormItem: () => <div />,
        colProps: {
          span: 24,
        },
      });
    }
  }
  if (field.showType != DeviceModelShowTypeEnum.HideName) {
    details.push?.({
      field: field?.id || '',
      label: field?.name,
      showPlaceholder: false,
      span: field?.span,
      unit: (field?.dataType as DeviceDoubleType)?.specs?.unit,
      valueStyle: {
        width: '100%',
      },
      valueInterceptor: (_, data) => {
        if (field?.deviceId) {
          return data?.[field?.deviceId || '']?.[getRealField(field?.id)];
        } else {
          return data?.[deviceData?.deviceId || '']?.[field?.id || ''];
        }
      },
      format: (value) => {
        switch (field.dataType?.type) {
          case DeviceModelTypeEnum.TreeSelect:
            return <TreeSelect className="w-full" field={field} />;
          default:
            return <></>;
        }
      },
      extral: (
        <>
          {field?.buttons?.includes?.('refresh') && (
            <RedoOutlined
              className={`cl-primary cursor ${styles.refresh}`}
              onClick={() => onRefresh(field)}
              title={formatMessage({
                id: 'common.refresh',
                defaultMessage: '刷新',
              })}
            />
          )}
          {field?.buttons?.includes?.('edit') && (
            <EditOutlined
              className={`cl-primary cursor ${styles.refresh}`}
              onClick={() =>
                onClick(
                  { ...field, id: field.serviceId },
                  columns.map((item) => ({ ...item, colProps: { span: 24 } })),
                  1,
                )
              }
              title={formatMessage({
                id: 'common.edit',
                defaultMessage: '编辑',
              })}
            />
          )}
        </>
      ),
    });
  }
  return {
    columns,
    details,
  };
};


export const getTipRules = (data: Record<string, any>, service?: DeviceServiceType) => {
  const rules: TipType[] = service?.promptRule?.map?.((item) => {
    try {
      return {
        title: template(item.title)(data),
        content: template(item.content)(data),
      };
    } catch (e) {
      console.error(e);
      return {
        title: item.title,
        content: item.content,
      };
    }
  }) || [
      {
        title: service?.name,
        content: formatMessage({
          id: 'device.whetherExecuteCurrentParameter',
          defaultMessage: '是否执行当前参数下发',
        }),
      },
    ];
  return rules;
};

export const getTipConfirm = (rules: TipType[], callback: () => Promise<any>, cancelBack?: () => void) => {
  let num = 0;
  const confirm = () => {
    const rule = rules?.[num];
    Modal.confirm({
      title: rule?.title || '',
      content: rule?.content,
      okText: formatMessage({ id: 'common.confirm', defaultMessage: '确认' }),
      cancelText: formatMessage({ id: 'common.cancel', defaultMessage: '取消' }),
      onOk: () => {
        num++;
        if (num < rules?.length) {
          confirm();
          return;
        } else {
          return callback?.();
        }
      },
      onCancel: () => {
        cancelBack?.();
      },
    });
  };
  confirm();
}

export const showControlIntercept = (options: {
  field: DeviceServiceModelType,
  Component: React.FC<ControlInterceptType>,
  title: string,
  deviceData?: DeviceDataType,
}) => {
  const { field, Component, title, deviceData } = options;
  let value: Record<string, any> | undefined;
  const onChange = (data?: Record<string, any>) => {
    value = data;
  };

  return new Promise<Record<string, any> | undefined>((resolve, reject) => {

    Modal.confirm({
      title: field.name + '：' + title,
      width: '552px',
      className: styles.modal,
      onCancel: () => {
        resolve(undefined);
      },
      onOk: () => {
        if (value) {
          resolve(value);
        } else {
          message.warning('请选择设备');
          return Promise.reject();
        }
      },
      content: <Suspense
        fallback={
          <div className="tx-center">
            <Spin />
          </div>
        }
      >
        <Component
          deviceData={deviceData}
          onChange={onChange}
        />
      </Suspense>,
    });
  });
};
