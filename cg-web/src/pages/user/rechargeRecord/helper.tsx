/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-21 10:11:07
 * @LastEditTime: 2025-02-21 16:52:59
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/user/rechargeRecord/helper.tsx
 */

import { ProColumns, ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage, isEmpty } from "@/utils";
import { enableStatus, gunStatus, logSelect, rechargeChannel, rechargeTypeOption } from "@/utils/dict";
import { YTProColumns } from "@/components/ProTable/typing";
import { QrcodeOutlined } from "@ant-design/icons";
import { verifyPhone } from "@/utils/reg";

export const columns: YTProColumns<any, any>[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '卡号' }),
    dataIndex: 'c',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '用户名' }),
    dataIndex: 'd',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充值唯一编码' }),
    dataIndex: 'e',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '被充值手机号' }),
    dataIndex: 'g',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '金额' }),
    dataIndex: 'h',
    width: 100,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '时间' }),
    dataIndex: 'i',
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
    title: formatMessage({ id: '', defaultMessage: '状态' }),
    dataIndex: 'k',
    valueEnum: logSelect,
    width: 100,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '充值渠道' }),
    dataIndex: 'j',
    valueEnum: rechargeChannel,
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '类型' }),
    dataIndex: 'k1',
    valueEnum: rechargeTypeOption,
    width: 100,
    ellipsis: true,
  },
];

export const formColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '用户名' }),
    dataIndex: 'c',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
    readonly: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '金额' }),
    dataIndex: 'd',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
    readonly: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '手机号' }),
    dataIndex: 'd1',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
    readonly: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '退款金额' }),
    dataIndex: 'd2',
    valueType: 'money',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
];
