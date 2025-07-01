/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-20 17:15:52
 * @LastEditTime: 2025-02-20 19:25:36
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/billPolicy/helper.tsx
 */

import { ProColumns, ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage } from "@/utils";
import { chargeTypeOption, defaultOption, enableStatus, feeOption, timeTypeOption } from "@/utils/dict";
import { getAgentCol } from "@/utils/column";
import { MinusCircleOutlined, PlusCircleOutlined } from "@ant-design/icons";
import styles from './index.less';
import { Col, Row } from "antd";
import { validateAllTime, validatorTime } from "@/components/Device/Control/helper";
import { TimeRangePicker } from "@/components/Time";
import React from "react";

export const columns: ProColumns[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '规则名称' }),
    dataIndex: 'a',
    width: 150,
    ellipsis: true,
  },
  getAgentCol({
    render: (_, { agent }) => {
      return agent;
    }
  }),
  {
    title: formatMessage({ id: '', defaultMessage: '平台默认' }),
    dataIndex: 'c',
    valueType: 'select',
    valueEnum: defaultOption,
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '代理商默认' }),
    dataIndex: 'd',
    valueType: 'select',
    valueEnum: defaultOption,
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '创建时间' }),
    dataIndex: 'j',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
];

export const formColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '规则名称' }),
    dataIndex: 'a',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '费用设置' }),
    dataIndex: 'fee',
    valueType: 'formList',
    fieldProps: {
      creatorButtonProps: false,
      deleteIconProps: false,
      copyIconProps: false,
      itemRender: ({ listDom, action }: any, { index }: any) => {
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
    formItemProps: {
      extra: formatMessage({ id: '', defaultMessage: '尖峰平谷时段价格均为必填项。因各地电价不同，请根据当地实际电价填写。' }),
      rules: [
        {
          required: true,
        },
      ],
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
        columns: [
          {
            dataIndex: 'timeType',
            colProps: { span: 8 },
            renderFormItem: () => {
              const TimeType: React.FC<any> = (props) => {
                return props.value;
              };
              return <TimeType />;
            }
          },
          {
            dataIndex: 'electricBill',
            valueType: 'digit',
            fieldProps: {
              addonAfter: formatMessage({ id: '', defaultMessage: '电费（元/度）' }),
            },
            formItemProps: {
              rules: [
                {
                  required: true,
                },
              ],
            },
            colProps: { span: 8 },
          },
          {
            dataIndex: 'serverBill',
            valueType: 'digit',
            fieldProps: {
              addonAfter: formatMessage({ id: '', defaultMessage: '服务费（元/度）' }),
            },
            formItemProps: {
              rules: [
                {
                  required: true,
                },
              ],
            },
            colProps: { span: 8 },
          },
        ],
        rowProps: {
          gutter: [24, 0],
        },
        colProps: {
          span: 24,
        },
      },
    ],
  },
  {
    title: formatMessage({ id: '', defaultMessage: '24小时分布设置' }),
    dataIndex: 'c',
    valueType: 'formList',
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
    formItemProps: {
      extra: formatMessage({ id: '', defaultMessage: '时间设置最小间隔为30分钟；最多可设置48个计费时段。' }),
      rules: [
        {
          required: true,
        },
        {
          validator: (rule, value) => {
            return validateAllTime(value, 'time');
          }
        }
      ],
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
        columns: [
          {
            // title: formatMessage({ id: '', defaultMessage: '时段类型' }),
            dataIndex: 'type',
            valueEnum: timeTypeOption,
            formItemProps: {
              rules: [
                {
                  required: true,
                },
              ],
            },
            colProps: { span: 12 },
          },
          {
            // title: formatMessage({ id: '', defaultMessage: '时段' }),
            dataIndex: 'time',
            valueType: 'timeRange',
            formItemProps: ({ getFieldValue }) => {
              return {
                rules: [
                  {
                    required: true,
                    message: formatMessage({
                      id: 'common.pleaseSelect',
                      defaultMessage: '请选择',
                    }),
                  },
                  {
                    validator: (rule, value) => {
                      return validatorTime(rule, value, 'c', getFieldValue);
                    },
                  },
                ],
              };
            },
            renderFormItem: () => <TimeRangePicker />,
            colProps: { span: 12 },
          },
        ],
        rowProps: {
          gutter: [24, 0],
        },
        colProps: {
          span: 24,
        },
      },
    ],
  },
];
