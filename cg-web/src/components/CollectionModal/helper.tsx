/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-10-17 16:03:16
 * @LastEditTime: 2025-02-05 15:25:01
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/CollectionModal/helper.tsx
 */
import { ModalProps } from 'antd';
import { DeviceModelType } from '@/types/device';
import { formatMessage } from '@/utils';
import { ProFormColumnsType } from '@ant-design/pro-components';
import moment, { Moment } from 'moment';
import Aggregation from '../Aggregation';

export type CollectionChartType = {
  title?: string;
  deviceId?: string;
  collection?: string;
  model?: DeviceModelType;
  date?: string[];
  height?: number | string;
  onLoadingChange?: (value: boolean) => void;
  containClassName?: string;
  searchParams?: Record<string, any>;
};

export type CollectionModalType = Omit<ModalProps, 'title'> & CollectionChartType;

export const columns: ProFormColumnsType[] = [
  {
    dataIndex: 'extralData',
    formItemProps: {
      labelCol: { span: 0 },
      wrapperCol: { span: 24 },
    },
    initialValue: {
      timeBucket: '',
      polymerizationType: '0',
    },
    renderFormItem: () => {
      return <Aggregation showBreakConnect={false} />;
    },
    fieldProps(form, config) {
      return {
        onChange: (value: any) => {
          const date = form?.getFieldValue?.('date');
          if (!value?.timeBucket && date[0] && date[1]) {
            if (date[1].diff(date[0], 'days') > 6) {
              form?.setFieldValue?.('date', []);
            }
          }
        },
      };
    },
  },
  {
    title: formatMessage({ id: 'common.selectDate', defaultMessage: '选择日期' }),
    dataIndex: 'date',
    valueType: 'dateRange',
    dependencies: ['extralData'],
    formItemProps: {
      labelCol: { span: 'auto' },
      rules: [{ required: true }],
    },
    initialValue: [moment(), moment()],
    transform: (value) => {
      return {
        startTime: value[0] + ' 00:00:00',
        endTime: value[1] + ' 23:59:59',
      };
    },
    fieldProps: (form) => {
      const timeBucket = form?.getFieldValue('extralData')?.timeBucket;
      const disabledDay = (timeBucket ? 31 : 7) - 1;
      return {
        onOpenChange: (openDate: boolean) => {
          if (openDate) {
            window.collectionSearchDates = [];
            window.collectionSelectDates = form?.getFieldValue?.('date');
            form?.setFieldValue?.('date', []);
          } else {
            if (window.collectionSearchDates?.[0] && window.collectionSearchDates?.[1]) {
              form?.setFieldValue?.('date', window.collectionSearchDates);
            } else {
              form?.setFieldValue?.('date', window.collectionSelectDates);
            }
          }
        },
        onCalendarChange: (val: Moment[]) => {
          window.collectionSearchDates = [...(val || [])];
        },
        disabledDate: (current: Moment) => {
          if (!window.collectionSearchDates) {
            return false;
          }
          const tooLate =
            window.collectionSearchDates?.[0] &&
            current.diff(window.collectionSearchDates?.[0], 'days') > disabledDay;
          const tooEarly =
            window.collectionSearchDates?.[1] &&
            window.collectionSearchDates?.[1].diff(current, 'days') > disabledDay;
          return !!tooEarly || !!tooLate;
        },
      };
    },
  },
];
