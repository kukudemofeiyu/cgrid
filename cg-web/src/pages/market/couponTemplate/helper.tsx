/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-22 15:31:09
 * @LastEditTime: 2025-02-22 16:12:13
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/market/couponTemplate/helper.tsx
 */

import { ProColumns, ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage, isEmpty } from "@/utils";
import { couponType, discountStatus, discountType, effectTime, enableStatus, gunStatus, siteDimention, useTime } from "@/utils/dict";
import { YTProColumns } from "@/components/ProTable/typing";
import { QrcodeOutlined } from "@ant-design/icons";
import { verifyPhone } from "@/utils/reg";
import Detail from "@/components/Detail";

export const columns: YTProColumns<any, any>[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '优惠卷ID' }),
    dataIndex: 'c',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '优惠卷名称' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '总张数' }),
    dataIndex: 'e',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '剩余张数' }),
    dataIndex: 'g',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '作废张数' }),
    dataIndex: 'h',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '优惠卷种类' }),
    dataIndex: 'i',
    valueEnum: couponType,
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '抵扣类型' }),
    dataIndex: 'j',
    valueEnum: discountType,
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '卡卷面额' }),
    dataIndex: 'k',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '折扣比例' }),
    dataIndex: 'l',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '使用条件' }),
    dataIndex: 'm',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '修改人' }),
    dataIndex: 'n',
    width: 120,
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
            defaultMessage: '优惠卷信息',
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
    title: formatMessage({ id: '', defaultMessage: '优惠卷名称' }),
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
    title: formatMessage({ id: '', defaultMessage: '优惠卷类型' }),
    dataIndex: 'd1',
    valueEnum: couponType,
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '优惠卷面额' }),
    dataIndex: 'e',
    valueType: 'digit',
    fieldProps: {
      className: 'w-full',
      addonAfter: formatMessage({ id: '', defaultMessage: '元' })
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
    title: formatMessage({ id: '', defaultMessage: '优惠卷数量' }),
    dataIndex: 'f',
    valueType: 'digit',
    fieldProps: {
      className: 'w-full',
      addonAfter: formatMessage({ id: '', defaultMessage: '张' })
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
    renderFormItem: () => {
      return (
        <Detail.DotLabel
          title={formatMessage({
            id: '',
            defaultMessage: '使用限制',
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
    title: formatMessage({ id: '', defaultMessage: '可抵扣费用' }),
    dataIndex: 'discount',
    valueType: 'radio',
    valueEnum: discountType,
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
    name: ['discount'],
    columns: ({ discount }) => {
      return [
        {
          title: discount ? formatMessage({ id: '', defaultMessage: '服务费' }) : formatMessage({ id: '', defaultMessage: '总费用' }),
          dataIndex: 'g',
          valueType: 'digit',
          fieldProps: {
            className: 'w-full',
            addonBefore: formatMessage({ id: '', defaultMessage: '满' }),
            addonAfter: formatMessage({ id: '', defaultMessage: '元可用' }),
          },
          formItemProps: {
            rules: [
              {
                required: true,
              },
            ],
          },
        },
      ];
    }
  },
  {
    title: formatMessage({ id: '', defaultMessage: '使用时间' }),
    dataIndex: 'useTime',
    valueType: 'radio',
    valueEnum: useTime,
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
    valueType: 'dependency',
    name: ['useTime'],
    columns: ({ useTime }) => {
      return useTime ? [
        {
          title: formatMessage({ id: '', defaultMessage: '相对时间' }),
          dataIndex: 'h',
          valueType: 'digit',
          fieldProps: {
            className: 'w-full',
            addonAfter: formatMessage({ id: '', defaultMessage: '天' }),
          },
          formItemProps: {
            rules: [
              {
                required: true,
              },
            ],
          },
        },
      ] : [
        {
          title: formatMessage({ id: '', defaultMessage: '使用期限' }),
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
      ];
    }
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
    colProps: {
      span: 24,
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
          colProps: {
            span: 24,
          },
        }
      ] : [];
    }
  },
  {
    title: formatMessage({ id: '', defaultMessage: '优惠卷使用说明' }),
    dataIndex: 'g1',
    valueType: 'textarea',
    colProps: {
      span: 24,
    },
  },
];
