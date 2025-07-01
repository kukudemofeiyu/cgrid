/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2025-02-22 16:49:50
 * @LastEditTime: 2025-02-22 16:49:50
 * @LastEditors: YangJianFei
 * @FilePath: /huizhi-web/src/pages/article/helper.tsx
 */

import { ProColumns, ProFormColumnsType } from "@ant-design/pro-components";
import { formatMessage, isEmpty } from "@/utils";
import { articleType, enableStatus, gunStatus, siteDimention } from "@/utils/dict";
import { YTProColumns } from "@/components/ProTable/typing";
import { QrcodeOutlined } from "@ant-design/icons";
import { verifyPhone } from "@/utils/reg";
import { Switch } from "antd";

export const columns: YTProColumns<any, any>[] = [
  {
    title: formatMessage({ id: '', defaultMessage: '标题' }),
    dataIndex: 'c',
    width: 150,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '文章键名' }),
    dataIndex: 'd',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '分类' }),
    dataIndex: 'e',
    valueEnum: articleType,
    width: 100,
    ellipsis: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '排序' }),
    dataIndex: 'g',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '备注' }),
    dataIndex: 'h',
    width: 120,
    ellipsis: true,
    hideInSearch: true,
  },
  {
    title: formatMessage({ id: '', defaultMessage: '状态' }),
    dataIndex: 'i',
    width: 100,
    ellipsis: true,
    hideInSearch: true,
    renderWithEmit: (_, { status, emit }) => {
      return <Switch
        checked={status}
        onChange={() => emit('status', { status })}
      />
    },
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
    title: formatMessage({ id: '', defaultMessage: '文章标题' }),
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
    title: formatMessage({ id: '', defaultMessage: '文章键名' }),
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
    title: formatMessage({ id: '', defaultMessage: '分类' }),
    dataIndex: 'd',
    valueEnum: articleType,
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
  },
  {
    title: formatMessage({ id: '', defaultMessage: '排序' }),
    dataIndex: 'd',
  },
  {
    title: formatMessage({ id: '', defaultMessage: '备注' }),
    dataIndex: 'd',
  },
  {
    title: formatMessage({ id: '', defaultMessage: '文章内容' }),
    dataIndex: 'd',
    valueType: 'textarea',
    formItemProps: {
      rules: [
        {
          required: true,
        },
      ],
    },
    colProps: {
      span: 24,
    }
  },
];
