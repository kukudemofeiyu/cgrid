/*
 * @Description:   
 * @Author: YangJianFei
 * @Date: 2025-02-21 14:41:01
 * @LastEditTime: 2025-02-21 14:46:52
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/divider/proportion/helper.tsx
 */

import { ProColumns, ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage, isEmpty } from "@/utils";
import { enableStatus, gunStatus } from "@/utils/dict";
import { YTProColumns } from "@/components/ProTable/typing";
import { QrcodeOutlined } from "@ant-design/icons";
import { verifyPhone } from "@/utils/reg";
import { Switch } from "antd";
import { getAgentCol } from "@/utils/column";

export const columns: YTProColumns<any, any>[] = [
  getAgentCol(),
  {
    title: formatMessage({ id: '', defaultMessage: '充电桩' }),
    dataIndex: 'c',
    valueType: 'select',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '分成者' }),
    dataIndex: 'd',
    valueType: 'select',
    width: 120,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '分成比例(%)' }),
    dataIndex: 'e',
    width: 150,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '分成状态' }),
    dataIndex: 'g',
    valueEnum: enableStatus,
    width: 150,
    ellipsis: true,
    hideInSearch: true,
    renderWithEmit: (_, { status, emit }) => {
      return <Switch
        checked={status}
        onChange={() => emit('status', { status })}
      />
    },
  },
];

export const formColumns: ProFormColumnsType[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '站点' }),
    dataIndex: 'b',
    valueType: 'select',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '分成者' }),
    dataIndex: 'c1',
    valueType: 'select',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '分成比例' }),
    dataIndex: 'd',
    valueType: 'digit',
    fieldProps: {
      className: 'w-full',
      max: 100,
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