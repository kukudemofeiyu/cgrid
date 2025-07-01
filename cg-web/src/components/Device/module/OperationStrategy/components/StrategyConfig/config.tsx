import { formatMessage } from '@/utils';
import type { ProFormColumnsType } from '@ant-design/pro-components';
import { DeleteOutlined, PlusCircleOutlined } from '@ant-design/icons';
import { Col, Row } from 'antd';
import styles from './index.less';
import { cloneDeep } from 'lodash';
import TemplateDetail from './module/TemplateDetail';
export enum TimeEnum {
  DAY,
  WEEK,
  MONTH,
}

export const timeOptions = [
  { label: formatMessage({ id: 'device.1099', defaultMessage: '周循环' }), value: TimeEnum.WEEK },
  {
    label: formatMessage({ id: 'device.1100', defaultMessage: '月循环' }),
    value: TimeEnum.MONTH,
  },
  { label: formatMessage({ id: 'device.1098', defaultMessage: '自定义日' }), value: TimeEnum.DAY },
];

export const timeRangeHanlder = (data: any, type = 'get') => {
  const curData = cloneDeep(data);
  curData.times = curData.times.map((item: any) => {
    if (type == 'get') {
      return {
        timeRange: [item.startTime, item.endTime],
        paramsTemplateId: item.paramsTemplateId,
      };
    } else {
      return {
        startTime: item.timeRange[0],
        endTime: item.timeRange[1],
        paramsTemplateId: item.paramsTemplateId,
      };
    }
  });
  return curData;
};

export const templateColumns = (templateOptions: any = []): ProFormColumnsType[] => [
  {
    title: formatMessage({ id: 'device.1096', defaultMessage: '策略模版名称' }),
    dataIndex: 'name',
    formItemProps: {
      rules: [
        {
          required: true,
          message: formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }),
        },
      ],
    },
    colProps: {
      span: 6,
    },
  },
  {
    valueType: 'formList',
    dataIndex: 'times',
    initialValue: [],
    fieldProps: {
      copyIconProps: false,
      creatorButtonProps: {
        creatorButtonText: formatMessage({
          id: 'siteManage.set.addTimeSlot',
          defaultMessage: '新增时间段',
        }),
        icon: <PlusCircleOutlined />,
        type: 'dashed',
        style: { width: '46%' },
      },
      min: 1,
      max: 10,
      deleteIconProps: {
        Icon: (prop: any) => {
          return <DeleteOutlined {...prop} style={{ color: '#697078' }} />;
        },
        tooltipText: formatMessage({ id: 'common.delete', defaultMessage: '删除' }),
      },
      itemRender: (params: any, ...resParams: any) => {
        const { listDom, action } = params;
        const { record } = resParams?.[0] || {};
        const values =
          templateOptions.find((item: any) => item.id == record.paramsTemplateId) || {};
        return (
          <Row gutter={0}>
            <Col className={styles.formAction} span={12}>
              {listDom}
              <TemplateDetail values={values} />
              {action}
            </Col>
          </Row>
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
            title: formatMessage({ id: 'common.time', defaultMessage: '时间' }),
            valueType: 'timeRange',
            dataIndex: 'timeRange',
            width: '100%',
            formItemProps: {
              rules: [
                {
                  required: true,
                  message: formatMessage({
                    id: 'common.pleaseEnter',
                    defaultMessage: '请输入',
                  }),
                },
              ],
            },
            fieldProps: () => {
              return {
                popupClassName: 'ant-picker-noyear',
                format: 'HH:mm',
              };
            },
            colProps: {
              span: 12,
            },
          },
          {
            title: formatMessage({ id: 'device.1097', defaultMessage: '参数模版' }),
            valueType: 'select',
            dataIndex: 'paramsTemplateId',
            formItemProps: {
              rules: [
                {
                  required: true,
                  message: formatMessage({
                    id: 'common.pleaseEnter',
                    defaultMessage: '请输入',
                  }),
                },
              ],
            },
            fieldProps: {
              options: templateOptions,
              fieldNames: { label: 'name', value: 'id' },
            },
            colProps: {
              span: 11,
            },
          },
        ],
      },
    ],
  },
];

export const schemaColumns = (templateOptions: any = []): ProFormColumnsType[] => [
  {
    title: formatMessage({ id: 'common.selectDate', defaultMessage: '选择日期' }),
    dataIndex: 'date',
    valueType: 'dateRange',
    transform: (value) => {
      return {
        startTime: value[0],
        endTime: value[1],
      };
    },
    formItemProps: {
      rules: [
        {
          required: true,
          message: formatMessage({ id: 'common.pleaseEnter', defaultMessage: '请输入' }),
        },
      ],
    },
  },
  {
    title: formatMessage({ id: 'device.1094', defaultMessage: '策略模版' }),
    dataIndex: 'templateId',
    valueType: 'select',
    formItemProps: {
      rules: [
        {
          required: true,
          message: formatMessage({ id: 'common.pleaseSelect', defaultMessage: '请选择' }),
        },
      ],
    },
    fieldProps: {
      options: templateOptions,
    },
  },
];

export const defaultValues = {
  name: '模版#',
  id: 'local',
  times: [
    {
      startTime: null,
      endTime: null,
      paramsTemplateId: '',
    },
  ],
};
export const dayOfWeekMap = {
  1: formatMessage({ id: 'date.monday', defaultMessage: '周一' }),
  2: formatMessage({ id: 'date.tuesday', defaultMessage: '周二' }),
  3: formatMessage({ id: 'date.wednesday', defaultMessage: '周三' }),
  4: formatMessage({ id: 'date.thursday', defaultMessage: '周四' }),
  5: formatMessage({ id: 'date.friday', defaultMessage: '周五' }),
  6: formatMessage({ id: 'date.saturday', defaultMessage: '周六' }),
  7: formatMessage({ id: 'date.sunday', defaultMessage: '周日' }),
};
