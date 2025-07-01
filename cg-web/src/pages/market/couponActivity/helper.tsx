/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-22 15:48:27
 * @LastEditTime: 2025-02-22 15:48:27
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/market/couponActivity/helper.tsx
 */

import { ProColumns, ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage, isEmpty } from "@/utils";
import { activityType, couponType, discountStatus, discountType, effectTime, enableStatus, gunStatus, siteDimention, useTime } from "@/utils/dict";
import { YTProColumns } from "@/components/ProTable/typing";
import { QrcodeOutlined } from "@ant-design/icons";
import { verifyPhone } from "@/utils/reg";
import Detail from "@/components/Detail";

export const columns: YTProColumns<any, any>[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '活动名称' }),
    dataIndex: 'c',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '活动承担方' }),
    dataIndex: 'd',
    valueEnum: discountStatus,
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '开始时间' }),
    dataIndex: 'e',
    valueType: 'dateRange',
    width: 150,
    ellipsis: true,
    render: (_, record) => record.createTime,
    search: {
      transform: (value) => {
        return {
          startTime: value[0],
          endTime: value[1],
        };
      },
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '结束时间' }),
    dataIndex: 'g',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '活动状态' }),
    dataIndex: 'h',
    valueEnum: discountStatus,
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '修改人' }),
    dataIndex: 'i',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '修改时间' }),
    dataIndex: 'j',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
];

export const formColumns: ProFormColumnsType[] = [
  {
    renderFormItem: () => {
      return (
        <Detail.DotLabel
          title={formatMessage({
            id: '',
            defaultMessage: '活动信息',
          })}
          className="mb0"
        />
      );
    },
    colProps: {
      span: 24,
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '活动名称' }),
    dataIndex: 'c',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '活动承担方' }),
    dataIndex: 'd1',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '活动类型' }),
    dataIndex: 'e',
    valueEnum: activityType,
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '活动时间' }),
    dataIndex: 'i',
    valueType: 'dateTimeRange',
    fieldProps: {
      className: 'w-full',
    },
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '活动规则' }),
    dataIndex: 'f',
    valueType: 'textarea',
    colProps: {
      span: 24,
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '活动介绍' }),
    dataIndex: 'f',
    valueType: 'textarea',
    colProps: {
      span: 24,
    },
  },
  {
    renderFormItem: () => {
      return (
        <Detail.DotLabel
          title={formatMessage({
            id: '',
            defaultMessage: '活动配置',
          })}
          className="mb0"
        />
      );
    },
    colProps: {
      span: 24,
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '可用维度' }),
    dataIndex: 'dimention',
    valueType: 'radio',
    valueEnum: siteDimention,
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    valueType: 'dependency',
    name: ['dimention'],
    columns: ({ dimention }) => {
      return dimention ? [
        {
          title: formatMessage({ id: '', defaultMessage: '选择站点' }),
          dataIndex: 'k',
          valueType: 'treeSelect',
          formItemProps: {
            rules: [
              {
                required: true,
              },
            ],
          },
        }
      ] : [];
    }
  },
];
